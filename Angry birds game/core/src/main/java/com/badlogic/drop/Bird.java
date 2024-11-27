package com.badlogic.drop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class Bird {
    int damage;
    Color color;

    BodyDef bdef;
    FixtureDef fdef;
    CircleShape cshape;
    Body body;
    public Bird(int damage,Color color) {
        this.damage = damage;
        this.color = color;

    }

    public abstract void specialAbility(Level level);


}

