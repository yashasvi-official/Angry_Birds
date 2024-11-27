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

import java.util.ArrayList;
import java.util.List;

public class SmallPig extends Pig{
    public static final int smallHealth=4;

    public static List<SmallPig> smallPigs = new ArrayList<SmallPig>();


    private TextureRegion texture;

    public static List<SmallPig> getSmallPigs() {
        return smallPigs;
    }

    public static void setSmallPigs(List<SmallPig> smallPigs) {
        SmallPig.smallPigs = smallPigs;
    }

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

    public void setY(float y) {
        this.y = y;
    }

    private float height;
    private float width;
    public float x;
    public float y;

    BodyDef bdef;
    FixtureDef fdef;
    CircleShape cshape;
    Body body;





    public SmallPig(float health){
        super(health);
//        this.map=map;

        bdef=new BodyDef();
        fdef=new FixtureDef();
        cshape=new CircleShape();



    }
    public void takeDamage(int damage){
        System.out.println("small pig is hit");
        health -= damage;
        if(this.health<=0){
            this.toDestroy=true;
        }

    }

    public static void createSmallPig(TiledMap map,Level level){
        MapLayer groupLayer=map.getLayers().get("objects");
        if(groupLayer != null){
            if(groupLayer instanceof MapGroupLayer){
                MapGroupLayer group=(MapGroupLayer)groupLayer;
                MapLayer smallPigLayer=group.getLayers().get("small pig");
                if(smallPigLayer != null){
                    for(MapObject obj:smallPigLayer.getObjects()){
                        if(obj instanceof TextureMapObject){
                            TextureMapObject tmo=(TextureMapObject)obj;

                            float width = (float) tmo.getProperties().get("width", Float.class);
                            float height = (float) tmo.getProperties().get("height", Float.class);
                            float x = tmo.getX();
                            float y = tmo.getY();

                            SmallPig pig=new SmallPig(smallHealth);

                            pig.width=width;
                            pig.height=height;
                            pig.x=x;
                            pig.y=y;
                            pig.texture=tmo.getTextureRegion();



                            //box2d-
                            pig.bdef.type=BodyDef.BodyType.DynamicBody;
                            pig.bdef.position.set((x+width/2)/Main.PPM, (y+height/2)/Main.PPM);
                            pig.body=level.world.createBody(pig.bdef);
                            pig.cshape.setRadius((width/2)/Main.PPM);
                            pig.fdef.shape= pig.cshape;

                            pig.fdef.density=1f;
                            pig.fdef.friction=0.5f;
                            pig.fdef.restitution=0.1f;
                            pig.body.createFixture(pig.fdef).setUserData(pig);

                            level.pigs.add(pig);

                            pig.cshape.dispose();
                        }
                    }

                }
            }
        }


    }

}
