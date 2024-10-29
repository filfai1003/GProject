package com.gproject.game.entities;

import com.gproject.game.manage.Game;

public class LivingEntity extends Entity {

    // Health attributes
    public int health;
    public int maxHealth;

    protected int velocityLimitX, velocityLimitY;

    // Movement attributes
    protected double acceleration;
    public int jumpSpeed;

    public double lastAttack = 0;

    // Status attribute
    protected boolean enemy;

    public int d = 1;

    // Constructor
    public LivingEntity(double x, double y, int width, int height, boolean affectedByGravity, boolean affectByCollision, int velocityLimitX, int velocityLimitY, int friction, int airFriction, int maxHealth, int acceleration, int jumpSpeed, boolean enemy) {
        super(x, y, width, height, affectedByGravity, affectByCollision, friction, airFriction);
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.velocityLimitX = velocityLimitX;
        this.velocityLimitY = velocityLimitY;
        this.acceleration = acceleration;
        this.jumpSpeed = jumpSpeed;
        this.enemy = enemy;
    }

    @Override
    public void update(double seconds, Game game) {
        super.update(seconds, game);
        lastAttack += seconds;
        if (health <= 0){
            toRemove = true;
        }
    }

    // Movement methods
    public void goRight(double seconds) {
        double netAcceleration = acceleration + (isOnGround() ? friction : airFriction);
        velocityX += netAcceleration * seconds;
        if (velocityX > velocityLimitX) {
            velocityX = velocityLimitX;
        }
    }

    public void goLeft(double seconds) {
        double netAcceleration = acceleration + (isOnGround() ? friction : airFriction);
        this.velocityX -= netAcceleration * seconds;
        if (velocityX < -velocityLimitX) {
            velocityX = -velocityLimitX;
        }
    }

    public void goDown(double seconds) {
        double netAcceleration = acceleration + airFriction;
        velocityY += netAcceleration * seconds;
        if (velocityY > velocityLimitY) {
            velocityY = velocityLimitY;
        }
    }

    public void goUp(double seconds) {
        double netAcceleration = acceleration - airFriction;
        velocityY -= netAcceleration * seconds;
        if (velocityY < -velocityLimitY) {
            velocityY = -velocityLimitY;
        }
    }

    public void jump() {
        if (isOnGround()) {
            this.velocityY = -jumpSpeed;
            lastOnGround = 1;
        }
    }

    @Override
    public String toString() {
        return "LivingEntity{" +
                "health=" + health +
                '}';
    }
}
