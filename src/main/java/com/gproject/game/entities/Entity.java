package com.gproject.game.entities;

import com.gproject.game.manage.Game;
import com.gproject.game.render.Animation;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.gproject.game.manage.Costants.*;

public abstract class Entity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public double x, y;
    public double width, height;
    public double velocityX, velocityY;

    public final boolean affectedByGravity;
    public boolean affectByCollision;
    public final double friction;
    public final double airFriction;

    public String status = " ";
    public String lastStatus = " ";

    public final Relation enemy;
    public boolean toRemove;

    public double lastOnGround = 0;
    public Sequence sequence;

    public Map<String, Animation> animations = new HashMap<>();

    public Entity(double x, double y, int width, int height, boolean affectedByGravity, boolean affectByCollision, int friction, int airFriction, Relation enemy) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.affectedByGravity = affectedByGravity;
        this.affectByCollision = affectByCollision;
        this.friction = friction;
        this.airFriction = airFriction;
        this.enemy = enemy;
    }

    public void update(double seconds, Game game) {
        lastOnGround += seconds;
        if (affectedByGravity) {
            velocityY += GRAVITY * seconds;
        }
        if (sequence == null) {
            applyFriction(seconds);
            applyVelocity(seconds);
        } else {
            if (this instanceof LivingEntity) {
                ((LivingEntity) this).lastAttack = 0;
            }
            sequence.update(seconds, this);
        }

        if (!Objects.equals(status, lastStatus)){
            for (Animation animation : animations.values()) {
                animation.updateFrames();
                animation.update(seconds);
            }
        } else {
            for (Animation animation : animations.values()) {
                animation.update(seconds);
            }
        }

    }

    private void applyFriction(double seconds) {
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

    public boolean isOnGround() {
        return lastOnGround < G_COYOTE_TIME;
    }
}
