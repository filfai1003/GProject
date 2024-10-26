package com.gproject.game.entities;

import static com.gproject.game.Costants.*;

public class Entity {

    // Static attribute
    private static int nextId = 0;

    // Final attribute
    protected final int id;

    // Position attributes
    public double x, y;
    protected int width, height;

    // Velocity attributes
    public double velocityX, velocityY;
    protected int velocityLimit;

    // Physics-related attributes
    protected boolean affectedByGravity;
    protected boolean affectByCollision;
    protected int friction;
    protected int airFriction;

    // Time on ground
    public double lastOnGround = 0;

    // Constructor
    public Entity(double x, double y, int width, int height, boolean affectedByGravity, boolean affectByCollision, int velocityLimit, int friction, int airFriction) {
        this.id = nextId++;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.affectedByGravity = affectedByGravity;
        this.affectByCollision = affectByCollision;
        this.velocityLimit = velocityLimit;
        this.friction = friction;
        this.airFriction = airFriction;
    }

    // Update entity status
    public void update(double seconds) {
        lastOnGround += seconds;
        if (isAffectedByGravity()) {
            velocityY += GRAVITY * seconds;
        }
        if (isOnGround()) {
            if (velocityX > 0) {
                velocityX = Math.max(0, velocityX - friction * seconds);
            } else if (velocityX < 0) {
                velocityX = Math.min(0, velocityX + friction * seconds);
            }
        } else {
            if (velocityX > 0) {
                velocityX = Math.max(0, velocityX - airFriction * seconds);
            } else if (velocityX < 0) {
                velocityX = Math.min(0, velocityX + airFriction * seconds);
            }
        }
    }

    // Apply velocity changes
    public void applyVelocity(double seconds) {
        if (Math.abs(velocityX) > velocityLimit) {
            velocityX = (velocityX > 0) ? velocityLimit : -velocityLimit;
        }
        if (Math.abs(velocityY) > velocityLimit) {
            velocityY = (velocityY > 0) ? velocityLimit : -velocityLimit;
        }

        x += velocityX * seconds;
        y += velocityY * seconds;
    }

    // Getters and Setters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Status checks
    public boolean isAffectedByGravity() {
        return affectedByGravity;
    }

    public boolean isAffectByCollision() {
        return affectByCollision;
    }

    public boolean isOnGround() {
        return lastOnGround < G_COYOTE_TIME;
    }
}
