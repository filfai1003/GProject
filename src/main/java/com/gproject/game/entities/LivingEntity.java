package com.gproject.game.entities;

public class LivingEntity extends Entity {

    // Health attributes
    protected int health;
    protected int maxHealth;

    // Movement attributes
    protected int acceleration;
    protected int jumpSpeed;

    // Status attribute
    protected boolean enemy;

    // Constructor
    public LivingEntity(double x, double y, int width, int height, boolean affectedByGravity, boolean collidable, int velocityLimit, int friction, int airFriction, int health, int maxHealth, int acceleration, int jumpSpeed, boolean enemy) {
        super(x, y, width, height, affectedByGravity, collidable, velocityLimit, friction, airFriction);
        this.health = health;
        this.maxHealth = maxHealth;
        this.acceleration = acceleration;
        this.jumpSpeed = jumpSpeed;
        this.enemy = enemy;
    }

    // Movement methods
    public void goRight(double seconds) {
        double netAcceleration = acceleration + (isOnGround() ? friction : airFriction);
        this.velocityX += netAcceleration * seconds;
    }

    public void goLeft(double seconds) {
        double netAcceleration = acceleration + (isOnGround() ? friction : airFriction);
        this.velocityX -= netAcceleration * seconds;
    }

    public void jump() {
        if (isOnGround()) {
            this.velocityY = -jumpSpeed;
            lastOnGround = 1;
        }
    }
}
