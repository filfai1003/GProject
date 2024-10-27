package com.gproject.game.entities;

import java.util.HashSet;
import java.util.function.Consumer;

public class Attack extends Entity {
    protected Entity caster;
    protected HashSet<Entity> damagedEntities;
    protected int damage;
    protected int knockback;
    protected double duration;
    protected Consumer<Entity> onCollision;

    public Attack(double x, double y, int width, int height, boolean affectedByGravity, boolean affectByCollision, int velocityLimitX, int velocityLimitY, int friction, int airFriction, Entity caster, HashSet<Entity> damagedEntities, int damage, int knockback, double duration, Consumer<Entity> onCollision) {
        super(x, y, width, height, affectedByGravity, affectByCollision, velocityLimitX, velocityLimitY, friction, airFriction);
        this.caster = caster;
        this.damagedEntities = damagedEntities;
        this.damage = damage;
        this.knockback = knockback;
        this.duration = duration;
        this.onCollision = onCollision;
    }

    @Override
    public void update(double seconds) {
        super.update(seconds);
        duration -= seconds;
        if (duration < 0) {
            toRemove = true;
        }
    }

    @Override
    public void onCollision(Entity other) {
        super.onCollision(other);
        if (other == caster) {
            return;
        }
        if (!(other instanceof Block)) {
            damagedEntities.remove(other);
            if (other instanceof LivingEntity) {
                ((LivingEntity) other).health -= damage;
            }
            if (x + width < other.x + other.width){
                other.velocityX += knockback;
            } else {
                other.velocityX -= knockback;
            }
            other.velocityY += knockback;
        }
        onCollision(other);
    }
}
