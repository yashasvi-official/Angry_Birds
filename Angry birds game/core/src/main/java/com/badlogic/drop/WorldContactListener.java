package com.badlogic.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener {
    private Level level;

    public WorldContactListener(Level level) {
        this.level=level;

    }
    @Override
    public void beginContact(Contact contact) {
        if(level.timeSinceStart<=0.5f){
            return;
        }
        Object A=contact.getFixtureA().getUserData();
        Object B=contact.getFixtureB().getUserData();


        if(A instanceof Bird || B instanceof Bird){
            Bird bird= A instanceof Bird? (Bird)A:(Bird)B;
            Object obj= A instanceof Bird? B:A;

            if(obj instanceof Material){
                Material block=(Material)obj;
                block.takeDamage(bird.damage);

            }
            else if(obj instanceof Pig){
//                Pig pig= (Pig)obj;
//                pig.takeDamage(bird.damage);
//                System.out.println(pig.health);



            }
            else if(obj instanceof Ground){
                Ground ground= (Ground)obj;
                //logic

            }
        }
        if(A instanceof Material || B instanceof Material){
            Material block=A instanceof Material? (Material)A:(Material)B;
            Object obj= A instanceof Material? B:A;




            if(obj instanceof Material){
                System.out.println("wood blocks in contact");
                //do nothing.
            }
            else if(obj instanceof Pig){
                //post solve-
            }
            else if(obj instanceof Ground){
//                Ground ground= (Ground)obj;
//                block.takeDamage(ground.damage);
                //post solve
            }

        }

        if(A instanceof Pig || B instanceof Pig){
            Pig pig= A instanceof Pig? (Pig)A:(Pig)B;
            Object obj= A instanceof Pig? B:A;

            if(obj instanceof Ground){
                //post solve.
            }
            else if(obj instanceof Pig){
                //do nothing
            }
        }



//        if (A instanceof Material && B instanceof Bird) {
//            Material block = (Material) A;
//            Bird bird = (Bird) B;
//            block.takeDamage(5);
//        } else if (B instanceof Material && A instanceof Bird) {
//            Material block = (Material) B;
//            Bird bird = (Bird) A;
//            block.takeDamage(5);

//        }

    }

    @Override
    public void endContact(Contact contact) {


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        float damageThreshold = 2f; // Minimum collision force to cause damage
        float collisionForce = impulse.getNormalImpulses()[0]; // Primary collision impulse

        if (collisionForce > damageThreshold) {
//            System.out.println("Collision force: "+collisionForce);
            Object A = contact.getFixtureA().getUserData();
            Object B = contact.getFixtureB().getUserData();

            if(A instanceof Pig || B instanceof Pig){
                Pig pig= A instanceof Pig? (Pig)A:(Pig)B;
                Object obj= A instanceof Pig? B:A;

                if(obj instanceof Material){
                    Material block=(Material)obj;
                    pig.takeDamage(block.damage);
                }
                else if(obj instanceof Ground){
                    Ground ground=(Ground)obj;
                    pig.takeDamage(ground.damage);
                }
                else if(obj instanceof Bird){
                    Bird bird=(Bird)obj;
                    pig.takeDamage(bird.damage);
                }


            }
            else if(A instanceof Ground && B instanceof Material){
                Ground ground=(Ground)A;
                Material block=(Material)B;
                block.takeDamage(ground.damage);

            }
            else if(A instanceof Material && B instanceof Ground){
                Ground ground=(Ground)B;
                Material block=(Material)A;
                block.takeDamage(ground.damage);

            }
//            if(A instanceof Pig && B instanceof Material) {
//                Pig pig = (Pig) A;
//                pig.takeDamage(collisionForce);
//            }
//            else if (B instanceof Pig && A instanceof Material) {
//                Pig pig = (Pig) B;
//                pig.takeDamage(collisionForce);
//            }
        }



    }
}
