package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.drop.Main.music;

public class LoadingScreen implements Screen{

    private AssetManager asset;
    float loadingTime;
    float totalTime=6;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private Main game;
    Sprite backgroundSprite;
    private ProgressBar progressBar;
    private Stage stage;




    public LoadingScreen(Main game, AssetManager asset) {
        this.asset = asset;
        this.game = game;

        camera=new OrthographicCamera();
        viewport=new FitViewport(Main.w_width,Main.w_height,camera);
        stage=new Stage(viewport);
        Texture backgroundTexture=new Texture(Gdx.files.internal("Loading.png"));
        backgroundSprite=new Sprite(backgroundTexture);
        backgroundSprite.setSize(1920,1080);
        backgroundSprite.setPosition(0,0);



//        asset.load("background.png", Texture.class);
//        asset.load("background.jpg", Texture.class);
        asset.load("slingshot.png", Texture.class);
        asset.load("first_skin.json", Skin.class);
        createProgressBar();



    }
    private void createProgressBar() {
        Skin skin=new Skin(Gdx.files.internal("first_skin.json"));

        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle(
            skin.newDrawable("white", Color.DARK_GRAY), // Background
            skin.newDrawable("white", Color.GREEN)      // Knob (progress)
        );


        progressBar = new ProgressBar(0f, 5f, 0.01f, false, skin);

        // Set width and height for the progress bar
        progressBar.setSize(450, 0);
        progressBar.setPosition((Main.w_width - progressBar.getWidth()) / 2, Main.w_height/10);

        stage.addActor(progressBar);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if(asset.update() && loadingTime>=totalTime){
            game.setScreen(new HomeScreen(game));
            music.play();

        }

        loadingTime+=delta;
        float progress=asset.getProgress();
        progressBar.setValue(loadingTime);

        ScreenUtils.clear(0,0,0,0);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        backgroundSprite.draw(game.batch);
        game.batch.end();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
