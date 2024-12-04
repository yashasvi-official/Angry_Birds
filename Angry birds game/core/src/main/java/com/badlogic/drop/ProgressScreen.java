package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ProgressScreen implements Screen {
    private Main game;
    BitmapFont font;
    Image background;
    FitViewport viewport;
    OrthographicCamera camera;
    public static Stage stage;
    Table table;
    private Skin skin;
    public Sprite sprite;
    ImageButton backButton;
    ImageButton settings;
    public static boolean isSettings=false;
    Image heading;
    ImageButton loadGame;
    ImageButton newGame;
    ImageButton loadLevel;

    Settings settingPopup;


    public ProgressScreen(Main game) {
        this.game=game;
        skin=new Skin(Gdx.files.internal("final_skin/final_skin.json"));
        camera = new OrthographicCamera();
        viewport=new FitViewport(Main.w_width, Main.w_height, camera);
        stage = new Stage(viewport,game.batch);
        settingPopup=new Settings(game);

        Texture backTexture = new Texture(Gdx.files.internal("select_level.png"));
        background = new Image(backTexture);
        stage.addActor(background);

        //back button-
        TextureRegionDrawable backUp=new TextureRegionDrawable(new Texture("back.png"));
        TextureRegionDrawable backHover=new TextureRegionDrawable(new Texture("back_hover.png"));
        ImageButton.ImageButtonStyle style=new ImageButton.ImageButtonStyle();
        style.up=backUp;
        style.over=backHover;

        backButton = new ImageButton(style);
        backButton.setPosition(20, Main.w_height - backButton.getHeight() - 20);

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(Main.isSound) Main.sound.play();
                game.setScreen(new  HomeScreen(game));
            }
        });
        stage.addActor(backButton);

        Texture texture = new Texture("settings.png");
//        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        TextureRegionDrawable settingUp = new TextureRegionDrawable(texture);

        TextureRegionDrawable settingHover = new TextureRegionDrawable(new Texture("settings_hover.png"));
        ImageButton.ImageButtonStyle settingStyle=new ImageButton.ImageButtonStyle();
        settingStyle.up = settingUp;
        settingStyle.over = settingHover;

        settings = new ImageButton(settingStyle);

        settings.setPosition(Main.w_width- settings.getWidth() - 20, Main.w_height - settings.getHeight() - 20);
        settings.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(Main.isSound) Main.sound.play();

                System.out.println("settings pressed");
                isSettings=true;
                Gdx.input.setInputProcessor(settingPopup.stage);
            }

        });
        stage.addActor(settings);

        TextureRegionDrawable loadGameText=new TextureRegionDrawable(new Texture("loadGame.png"));
        loadGame = new ImageButton(loadGameText);
        loadGame.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(Main.isSound) Main.sound.play();
                GameState loadedState = GameState.loadFromFile("game_save.dat");
                GameState.loadGame(loadedState);
                game.setScreen(new SelectLevel(game));

            }
        });

//        TextureRegionDrawable loadLevelText=new TextureRegionDrawable(new Texture("loadLevel.png"));
//        loadLevel = new ImageButton(loadLevelText);
//        loadLevel.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                if(Main.isSound) Main.sound.play();
//                GameState obj=LoadGame.loadGameState();
//                game.setScreen(obj.loadGame());
//
//            }
//        });

        TextureRegionDrawable newGameText=new TextureRegionDrawable(new Texture("newGame.png"));
        newGame = new ImageButton(newGameText);
        newGame.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(Main.isSound) Main.sound.play();
                GameState.newGame();
//                for(Level obj: levels)
//                game.setScreen(restartedLevel);
                game.setScreen(new SelectLevel(game));

            }
        });






        table = new Table();
        table.setFillParent(true);
        createOptions(table);
        stage.addActor(table);



    }
    public void createOptions(Table table){
        table.top();
        table.padTop(250);
        table.padLeft(200);
        table.padRight(200);

        table.defaults().space(50);
        table.add(newGame).row();
        table.add(loadGame).row();
//        table.add(loadLevel).row();




    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,0);
        stage.act(delta);
        stage.draw();

        if(isSettings){
            settingPopup.stage.act(delta);
            settingPopup.stage.draw();
        }


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
