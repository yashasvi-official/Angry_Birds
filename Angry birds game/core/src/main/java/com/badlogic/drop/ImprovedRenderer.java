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

    public ImprovedRenderer(TiledMap map, SpriteBatch batch, Level level,float value) {
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
                float angleWood =wood.body.getAngle();
                batch.draw(wood.getTexture(),(wood.body.getPosition().x)*Main.PPM-wood.getWidth()/2,(wood.body.getPosition().y)*Main.PPM-wood.getHeight()/2,
                    wood.getWidth()/2,wood.getHeight()/2,
                    wood.getWidth(),wood.getHeight(),1,1,
                    (float) Math.toDegrees(angleWood));

            }
        }


        //glass
        for (Material obj : level.blocks) {
            if (obj instanceof Glass) {
                Glass glass = (Glass) obj;
                float x = glass.body.getPosition().x*Main.PPM;
                float y = glass.body.getPosition().y*Main.PPM;
                float angle = glass.body.getAngle();  // Get rotation angle of the body

                // Draw the texture with rotation
                batch.draw(
                    glass.getTexture(),
                    x - glass.getWidth() / 2,  // Offset by half the width (to center)
                    y - glass.getHeight() / 2, // Offset by half the height (to center)
                    glass.getWidth() / 2,  // Origin is the center of the texture
                    glass.getHeight() / 2, // Origin is the center of the texture
                    glass.getWidth(), // Width of the texture
                    glass.getHeight(), // Height of the texture
                    1, 1,  // Scale (no scaling)
                    (float) Math.toDegrees(angle)  // Apply the rotation
                );
            }


        }

        for (Material obj : level.blocks) {
            if (obj instanceof Rock) {
                Rock rock = (Rock) obj;
                float angle=rock.body.getAngle();
                batch.draw(
                    rock.getTexture(),
                    (rock.body.getPosition().x)*Main.PPM - rock.getWidth() / 2,
                    (rock.body.getPosition().y)*Main.PPM - rock.getHeight() / 2,
                    rock.getWidth()/2,
                    rock.getHeight()/2,
                    rock.getWidth(),
                    rock.getHeight(),
                    1,1,
                    (float) Math.toDegrees(angle) // Convert radians to degrees

                );
            }
        }

        // Render Red Bird
        for (Bird bird : level.birds) {
            if (bird instanceof Red) {
                Red red = (Red) bird;
                float angle=red.body.getAngle();

                batch.draw(
                    red.getTexture(),
                    (red.body.getPosition().x)*Main.PPM - red.getWidth() / 2,
                    (red.body.getPosition().y)*Main.PPM - red.getHeight() / 2,
                    red.getWidth()/2,
                    red.getHeight()/2,
                    red.getWidth(),
                    red.getHeight(),
                    1,1,
                    (float) Math.toDegrees(angle)
                );
            }
        }

        // Render Blue Bird
        for (Bird bird : level.birds) {
            if (bird instanceof Blue) {
                Blue blue = (Blue) bird;
                float angle=blue.body.getAngle();

                batch.draw(
                    blue.getTexture(),
                    (blue.body.getPosition().x)*Main.PPM - blue.getWidth() / 2,
                    (blue.body.getPosition().y)*Main.PPM - blue.getHeight() / 2,
                    blue.getWidth()/2,
                    blue.getHeight()/2,

                    blue.getWidth(),
                    blue.getHeight(),
                    1,1,
                    (float) Math.toDegrees(angle)
                );
                if(blue.abilityUsed){
                    for(Blue obj:blue.splitBirds){
                        float splitAngle=obj.body.getAngle();
                        batch.draw(
                            obj.getTexture(),
                            (obj.body.getPosition().x)*Main.PPM-obj.getWidth() / 2,
                            (obj.body.getPosition().y)*Main.PPM - obj.getHeight() / 2,
                            obj.getWidth()/2,
                            obj.getHeight()/2,

                            blue.getWidth(),
                            blue.getHeight(),
                            1,1,
                            (float) Math.toDegrees(splitAngle)

                        );
                    }
                }
            }
        }

        // Render Yellow Bird
        for (Bird bird : level.birds) {
            if (bird instanceof Yellow) {
                Yellow yellow = (Yellow) bird;

                float angle=yellow.body.getAngle();
                batch.draw(
                    yellow.getTexture(),
                    (yellow.body.getPosition().x)*Main.PPM - yellow.getWidth() / 2,
                    (yellow.body.getPosition().y)*Main.PPM - yellow.getHeight() / 2,
                    yellow.getWidth()/2,
                    yellow.getHeight()/2,
                    yellow.getWidth(),
                    yellow.getHeight(),
                    1,1,
                    (float) Math.toDegrees(angle)
                );
            }
        }

        for (Pig pig : level.pigs) {
            if (pig instanceof SmallPig) {
                SmallPig smallPig = (SmallPig) pig;
                smallPig.x=(smallPig.body.getPosition().x)*Main.PPM - smallPig.getWidth() / 2;
                smallPig.y=(smallPig.body.getPosition().y)*Main.PPM - smallPig.getHeight() / 2;

                float angle=smallPig.body.getAngle();
                batch.draw(
                    smallPig.getTexture(),
                    (smallPig.body.getPosition().x)*Main.PPM - smallPig.getWidth() / 2,
                    (smallPig.body.getPosition().y)*Main.PPM - smallPig.getHeight() / 2,
                    smallPig.getWidth()/2,
                    smallPig.getHeight()/2,
                    smallPig.getWidth(),
                    smallPig.getHeight(),
                    1,1,
                    (float) Math.toDegrees(angle)
                );
            } else if (pig instanceof SoldierPig) {
                SoldierPig soldierPig = (SoldierPig) pig;
                float angle=soldierPig.body.getAngle();
                batch.draw(
                    soldierPig.getTexture(),
                    (soldierPig.body.getPosition().x)*Main.PPM - soldierPig.getWidth() / 2,
                    (soldierPig.body.getPosition().y)*Main.PPM - soldierPig.getHeight() / 2,
                    soldierPig.getWidth()/2,
                    soldierPig.getHeight()/2,
                    soldierPig.getWidth(),
                    soldierPig.getHeight(),
                    1,1,
                    (float) Math.toDegrees(angle)
                );
            } else if (pig instanceof KingPig) {
                KingPig kingPig = (KingPig) pig;
                float angle=kingPig.body.getAngle();
                batch.draw(
                    kingPig.getTexture(),
                    (kingPig.body.getPosition().x)*Main.PPM - kingPig.getWidth() / 2,
                    (kingPig.body.getPosition().y)*Main.PPM - kingPig.getHeight() / 2,
                    kingPig.getWidth()/2,
                    kingPig.getHeight()/2,
                    kingPig.getWidth(),
                    kingPig.getHeight(),
                    1,1,
                    (float) Math.toDegrees(angle)
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
