package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GamePaused implements Disposable {
    public final Stage stage;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Table popUpTable;
    private final ImageButton goHome;
    private final ImageButton resume;
    private final ImageButton restart;
//    private final ImageButton about;
    private final Main game;
//    private final Skin skin;
    private final TextureRegionDrawable goHomeText;
    private final TextureRegionDrawable resumeText;
    private final TextureRegionDrawable restartText;
    Level level;

    public GamePaused (Main game,Level level) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport=new FitViewport(Main.w_width, Main.w_height, camera);
        stage = new Stage(viewport, game.batch);
        this.level=level;




        popUpTable = new Table();
        popUpTable.setFillParent(true);

        Texture pauseBackground=new Texture("game_paused.png");
        Image background=new Image(pauseBackground);
        background.setPosition(
            (viewport.getWorldWidth() - background.getWidth()) / 2,
            (viewport.getWorldHeight()- background.getHeight()) / 2
        );
        stage.addActor(background);


        goHomeText=new TextureRegionDrawable(new Texture(Gdx.files.internal("homeIcon.png")));
        restartText=new TextureRegionDrawable(new Texture(Gdx.files.internal("restart.png")));
        resumeText=new TextureRegionDrawable(new Texture(Gdx.files.internal("resume.png")));

        restart=new ImageButton(restartText);
        resume=new ImageButton(resumeText);
        goHome=new ImageButton(goHomeText);

        restart.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {

                if(Main.isSound) Main.sound.play();
                Level restartedLevel=level.restartLevel(level);
                game.setScreen(restartedLevel);


            }
        });

        resume.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(Main.isSound) Main.sound.play();
                level.isGamePaused=false;
//                Gdx.input.setInputProcessor(level.hud.stage);

                game.setScreen(level);

            }
        });

        goHome.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(Main.isSound) Main.sound.play();
                level.isGamePaused=false;
                if(level.getLevel()>=Main.currentLevel){
                    Main.gameState=new GameState(level.getLevel());
                    Main.gameState.saveToFile("game_save.dat");

                }

                game.setScreen(new HomeScreen(game));


            }
        });
        popUpTable.top();


        popUpTable.padTop(500);
        popUpTable.padLeft(150);
        popUpTable.padRight(150);
        popUpTable.defaults().space(50);


        popUpTable.add(goHome);
        popUpTable.add(restart);
        popUpTable.add(resume);
        stage.addActor(popUpTable);




    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }


    @Override
    public void dispose() {
        stage.dispose();

    }
}
