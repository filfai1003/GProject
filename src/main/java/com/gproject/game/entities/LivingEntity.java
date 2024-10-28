package com.gproject.game.entities;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LivingEntity extends Entity {

    // Health attributes
    protected int health;
    protected int maxHealth;

    // Movement attributes
    protected double acceleration;
    public int jumpSpeed;

    public double lastAttack = 0;

    public transient BiConsumer<Player, Double> trigger;


    // Status attribute
    protected boolean enemy;

    // Constructor
    public LivingEntity(double x, double y, int width, int height, boolean affectedByGravity, boolean affectByCollision, int velocityLimitX, int velocityLimitY, int friction, int airFriction, int health, int maxHealth, int acceleration, int jumpSpeed, boolean enemy, BiConsumer<Player, Double> trigger) {
        super(x, y, width, height, affectedByGravity, affectByCollision, velocityLimitX, velocityLimitY, friction, airFriction);
        this.health = health;
        this.maxHealth = maxHealth;
        this.acceleration = acceleration;
        this.jumpSpeed = jumpSpeed;
        this.enemy = enemy;
        this.trigger = trigger;
    }

    @Override
    public void update(double seconds) {
        super.update(seconds);
        lastAttack += seconds;
        if (health <= 0){
            toRemove = true;
        }
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

    @Override
    public String toString() {
        return "LivingEntity{" +
                "health=" + health +
                '}';
    }
}
