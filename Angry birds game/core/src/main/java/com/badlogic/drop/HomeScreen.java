package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class HomeScreen implements Screen {

    private Main game;
    private OrthographicCamera camera;
    private FitViewport viewport;
    public Stage stage;
    Image background;
    ImageButton settings;
    ImageButton play;
    Settings settingsPopup;
    public static boolean isSettings = false;
    Music music;

    public HomeScreen (Main game) {
        this.game = game;
//        music=Gdx.audio.newMusic(Gdx.files.internal("mahesh_dalle.mp3"));
//        music.setLooping(true);
//        music.play();
        camera = new OrthographicCamera();
        viewport=new FitViewport(Main.w_width,Main.w_height,camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        background = new Image(new Texture("home.png"));
        background.setPosition(0,0);
        background.setSize(Main.w_width,Main.w_height);
        stage.addActor(background);
        settingsPopup = new Settings(game,this);

        //settings button-
        Texture texture = new Texture("settings.png");
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        TextureRegionDrawable drawable = new TextureRegionDrawable(texture);
        settings = new ImageButton(drawable);

        settings.setPosition(Main.w_width- settings.getWidth() - 20, Main.w_height - settings.getHeight() - 20);


        settings.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                // Code to open settings screen
                System.out.println("Settings button clicked!");
                isSettings = true;
                Gdx.input.setInputProcessor(settingsPopup.stage);

            }

        });



        stage.addActor(settings);


        Texture textureplay = new Texture("playbutton.png");
//        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        TextureRegionDrawable playUp = new TextureRegionDrawable(textureplay);
        TextureRegionDrawable playHover = new TextureRegionDrawable(new Texture("playHover.png"));
        TextureRegionDrawable playDown = new TextureRegionDrawable(new Texture("playDown.png"));

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = playUp;
        style.over = playHover;
        style.down = playDown;

        play= new ImageButton(style);

//        play.setOrigin(Main.w_width/2,Main.w_height/2);


        play.setPosition((viewport.getWorldWidth()-play.getWidth())/2,(viewport.getWorldHeight()-play.getHeight()*3)/2);


        play.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                // Code to open settings screen
                System.out.println("Settings button clicked!");
                game.setScreen(new SelectLevel(game));
            }

        });
        stage.addActor(play);








    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(0,0,0,0);
        stage.act(delta);
        stage.draw();

        if(isSettings){
//            settingsPopup.stage.act(delta);
            Gdx.input.setInputProcessor(settingsPopup.stage);
            settingsPopup.stage.draw();

        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        settingsPopup.resize(width,height);

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
