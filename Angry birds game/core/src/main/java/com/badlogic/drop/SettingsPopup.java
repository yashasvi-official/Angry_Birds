package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsPopup implements Screen {

    private Main game;
    private static Stage stage;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Table popUpTable;
    ImageButton sound;
//    ImageButton sound_off;
    ImageButton music;
//    ImageButton music_off;
    ImageButton exit;
    ImageButton about;


    public SettingsPopup (Main game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        popUpTable = new Table();


        createPopup(popUpTable);

        stage.addActor(popUpTable);
        TextureRegionDrawable exit_drawable=new TextureRegionDrawable(new Texture(Gdx.files.internal("cross.png")));
        exit=new ImageButton(exit_drawable);
        exit.setPosition((Gdx.graphics.getWidth() + 1252) / 2, (Gdx.graphics.getHeight() + 766) / 2);

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(null); // Remove input control
                stage.clear();
            }
        });
        stage.addActor(exit);




    }


    private void createPopup (Table popUpTable) {

        popUpTable.setFillParent(true);
        popUpTable.setSize(400,300);
        popUpTable.setPosition((Gdx.graphics.getWidth() - 400) / 2, (Gdx.graphics.getHeight() - 300) / 2);

        Texture settingsBackground = new Texture(Gdx.files.internal("settings_page.png"));
        TextureRegionDrawable drawable= new TextureRegionDrawable(settingsBackground);

        popUpTable.setBackground(drawable);

        popUpTable.top();
        popUpTable.padTop(150);
        popUpTable.padLeft(150);
        popUpTable.padRight(150);

        popUpTable.defaults().space(50);

        TextureRegionDrawable sound_onDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("sound_on.png")));
        TextureRegionDrawable sound_offDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("sound_off.png")));
        music=new ImageButton(sound_onDrawable);



        TextureRegionDrawable music_onDrawable=new TextureRegionDrawable(new Texture(Gdx.files.internal("music_on.png")));
        TextureRegionDrawable music_offDrawable=new TextureRegionDrawable(new Texture(Gdx.files.internal("music_off.png")));
        music=new ImageButton(music_onDrawable);




        TextureRegionDrawable about_drawable=new TextureRegionDrawable(new Texture(Gdx.files.internal("about.png")));
        about=new ImageButton(about_drawable);



        popUpTable.add(sound).row();
        popUpTable.add(music).row();
        popUpTable.add(about).row();

        sound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.isSound = !Main.isSound; // Toggle sound state

                // Swap the button image directly based on the sound state
                if (Main.isSound) {
                    sound.getImage().setDrawable(sound_onDrawable);

                    // Play sound
                    System.out.println("Sound On");
                } else {
                    sound.getImage().setDrawable(sound_offDrawable);
                    // Mute sound
                    System.out.println("Sound Off");
                }
            }
        });
        music.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.isMusic = !Main.isMusic;
                if (Main.isMusic) {
                    music.getImage().setDrawable(music_onDrawable);
                    System.out.println("Music On");

                }
                else {
                    music.getImage().setDrawable(music_offDrawable);
                    System.out.println("Music Off");
                }
            }
        });








    }

    public void showPopup() {
        Gdx.input.setInputProcessor(stage); // Set the stage for input handling
    }
    @Override
    public void show() {
        showPopup();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,0);


        stage.act(delta);
        stage.draw();



    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

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
