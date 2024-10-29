package com.gproject.game.entities;

import com.gproject.game.manage.Game;

import java.io.Serial;
import java.io.Serializable;

import static com.gproject.game.manage.Costants.*;

public abstract class Entity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Position attributes
    public double x, y;
    public double width, height;

    // Velocity attributes
    public double velocityX, velocityY;

    // Physics-related attributes
    protected boolean affectedByGravity;
    protected boolean affectByCollision;
    protected double friction;
    protected double airFriction;

    // Time on ground
    public double lastOnGround = 0;

    protected boolean toRemove;

    // Constructor
    public Entity(double x, double y, int width, int height, boolean affectedByGravity, boolean affectByCollision, int friction, int airFriction) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.affectedByGravity = affectedByGravity;
        this.affectByCollision = affectByCollision;
        this.friction = friction;
        this.airFriction = airFriction;
    }

    // Update entity status
    public void update(double seconds, Game game) {
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
        if (velocityY > 0) {
            velocityY = Math.max(0, velocityY - airFriction * seconds);
        } else if (velocityY < 0) {
            velocityY = Math.min(0, velocityY + airFriction * seconds);
        }
    }

    // Apply velocity changes
    public void applyVelocity(double seconds) {
       if (Math.abs(velocityX) > G_VELOCITY_LIMIT_X) {
           velocityX = (velocityX > 0) ? G_VELOCITY_LIMIT_X : -G_VELOCITY_LIMIT_X;
       }
       if (Math.abs(velocityY) > G_VELOCITY_LIMIT_Y) {
           velocityY = (velocityY > 0) ? G_VELOCITY_LIMIT_Y : -G_VELOCITY_LIMIT_Y;
       }

        x += velocityX * seconds;
        y += velocityY * seconds;
    }

    public void onCollision(Entity other) {}

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
