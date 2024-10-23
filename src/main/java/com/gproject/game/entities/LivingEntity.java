package com.gproject.game.entities;

public class LivingEntity extends Entity {
    private int health, maxHealth;
    private int acceleration;
    private boolean enemy;

    public LivingEntity(float x, float y, int width, int height, boolean affectedByGravity, boolean collidable, int health, int maxHealth, int acceleration, boolean enemy) {
        super(x, y, width, height, affectedByGravity, collidable);
        this.health = health;
        this.maxHealth = maxHealth;
        this.acceleration = acceleration;
        this.enemy = enemy;
    }

    public void goRight(double seconds) {
        this.velocityX += acceleration * seconds;
    }

    public void goLeft(double seconds) {
        this.velocityX -= acceleration * seconds;
    }

    public void jump() {
        this.velocityY = -acceleration;
    }
}
