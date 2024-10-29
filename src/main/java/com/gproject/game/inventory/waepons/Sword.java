package com.gproject.game.inventory.waepons;

import com.gproject.game.manage.Chunk;
import com.gproject.game.manage.Game;
import com.gproject.game.entities.*;
import com.gproject.game.inventory.Weapon;

import java.util.HashSet;
import java.util.function.Consumer;

import static com.gproject.game.manage.PhysicsAndLogic.insertNewEntity;

public class Sword extends Weapon {

    public Sword(LivingEntity caster) {
        super("Sword", baseAttack(caster), heavyAttack(caster), specialAttack(caster), passifEffect(caster));
    }

    @Override
    public void heavyAttack(Game game) {

        Player player = game.getPlayer();
        player.removeCharge(2);

        Chunk[][] chunks = game.getChunks();

        player.lastAttack = 0;
        Attack attack = heavyAttack.clone();

        attack.x = player.width + attack.x;
        attack.y = player.height / 2 - attack.height / 2;

        attack.x = player.x + attack.x;
        attack.y = player.y + attack.y;
        insertNewEntity(attack, chunks);
    }

    private static Attack baseAttack(Entity caster) {
        Attack baseAttack = new Attack(0, 0, 50, 150, false, caster, 1, 1000, 1, null, 1);
        baseAttack.onCollisionEffect = (entity -> {
            ((Player) caster).addCharge(1);
            if (entity instanceof LivingEntity) {
                ((Player) caster).onSingleJump = true;
                if (((Player) caster).direction == PlayerDirection.DOWN) {
                    caster.velocityY = -((Player) caster).jumpSpeed;
                }
            }
        });
        return baseAttack;
    }

    private static Attack heavyAttack(Entity caster) {
        Attack heavyAttack = new Attack(-caster.width / 2 - 150, 0, 300, 50, false, caster, 100, 1500, 1, null, 1);
        heavyAttack.onCollisionEffect = (entity -> {
        });
        return heavyAttack;
    }

    private static Attack specialAttack(Entity caster) { // TODO
        Attack specialAttack = new Attack(0, 0, 0, 0, false, caster, 0, 0, 0, null, 2);
        specialAttack.onCollisionEffect = (entity -> {
        });
        return specialAttack;
    }

    private static Consumer<Double> passifEffect(Entity caster) { // TODO
        return ((seconds) -> {
        });
    }
}
