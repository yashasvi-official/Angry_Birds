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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SelectLevel implements Screen {



    private Main game;
    BitmapFont font;
    Image background;
    FitViewport viewport;
    OrthographicCamera camera;
    Stage stage;
    Table table;
    private Skin skin;
    public Sprite sprite;
    ImageButton backButton;
    ImageButton settings;

    Label heading;
    public SelectLevel (Main game) {
        this.game = game;
        skin=new Skin(Gdx.files.internal("final_skin/final_skin.json"));
        camera = new OrthographicCamera();
        viewport=new FitViewport(Main.w_width, Main.w_height, camera);
        stage = new Stage(viewport,game.batch);
        Gdx.input.setInputProcessor(stage);
//        Texture sling=new Texture(Gdx.files.internal("play.png"));
//        sprite=new Sprite(sling);
//        sprite.setCenter(300,300);

        Texture backTexture = new Texture(Gdx.files.internal("home.png"));
        background = new Image(backTexture);
        stage.addActor(background);

//        heading = new Label("Select Level", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("final_skin/angrybirds-regular(7).fnt")), Color.BLACK));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("angrybirdswhite");
        labelStyle.fontColor = skin.getColor("brown");



        Label heading = new Label("Select Level", labelStyle);
        heading.setFontScale(2);

        heading.setPosition((viewport.getWorldWidth()-heading.getWidth()*2)/2,(viewport.getWorldHeight()-viewport.getWorldHeight()/5));

        stage.addActor(heading);

        //back button-
        TextureRegionDrawable back=new TextureRegionDrawable(new Texture("back.png"));
        backButton = new ImageButton(back);
        backButton.setPosition(20, Main.w_height - backButton.getHeight() - 20);

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new  HomeScreen(game));
            }
        });
        stage.addActor(backButton);

        //settings-
        Texture texture = new Texture("settings.png");
//        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        TextureRegionDrawable drawable = new TextureRegionDrawable(texture);
        settings = new ImageButton(drawable);

        settings.setPosition(Main.w_width- settings.getWidth() - 20, Main.w_height - settings.getHeight() - 20);
        settings.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("settings pressed");
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

        table.padTop(400);
        table.padLeft(200);
        table.padRight(200);

        table.defaults().space(50);
        int count=0;
        for(int i=1;i<=3;i++){
            if(count%3==0){
                table.row();
            }
            TextButton button = new TextButton("Level"+Integer.toString(i),skin);
//            button.setSize(300,400);
            final int levelIndex=i-1;
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {


                    game.setScreen(Level.levels.get(levelIndex));
                }
            });

            table.add(button);
            count++;



        }




    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,0);
        stage.act(delta);
        stage.draw();
//        game.batch.begin();
//        sprite.draw(game.batch);
//        game.batch.end();


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
