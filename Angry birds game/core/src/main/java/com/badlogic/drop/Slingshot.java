package com.badlogic.drop;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

public class Slingshot extends InputAdapter {
    public  Bird activeBird=null;

    private World world;
    private Body bird;
    private Vector2 frontAnchor;
    private Vector2 backAnchor;
    private boolean isDragging;
    private float maxStretch = 200f / Main.PPM;
    private ShapeRenderer shapeRenderer;
    private List<Vector2> trajectoryPoints;
    private float impulseStrength = 15f;
    private OrthographicCamera camera;
    private static final float GRAVITY = -9.8f;
    private boolean showTrajectory;

    private Vector2 currPosition;
    private static final float BAND_WIDTH = 20f;
    private static final Color BAND_COLOR = new Color(0.4f, 0.2f, 0.1f, 0.8f); // Brown-ish color
    private static final int BAND_SEGMENTS = 15;
    private boolean showBands;

    public Slingshot(World world, TiledMap map, OrthographicCamera camera) {
        this.world = world;
        this.shapeRenderer = new ShapeRenderer();
        this.trajectoryPoints = new ArrayList<>();
        this.camera = camera;
        this.showTrajectory = false;
        extractAnchorPoints(map);
    }

    private void extractAnchorPoints(TiledMap map) {
        MapLayer objectsLayer = map.getLayers().get("objects");
        if (objectsLayer instanceof MapGroupLayer) {
            MapGroupLayer groupLayer = (MapGroupLayer) objectsLayer;
            MapLayer slingshotLayer = groupLayer.getLayers().get("slingshot");
            if (slingshotLayer != null) {
                for (MapObject object : slingshotLayer.getObjects()) {
                    float x = (float) object.getProperties().get("x", Float.class) / Main.PPM;
                    float y = (float) object.getProperties().get("y", Float.class) / Main.PPM;
                    if ("anchor_back".equals(object.getName())) {
                        backAnchor = new Vector2(x, y);
                    } else if ("anchor_front".equals(object.getName())) {
                        frontAnchor = new Vector2(x, y);
                    }
                }
            } else {
                System.err.println("Error: Slingshot layer is missing!");
            }
        } else {
            System.err.println("Error: Objects layer is missing or not a MapGroupLayer!");
        }
    }

    //    public void loadBird(Body bird) {
//        this.bird = bird;
//        Vector2 midpoint = new Vector2(
//            (frontAnchor.x + backAnchor.x) / 2,
//            (frontAnchor.y + backAnchor.y) / 2
//        );
//        bird.setTransform(midpoint, 0);
//        bird.setType(BodyDef.BodyType.StaticBody);
//    }
    public void loadBird(Body bird) {
        this.bird = bird;
        Vector2 midpoint = new Vector2(
            (frontAnchor.x + backAnchor.x) / 2,
            (frontAnchor.y + backAnchor.y) / 2
        );

        //jump vector-
        Vector2 currentPos = bird.getPosition();
        Vector2 jumpVector = midpoint.cpy().sub(currentPos);

        // jump height and trajactory-
        float jumpHeight = Math.abs(jumpVector.y) * 1.5f;  // Add some extra height
        Vector2 jumpImpulse = new Vector2(
            jumpVector.x * 1.8f,  // Horizontal component
            jumpHeight * 3f     // Vertical component for arc
        );


        bird.setType(BodyDef.BodyType.DynamicBody);


        bird.applyLinearImpulse(
            jumpImpulse,
            bird.getWorldCenter(),
            true
        );


        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                bird.setLinearVelocity(0, 0);  // Stop movement
                bird.setTransform(midpoint, 0);  // Snap to midpoint
                bird.setType(BodyDef.BodyType.StaticBody);
            }
        }, 0.6f);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (bird != null) {
            Vector3 worldCoords = new Vector3(screenX, screenY, 0);
            camera.unproject(worldCoords);
            Vector2 touchPos = new Vector2(worldCoords.x / Main.PPM, worldCoords.y / Main.PPM);
            if (touchPos.dst(bird.getPosition()) < 50f / Main.PPM) {
                isDragging = true;
                showTrajectory = true;
                showBands = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging && bird != null) {
            Vector3 worldCoords = new Vector3(screenX, screenY, 0);
            camera.unproject(worldCoords);
            Vector2 touchPos = new Vector2(worldCoords.x / Main.PPM, worldCoords.y / Main.PPM);

            Vector2 dragDirection = touchPos.cpy().sub(frontAnchor);
            float stretch = Math.min(dragDirection.len(), maxStretch);
            dragDirection.nor().scl(stretch);

            Vector2 newBirdPos = frontAnchor.cpy().add(dragDirection);
            currPosition=newBirdPos;
            bird.setTransform(newBirdPos, 0);

            //impulse based on stretch-
            float impulseScale = (stretch / maxStretch) * impulseStrength;
            Vector2 launchImpulse = frontAnchor.cpy().sub(newBirdPos).nor().scl(impulseScale);
            calculateTrajectoryWithPhysics(newBirdPos, launchImpulse);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (isDragging) {
            isDragging = false;
            showTrajectory = false; //to hide trajactory
            showBands=false;
//            System.out.println("Bird released");

            releaseBird();
            return true;
        }
        return false;
    }

    private void releaseBird() {
        if (bird != null) {
            Vector2 birdPos = bird.getPosition();
            Vector2 direction = frontAnchor.cpy().sub(birdPos);
            float stretch = direction.len();
            float impulseScale = (stretch / maxStretch) * impulseStrength;

            //applying impulse
            Vector2 releaseImpulse = direction.nor().scl(impulseScale);

            bird.setType(BodyDef.BodyType.DynamicBody);

            bird.applyLinearImpulse(
                releaseImpulse,
                bird.getWorldCenter(),
                true
            );
            trajectoryPoints.clear();

            bird=null;
        }
    }

    private void calculateTrajectoryWithPhysics(Vector2 startPosition, Vector2 impulse) {
        trajectoryPoints.clear();

        //temporary simulation of world for exact calcualtions-
        World tempWorld = new World(new Vector2(0, GRAVITY), true);

        //temp bird-
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startPosition);

        Body tempBody = tempWorld.createBody(bodyDef);
        // Copying properties-
        if(bird!=null){
            tempBody.createFixture(bird.getFixtureList().get(0).getShape(),
                bird.getFixtureList().get(0).getDensity());
            tempBody.setTransform(startPosition, 0);


            tempBody.applyLinearImpulse(
                impulse,
                tempBody.getWorldCenter(),
                true
            );

        }

        float timeStep = 1/60f;

        int velocityIterations = 6;
        int positionIterations = 2;

        trajectoryPoints.add(new Vector2(startPosition));


        for (int i = 0; i < 120; i++) {
            tempWorld.step(timeStep, velocityIterations, positionIterations);
            Vector2 position = tempBody.getPosition();
            trajectoryPoints.add(new Vector2(position));
        }


        tempWorld.dispose();
    }

    public void render(SpriteBatch batch) {

        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);

        //drawing rubber bands-
        if (bird != null && showBands && isDragging) {
            Vector2 birdPos = bird.getPosition().cpy().scl(Main.PPM);
            Vector2 frontAnchorScreen = frontAnchor.cpy().scl(Main.PPM);
            Vector2 backAnchorScreen = backAnchor.cpy().scl(Main.PPM);


            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


            Vector2 midPoint = new Vector2(
                (frontAnchorScreen.x + backAnchorScreen.x) / 2,
                (frontAnchorScreen.y + backAnchorScreen.y) / 2
            );

            Color bandColor = new Color(0.7f, 0.72f, 0.2f, 0.6f);

            //front rubber-
            drawRubberBand(frontAnchorScreen, birdPos, midPoint, bandColor);

            //baccK rubber-
            drawRubberBand(backAnchorScreen, birdPos, midPoint, bandColor);

            shapeRenderer.end();
        }

        //trajactory-
        if (showTrajectory && !trajectoryPoints.isEmpty()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            for (int i = 14; i < trajectoryPoints.size(); i = i + 4) {
                Vector2 point = trajectoryPoints.get(i).cpy().scl(Main.PPM);
                float alpha = 1f - (float) i / trajectoryPoints.size();




                // Outer shadow circle
                shapeRenderer.setColor(0f, 0f, 0f, alpha * 0.3f);
                shapeRenderer.circle(point.x + 2, point.y - 2, 10-(i/(float)10) * 1.1f);

                // Main colored circle
                shapeRenderer.setColor(activeBird.color.r,
                    activeBird.color.g,
                    activeBird.color.b,
                    alpha);
                shapeRenderer.circle(point.x, point.y, 10-(i/(float)10));

            }
//            for (int i = 10; i < trajectoryPoints.size(); i = i + 4) {
//                Vector2 point = trajectoryPoints.get(i).cpy().scl(Main.PPM);
//                float alpha = 1f - (float) i / trajectoryPoints.size();
////                shapeRenderer.setColor(1, 0, 0, alpha);
//                shapeRenderer.setColor(activeBird.color);
//                shapeRenderer.circle(point.x, point.y, 12-(i/(float)10));
//
//            }

            shapeRenderer.end();
        }


        batch.begin();
    }

    private void drawRubberBand(Vector2 anchor, Vector2 birdPos, Vector2 midPoint, Color color) {
        // stretch based on distance-
        float stretch = anchor.dst(birdPos) / Main.PPM;
        float stretchFactor = Math.min(1f, stretch / maxStretch);
        float currentWidth = BAND_WIDTH * (1f - (stretchFactor * 0.5f)); //band thinning on stretch

        // curve-
        for (int i = 0; i < BAND_SEGMENTS; i++) {
            float t = (float) i / (BAND_SEGMENTS - 1);
            float nextT = (float) (i + 1) / (BAND_SEGMENTS - 1);


            Vector2 curr = getBezierPoint(anchor, midPoint, birdPos, t);
            Vector2 next = getBezierPoint(anchor, midPoint, birdPos, nextT);


            Color bandColor = color.cpy();
            bandColor.a = color.a * (1f - (stretchFactor * 0.2f));
            shapeRenderer.setColor(bandColor);


            shapeRenderer.rectLine(curr.x, curr.y, next.x, next.y, currentWidth);
        }
    }

    private Vector2 getBezierPoint(Vector2 p0, Vector2 p1, Vector2 p2, float t) {
        float oneMinusT = 1f - t;
        float oneMinusTSquared = oneMinusT * oneMinusT;


        float tSquared = t * t;








        return new Vector2(
            oneMinusTSquared * p0.x + 2f * oneMinusT * t * p1.x + tSquared * p2.x,
            oneMinusTSquared * p0.y + 2f * oneMinusT * t * p1.y + tSquared * p2.y
        );
    }
//    public void resize(OrthographicCamera camera){
//        this.camera=camera;

//    }

}
