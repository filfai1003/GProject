package com.gproject.game.inventory.waepons;

import com.gproject.game.entities.Attack;
import com.gproject.game.entities.Player;

import com.gproject.game.entities.PlayerDirection;
import com.gproject.game.inventory.Weapon;
import com.gproject.game.manage.Chunk;
import com.gproject.game.manage.Game;

import static com.gproject.game.manage.PhysicsAndLogic.insertNewEntity;

public class Grapling extends Weapon {
    public Grapling(Player caster) {
        super("Grapling", baseAttack(caster), null, null, null);
    }

    @Override
    public void baseAttack(Game game) {
        Chunk[][] chunks = game.getChunks();
        Player player = game.getPlayer();
        if (player.lastAttack > baseAttack.reloadTime) {
            player.lastAttack = 0;
            Attack attack = baseAttack.clone();
            if (player.direction == PlayerDirection.RIGHT) {
                attack.x = player.width + attack.x;
                attack.y = player.height / 2 - attack.height / 2;
            } else if (player.direction == PlayerDirection.LEFT) {
                attack.x = -attack.x - attack.width;
                attack.y = player.height / 2 - attack.height / 2;
                attack.velocityX = -attack.velocityX;
            }
            attack.x = player.x + attack.x;
            attack.y = player.y + attack.y;
            insertNewEntity(attack, chunks);
        }
    }

    private static Attack baseAttack(Player caster) {
        Attack baseAttack = new Attack(0, caster.height, 20, 20, false, caster, 0, 0, false, 1, null, 2);
        baseAttack.velocityX = 5000;
        baseAttack.velocityY = -5000;
        baseAttack.onCollisionEffect = ((entity) -> {
            caster.velocityX += entity.x > caster.x ? baseAttack.velocityX : -baseAttack.velocityX;
            caster.velocityY += baseAttack.velocityY;
        });
        return baseAttack;
    }
}
