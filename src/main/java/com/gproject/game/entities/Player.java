package com.gproject.game.entities;

public class Player extends LivingEntity {
    // TODO 5

    public Player(double x, double y, int width, int height, boolean affectedByGravity, boolean collidable, int velocityLimit, int friction, int health, int maxHealth, int acceleration, int jumpSpeed) {
        super(x, y, width, height, affectedByGravity, collidable, velocityLimit, friction, health, maxHealth, acceleration, jumpSpeed, false);
    }
}
