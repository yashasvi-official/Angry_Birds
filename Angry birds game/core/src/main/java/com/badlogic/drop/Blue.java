package com.badlogic.drop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import java.util.ArrayList;


public class Blue extends Bird {
    public static final int givenDamage=4;
    public  boolean abilityUsed=false;


    private TextureRegion texture;
    private float height;
    private float width;
    private float x;
    private float y;
    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public ArrayList<Blue> splitBirds=new ArrayList<Blue>();

    // BodyDef bdef;
    // FixtureDef fdef;
    // CircleShape cshape;
    // Body body;

    public Blue(int damage) {
        super(damage, Color.BLUE);


        bdef = new BodyDef();
        fdef = new FixtureDef();
        cshape = new CircleShape();

    }
    public void specialAbility(Level level) {
        if(abilityUsed){
            return;
        }

        abilityUsed=true;
        Vector2 originalVelocity = this.body.getLinearVelocity();
        float xVelocity = originalVelocity.x; // Maintain the original x-velocity
        float yVelocity = originalVelocity.y;


        Blue bird1 = new Blue(givenDamage);
        Blue bird2 = new Blue(givenDamage);


        bird1.width = this.width / 1.5f;
        bird1.height = this.height / 1.5f;
        bird1.texture = this.texture;

        bird2.width = this.width / 1.5f;
        bird2.height = this.height / 1.5f;
        bird2.texture = this.texture;



        Vector2 position = this.body.getPosition();
        bird1.x = position.x;
        bird1.y = position.y- 0.5f / Main.PPM;
        bird2.x = position.x;
        bird2.y = position.y- 0.5f / Main.PPM;


        createBodyForBird(bird1, level, xVelocity+2f, 5f+yVelocity); // Launch bird1 to the left
        createBodyForBird(bird2, level, xVelocity+2f, yVelocity-1f);  // Launch bird2 to the right


//        level.birds.add(bird1);
//        level.birds.add(bird2);

        this.width=this.width/1.5f;
        this.height=this.height/1.5f;
        this.cshape.setRadius(this.width/2);
        this.body.setLinearVelocity(xVelocity+2f,yVelocity);



    }

    private void createBodyForBird(Blue bird, Level level, float velocityX, float velocityY) {
        bird.bdef.type = BodyDef.BodyType.DynamicBody;
        bird.bdef.position.set(bird.x, bird.y);

        bird.body = level.world.createBody(bird.bdef);

        bird.cshape.setRadius((bird.width / 2) / Main.PPM);
        bird.fdef.shape = bird.cshape;

        bird.body.createFixture(bird.fdef).setUserData(bird);

        //applying velocity-
        bird.body.setLinearVelocity(velocityX, velocityY);
        this.splitBirds.add(bird);

        bird.cshape.dispose();
    }

    public static void createBlue(TiledMap map, Level level){
        MapLayer groupLayer=map.getLayers().get("objects");
        if(groupLayer != null){
            if(groupLayer instanceof MapGroupLayer){
                MapGroupLayer group=(MapGroupLayer)groupLayer;
                MapLayer BlueLayer =group.getLayers().get("blue");
                if(BlueLayer != null){
                    for(MapObject obj: BlueLayer.getObjects()){
                        if(obj instanceof TextureMapObject){
                            TextureMapObject tmo=(TextureMapObject)obj;

                            float width = (float) tmo.getProperties().get("width", Float.class);
                            float height = (float) tmo.getProperties().get("height", Float.class);
                            float x = tmo.getX();
                            float y = tmo.getY();

                            Blue blue=new  Blue(givenDamage);


                            blue.width=width;
                            blue.height=height;
                            blue.x=x;
                            blue.y=y;
                            blue.texture=tmo.getTextureRegion();


                            //box2d-
                            blue.bdef.type=BodyDef.BodyType.StaticBody;
                            blue.bdef.position.set((x+width/2)/Main.PPM, (y+height/2)/Main.PPM);
                            blue.body=level.world.createBody(blue.bdef);
                            blue.cshape.setRadius((width/2)/Main.PPM);
                            blue.fdef.shape= blue.cshape;

                            blue.body.createFixture(blue.fdef).setUserData(blue);

                            level.birds.add(blue);


                            blue.cshape.dispose();
                        }
                    }

                }
            }
        }


    }



}
