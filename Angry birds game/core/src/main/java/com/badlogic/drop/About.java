package com.badlogic.drop;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class About implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private Texture background, gradient, combinedBirds, blackBird, aboutHeading;
    private BitmapFont font;
    private OrthographicCamera camera;
    Stage stage;

    private float elapsedTime;
    private float blackBirdX, blackBirdY;
    private float blackBirdStartX, blackBirdStartY;
    private float combinedBirdsX, combinedBirdsY;
    private boolean blackBirdStopped;
    private StringBuilder displayedText;
    private int currentCharIndex;
    private boolean isTextComplete;
    ImageButton backButton;

    public About(Main game) {
        this.game = game;
        this.batch = game.batch;
        stage=new Stage();



        // Load textures
        background = new Texture("backgroundForAbout.png");
        gradient = new Texture("gradient.png"); // New gradient image
        combinedBirds = new Texture("combined_birds.png");
        blackBird = new Texture("black_birdAbout.png");


        aboutHeading = new Texture("about_heading.png");

        // Initialize font
        font = new BitmapFont();
        font.getData().setScale(2.5f);
        font.setColor(Color.BLACK);

        // Initialize camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // Animation variables
        elapsedTime = 0;
        blackBirdStartX = -100; // Start off-screen
        blackBirdStartY = Gdx.graphics.getHeight() / 2; // Start from the middle
        blackBirdX = blackBirdStartX;
        blackBirdY = blackBirdStartY;
        blackBirdStopped = false;
        // positioning of the group birds
        combinedBirdsX = 50; // Absolute right corner
        combinedBirdsY = 0;

        displayedText = new StringBuilder();
        currentCharIndex = 0;
        isTextComplete = false;

        TextureRegionDrawable backUp=new TextureRegionDrawable(new Texture("back.png"));
        TextureRegionDrawable backHover=new TextureRegionDrawable(new Texture("back_hover.png"));
        ImageButton.ImageButtonStyle style=new ImageButton.ImageButtonStyle();
        style.up=backUp;
        style.over=backHover;

        backButton = new ImageButton(style);
        backButton.setPosition(20, Main.w_height - backButton.getHeight() - 200);

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(Main.isSound) Main.sound.play();
                game.setScreen(new  HomeScreen(game));
            }
        });

        stage.addActor(backButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsedTime += delta;

        // Update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Draw background
        batch.draw(background, 0, 0, Main.w_width, Main.w_height);

        // Draw gradient above background
        batch.draw(gradient, 0, 0, Main.w_width, Main.w_height);

        // "About" heading - Bounce in effect
        if (elapsedTime > 1) {
            float headingScale = Interpolation.bounceOut.apply(0, 1, Math.min((elapsedTime - 1) / 1.5f, 1));
            float headingWidth = 500 * headingScale;
            float headingHeight = 150 * headingScale;
            batch.draw(
                aboutHeading,
                (Gdx.graphics.getWidth() - headingWidth) / 2, // Centered horizontally
                Gdx.graphics.getHeight() - headingHeight - 50, // Position near the top
                headingWidth,
                headingHeight
            );
        }

        // Combined birds with shadow in bottom-right corner
        if (elapsedTime > 2) {
            batch.setColor(0, 0, 0, 0.4f); // Shadow effect
            batch.draw(combinedBirds, combinedBirdsX - 10, combinedBirdsY - 10, 310, 260); // Shadow offset
            batch.setColor(Color.WHITE); // Reset to normal color
            batch.draw(combinedBirds, combinedBirdsX, combinedBirdsY, 450, 375); // Original image
        }

        // Black bird projectile motion
        if (elapsedTime > 3) {
            if (!blackBirdStopped) {
                // Update position for a smooth diagonal trajectory
                float t = Math.min((elapsedTime - 3) / 2f, 1); // Time normalized for motion duration
                blackBirdX = Interpolation.sine.apply(blackBirdStartX, Gdx.graphics.getWidth() - 200, t);
                blackBirdY = Interpolation.sine.apply(blackBirdStartY, Gdx.graphics.getHeight() - 150, t);

                // Stop the bird when it reaches its target
                if (t >= 1) {
                    blackBirdStopped = true;
                }
            }

            batch.draw(blackBird, blackBirdX, blackBirdY, 100, 100);
        }

        // Typing effect for the "About" paragraph
        if (elapsedTime > 4 && !isTextComplete) {
            String aboutText = "Angry Birds is a classic mobile physics-based game where players catapult unique birds at structures inhabited by green pigs. Developed for the OOPS course, this project demonstrates key object-oriented programming principles through careful design. We implemented inheritance for different bird types, encapsulation for game mechanics, polymorphism in bird abilities, and robust class hierarchies to manage game objects, physics interactions, and level progressions. By creating reusable classes for birds, structures, and game states, we showcased our AP concepts.";
            if (currentCharIndex < aboutText.length()) {
                displayedText.append(aboutText.charAt(currentCharIndex));
                currentCharIndex++;
            } else {
                isTextComplete = true;
            }
        }

        // Draw text - Centered and wrapped
        font.draw(
            batch,
            displayedText.toString(),
            50,
            Gdx.graphics.getHeight() - 300,
            Gdx.graphics.getWidth() - 100, // Wrap text within screen width
            1, // Align text to the left
            true
        );
        stage.act(delta);
        stage.draw();


        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    


    @Override
    public void hide() { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        gradient.dispose();
        combinedBirds.dispose();
        blackBird.dispose();
        aboutHeading.dispose();
        font.dispose();
    }
}
