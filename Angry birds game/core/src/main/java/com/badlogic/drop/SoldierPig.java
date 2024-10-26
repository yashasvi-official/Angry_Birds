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

public class SoldierPig extends Pig{

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


    BodyDef bdef;
    FixtureDef fdef;
    CircleShape cshape;
    Body body;


    public SoldierPig(float health) {
        super(health);
        bdef = new BodyDef();
        fdef = new FixtureDef();
        cshape = new CircleShape();



    }

    @Override
    public void takeDamage(int damage) {

    }
    public static void createSoldierPig(TiledMap map, Level level) {
        MapLayer groupLayer=map.getLayers().get("objects");
        if(groupLayer != null) {
            if (groupLayer instanceof MapGroupLayer) {
                MapGroupLayer group = (MapGroupLayer) groupLayer;
                MapLayer soldierPigLayer = group.getLayers().get("soldier pig");
                if (soldierPigLayer != null) {
                    for (MapObject obj : soldierPigLayer.getObjects()) {
                        if (obj instanceof TextureMapObject) {
                            TextureMapObject tmo = (TextureMapObject) obj;

                            float width = (float) tmo.getProperties().get("width", Float.class);
                            float height = (float) tmo.getProperties().get("height", Float.class);
                            float x = tmo.getX();
                            float y = tmo.getY();

                            SoldierPig pig = new SoldierPig(5);
                            pig.width=width;
                            pig.height=height;
                            pig.x=x;
                            pig.y=y;
                            pig.texture=tmo.getTextureRegion();

                            pig.bdef.type = BodyDef.BodyType.DynamicBody;
                            pig.bdef.position.set(x + width / 2, y + height / 2);
                            pig.body = level.world.createBody(pig.bdef);
                            pig.cshape.setRadius(width / 2);
                            pig.fdef.shape = pig.cshape;
                            pig.body.createFixture(pig.fdef);
                            level.pigs.add(pig);
                            pig.cshape.dispose();
                        }
                    }

                }
            }

        }
    }
}
