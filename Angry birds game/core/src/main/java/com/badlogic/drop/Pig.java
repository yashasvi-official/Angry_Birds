package com.badlogic.drop;

public abstract class  Pig {
    protected float health;


    public Pig(float health) {
        this.health = health;

    }
    public abstract void takeDamage(int damage);


}
