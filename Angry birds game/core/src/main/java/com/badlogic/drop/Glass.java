package com.badlogic.drop;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Glass extends Material{

    private TextureRegion texture;
    private float height;
    private float width;

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
    Body body;

    public Glass(float health){
        super(health);

        bdef= new BodyDef();
        fdef= new FixtureDef();
        shape= new PolygonShape();

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
                    }

                }
            }
        }


    }
}
