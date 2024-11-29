package com.badlogic.drop;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private int progressLevel;
    public int getProgressLevel(){
        return progressLevel;
    }
    public void setProgressLevel(int level){
        this.progressLevel=level;
    }

    public GameState(int progressLevel){
        this.progressLevel=progressLevel;

    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameState loadFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameState) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void loadGame(GameState state) {
        int currentlevel=state.getProgressLevel();
        for(int i=0;i<Main.unlockedLevels.length;i++){
            Main.unlockedLevels[i]=false;
        }
        for(int i=0;i<Main.unlockedLevels.length;i++){
            if(i<currentlevel){
                Main.unlockedLevels[i]=true;

            }
        }
        Main.currentLevel=currentlevel;




    }
    public static void newGame(){
        Main.gameState=new GameState(1);
        Main.gameState.saveToFile("game_save.dat");
        for(Level level: Level.levels){
            Level restartedLevel=level.restartLevel(level);

        }


        for(int i=0;i<Main.unlockedLevels.length;i++){
            Main.unlockedLevels[i]=false;
        }
        Main.currentLevel=0;
        Main.unlockedLevels[0]=true;
    }
}



//     private int currentLevel;
//     private List<Bird> birds;
//     private List<Pig> pigs;
//     private List<Material> blocks;
// //    public Level level;



//     private boolean isGamePaused;
//     private boolean isLevelCleared;
//     boolean gameOver;
//     float timeStart;
//     float timeLevelOver;

//     public GameState(int currentLevel, List<Bird> birds, List<Pig> pigs, List<Material> blocks, boolean isGamePaused, boolean isLevelCleared) {
//         this.currentLevel = currentLevel;
//         this.birds = birds;
//         this.pigs = pigs;
//         this.blocks = blocks;
//         this.isGamePaused = isGamePaused;
//         this.isLevelCleared = isLevelCleared;
//     }
//     public GameState(int currentLevel,Level level){
//         saveState(level);
// //        this.level=level;

//         this.currentLevel=currentLevel;
//         this.birds=level.birds;
//         this.pigs=level.pigs;
//         this.blocks=level.blocks;
//         this.isGamePaused=level.isGamePaused;
//         this.isLevelCleared=level.isLevelCleared;
//         this.timeStart=level.timeStart;
//         this.timeLevelOver=level.timeLevelOver;
//         this.gameOver=level.gameOver;



//     }


//     public  void saveState(Level level){
//         for(Bird bird:level.birds){
//             bird.x=bird.body.getPosition().x;
//             bird.y=bird.body.getPosition().y;
//             bird.angle=bird.body.getAngle();
//             // bird.texture=bird.texture;






//         }
//         for(Pig pig:level.pigs){
//             pig.x=pig.body.getPosition().x;
//             pig.y=pig.body.getPosition().y;
//             pig.angle=pig.body.getAngle();
//             pig.texture=pig.texture;


//         }
//         for(Material block:level.blocks){
//             block.x=block.body.getPosition().x;
//             block.y=block.body.getPosition().y;
//             block.angle=block.body.getAngle();
//             block.texture=block.texture;



//         }

//     }
//     private void replaceBird(Bird oldBird, Class<? extends Bird> birdType,Level level) {
//         Bird newBird;

//         // Create a new bird instance based on the type
//         if (birdType.equals(Red.class)) {
//             newBird = new Red(Red.givenDamage);
//         } else if (birdType.equals(Blue.class)) {
//             newBird = new Blue(Blue.givenDamage);
//         } else if (birdType.equals(Yellow.class)) {
//             newBird = new Yellow(Yellow.givenDamage);
//         } else {
//             throw new IllegalArgumentException("Unsupported bird type: " + birdType);
//         }

//         // Ensure the texture path is not null before creating a Texture
//         if (oldBird.texturePath != null) {
//             newBird.texture = new TextureRegion(new Texture(oldBird.texturePath));
//         } else {
//             // Handle the case where texturePath is null
//             System.err.println("Warning: Texture path is null for bird of type " + birdType.getSimpleName());
//             // You might want to set a default texture or handle this case differently
//         }

//         // Copy properties from old bird
//         float x = oldBird.x;
//         float y = oldBird.y;
//         float height = oldBird.height;
//         float width = oldBird.width;
//         float angle = oldBird.angle;

//         newBird.bdef.type = BodyDef.BodyType.StaticBody;
//         newBird.bdef.position.set((x + width / 2) / Main.PPM, (y + height / 2) / Main.PPM);
//         newBird.bdef.angle = angle;

//         newBird.body = level.world.createBody(newBird.bdef);
//         newBird.cshape.setRadius((width / 2) / Main.PPM);
//         newBird.fdef.shape = newBird.cshape;
//         // Customize as needed
//         // newBird.fdef.density = 0.8f;
//         newBird.fdef.friction = 1f;

//         newBird.body.createFixture(newBird.fdef).setUserData(newBird);

//         // Replace the bird in the list
//         int index = this.birds.indexOf(oldBird);
//         if (index != -1) {
//             this.birds.set(index, newBird);
//         }
//     }

//     private void replaceMaterial(Material oldMaterial, Class<? extends Material> materialType,Level level) {
//         Material newMaterial;

//         // Create a new material instance based on the type
//         if (materialType.equals(Wood.class)) {
//             newMaterial = new Wood(Wood.woodHealth, Wood.woodDamage);
//             newMaterial.texture=new TextureRegion(new Texture(oldMaterial.texturePath));
//         } else if (materialType.equals(Glass.class)) {
//             newMaterial = new Glass(Glass.glassHealth, Glass.glassDamage);
//             newMaterial.texture=new TextureRegion(new Texture(oldMaterial.texturePath));
//         } else if (materialType.equals(Rock.class)) {
//             newMaterial = new Rock(Rock.rockHealth, Rock.rockDamage);
//             newMaterial.texture=new TextureRegion(new Texture(oldMaterial.texturePath));
//         } else {
//             throw new IllegalArgumentException("Unsupported material type: " + materialType);
//         }

//         // Copy properties from the old material
//         float x = oldMaterial.x;
//         float y = oldMaterial.y;
//         float height = oldMaterial.height;
//         float width = oldMaterial.width;
//         float angle = oldMaterial.angle;

//         newMaterial.bdef.type = BodyDef.BodyType.StaticBody;
//         newMaterial.bdef.position.set((x + width / 2) / Main.PPM, (y + height / 2) / Main.PPM);
//         newMaterial.bdef.angle = angle;
//         newMaterial.body = level.world.createBody(newMaterial.bdef);

//         // Set material-specific shape and fixture
//         if ("box".equals(oldMaterial.objShape)) {
//             newMaterial.shape.setAsBox((width / 2) / Main.PPM, (height / 2) / Main.PPM);
//             newMaterial.fdef.shape = newMaterial.shape;
//             newMaterial.fdef.density = 1f;
//             newMaterial.fdef.friction = 0.5f;
//             newMaterial.fdef.restitution = 0.1f;
//             newMaterial.body.createFixture(newMaterial.fdef).setUserData(newMaterial);
//         } else { // Circular shape
//             newMaterial.cshape.setRadius((width / 2) / Main.PPM);
//             newMaterial.fdef.shape = newMaterial.cshape;
//             newMaterial.fdef.density = 1f;
//             newMaterial.fdef.friction = 0.5f;
//             newMaterial.fdef.restitution = 0.1f;
//             newMaterial.body.createFixture(newMaterial.fdef).setUserData(newMaterial);
//         }

//         // Replace the material in the list
//         int index = this.blocks.indexOf(oldMaterial);
//         if (index != -1) {
//             this.blocks.set(index, newMaterial);
//         }
//     }
//     private void replacePig(Pig oldPig, Class<? extends Pig> pigType,Level level) {
//         Pig newPig;

//         // Create a new pig instance based on the type
//         if (pigType.equals(SmallPig.class)) {
//             newPig = new SmallPig(SmallPig.smallHealth);
//             newPig.texture=new TextureRegion(new Texture(oldPig.texturePath));
//         } else if (pigType.equals(SoldierPig.class)) {
//             newPig = new SoldierPig(SoldierPig.soldierHealth);
//             newPig.texture=new TextureRegion(new Texture(oldPig.texturePath));
//         } else if (pigType.equals(KingPig.class)) {
//             newPig = new KingPig(KingPig.kingHealth);
//             newPig.texture=new TextureRegion(new Texture(oldPig.texturePath));
//         } else {
//             throw new IllegalArgumentException("Unsupported pig type: " + pigType);
//         }

//         // Copy properties from the old pig
//         float x = oldPig.x;
//         float y = oldPig.y;
//         float height = oldPig.height;
//         float width = oldPig.width;
//         float angle = oldPig.angle;

//         newPig.bdef.type = BodyDef.BodyType.DynamicBody;
//         newPig.bdef.position.set((x + width / 2) / Main.PPM, (y + height / 2) / Main.PPM);
//         newPig.bdef.angle = angle;

//         newPig.body = level.world.createBody(newPig.bdef);
//         newPig.cshape.setRadius((width / 2) / Main.PPM);
//         newPig.fdef.shape = newPig.cshape;

//         // Set fixture properties
//         newPig.fdef.density = 1f;
//         newPig.fdef.friction = 0.5f;
//         newPig.fdef.restitution = 0.1f;
//         newPig.body.createFixture(newPig.fdef).setUserData(newPig);

//         // Replace the pig in the list
//         int index = this.pigs.indexOf(oldPig);
//         if (index != -1) {
//             this.pigs.set(index, newPig);
//         }
//     }



//     public Level loadGame(){
//         Level returnObj=null;
//         for(Level obj: Level.levels){
//             if(obj.getLevel()==this.currentLevel){

//                 for(Bird bird:this.birds){
//                     if (bird instanceof Red) {
//                         replaceBird(bird, Red.class,obj);
//                     } else if (bird instanceof Blue) {
//                         replaceBird(bird, Blue.class,obj);
//                     } else if (bird instanceof Yellow) {
//                         replaceBird(bird, Yellow.class,obj);
//                     }
//                 }
//                 for(Material block:this.blocks){
//                     if (block instanceof Wood) {
//                         replaceMaterial(block, Wood.class,obj);
//                     } else if (block instanceof Glass) {
//                         replaceMaterial(block, Glass.class,obj);
//                     } else if (block instanceof Rock) {
//                         replaceMaterial(block, Rock.class,obj);
//                     }


//                 }
//                 for(Pig pig:this.pigs){
//                     if (pig instanceof SmallPig) {
//                         replacePig(pig, SmallPig.class,obj);
//                     } else if (pig instanceof SoldierPig) {
//                         replacePig(pig, SoldierPig.class,obj);
//                     } else if (pig instanceof KingPig) {
//                         replacePig(pig, KingPig.class,obj);
//                     }

//                 }

//                 obj.birds=this.birds;
//                 obj.blocks=this.blocks;
//                 obj.pigs=this.pigs;

// //                obj.gameOver=this.gameOver;
// //                obj.

// //                for(Bird bird: obj.birds){
// //                    bird.body.getPosition().x=bird.x;
// //                    bird.body.getPosition().y=bird.y;
// //
// //                }
// //                for(Pig pig:obj.pigs){
// //                    pig.body.getPosition().x=pig.x;
// //                    pig.body.getPosition().y=pig.y;
// //
// //                }
// //                for(Material block:obj.blocks){
// //                    block.body.getPosition().x=block.x;
// //                    block.body.getPosition().y=block.y;
// //
// //                }
//                 returnObj=obj;
//                 break;
//             }

//         }
//         return returnObj;
//     }

//     // Getters and setters
// }

