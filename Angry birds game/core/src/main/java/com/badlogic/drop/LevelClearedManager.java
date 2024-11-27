package com.badlogic.drop;

public class LevelClearedManager {
    public static boolean star1;
    public static boolean star2;
    public static boolean star3;

    public static void levelCleared(Main game,Level level) {
        if(star1){
            star1 = false;
            game.setScreen(new LevelClearedScreen(game, level));
        }
        else if(star2){
            star2 = false;
            game.setScreen(new LevelClearedScreen2(game, level));
        }
        else if(star3){
            star3 = false;
            game.setScreen(new LevelClearedScreen(game, level));
        }



    }
}
