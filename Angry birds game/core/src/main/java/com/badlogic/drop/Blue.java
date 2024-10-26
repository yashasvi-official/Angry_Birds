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


public class Blue extends Bird {

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

    public Blue(int damage) {
        super(damage);

        bdef = new BodyDef();
        fdef = new FixtureDef();
        cshape = new CircleShape();

    }

    public static void createBlue(TiledMap map, Level level){
        MapLayer groupLayer=map.getLayers().get("objects");
        if(groupLayer != null){
            if(groupLayer instanceof MapGroupLayer){
                MapGroupLayer group=(MapGroupLayer)groupLayer;
                MapLayer BlueLayer =group.getLayers().get("blue");
                if(BlueLayer != null){
                    for(MapObject obj: BlueLayer.getObjects()){
                        if(obj instanceof TextureMapObject){
                            TextureMapObject tmo=(TextureMapObject)obj;

                            float width = (float) tmo.getProperties().get("width", Float.class);
                            float height = (float) tmo.getProperties().get("height", Float.class);
                            float x = tmo.getX();
                            float y = tmo.getY();

                            Blue blue=new  Blue(2);


                            blue.width=width;
                            blue.height=height;
                            blue.x=x;
                            blue.y=y;
                            blue.texture=tmo.getTextureRegion();


                            //box2d-
                            blue.bdef.type=BodyDef.BodyType.DynamicBody;
                            blue.bdef.position.set(x+width/2, y+height/2);
                            blue.body=level.world.createBody(blue.bdef);
                            blue.cshape.setRadius(width/2);
                            blue.fdef.shape= blue.cshape;
                            blue.body.createFixture(blue.fdef);

                            level.birds.add(blue);


                            blue.cshape.dispose();
                        }
                    }

                }
            }
        }


    }



}
