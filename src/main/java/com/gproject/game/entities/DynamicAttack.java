package com.gproject.game.entities;

import com.gproject.game.manage.Game;

import java.util.function.Consumer;

public class DynamicAttack extends Attack {

    private double xOffset, yOffset;

    public DynamicAttack(double xOffset, double yOffset, int width, int height, Entity caster, int damage, int knockback, boolean meleeAttack, double duration, Consumer<Entity> onCollisionEffect, double reloadTime) {
        super(caster.x + caster.width/2 + xOffset, caster.y + caster.height/2 + yOffset, width, height, false, caster, damage, knockback, meleeAttack, duration, onCollisionEffect, reloadTime);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    @Override
    public void update(double seconds, Game game) {
        x = caster.x + caster.width/2 + xOffset;
        y = caster.y + caster.height/2 + yOffset;
        super.update(seconds, game);
    }
}
