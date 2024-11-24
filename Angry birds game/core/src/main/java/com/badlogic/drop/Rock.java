package com.badlogic.drop;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Rock extends Material{

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






    BodyDef bdef;
    FixtureDef fdef;
    PolygonShape shape;
    CircleShape cshape;
    Body body;


    public Rock(float health){
        super(health);

        bdef= new BodyDef();
        fdef= new FixtureDef();
        shape= new PolygonShape();
        cshape= new CircleShape();
    }

    @Override
    public void takeDamage(float damage) {

    }

    public static void createrock(TiledMap map, Level level) {
        MapLayer groupLayer = map.getLayers().get("objects");
        if (groupLayer != null) {
            if (groupLayer instanceof MapGroupLayer) {
                MapGroupLayer group = (MapGroupLayer) groupLayer;
                MapLayer rockLayer = group.getLayers().get("rock");
                if (rockLayer != null) {
                    for (MapObject obj : rockLayer.getObjects()) {
                        if (obj instanceof TextureMapObject) {
                            TextureMapObject tmo = (TextureMapObject) obj;
                            boolean shapePresent= tmo.getProperties().containsKey("shape");
                            if(!shapePresent){
                                float width = (float) tmo.getProperties().get("width", Float.class);
                                float height = (float) tmo.getProperties().get("height", Float.class);
                                float x = tmo.getX();
                                float y = tmo.getY();



                                Rock rock=new Rock(5);

                                rock.width=width;
                                rock.height=height;
                                rock.texture=tmo.getTextureRegion();

                                rock.bdef.type = BodyDef.BodyType.DynamicBody;
                                rock.bdef.position.set(x + width / 2, y + height / 2);
                                rock.body = level.world.createBody(rock.bdef);

                                rock.shape.setAsBox(width/2, height/2);
                                rock.fdef.shape = rock.shape;
                                rock.fdef.density = 1.0f;
                                rock.fdef.friction = 0.5f;
                                rock.fdef.restitution = 0.1f;

                                rock.body.createFixture(rock.fdef);
                                level.blocks.add(rock);

                                rock.shape.dispose();

                            }
                            else{
                                String shapeType= (String) tmo.getProperties().get("shape");
                                if(shapeType.equals("circle")){
                                    float width= (float) tmo.getProperties().get("width", Float.class);
                                    float height= (float) tmo.getProperties().get("height", Float.class);
                                    float x = tmo.getX();
                                    float y = tmo.getY();

                                    Rock rock = new Rock(5);

                                    rock.width=width;
                                    rock.height=height;
                                    rock.x=x;
                                    rock.y=y;
                                    rock.texture=tmo.getTextureRegion();

                                    rock.bdef.type = BodyDef.BodyType.DynamicBody;
                                    rock.bdef.position.set(x + width / 2, y + height / 2);
                                    rock.body = level.world.createBody(rock.bdef);
                                    rock.cshape.setRadius(width / 2);
                                    rock.fdef.shape = rock.cshape;
                                    rock.body.createFixture(rock.fdef);
                                    level.blocks.add(rock);
                                    rock.cshape.dispose();


                                }
                                else if(shapeType.equals("triangle")){
                                    float width= (float) tmo.getProperties().get("width", Float.class);
                                    float height= (float) tmo.getProperties().get("height", Float.class);
                                    float x = tmo.getX();
                                    float y = tmo.getY();

                                    Rock rock = new Rock(5);
                                    float[] vertices= new float[]{
                                        -width/2,0,
                                        width/2,0,
                                        0,height

                                    };
                                    rock.shape.set(vertices);
                                    rock.texture=tmo.getTextureRegion();

                                    rock.bdef.type = BodyDef.BodyType.DynamicBody;
                                    rock.bdef.position.set(x + width / 2, y + height / 2);
                                    rock.body = level.world.createBody(rock.bdef);
                                    rock.fdef.shape = rock.shape;
                                    rock.body.createFixture(rock.fdef);
                                    level.blocks.add(rock);
                                    rock.shape.dispose();



                                }
                            }

                        }
                    }

                }
            }
        }


    }
}
