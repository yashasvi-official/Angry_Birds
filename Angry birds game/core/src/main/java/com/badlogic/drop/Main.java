package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public SpriteBatch batch;
    public static int w_width = 1920;
    public static int w_height = 1080;
    private AssetManager assetManager;

    public static boolean isSound=true;
    public static boolean isMusic=true;
    public static Music music;
    public static Sound sound;

    public static final float PPM=100f;
    public static float currentLevel;


    public static GameState gameState;
    public static boolean[] unlockedLevels;

    @Override
    public void create() {
        unlockedLevels = new boolean[3];
        unlockedLevels[0] = true;

        music= Gdx.audio.newMusic(Gdx.files.internal("title_song.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);

        sound=Gdx.audio.newSound(Gdx.files.internal("click.mp3"));



//        viewport = new FitViewport(800, 600,camera);
        batch = new SpriteBatch();
        assetManager = new AssetManager();

        Setup setup=new Setup(this);

//        setScreen(new PlayScreen(this));
        // setScreen(new LoadingScreen(this,assetManager));
        setScreen(new LoadingScreen(this,assetManager));

    }
    public void render(){
        super.render();

    }
}
