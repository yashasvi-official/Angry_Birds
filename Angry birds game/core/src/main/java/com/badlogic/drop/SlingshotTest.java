//package com.badlogic.drop;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Body;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import com.badlogic.gdx.physics.box2d.FixtureDef;
//import com.badlogic.gdx.physics.box2d.World;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.AfterEach;
//
//
//import java.util.Timer;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class SlingshotTest {
//    private World world;
//    private Body bird;
//    private Slingshot slingshot;
//    public Vector2 frontAnchor;
//    public Vector2 backAnchor;
//
//    @BeforeEach
//    public void setUp() {
//
//        world = new World(new Vector2(0, -9.8f), true);
//
//
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        bodyDef.position.set(0, 0);
//        bird = world.createBody(bodyDef);
//
//
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.density = 1f;
//        fixtureDef.shape = new com.badlogic.gdx.physics.box2d.CircleShape();
//        fixtureDef.shape.setRadius(0.5f);
//        bird.createFixture(fixtureDef);
//
//        frontAnchor = new Vector2(5, 5); // Example front anchor position
//        backAnchor = new Vector2(3, 3); //
//
//
//        slingshot=new Slingshot(world,null,null);
//        slingshot.frontAnchor=frontAnchor;
//        slingshot.backAnchor=backAnchor;
//
//    }
//
//    @AfterEach
//    public void tearDown() {
//        world.dispose();
//    }
//
//    @Test
//    public void testLoadBird() {
//
//        slingshot.loadBird(bird);
//
//
//        assertEquals(BodyDef.BodyType.DynamicBody, bird.getType());
//
//        // Check the impulse applied to the bird
//        Vector2 expectedImpulse = new Vector2(
//            (slingshot.frontAnchor.x + slingshot.backAnchor.x) / 2 - bird.getPosition().x,
//            Math.abs((slingshot.frontAnchor.y + slingshot.backAnchor.y) / 2 - bird.getPosition().y) * 4.5f
//        );
//
//
//        assertTrue(bird.getLinearVelocity().len() > 0);
//
//
////        Timer.schedule(new Timer.Task() {
////            @Override
////            public void run() {
////                // Assert bird is back to static and reset position
////                assertEquals(BodyDef.BodyType.StaticBody, bird.getType());
////                assertEquals(new Vector2(1, 1), bird.getPosition());
////            }
////        }, 0.6f);
//    }
//}
//
