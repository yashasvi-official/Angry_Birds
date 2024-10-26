package com.badlogic.drop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class ImprovedRenderer extends OrthogonalTiledMapRenderer {

    SpriteBatch batch;
    Level level;

    public ImprovedRenderer(TiledMap map, SpriteBatch batch, Level level) {
        super(map);
        this.batch = batch;
        this.level = level;


    }
    @Override
    public void render(){
        super.render();


        batch.begin();

        //wood-
        for(Material obj:level.blocks){
            if(obj instanceof Wood){
                Wood wood = (Wood)obj;
                batch.draw(wood.getTexture(),wood.body.getPosition().x-wood.getWidth()/2,wood.body.getPosition().y-wood.getHeight()/2,wood.getWidth(),wood.getHeight());

            }
        }


        //glass
        for(Material obj:level.blocks){
            if(obj instanceof Glass){
                Glass glass=(Glass)obj;
                batch.draw(glass.getTexture(),glass.body.getPosition().x-glass.getWidth()/2,glass.body.getPosition().y-glass.getHeight()/2,glass.getWidth(),glass.getHeight());

            }
        }

        for (Material obj : level.blocks) {
            if (obj instanceof Rock) {
                Rock rock = (Rock) obj;
                batch.draw(
                    rock.getTexture(),
                    rock.body.getPosition().x - rock.getWidth() / 2,
                    rock.body.getPosition().y - rock.getHeight() / 2,
                    rock.getWidth(),
                    rock.getHeight()
                );
            }
        }

        // Render Red Bird
        for (Bird bird : level.birds) {
            if (bird instanceof Red) {
                Red red = (Red) bird;
                batch.draw(
                    red.getTexture(),
                    red.body.getPosition().x - red.getWidth() / 2,
                    red.body.getPosition().y - red.getHeight() / 2,
                    red.getWidth(),
                    red.getHeight()
                );
            }
        }

        // Render Blue Bird
        for (Bird bird : level.birds) {
            if (bird instanceof Blue) {
                Blue blue = (Blue) bird;
                batch.draw(
                    blue.getTexture(),
                    blue.body.getPosition().x - blue.getWidth() / 2,
                    blue.body.getPosition().y - blue.getHeight() / 2,
                    blue.getWidth(),
                    blue.getHeight()
                );
            }
        }

        // Render Yellow Bird
        for (Bird bird : level.birds) {
            if (bird instanceof Yellow) {
                Yellow yellow = (Yellow) bird;
                batch.draw(
                    yellow.getTexture(),
                    yellow.body.getPosition().x - yellow.getWidth() / 2,
                    yellow.body.getPosition().y - yellow.getHeight() / 2,
                    yellow.getWidth(),
                    yellow.getHeight()
                );
            }
        }

        for (Pig pig : level.pigs) {
            if (pig instanceof SmallPig) {
                SmallPig smallPig = (SmallPig) pig;
                batch.draw(
                    smallPig.getTexture(),
                    smallPig.body.getPosition().x - smallPig.getWidth() / 2,
                    smallPig.body.getPosition().y - smallPig.getHeight() / 2,
                    smallPig.getWidth(),
                    smallPig.getHeight()
                );
            } else if (pig instanceof SoldierPig) {
                SoldierPig soldierPig = (SoldierPig) pig;
                batch.draw(
                    soldierPig.getTexture(),
                    soldierPig.body.getPosition().x - soldierPig.getWidth() / 2,
                    soldierPig.body.getPosition().y - soldierPig.getHeight() / 2,
                    soldierPig.getWidth(),
                    soldierPig.getHeight()
                );
            } else if (pig instanceof KingPig) {
                KingPig kingPig = (KingPig) pig;
                batch.draw(
                    kingPig.getTexture(),
                    kingPig.body.getPosition().x - kingPig.getWidth() / 2,
                    kingPig.body.getPosition().y - kingPig.getHeight() / 2,
                    kingPig.getWidth(),
                    kingPig.getHeight()
                );
            }

        }

        //slingshot-
        MapLayer layer=map.getLayers().get("objects");
        if(layer!=null){
            if(layer instanceof MapGroupLayer){
                MapGroupLayer mapGroupLayer=(MapGroupLayer)layer;
                MapLayer slingLayer=mapGroupLayer.getLayers().get("slingshot");
                if(slingLayer!=null){
                    for(MapObject obj:slingLayer.getObjects()){
                        if(obj instanceof TextureMapObject){
                            TextureMapObject textureMapObject=(TextureMapObject)obj;
                            float width = (float) textureMapObject.getProperties().get("width", Float.class);
                            float height = (float) textureMapObject.getProperties().get("height", Float.class);
                            float x = textureMapObject.getX();
                            float y = textureMapObject.getY();

                            batch.draw(textureMapObject.getTextureRegion(),x,y,width,height);
                        }

                    }
                }
        }

        }
        batch.end();



    }
}
