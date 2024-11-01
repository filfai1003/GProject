package com.gproject.game.entities;

import com.gproject.game.manage.Game;
import com.gproject.game.render.DynamicAnimation;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.gproject.game.manage.Costants.*;

public abstract class Entity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public double x, y;
    public double width, height;
    public double velocityX, velocityY;

    private final boolean affectedByGravity;
    private final boolean affectByCollision;
    private final double friction;
    private final double airFriction;

    public String status = " ";
    private final boolean enemy;
    protected boolean toRemove;

    public double lastOnGround = 0;
    public Map<String, DynamicAnimation> dynamicAnimations;
    public Sequence sequence;

    public Entity(double x, double y, int width, int height, boolean affectedByGravity, boolean affectByCollision,
                  int friction, int airFriction, boolean enemy) {
        this(x, y, width, height, affectedByGravity, affectByCollision, friction, airFriction, enemy, new HashMap<>());
        DynamicAnimation d = new DynamicAnimation(0, 0, width, height, 1, 1, "");
        dynamicAnimations.put("",d);
    }

    public Entity(double x, double y, int width, int height, boolean affectedByGravity, boolean affectByCollision,
                  int friction, int airFriction, boolean enemy, Map<String, DynamicAnimation> dynamicAnimations) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.affectedByGravity = affectedByGravity;
        this.affectByCollision = affectByCollision;
        this.friction = friction;
        this.airFriction = airFriction;
        this.enemy = enemy;
        this.dynamicAnimations = dynamicAnimations;
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
        for (DynamicAnimation dynamicAnimation : dynamicAnimations.values()) {
            dynamicAnimation.update(seconds);
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

    public boolean isAffectedByCollision() {
        return affectByCollision;
    }

    public double getFriction() {
        return friction;
    }

    public double getAirFriction() {
        return airFriction;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public boolean isToRemove() {
        return toRemove;
    }

    public boolean isOnGround() {
        return lastOnGround < G_COYOTE_TIME;
    }

    public String getStatus() {
        return status;
    }
}
