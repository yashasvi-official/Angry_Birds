package com.badlogic.drop;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;
import java.util.List;

public class Maps {
    private TmxMapLoader mapLoader;

    public static List<TiledMap> maps;

    public static void createMaps(){
        maps=new ArrayList<>();
        TmxMapLoader mapLoader=new TmxMapLoader();
        TiledMap map1=mapLoader.load("level1.tmx");
        TiledMap map2=mapLoader.load("level2.tmx");
        TiledMap map3=mapLoader.load("level1.tmx");
        maps.add(map1);
        maps.add(map2);
        maps.add(map3);




    }
}
