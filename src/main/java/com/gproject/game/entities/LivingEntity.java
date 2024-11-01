package com.gproject.game.entities;

import com.gproject.game.manage.Game;

public class LivingEntity extends Entity {

    public int health;
    public int maxHealth;

    protected int velocityLimitX, velocityLimitY;
    protected double acceleration;
    public int jumpSpeed;

    public double lastAttack = 0;
    public int d = 1;

    public LivingEntity(double x, double y, int width, int height, boolean affectedByGravity,
                        boolean affectByCollision, int velocityLimitX, int velocityLimitY,
                        int friction, int airFriction, int maxHealth, int acceleration,
                        int jumpSpeed, Relation enemy) {
        super(x, y, width, height, affectedByGravity, affectByCollision, friction, airFriction, enemy);
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.velocityLimitX = velocityLimitX;
        this.velocityLimitY = velocityLimitY;
        this.acceleration = acceleration;
        this.jumpSpeed = jumpSpeed;
    }


    @Override
    public void update(double seconds, Game game) {
        super.update(seconds, game);
        lastAttack += seconds;
        if (health <= 0) {
            toRemove = true;
        }
    }

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
        status = "jump";
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
