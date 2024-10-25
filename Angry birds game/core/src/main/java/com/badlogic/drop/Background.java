package com.badlogic.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Background extends Actor {
    private Texture backgroundTexture;
    private Drawable drawable;


    public Background(Texture texture,float width,float height) {
        backgroundTexture = texture;
        this.setSize(width,height);






    }
    public void draw(SpriteBatch batch, float parentAlpha) {
        batch.draw(backgroundTexture, 0, 0,getWidth(),getHeight());
    }

}
