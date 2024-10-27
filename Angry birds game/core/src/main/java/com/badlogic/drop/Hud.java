package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
    public Stage stage;
    private FitViewport viewport;
    Label levelNo;
    Main game;
    Level level;
    ImageButton pause;
    Skin skin;

    public Hud(Main game,Level level) {
        this.game = game;
        viewport=new FitViewport(Main.w_width, Main.w_height,new OrthographicCamera());
        stage=new Stage(viewport,game.batch);
        skin=new Skin(Gdx.files.internal("final_skin/final_skin.json"));
        this.level=level;




        Table table=new Table();
        table.setFillParent(true);
        table.top().left();
        table.pad(50);
        table.defaults().space(40);

        TextureRegionDrawable pauseText=new TextureRegionDrawable(new Texture("pause.png"));

        pause=new ImageButton(pauseText);
        pause.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(Main.isSound) Main.sound.play();
                level.isGamePaused=true;



            }

        });
        table.add(pause);


        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("angrybirdswhite");
        labelStyle.fontColor = skin.getColor("brown");

        levelNo=new Label("Level No:"+level.getLevel(), labelStyle);
        table.add(levelNo);
        stage.addActor(table);





    }

}
