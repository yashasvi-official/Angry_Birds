package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

public class Level implements Screen {
    private Main game;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private CustomMapRenderer renderer;

    public static List<Level> levels = new ArrayList<Level>();



    public Level (Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport =new FitViewport(2240,1280,camera);
//        mapLoader = new TmxMapLoader();
//        map = mapLoader.load("level1export.tmx");
//        renderer=new CustomMapRenderer(map,game.batch);
        camera.position.set(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,0);











    }

    public static void createLevels(Main game) {
        Maps.createMaps();
        for(TiledMap map:Maps.maps){
            Level obj=new Level(game);
            obj.map=map;
            obj.renderer=new CustomMapRenderer(map,game.batch);
            levels.add(obj);

        }


    }





    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        camera.update();
        viewport.apply();
        game.batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(0,0,0,0);
        renderer.setView(camera);
        renderer.render();



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
