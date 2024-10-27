package com.gproject.game.entities;

import java.io.Serial;
import java.io.Serializable;

import static com.gproject.game.Costants.*;

public class Entity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Position attributes
    public double x, y;
    protected int width, height;

    // Velocity attributes
    public double velocityX, velocityY;
    protected int velocityLimitX, velocityLimitY;

    // Physics-related attributes
    protected boolean affectedByGravity;
    protected boolean affectByCollision;
    protected int friction;
    protected int airFriction;

    // Time on ground
    public double lastOnGround = 0;

    protected boolean toRemove;

    // Constructor
    public Entity(double x, double y, int width, int height, boolean affectedByGravity, boolean affectByCollision, int velocityLimitX, int velocityLimitY, int friction, int airFriction) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.affectedByGravity = affectedByGravity;
        this.affectByCollision = affectByCollision;
        this.velocityLimitX = velocityLimitX;
        this.velocityLimitY = velocityLimitY;
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
        if (Math.abs(velocityX) > velocityLimitX) {
            velocityX = (velocityX > 0) ? velocityLimitX : -velocityLimitX;
        }
        if (Math.abs(velocityY) > velocityLimitY) {
            velocityY = (velocityY > 0) ? velocityLimitY : -velocityLimitY;
        }

        x += velocityX * seconds;
        y += velocityY * seconds;
    }

    public void onCollision(Entity other) {}

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

    public boolean isToRemove() {
        return toRemove;
    }
}
