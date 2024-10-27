package com.badlogic.drop;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Timer;

public class Journal implements Screen {
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture brownBackgroundTexture;
    private Texture titleTexture;
    private Texture backButtonTexture;
    private Texture[] birdTextures;
    private Texture[] cloudTextures;
    private BitmapFont font;

    private float WORLD_WIDTH = 800;
    private float WORLD_HEIGHT = 480;

    private final float BIRD_SIZE = 180f;
    private final float RED_BIRD_SIZE = 150f;
    private final float CLOUD_WIDTH = 300f;
    private final float CLOUD_HEIGHT = 120f;
    private final float Y_OFFSET_BIRDS = WORLD_HEIGHT - 280;
    private final float Y_OFFSET_CLOUDS = Y_OFFSET_BIRDS - 140;

    private float brownBackgroundX = 40;
    private float brownBackgroundY = 60;
    private float brownBackgroundWidth = WORLD_WIDTH - 80;
    private float brownBackgroundHeight = WORLD_HEIGHT - 120;

    private float[] birdAlpha;
    private float[] cloudAlpha;
    private float[] birdOffsets;
    private float[] cloudOffsets;
    private int animationIndex = 0;
    private float animationTime = 0;

    private Camera camera;
    private Viewport viewport;

    // Increased back button size
    private float backButtonX = 20;
    private float backButtonY = WORLD_HEIGHT - 70;
    private float backButtonSize = 60;

    private boolean isBackButtonHovered = false;
    private boolean wasBackButtonPressed = false;
    private final Screen previousScreen;

    public Journal(Screen previousScreen) {
        this.previousScreen = previousScreen;
        batch = new SpriteBatch();
        loadAssets();
        setupAnimations();

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
    }

    private void loadAssets() {
        backgroundTexture = new Texture("journal.png");
        brownBackgroundTexture = new Texture("brown.png");
        titleTexture = new Texture("birds_title.png");
        backButtonTexture = new Texture("back.png");

        birdTextures = new Texture[3];
        birdTextures[0] = new Texture("red_bird.png");
        birdTextures[1] = new Texture("yellow_bird.png");
        birdTextures[2] = new Texture("blue_bird.png");

        cloudTextures = new Texture[3];
        cloudTextures[0] = new Texture("cloud1.png");
        cloudTextures[1] = new Texture("cloud2.png");
        cloudTextures[2] = new Texture("cloud3.png");

        font = new BitmapFont();
        font.getData().setScale(2f);
    }

    private void setupAnimations() {
        birdAlpha = new float[]{0, 0, 0};
        cloudAlpha = new float[]{0, 0, 0};
        birdOffsets = new float[]{0, 0, 0};
        cloudOffsets = new float[]{0, 0, 0};

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (animationIndex < 3) {
                    final int index = animationIndex;
                    Timer.schedule(new Timer.Task() {
                        float progress = 0;
                        @Override
                        public void run() {
                            progress += 0.1f;
                            if (progress <= 1) {
                                float bounce = 1 + (float)Math.sin(progress * Math.PI) * 0.2f;
                                birdAlpha[index] = progress;
                                cloudAlpha[index] = progress;
                                birdOffsets[index] = (1 - bounce) * 30;
                            } else {
                                this.cancel();
                            }
                        }
                    }, 0, 0.05f);
                    animationIndex++;
                }
            }
        }, 0.5f, 0.7f, 2);
    }

    private void drawTitleWithShadow() {
        float titleWidth = brownBackgroundWidth * 0.5f;
        float titleHeight = brownBackgroundHeight * 0.15f;
        float titleX = brownBackgroundX + (brownBackgroundWidth - titleWidth) / 2;
        float titleY = brownBackgroundY + brownBackgroundHeight - titleHeight - 10;

        batch.setColor(0, 0, 0, 0.6f);
        batch.draw(titleTexture, titleX + 6, titleY - 6, titleWidth, titleHeight);

        batch.setColor(1, 1, 1, 1);
        batch.draw(titleTexture, titleX, titleY, titleWidth, titleHeight);
    }

    private void drawBackButton() {
        batch.setColor(1, 1, 1, isBackButtonHovered ? 0.7f : 1f);
        batch.draw(backButtonTexture, backButtonX, backButtonY, backButtonSize, backButtonSize);
    }

    private void handleInput() {
        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touchPos);

        isBackButtonHovered = touchPos.x > backButtonX && touchPos.x < backButtonX + backButtonSize &&
            touchPos.y > backButtonY && touchPos.y < backButtonY + backButtonSize;

        // Improved back button logic
        if (isBackButtonHovered && Gdx.input.justTouched() && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            wasBackButtonPressed = true;
        }


        if (wasBackButtonPressed && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (isBackButtonHovered) {
                HomeScreen.isJournal = false; // Set isJournal to false
                ((Game) Gdx.app.getApplicationListener()).setScreen(previousScreen);
            }
            wasBackButtonPressed = false;
        }
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        animationTime += delta;
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);

        batch.setColor(1, 1, 1, 0.9f);
        batch.draw(brownBackgroundTexture, brownBackgroundX, brownBackgroundY, brownBackgroundWidth, brownBackgroundHeight);
        batch.setColor(1, 1, 1, 1);

        drawTitleWithShadow();
        drawBackButton();

        float horizontalSpacing = WORLD_WIDTH / 4;

        for (int i = 0; i < 3; i++) {
            float xPos = horizontalSpacing * (i + 1) - (BIRD_SIZE / 2);
            float birdFloat = (float)Math.sin(animationTime * 2 + i * 2) * 10;
            float cloudFloat = (float)Math.sin(animationTime + i * 2) * 5;

            batch.setColor(1, 1, 1, birdAlpha[i]);
            batch.draw(i == 0 ? birdTextures[i] : birdTextures[i],
                xPos,
                Y_OFFSET_BIRDS + birdFloat + birdOffsets[i],
                i == 0 ? RED_BIRD_SIZE : BIRD_SIZE,
                i == 0 ? RED_BIRD_SIZE : BIRD_SIZE);

            batch.setColor(1, 1, 1, cloudAlpha[i]);
            batch.draw(cloudTextures[i],
                xPos - (CLOUD_WIDTH - BIRD_SIZE) / 2 + cloudFloat,
                Y_OFFSET_CLOUDS + cloudOffsets[i],
                CLOUD_WIDTH,
                CLOUD_HEIGHT);
        }

        batch.setColor(1, 1, 1, 1);
        batch.end();

        handleInput();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0f);
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        brownBackgroundTexture.dispose();
        titleTexture.dispose();
        backButtonTexture.dispose();
        font.dispose();
        for (Texture texture : birdTextures) {
            texture.dispose();
        }
        for (Texture texture : cloudTextures) {
            texture.dispose();
        }
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
