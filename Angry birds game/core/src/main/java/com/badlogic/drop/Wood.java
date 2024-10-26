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

public class Wood extends Material{
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
    Body body;

    public Wood(float health){
        super(health);
        bdef = new BodyDef();
        fdef = new FixtureDef();
        shape= new PolygonShape();

    }

    @Override
    public void takeDamage(float damage) {

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

                            float width = (float) tmo.getProperties().get("width", Float.class);
                            float height = (float) tmo.getProperties().get("height", Float.class);
                            float x = tmo.getX();
                            float y = tmo.getY();



                            Wood wood = new Wood(5);
                            wood.width=width;
                            wood.height=height;

                            wood.texture=tmo.getTextureRegion();

                            wood.bdef.type = BodyDef.BodyType.DynamicBody;
                            wood.bdef.position.set(x + width / 2, y + height / 2);
                            wood.body = level.world.createBody(wood.bdef);

                            wood.shape.setAsBox(width/2, height/2);
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
