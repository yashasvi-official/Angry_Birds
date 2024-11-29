package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SelectLevel implements Screen {



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
    Array<TextButton> levelButtons = new Array<>();
    Settings settingPopup;

    //    Label heading;
    public SelectLevel (Main game) {
        this.game = game;
        settingPopup=new Settings(game);
        skin=new Skin(Gdx.files.internal("final_skin/final_skin.json"));
        camera = new OrthographicCamera();
        viewport=new FitViewport(Main.w_width, Main.w_height, camera);
        stage = new Stage(viewport,game.batch);
        Gdx.input.setInputProcessor(stage);
//        Texture sling=new Texture(Gdx.files.internal("play.png"));
//        sprite=new Sprite(sling);
//        sprite.setCenter(300,300);

        Texture backTexture = new Texture(Gdx.files.internal("select_level.png"));
        background = new Image(backTexture);
        stage.addActor(background);

//        heading = new Label("Select Level", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("final_skin/angrybirds-regular(7).fnt")), Color.BLACK));
//        Label.LabelStyle labelStyle = new Label.LabelStyle();
//        labelStyle.font = skin.getFont("angrybirdswhite");
//        labelStyle.fontColor = skin.getColor("brown");
//
//
//
//        Label heading = new Label("Select Level", labelStyle);
//        heading.setFontScale(2);

        heading=new Image(new Texture("selectLevel.png"));


//        heading.setPosition((viewport.getWorldWidth()-heading.getWidth()*2)/2,(viewport.getWorldHeight()-viewport.getWorldHeight()/5));
        heading.setPosition((viewport.getWorldWidth()-heading.getWidth())/2-50,5*viewport.getWorldHeight()/8 );
        stage.addActor(heading);

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

        //settings-
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


        table = new Table();
        table.setFillParent(true);
        createLevel(table);
        stage.addActor(table);




    }
    private void createLevel(Table table) {
        table.top();
        table.padTop(450);
        table.padLeft(200);
        table.padRight(200);
        table.defaults().space(50);

        int count = 0;


        for(int i = 1; i <= 3; i++) {
            if(count % 3 == 0) {
                table.row();
            }

            final int levelIndex = i-1;
            TextButton button = new TextButton("Level" + i, skin);

            // Apply visual feedback for locked levels
            if (!Main.unlockedLevels[levelIndex]) {
                button.setColor(Color.GRAY);
                button.getLabel().setColor(Color.DARK_GRAY);
                button.setTouchable(Touchable.disabled);
            }

            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    // Only allow clicking if level is unlocked

                    if(Main.isSound) Main.sound.play();
                    game.setScreen(Level.levels.get(levelIndex));

                }
            });
            levelButtons.add(button);

            table.add(button);
            count++;
        }
    }
    @Override
    public void show() {
        // Update button states when screen becomes visible
        for(int i = 0; i < levelButtons.size; i++) {
            updateButtonState(levelButtons.get(i), i);
        }
        Gdx.input.setInputProcessor(stage);
    }

    private void updateButtonState(TextButton button, int levelIndex) {
        if (Main.unlockedLevels[levelIndex]) {
            button.setColor(Color.WHITE);
            button.getLabel().setColor(Color.WHITE);
            // Make sure the button is enabled
            button.setTouchable(Touchable.enabled);
        } else {
            button.setColor(Color.GRAY);
            button.getLabel().setColor(Color.DARK_GRAY);
            // Disable touch for locked levels
            button.setTouchable(Touchable.disabled);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,0);
        stage.act(delta);
        stage.draw();
//        game.batch.begin();
//        sprite.draw(game.batch);
//        game.batch.end();
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
