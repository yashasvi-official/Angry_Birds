package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public SpriteBatch batch;
    public static int w_width = 1920;
    public static int w_height = 1080;
    private AssetManager assetManager;

    public static boolean isSound=true;
    public static boolean isMusic=true;



    @Override
    public void create() {

//        viewport = new FitViewport(800, 600,camera);
        batch = new SpriteBatch();
        assetManager = new AssetManager();

        Setup setup=new Setup(this);

//        setScreen(new PlayScreen(this));
        setScreen(new LoadingScreen(this,assetManager));
    }
    public void render(){
        super.render();

    }
}
