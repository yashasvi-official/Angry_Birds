//package com.badlogic.drop;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
//import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.utils.viewport.FitViewport;
//
//public class PlayScreen implements Screen {
//    private FitViewport viewport;
//    private Main game;
//    OrthographicCamera camera;
//    private Stage stage;
//    private Image background;
//    private ImageButton playButton;
//
//    public PlayScreen (Main game) {
//        this.game = game;
//        camera = new OrthographicCamera();
//        viewport=new FitViewport(800,600,camera);
//        stage=new Stage(viewport,game.batch );
//        Gdx.input.setInputProcessor(stage);
//
//        Texture backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));
//        background=new Image(backgroundTexture);
//
//        background.setSize(viewport.getWorldWidth(),viewport.getWorldHeight());
//        background.setPosition(0,0);
//
//        stage.addActor(background);
//
//        Texture playTexture = new Texture(Gdx.files.internal("play.png"));
//        TextureRegionDrawable drawable = new TextureRegionDrawable(playTexture);
//        playButton=new ImageButton(drawable);
//
//        playButton.setSize(playTexture.getWidth()*0.7f,playTexture.getHeight()*0.7f);
//        float buttonWidth = playButton.getWidth();
//        float buttonHeight = playButton.getHeight();
//
//        // Position the button in the center of the screen
//        playButton.setPosition((viewport.getWorldWidth() - buttonWidth) / 2, (viewport.getWorldHeight() - buttonHeight) / 2);
//
//        stage.addActor(playButton);
//
//
//
//
//
//
//    }
//
//    @Override
//    public void show() {
////        camera=new OrthographicCamera();
////        viewport =new FitViewport(1920,1080,camera);
////        stage=new Stage(viewport,game.batch);
////        Gdx.input.setInputProcessor(stage);
////
////        camera.setToOrtho(false, 800, 480);
////
////        Texture backgroundTexture;
////        backgroundTexture=new Texture("background.jpg");
////        background=new Image(backgroundTexture);
////        background.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
////        background.setPosition(0,0);
////
////        stage.addActor(background);
////
////        Texture play=new Texture("play.png");
////        TextureRegionDrawable drawable=new TextureRegionDrawable(play);
////        playButton=new ImageButton(drawable);
////
////        // Calculate center position accounting for button size
////        playButton.setSize(play.getWidth()*0.7f,play.getHeight()*0.7f);
////        playButton.setPosition(
////            (viewport.getWorldWidth()*0.5f),  // horizontal center
////            (viewport.getWorldHeight() * 0.5f)  // vertical center
////        );
////        stage.addActor(playButton);
//
//
//
//
//
//    }
//
//    @Override
//    public void render(float delta) {
//
//        camera.update();
//        ScreenUtils.clear(0,0,0.2f,1);
//        stage.act(delta);
//        stage.draw();
//
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        viewport.update(width, height, true);
//
//
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//
//    }
//
//    @Override
//    public void dispose() {
//        stage.dispose();
//
//    }
//}
