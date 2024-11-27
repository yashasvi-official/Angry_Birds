package com.badlogic.drop;

public abstract class Material {
    int health;
    int damage;
    public boolean toDestroy=false;



    public Material(int health,int damage) {
        this.health = health;
        this.damage = damage;
    }
    public abstract void takeDamage(float damage);

}
