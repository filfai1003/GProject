package com.gproject.game.entities;

public class LivingEntity extends Entity {
    private int health, maxHealth;
    private int acceleration, jumpSpeed;
    private boolean enemy;

    public LivingEntity(double x, double y, int width, int height, boolean affectedByGravity, boolean collidable, int velocityLimit, int frictionLimit, int health, int maxHealth, int acceleration, int jumpSpeed, boolean enemy) {
        super(x, y, width, height, affectedByGravity, collidable, velocityLimit, frictionLimit);
        this.health = health;
        this.maxHealth = maxHealth;
        this.acceleration = acceleration;
        this.jumpSpeed = jumpSpeed;
        this.enemy = enemy;
    }

    public void goRight(double seconds) {
        this.velocityX += acceleration * seconds;
    }

    public void goLeft(double seconds) {
        this.velocityX -= acceleration * seconds;
    }

    public void jump() {
        onGround =true; // TODO
        if (onGround) {
            this.velocityY = -jumpSpeed;
        }
    }
}
