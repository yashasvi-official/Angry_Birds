package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Settings implements Disposable {
    public final Stage stage;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Table popUpTable;
    private final ImageButton sound;
    private final ImageButton music;
    private final ImageButton exit;
    private final ImageButton about;
    private final Main game;
    private final Skin skin;
    private final TextureRegionDrawable soundOnDrawable;
    private final TextureRegionDrawable soundOffDrawable;
    private final TextureRegionDrawable musicOnDrawable;
    private final TextureRegionDrawable musicOffDrawable;
    private Image mascot;

    public Settings(Main game) {
        this.game = game;


        camera = new OrthographicCamera();
        viewport = new FitViewport(Main.w_width, Main.w_height, camera);
        stage = new Stage(viewport);

        skin = new Skin(Gdx.files.internal("final_skin/final_skin.json"));

        // Create main table
        popUpTable = new Table();
        popUpTable.setFillParent(true);

        // Setup background
        Texture settingsBackground = new Texture(Gdx.files.internal("settings_back.png"));
        Image background = new Image(settingsBackground);
//        background.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());

        background.setPosition(
            (viewport.getWorldWidth() - background.getWidth()) / 2,
            (viewport.getWorldHeight()- background.getHeight()) / 2
        );
        stage.addActor(background);

        // Initialize drawables
        soundOnDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("sound_on.png")));
        soundOffDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("sound_off.png")));
        musicOnDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("music_on.png")));
        musicOffDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("music_off.png")));

        // Creating buttons
        sound = createSoundButton();
        music = createMusicButton();
        about = createAboutButton();
        exit = createExitButton(background);

        Texture redCartoon=new Texture(Gdx.files.internal("redCartoon.png"));
        mascot = new Image(redCartoon);
        mascot.setPosition(
            (viewport.getWorldWidth() - background.getWidth()) / 2 - (1*mascot.getWidth()/7),
            (Main.w_height + background.getHeight()) / 2 - (1*mascot.getHeight()/2)
        );

        setupTable();
        stage.addActor(popUpTable);
        stage.addActor(exit);
        stage.addActor(mascot);

    }

    private void setupTable() {
        popUpTable.clear();
        popUpTable.top();
        popUpTable.padTop(300);
        popUpTable.padLeft(50);
        popUpTable.padRight(350);
        popUpTable.defaults().space(40);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("angrybirdswhite");
        labelStyle.fontColor = skin.getColor("white");

        // Add buttons and labels
        addButtonRow(sound, "Sound", labelStyle);
        addButtonRow(music, "Music", labelStyle);
        addButtonRow(about, "About", labelStyle);
    }

    private void addButtonRow(ImageButton button, String labelText, Label.LabelStyle style) {
        popUpTable.add(button);
        popUpTable.add(new Label(labelText, style));
        popUpTable.row();
    }

    private ImageButton createSoundButton() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = Main.isSound ? soundOnDrawable : soundOffDrawable;

        ImageButton button = new ImageButton(style);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Main.isSound = !Main.isSound;


                button.getStyle().up = Main.isSound ? soundOnDrawable : soundOffDrawable;
            }
        });
        return button;
    }

    private ImageButton createMusicButton() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = Main.isMusic ? musicOnDrawable : musicOffDrawable;

        ImageButton button = new ImageButton(style);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Main.isMusic = !Main.isMusic;
                if(Main.isMusic){
                    Main.music.play();
                }
                else{
                    Main.music.pause();
                }
                button.getStyle().up = Main.isMusic ? musicOnDrawable : musicOffDrawable;
            }
        });
        return button;
    }

    private ImageButton createAboutButton() {
        TextureRegionDrawable drawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("about.png")));
        ImageButton obj=new ImageButton(drawable);

        obj.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Main.isSound) Main.sound.play();
                game.setScreen(new About(game));

            }
        });
        return obj;


//        return new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("about.png"))));
    }

    private ImageButton createExitButton(Image background) {
        TextureRegionDrawable exitDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("cross.png")));
        ImageButton exitButton = new ImageButton(exitDrawable);


        exitButton.setPosition(
            (viewport.getWorldWidth() + background.getWidth()) / 2 - (3*exitButton.getWidth()/4),
            (Main.w_height + background.getHeight()) / 2 - (3*exitButton.getHeight()/4)
        );

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Main.isSound) Main.sound.play();
                if(HomeScreen.isSettings){
                    HomeScreen.isSettings = false;
                    Gdx.input.setInputProcessor(HomeScreen.stage);

                }
                else if(SelectLevel.isSettings){
                    SelectLevel.isSettings = false;
                    Gdx.input.setInputProcessor(SelectLevel.stage);
                }
                else if(ProgressScreen.isSettings){
                    ProgressScreen.isSettings=false;
                    Gdx.input.setInputProcessor(ProgressScreen.stage);
                }
            }
        });
        return exitButton;
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
