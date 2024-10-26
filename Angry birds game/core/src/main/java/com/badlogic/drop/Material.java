package com.badlogic.drop;

public abstract class Material {
    float health;

    public Material(float health) {
        this.health = health;
    }
    public abstract void takeDamage(float damage);

}
