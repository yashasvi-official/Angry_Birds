package com.badlogic.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class CustomMapRenderer extends OrthogonalTiledMapRenderer {
    private SpriteBatch batch;
    public CustomMapRenderer(TiledMap map, SpriteBatch batch) {
        super(map);
        this.batch = batch;
    }


    @Override
    public void render() {
        super.render();

        MapLayer groupLayer = map.getLayers().get("objects");
        if (groupLayer != null) {
            if (groupLayer instanceof MapGroupLayer) {
                batch.begin();
                MapGroupLayer group = (MapGroupLayer) groupLayer;
                MapLayers inneLayers = group.getLayers();

                for (MapLayer layer : inneLayers) {
                    if (layer != null) {
                        for (MapObject object : layer.getObjects()) {

                            if (object instanceof TextureMapObject) {
                                TextureMapObject textureMapObject = (TextureMapObject) object;

                                // Get texture region properties
                                float width = (float) textureMapObject.getProperties().get("width", Float.class);
                                float height = (float) textureMapObject.getProperties().get("height", Float.class);
                                float x = textureMapObject.getX();
                                float y = textureMapObject.getY();
                                float rotation = textureMapObject.getRotation();

                                float originX = width / 2;
                                float originY = height / 2;

                                // Correct the drawing position to center the object
                                float adjustedX = x - originX;
                                float adjustedY = y - originY;


                                // Draw the texture
                                batch.draw(textureMapObject.getTextureRegion(), x, y, adjustedX, adjustedY, width, height, 1, 1, rotation);
                            }
                        }

                    }
                }
                batch.end();

            }
        }

//        MapLayer layer = map.getLayers().get("objects");
//
//        if (layer != null) {
//
//
//            batch.begin();
//
//
//            for (MapObject object : layer.getObjects()) {
//
//                if (object instanceof TextureMapObject) {
//                    TextureMapObject textureMapObject = (TextureMapObject) object;
//
//                    // Get texture region properties
//                    float width = (float) textureMapObject.getProperties().get("width", Float.class);
//                    float height = (float) textureMapObject.getProperties().get("height", Float.class);
//                    float x = textureMapObject.getX();
//                    float y = textureMapObject.getY();
//                    float rotation = textureMapObject.getRotation();
//
//                    float originX = width / 2;
//                    float originY = height / 2;
//
//                    // Correct the drawing position to center the object
//                    float adjustedX = x - originX;
//                    float adjustedY = y - originY;
//
//
//                    // Draw the texture
//                    batch.draw(textureMapObject.getTextureRegion(), x, y,adjustedX,adjustedY,width, height,1,1,rotation);
//                }
//            }
//            batch.end();
//        }

    }

}
