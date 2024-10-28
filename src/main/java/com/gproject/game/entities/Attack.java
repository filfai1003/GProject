package com.gproject.game.entities;

import java.util.HashSet;
import java.util.function.Consumer;

public class Attack extends Entity implements Cloneable {

    public Entity caster;
    public HashSet<Entity> damagedEntities = new HashSet<>();
    public int damage;
    public int knockback;
    public double duration;
    public transient Consumer<Entity> onCollisionEffect;
    public double reloadTime;

    public Attack(double x, double y, int width, int height, boolean affectedByGravity, int velocityLimitX, int velocityLimitY, Entity caster, int damage, int knockback, double duration, Consumer<Entity> onCollisionEffect, double reloadTime) {
        super(x, y, width, height, affectedByGravity, true, velocityLimitX, velocityLimitY, 0, 0);
        this.caster = caster;
        this.damage = damage;
        this.knockback = knockback;
        this.duration = duration;
        this.onCollisionEffect = onCollisionEffect;
        this.reloadTime = reloadTime;
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
        if (!damagedEntities.contains(other)) {
            if (!(other instanceof Block)) {
                damagedEntities.add(other);
                if (other instanceof LivingEntity) {
                    ((LivingEntity) other).health -= damage;
                    if ((velocityLimitX == 0 && caster.x + caster.width / 2 < other.x + other.width / 2) || (x + width / 2 < other.x + other.width / 2)) {
                        other.velocityX += knockback;
                    } else {
                        other.velocityX -= knockback;
                    }
                    other.velocityY -= knockback;
                    if (onCollisionEffect != null) {
                        onCollisionEffect.accept(other);
                    }
                }
            }
        }
    }

    public Attack clone() {
        try {
            Attack clonedAttack = (Attack) super.clone();

            clonedAttack.damagedEntities = new HashSet<>(this.damagedEntities);

            if (this.onCollisionEffect != null) {
                clonedAttack.onCollisionEffect = this.onCollisionEffect;
            }

            return clonedAttack;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Questo errore non dovrebbe verificarsi
        }
    }
}
