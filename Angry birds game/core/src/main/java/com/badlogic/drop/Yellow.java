package com.badlogic.drop;

import com.badlogic.gdx.graphics.Color;
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


public class Yellow extends Bird {
    public static final int givenDamage=3;

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

    // BodyDef bdef;
    // FixtureDef fdef;
    // CircleShape cshape;
    // Body body;

    public Yellow(int damage) {
        super(damage, Color.YELLOW);

        bdef = new BodyDef();
        fdef = new FixtureDef();
        cshape = new CircleShape();


    }
    public void specialAbility(Level level){
        Body body= this.body;
        float speedIncrease = 3.5f;


        float currentVelocityX = body.getLinearVelocity().x;
        float currentVelocityY = body.getLinearVelocity().y;

        body.applyLinearImpulse(currentVelocityX * speedIncrease, currentVelocityY * speedIncrease, body.getWorldCenter().x, body.getWorldCenter().y, true);
    }

    public static void createYellow(TiledMap map, Level level){
        MapLayer groupLayer=map.getLayers().get("objects");
        if(groupLayer != null){
            if(groupLayer instanceof MapGroupLayer){
                MapGroupLayer group=(MapGroupLayer)groupLayer;
                MapLayer YellowLayer =group.getLayers().get("yellow");
                if(YellowLayer != null){
                    for(MapObject obj: YellowLayer.getObjects()){
                        if(obj instanceof TextureMapObject){
                            TextureMapObject tmo=(TextureMapObject)obj;

                            float width = (float) tmo.getProperties().get("width", Float.class);
                            float height = (float) tmo.getProperties().get("height", Float.class);
                            float x = tmo.getX();
                            float y = tmo.getY();

                            Yellow yellow=new Yellow(givenDamage);


                            yellow.width=width;
                            yellow.height=height;
                            yellow.x=x;
                            yellow.y=y;
                            yellow.texture=tmo.getTextureRegion();


                            //box2d-
                            yellow.bdef.type=BodyDef.BodyType.StaticBody;
                            yellow.bdef.position.set((x+width/2)/Main.PPM, (y+height/2)/Main.PPM);
                            yellow.body=level.world.createBody(yellow.bdef);
                            yellow.cshape.setRadius((width/2)/Main.PPM);
                            yellow.fdef.shape= yellow.cshape;
                            yellow.body.createFixture(yellow.fdef).setUserData(yellow);
                            level.birds.add(yellow);
                            yellow.cshape.dispose();
                        }
                    }

                }
            }
        }


    }



}
