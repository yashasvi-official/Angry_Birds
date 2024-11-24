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

public class Glass extends Material{

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


    public Glass(float health){
        super(health);

        bdef= new BodyDef();
        fdef= new FixtureDef();
        shape= new PolygonShape();
        cshape= new CircleShape();

    }

    @Override
    public void takeDamage(float damage) {

    }

    public static void createGlass(TiledMap map, Level level) {
        MapLayer groupLayer = map.getLayers().get("objects");
        if (groupLayer != null) {
            if (groupLayer instanceof MapGroupLayer) {
                MapGroupLayer group = (MapGroupLayer) groupLayer;
                MapLayer GlassLayer = group.getLayers().get("glass");
                if (GlassLayer != null) {
                    for (MapObject obj : GlassLayer.getObjects()) {
                        if (obj instanceof TextureMapObject) {
                            TextureMapObject tmo = (TextureMapObject) obj;

                            boolean shapePresent= tmo.getProperties().containsKey("shape");
                            if(!shapePresent){
                                float width = (float) tmo.getProperties().get("width", Float.class);
                                float height = (float) tmo.getProperties().get("height", Float.class);
                                float x = tmo.getX();
                                float y = tmo.getY();



                                Glass glass=new Glass(5);

                                glass.width=width;
                                glass.height=height;
                                glass.texture=tmo.getTextureRegion();

                                glass.bdef.type = BodyDef.BodyType.DynamicBody;
                                glass.bdef.position.set(x + width / 2, y + height / 2);
                                glass.body = level.world.createBody(glass.bdef);

                                glass.shape.setAsBox(width/2, height/2);
                                glass.fdef.shape = glass.shape;
                                glass.body.createFixture(glass.fdef);

                                level.blocks.add(glass);

                                glass.shape.dispose();

                            }
                            else {
                                String shapeType= (String) tmo.getProperties().get("shape");
                                // String shapeType=(String) tmo.getProperties().get("shape");

                                if(shapeType.equals("circle")){
                                    float width= (float) tmo.getProperties().get("width", Float.class);
                                    float height= (float) tmo.getProperties().get("height", Float.class);
                                    float x = tmo.getX();
                                    float y = tmo.getY();

                                    Glass glass = new Glass(5);

                                    glass.width=width;
                                    glass.height=height;
                                    glass.x=x;
                                    glass.y=y;
                                    glass.texture=tmo.getTextureRegion();

                                    glass.bdef.type = BodyDef.BodyType.DynamicBody;
                                    glass.bdef.position.set(x + width / 2, y + height / 2);
                                    glass.body = level.world.createBody(glass.bdef);
                                    glass.cshape.setRadius(width / 2);
                                    glass.fdef.shape = glass.cshape;
                                    glass.body.createFixture(glass.fdef);
                                    level.blocks.add(glass);
                                    glass.cshape.dispose();


                                }
                                else if(shapeType.equals("triangle")){
                                    float width= (float) tmo.getProperties().get("width", Float.class);
                                    float height= (float) tmo.getProperties().get("height", Float.class);
                                    float x = tmo.getX();
                                    float y = tmo.getY();

                                    Glass glass = new Glass(5);
                                    float[] vertices= new float[]{
                                        0, 0,                    // Bottom-left
                                        width, 0,                // Bottom-right
                                        width / 2, height

                                    };
                                    glass.shape.set(vertices);
                                    glass.width=width;
                                    glass.height=height;
                                    glass.x=x;
                                    glass.y=y;
                                    glass.texture=tmo.getTextureRegion();

                                    glass.bdef.type = BodyDef.BodyType.DynamicBody;
                                    glass.bdef.position.set(x, y);
                                    glass.body = level.world.createBody(glass.bdef);
                                    glass.fdef.shape = glass.shape;
                                    glass.body.createFixture(glass.fdef);
                                    level.blocks.add(glass);
                                    glass.shape.dispose();



                                }
                            }

                        }
                    }

                }
            }
        }


    }
}
