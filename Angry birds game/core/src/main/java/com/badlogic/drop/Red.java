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

public class Red extends Bird {

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

    public Red(int damage) {
        super(damage);

        bdef = new BodyDef();
        fdef = new FixtureDef();
        cshape = new CircleShape();

    }

    public static void createRed(TiledMap map, Level level){
        MapLayer groupLayer=map.getLayers().get("objects");
        if(groupLayer != null){
            if(groupLayer instanceof MapGroupLayer){
                MapGroupLayer group=(MapGroupLayer)groupLayer;
                MapLayer RedLayer =group.getLayers().get("red");
                if(RedLayer != null){
                    for(MapObject obj: RedLayer.getObjects()){
                        if(obj instanceof TextureMapObject){
                            TextureMapObject tmo=(TextureMapObject)obj;

                            float width = (float) tmo.getProperties().get("width", Float.class);
                            float height = (float) tmo.getProperties().get("height", Float.class);
                            float x = tmo.getX();
                            float y = tmo.getY();

                            Red red=new Red(2);

                            red.width=width;
                            red.height=height;
                            red.x=x;
                            red.y=y;
                            red.texture=tmo.getTextureRegion();


                            //box2d-
                            red.bdef.type=BodyDef.BodyType.DynamicBody;
                            red.bdef.position.set(x+width/2, y+height/2);
                            red.body=level.world.createBody(red.bdef);
                            red.cshape.setRadius(width/2);
                            red.fdef.shape= red.cshape;
                            red.body.createFixture(red.fdef);

                            level.birds.add(red);

                            red.cshape.dispose();
                        }
                    }

                }
            }
        }


    }



}