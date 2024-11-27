package com.badlogic.drop;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Ground {

    private float width;
    private float height;
    private TextureRegion texture;

    int damage=1;

    BodyDef bdef;
    FixtureDef fdef;
    PolygonShape shape;
    CircleShape cshape;
    Body body;

    public Ground() {
        bdef = new BodyDef();
        fdef = new FixtureDef();
        shape = new PolygonShape();
    }

    public static void createGround(TiledMap map, Level level){
        MapLayer groupLayer = map.getLayers().get("objects");
        if (groupLayer != null) {
            if (groupLayer instanceof MapGroupLayer) {
                MapGroupLayer group = (MapGroupLayer) groupLayer;
                MapLayer groundLayer = group.getLayers().get("ground");
                if (groundLayer != null) {
                    for (MapObject obj : groundLayer.getObjects()) {
                        if (obj instanceof RectangleMapObject) {
                            RectangleMapObject rmo = (RectangleMapObject) obj;



                            float width = (float) rmo.getProperties().get("width", Float.class);
                            float height = (float) rmo.getProperties().get("height", Float.class);
                            float x = rmo.getRectangle().x;
                            float y = rmo.getRectangle().y;



                            Ground ground = new Ground();
                            ground.width=width;
                            ground.height=height;



                            ground.bdef.type = BodyDef.BodyType.StaticBody;
                            ground.bdef.position.set((x + width / 2)/Main.PPM, (y + height / 2)/Main.PPM);
                            ground.body = level.world.createBody(ground.bdef);

                            ground.shape.setAsBox((width/2)/Main.PPM, (height/2)/Main.PPM);
                            ground.fdef.shape = ground.shape;
                            // ground.fdef.density=100f;
                            // ground.fdef.friction=0.8f;
                            ground.body.createFixture(ground.fdef).setUserData(ground);


                            ground.shape.dispose();





                        }
                    }

                }
            }
        }


    }




}
