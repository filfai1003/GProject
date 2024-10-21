package com.gproject.game.entities;

public class LivingEntity extends Entity {
    private int health;
    private int maxHealth;
    private int maxSpeed;
    private int acceleration;
    private boolean enemy;

    public LivingEntity(float x, float y, int width, int height, boolean affectedByGravity, boolean collidable, float mass, int health, int maxHealth, int maxSpeed, int acceleration, boolean enemy) {
        super(x, y, width, height, affectedByGravity, collidable, mass);
        this.health = health;
        this.maxHealth = maxHealth;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.enemy = enemy;
    }

    public void goRight() {
        // TODO LivingEntity.goRight
    }

    public void goLeft() {
        // TODO LivingEntity.goLeft
    }

    public void jump() {
        // TODO LivingEntity.jump
    }
}
