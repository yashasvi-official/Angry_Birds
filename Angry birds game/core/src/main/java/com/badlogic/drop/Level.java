package com.badlogic.drop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.physics.box2d.Body;


public class Level implements Screen {
    boolean gameOver=false;
    float timeStart=0;
    float timeLevelOver=0;

    private ParticleEffectPool woodEffectPool;
    private ParticleEffectPool glassEffectPool;
    private ParticleEffectPool rockEffectPool;
    private ArrayList<ParticleEffectPool.PooledEffect> activeBlockEffects;



    private ParticleEffectPool particleEffectPool;
    private List<ParticleEffectPool.PooledEffect> activeEffects;

    private ParticleEffect rainEffect;


    private Iterator<Bird> iterator;

    public float timeSinceStart=0;
    public  Main game;
    private FitViewport viewport;
    public OrthographicCamera camera;
    private int level;
    public  Hud hud;
    public static final float PPM=100f;
    // private Slingshot slingshot;

    public InputMultiplexer inputMultiplexer=new InputMultiplexer();
    public static int count=1;

    public boolean isGamePaused=false;
    public boolean isLevelCleared=false;
    public GamePaused gamePaused;
    public LevelClearedScreen2 levelClearedScreen;

    private TmxMapLoader mapLoader;
    public TiledMap map;
    private ImprovedRenderer renderer;
    public World world;
    public static Box2DDebugRenderer rendererDebug=new Box2DDebugRenderer();

    public List<Pig> pigs;
    public List<Bird> birds;
    public List<Material> blocks;

    private Slingshot slingshot;

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
        world=new World(new Vector2(0,-9.8f),true);

        pigs=new ArrayList<>();
        birds=new ArrayList<>();
        blocks=new ArrayList<>();
        gamePaused=new GamePaused(game,this);
        levelClearedScreen=new LevelClearedScreen2(game,this);

        ParticleEffect particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("pig_blast.p"), Gdx.files.internal(""));
        particleEffectPool = new ParticleEffectPool(particleEffect, 5, 20);
        activeEffects = new ArrayList<>();

        //pool for rock,glass,wood
        ParticleEffect woodEffect = new ParticleEffect();
        woodEffect.load(Gdx.files.internal("wood_blast.p"), Gdx.files.internal(""));
        woodEffectPool = new ParticleEffectPool(woodEffect, 5, 20);

        ParticleEffect glassEffect = new ParticleEffect();
        glassEffect.load(Gdx.files.internal("glass.p"), Gdx.files.internal(""));
        glassEffectPool = new ParticleEffectPool(glassEffect, 5, 20);

        ParticleEffect rockEffect = new ParticleEffect();
        rockEffect.load(Gdx.files.internal("smoke.p"), Gdx.files.internal(""));
        rockEffectPool = new ParticleEffectPool(rockEffect, 5, 20);

        activeBlockEffects = new ArrayList<>();


        //rain effect
        rainEffect = new ParticleEffect();
        rainEffect.load(Gdx.files.internal("rain.p"), Gdx.files.internal(""));


        iterator=birds.iterator();

        // slingshot = new Slingshot(world, this);

        // inputMultiplexer.addProcessor(slingshot);
        // inputMultiplexer.addProcessor(hud.stage);
        // Gdx.input.setInputProcessor(inputMultiplexer);


        // Initialize the slingshot
        // slingshot = new Slingshot(world, map, camera);

        // // Load the first bird into the slingshot if available
        // if (!birds.isEmpty()) {
        //     slingshot.loadBird(birds.get(0).body);
        // }

        // // Add slingshot input processor
        // inputMultiplexer.addProcessor(slingshot);
        // inputMultiplexer.addProcessor(hud.stage);
        // Gdx.input.setInputProcessor(inputMultiplexer);













    }

    public static void createLevels(Main game) {

        Maps.createMaps();
        for (TiledMap map : Maps.maps) {
            if (map == null) {
                System.err.println("Error: Map is null!");
                continue; // Skip this map if it's null
            }


            Level obj = new Level(game);
//            obj.level=Level.count;
            obj.map = map; // Ensure map is assigned before using it
            obj.setLevel(count);
            count++;
            obj.createSingleLevel(obj);


            levels.add(obj);
//            Level.count++;
        }
    }
    public void createSingleLevel(Level obj){
        obj.hud = new Hud(game, obj);

        // Initialize slingshot after map is assigned

        SmallPig.createSmallPig(obj.map, obj);
        SoldierPig.createSoldierPig(obj.map, obj);
        KingPig.createKingPig(obj.map, obj);
        Glass.createGlass(obj.map, obj);
        Wood.createWood(obj.map, obj);
        Rock.createrock(obj.map, obj);

        Red.createRed(obj.map, obj);
        Blue.createBlue(obj.map, obj);
        Yellow.createYellow(obj.map, obj);
        Ground.createGround(obj.map, obj);
        Wall.createWall(obj.map,obj);

        obj.slingshot = new Slingshot(obj.world, obj.map, obj.camera);
        obj.renderer = new ImprovedRenderer(obj.map, game.batch, obj,(1/Main.PPM));
        obj.world.setContactListener(new WorldContactListener(obj));


    }

    public Level restartLevel(Level level){
        Level obj=new Level(game);
        obj.setLevel(level.level);
        obj.map=level.map;
        obj.createSingleLevel(obj);

        for(Level n:levels){
            if(n.getLevel()==level.getLevel()){
                levels.set(level.getLevel()-1,obj);

            }
        }
        disposeLevel(level);
        return obj;

    }

    public void disposeLevel(Level levelToRemove) {
        // Dispose of Box2D world
        if (levelToRemove.world != null) {
            // Destroy all bodies in the world
            Array<Body> bodies = new Array<Body>();
            levelToRemove.world.getBodies(bodies);
            for (Body body : bodies) {
                levelToRemove.world.destroyBody(body);
            }

            // Dispose of the world
            levelToRemove.world.dispose();
        }

        // Dispose of associated resources
        if (levelToRemove.renderer != null) {
            levelToRemove.renderer.dispose();
        }

//        if (levelToRemove.map != null) {
//            levelToRemove.map.dispose();
//        }

//        if (levelToRemove.hud != null) {
//            levelToRemove.hud.dispose();
//        }

        // Remove from levels list
//        levels.remove(levelToRemove);

        // Optional: Decrement level count if you're tracking it
//        Level.count--;
    }








    private void triggerParticleEffect(float x, float y) {
        ParticleEffectPool.PooledEffect effect = particleEffectPool.obtain();
        effect.setPosition(x, y);
        activeEffects.add(effect);
    }

    private void updateParticleEffects(float delta) {
        // Update and remove finished effects
        Iterator<ParticleEffectPool.PooledEffect> iterator = activeEffects.iterator();
        while (iterator.hasNext()) {
            ParticleEffectPool.PooledEffect effect = iterator.next();
            effect.update(delta);
            if (effect.isComplete()) {
                effect.free();
                iterator.remove();
            }
        }
    }

    private void renderParticleEffects(SpriteBatch batch) {
        for (ParticleEffectPool.PooledEffect effect : activeEffects) {
            effect.draw(batch);
        }
    }

    private void triggerBlockEffect(ParticleEffectPool effectPool, float x, float y) {
        ParticleEffectPool.PooledEffect effect = effectPool.obtain();
        effect.setPosition(x, y);
        activeBlockEffects.add(effect); // Add the effect to the block effects list
    }

    private void updateAndRenderBlockEffects(SpriteBatch batch, float delta) {
//        batch.begin();

        // Iterate backward to safely remove completed effects
        for (int i = activeBlockEffects.size() - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = activeBlockEffects.get(i);

            // Update and draw the effect
            effect.update(delta);
            effect.draw(batch);

            // If the effect is complete, free and remove it
            if (effect.isComplete()) {
                effect.free(); // Return it to the appropriate pool
                activeBlockEffects.remove(i);
            }
        }

//        batch.end();
    }






    public void birdLoading() {
        if(slingshot.activeBird==null){
            if(!birds.isEmpty()){
                slingshot.activeBird=birds.getFirst();
                slingshot.loadBird(slingshot.activeBird.body);
            }
            else{
                gameOver=true;

            }

        }

    }
    public void checkLevelCleared(){
        if(pigs.isEmpty()){
            gameOver=true;
        }
    }

    public void  isLevelOver(float delta){
        if(gameOver) {
            timeLevelOver += delta;
            if(timeLevelOver < 3f) {
                return;
            }
            timeLevelOver = 0;

            if(pigs.isEmpty()) {
                // Unlock next level when current level is cleared
                int nextLevelIndex = this.level;  // level is 1-based, array is 0-based
                if (nextLevelIndex < Main.unlockedLevels.length) {
                    Main.unlockedLevels[nextLevelIndex] = true;
                }

                // Existing star logic
                if(blocks.isEmpty()) {
                    LevelClearedManager.star3 = true;
                } else {
                    LevelClearedManager.star2 = true;
                }
            } else {
                LevelClearedManager.star1 = true;
            }

            LevelClearedManager.levelCleared(game, this);
        }
    }



    public void removeBird(float delta){
        if(slingshot.activeBird!=null){
            if(slingshot.activeBird.body.getType() == BodyDef.BodyType.DynamicBody && slingshot.activeBird.body.getLinearVelocity().isZero()){
                timeStart+=delta;
                if(timeStart<=0.8f){
                    return;
                }
                timeStart=0;
                if(slingshot.activeBird instanceof Blue){
                    Blue currBird=((Blue) slingshot.activeBird);
                    Iterator<Blue> it=((Blue) slingshot.activeBird).splitBirds.iterator();

                    while(it.hasNext()){
                        Blue obj=it.next();
                        world.destroyBody(obj.body);
                        it.remove();

                    }
                    currBird.abilityUsed=false;


                }
                world.destroyBody(slingshot.activeBird.body);
                birds.remove(slingshot.activeBird);
                slingshot.activeBird = null;

            }


        }
    }





    @Override
    public void show() {
        rainEffect.start();

        inputMultiplexer.addProcessor(slingshot);
        inputMultiplexer.addProcessor(hud.stage);
        Gdx.input.setInputProcessor(inputMultiplexer);




    }
    private void removeBlock() {
        List<Material> toRemove = new ArrayList<>();
        for (Material block : blocks) {
            if (block.toDestroy) {
                if (block instanceof Wood) {
                    Wood wood=(Wood)block;
                    Vector2 position = wood.body.getPosition();
                    triggerBlockEffect(woodEffectPool, position.x * PPM-200, position.y * PPM-50);
                    world.destroyBody(wood.body);
                    toRemove.add(block);
                } else if (block instanceof Glass) {
                    Glass glass=(Glass)block;
                    Vector2 position = glass.body.getPosition();
                    triggerBlockEffect(glassEffectPool, position.x * PPM-200, position.y * PPM-50);
                    world.destroyBody(glass.body);
                    toRemove.add(block);
                }
                else if (block instanceof Rock) {
                    Rock rock=(Rock)block;
                    Vector2 position = rock.body.getPosition();

                    triggerBlockEffect(rockEffectPool, position.x * PPM-200, position.y * PPM-50);
                    world.destroyBody(rock.body);
                    toRemove.add(block);
                }
            }
        }
        blocks.removeAll(toRemove);
    }

    public void removePigs(){
        List<Pig> toRemove = new ArrayList<>();
        for (Pig pig : pigs) {
            if(pig.toDestroy){
                if(pig instanceof SmallPig){
                    SmallPig smallPig = (SmallPig) pig;

                    Vector2 position = ((SmallPig)pig).body.getPosition();
                    triggerParticleEffect(position.x * Main.PPM- smallPig.getWidth()/2-200, position.y * Main.PPM- smallPig.getHeight()/2-50);

                    world.destroyBody(((SmallPig) pig).body);
                    toRemove.add(pig);

                }
                else if(pig instanceof SoldierPig){
                    SoldierPig soldierPig = (SoldierPig) pig;
                    Vector2 position = ((SoldierPig)pig).body.getPosition();
                    triggerParticleEffect(position.x * Main.PPM- soldierPig.getWidth()/2-200, position.y * Main.PPM- soldierPig.getHeight()/2-50);


                    world.destroyBody(((SoldierPig) pig).body);
                    toRemove.add(pig);
                }
                else if(pig instanceof KingPig){
                    KingPig kingPig = (KingPig) pig;
                    Vector2 position = ((KingPig)pig).body.getPosition();
                    triggerParticleEffect(position.x * Main.PPM- kingPig.getWidth()/2-200, position.y * Main.PPM- kingPig.getHeight()/2-50);

                    world.destroyBody(((KingPig) pig).body);
                    toRemove.add(pig);
                }
            }

        }
        pigs.removeAll(toRemove);

    }

    @Override
    public void render(float delta) {
        timeSinceStart+=delta;

        world.step(1/60f,6,2);
        removeBird(delta);
        birdLoading();

        checkLevelCleared();

        isLevelOver(delta);

        removeBlock();
        removePigs();




        if(Gdx.input.isKeyJustPressed((Input.Keys.SPACE))){
            slingshot.activeBird.specialAbility(this);
        }


        camera.update();
        viewport.apply();
        game.batch.setProjectionMatrix(camera.combined);
        //        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        ScreenUtils.clear(0,0,0,0);
        renderer.setView(camera);



        renderer.render();
        rendererDebug.render(world,camera.combined);
        hud.stage.draw();
        if(isLevelCleared){
            Gdx.input.setInputProcessor(levelClearedScreen.stage);
            levelClearedScreen.render(delta);
        }
        if(isGamePaused) {
            Gdx.input.setInputProcessor(gamePaused.stage);
            gamePaused.stage.act();
            gamePaused.stage.draw();
        }


        updateParticleEffects(delta);
        rainEffect.update(delta);
        game.batch.begin();
        renderParticleEffects(game.batch);
        updateAndRenderBlockEffects(game.batch,delta);
        rainEffect.draw(game.batch);
        slingshot.render(game.batch);

        game.batch.end();

        // Render the slingshot
        // slingshot.render();





    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.viewportWidth = viewport.getWorldWidth();
        camera.viewportHeight = viewport.getWorldHeight();
        camera.update();

        // Recreate input processors
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(slingshot);
        inputMultiplexer.addProcessor(hud.stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        // Update HUD stage viewport
        hud.stage.getViewport().update(width, height, true);
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
        // slingshot.dispose();
    }
}
