package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LevelFailed implements Screen {
    private final Main game;
    public static Stage stage;
    private Texture starTexture;
    private Texture settingsTexture;
    private Texture playTexture;
    private Texture burstTexture;
    private Texture levelFailedTexture;
    private SpriteBatch batch;
    private Image burst;
    private Image levelFailedImage;
    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 480;
    Level level;
    public LevelFailed(Main game,Level level) {
        this.game = game;
        stage = new Stage(new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        this.level=level;
        // Load textures
        starTexture = new Texture(Gdx.files.internal("star.png"));
        settingsTexture = new Texture(Gdx.files.internal("homeIcon.png"));
        playTexture = new Texture(Gdx.files.internal("restart.png"));
        burstTexture = new Texture(Gdx.files.internal("burst.png"));
        levelFailedTexture = new Texture(Gdx.files.internal("level_failed.png"));

        // Setup background burst
        burst = new Image(burstTexture);
        burst.setSize(VIRTUAL_WIDTH * 2.5f, VIRTUAL_HEIGHT * 2.5f);
        burst.setPosition(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2, Align.center);
        burst.setOrigin(Align.center);
        stage.addActor(burst);

        burst.addAction(Actions.forever(Actions.rotateBy(360, 20f)));

        // Level Cleared text image - slightly smaller
        levelFailedImage = new Image(levelFailedTexture);

        levelFailedImage.setSize(600, 150);
        levelFailedImage.setPosition(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT * 0.8f, Align.center);
        stage.addActor(levelFailedImage);

        //stars
        float starY = VIRTUAL_HEIGHT * 0.55f;
        float starSize = 140;
        float starSpacing = 160;
        float startX = (VIRTUAL_WIDTH - (starSpacing * 2)) / 2;

        for (int i = 0; i < 3; i++) {
            Image star = new Image(starTexture);
            star.setSize(starSize, starSize);
            star.setPosition(startX + (i * starSpacing), starY, Align.center);
            star.setOrigin(Align.center);
            star.setScale(0);

            // Set all stars to semi-transparent
            star.getColor().a = 0.1f;

            // Add rotation based on position
            if (i == 0) {
                star.setRotation(-20);
            } else if (i == 2) {
                star.setRotation(20);
            }

            // Simple scale-up animation without the bounce effect
            star.addAction(Actions.sequence(
                Actions.delay(i * 0.2f),
                Actions.scaleTo(1f, 1f, 0.3f)
            ));

            stage.addActor(star);
        }




        float buttonY = VIRTUAL_HEIGHT * 0.2f;
        float settingsButtonSize = 120;
        float playButtonSize = 120;
        float buttonSpacing = 160;

        // Settings button
        ImageButton settingsButton = createButton(settingsTexture,
            VIRTUAL_WIDTH/2 - buttonSpacing/2, buttonY, settingsButtonSize);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Main.isSound) Main.sound.play();
                game.setScreen(new HomeScreen(game));
            }
        });

        // Play button
        ImageButton playButton = createButton(playTexture,
            VIRTUAL_WIDTH/2 + buttonSpacing/2, buttonY, playButtonSize);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Main.isSound) Main.sound.play();
                Level restartedLevel=level.restartLevel(level);
                game.setScreen(restartedLevel);
            }
        });

        addShadowEffect(settingsButton);
        addShadowEffect(playButton);

        stage.addActor(settingsButton);
        stage.addActor(playButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    private void addShadowEffect(ImageButton button) {
        // Add a subtle drop shadow effect
        button.addAction(Actions.forever(Actions.sequence(
            Actions.alpha(0.95f, 0.5f),
            Actions.alpha(1f, 0.5f)
        )));
    }

    private ImageButton createButton(Texture texture, float x, float y, float size) {
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        ImageButton button = new ImageButton(drawable);
        button.setSize(size, size);
        button.setPosition(x, y, Align.center);
        button.setOrigin(Align.center);

        // Enhanced hover effect
        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                button.addAction(Actions.sequence(
                    Actions.scaleTo(1.15f, 1.15f, 0.15f, Interpolation.swingOut)
                ));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                button.addAction(Actions.sequence(
                    Actions.scaleTo(1f, 1f, 0.15f, Interpolation.swingOut)
                ));
            }
        });

        return button;
    }

    @Override
    public void render(float delta) {
        // Lighter blue background color to match reference
        Gdx.gl.glClearColor(0.9f, 0.97f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        starTexture.dispose();
        settingsTexture.dispose();
        playTexture.dispose();
        burstTexture.dispose();
        levelFailedTexture.dispose();
        batch.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
