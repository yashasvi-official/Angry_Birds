package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

public class Level implements Screen {
    private Main game;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private int level;
    public  Hud hud;

    public static int count=1;

    public boolean isGamePaused=false;
    public GamePaused gamePaused;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private ImprovedRenderer renderer;
    public World world;
    public static Box2DDebugRenderer rendererDebug=new Box2DDebugRenderer();

    public List<Pig> pigs;
    public List<Bird> birds;
    public List<Material> blocks;



    public static List<Level> levels = new ArrayList<Level>();

    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }




    public Level (Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport =new FitViewport(2240,1280,camera);
//        mapLoader = new TmxMapLoader();
//        map = mapLoader.load("level1export.tmx");
//        renderer=new CustomMapRenderer(map,game.batch);
        camera.position.set(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,0);
        world=new World(new Vector2(0,0),true);
        pigs=new ArrayList<>();
        birds=new ArrayList<>();
        blocks=new ArrayList<>();
        gamePaused=new GamePaused(game,this);













    }

    public static void createLevels(Main game) {
        Maps.createMaps();
        for(TiledMap map:Maps.maps){
            Level obj=new Level(game);

            obj.map=map;
            obj.setLevel(count);
            count++;
            obj.hud=new Hud(game,obj);

            SmallPig.createSmallPig(obj.map,obj);
            SoldierPig.createSoldierPig(obj.map,obj);
            KingPig.createKingPig(obj.map,obj);
            Glass.createGlass(obj.map,obj);
            Wood.createWood(obj.map,obj);
            Rock.createrock(obj.map,obj);

            Red.createRed(obj.map,obj);
            Blue.createBlue(obj.map,obj);
            Yellow.createYellow(obj.map,obj);
            obj.renderer=new ImprovedRenderer(map,game.batch,obj);



            levels.add(obj);


        }


    }





    @Override
    public void show() {
        Gdx.input.setInputProcessor(hud.stage);



    }

    @Override
    public void render(float delta) {


        camera.update();
        viewport.apply();
        game.batch.setProjectionMatrix(camera.combined);
//        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        ScreenUtils.clear(0,0,0,0);
        renderer.setView(camera);


        renderer.render();
        rendererDebug.render(world,camera.combined);
        hud.stage.draw();
        if(isGamePaused) {
            Gdx.input.setInputProcessor(gamePaused.stage);
            gamePaused.stage.act();
            gamePaused.stage.draw();
        }





    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);

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
