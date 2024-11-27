package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
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

public class Wood extends Material{


    //    public boolean toDestroy = false;
    public static final int woodHealth=2;
    public static final int woodDamage=2;

    private TextureRegion texture;

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

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    private float height;
    private float width;
    private float x;
    private float y;




    BodyDef bdef;
    FixtureDef fdef;
    PolygonShape shape;
    CircleShape cshape;
    Body body;

    public Wood(int health,int woodDamage){

        super(health,woodDamage);

        bdef = new BodyDef();
        fdef = new FixtureDef();
        shape= new PolygonShape();
        cshape= new CircleShape();


    }

    @Override
    public void takeDamage(float damage) {
        System.out.println("Wood block hit");
        this.health-=damage;
        if(this.health<=0){
            this.toDestroy = true;
            System.out.println("Wood block destroyed");
        }




    }
    public static void createWood(TiledMap map, Level level) {
        MapLayer groupLayer = map.getLayers().get("objects");
        if (groupLayer != null) {
            if (groupLayer instanceof MapGroupLayer) {
                MapGroupLayer group = (MapGroupLayer) groupLayer;
                MapLayer WoodLayer = group.getLayers().get("wood");
                if (WoodLayer != null) {
                    for (MapObject obj : WoodLayer.getObjects()) {
                        if (obj instanceof TextureMapObject) {
                            TextureMapObject tmo = (TextureMapObject) obj;

                            boolean shapePresent=tmo.getProperties().containsKey("shape");
                            if(!shapePresent){
                                float width = (float) tmo.getProperties().get("width", Float.class);
                                float height = (float) tmo.getProperties().get("height", Float.class);
                                float x = tmo.getX();
                                float y = tmo.getY();



                                Wood wood = new Wood(woodHealth,woodDamage);

                                wood.width=width;
                                wood.height=height;

                                wood.texture=tmo.getTextureRegion();

                                wood.bdef.type = BodyDef.BodyType.DynamicBody;
                                wood.bdef.position.set((x + width / 2)/Main.PPM, (y + height / 2)/Main.PPM);
                                wood.body = level.world.createBody(wood.bdef);

                                wood.shape.setAsBox((width/2)/Main.PPM, (height/2)/Main.PPM);
                                wood.fdef.shape = wood.shape;
                                wood.fdef.density=1f;
                                wood.fdef.friction=0.5f;
                                wood.fdef.restitution=0.1f;
                                wood.body.createFixture(wood.fdef).setUserData(wood);

                                level.blocks.add(wood);
                                wood.shape.dispose();

                            }
                            else{
                                String shapeType=(String) tmo.getProperties().get("shape");
                                if(shapeType.equals("circle")){
                                    float width= (float) tmo.getProperties().get("width", Float.class);
                                    float height= (float) tmo.getProperties().get("height", Float.class);
                                    float x = tmo.getX();
                                    float y = tmo.getY();

                                    Wood wood = new Wood(woodHealth,woodDamage);

                                    wood.width=width;
                                    wood.height=height;
                                    wood.x=x;
                                    wood.y=y;
                                    wood.texture=tmo.getTextureRegion();

                                    wood.bdef.type = BodyDef.BodyType.DynamicBody;
                                    wood.bdef.position.set((x + width / 2)/Main.PPM, (y + height / 2)/Main.PPM);
                                    wood.body = level.world.createBody(wood.bdef);
                                    wood.cshape.setRadius((width / 2)/Main.PPM);
                                    wood.fdef.shape = wood.cshape;

                                    wood.fdef.density=1f;
                                    wood.fdef.friction=0.5f;
                                    wood.fdef.restitution=0.1f;

                                    wood.body.createFixture(wood.fdef).setUserData(wood);
                                    level.blocks.add(wood);
                                    wood.cshape.dispose();


                                }
                                else if(shapeType.equals("triangle")){
                                    float width= (float) tmo.getProperties().get("width", Float.class);
                                    float height= (float) tmo.getProperties().get("height", Float.class);
                                    float x = tmo.getX();
                                    float y = tmo.getY();

                                    Wood wood = new Wood(woodHealth,woodDamage);
                                    float[] vertices= new float[]{
                                        -width/2,0,
                                        width/2,0,
                                        0,height

                                    };
                                    wood.shape.set(vertices);
                                    wood.texture=tmo.getTextureRegion();

                                    wood.bdef.type = BodyDef.BodyType.DynamicBody;
                                    wood.bdef.position.set(x + width / 2, y + height / 2);
                                    wood.body = level.world.createBody(wood.bdef);
                                    wood.fdef.shape = wood.shape;
                                    wood.body.createFixture(wood.fdef);
                                    level.blocks.add(wood);
                                    wood.shape.dispose();



                                }

                            }


                        }
                    }

                }
            }
        }


    }
}
