package com.gproject.game;

import com.gproject.game.entities.*;
import com.gproject.game.inventory.Inventory;
import com.gproject.game.inventory.Weapon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.gproject.game.Costants.*;
import static com.gproject.game.PhysicsAndLogic.insertNewEntity;

public class NewGame {
    public static Game initialize(){

        Camera camera = new Camera(0, 0, 0.5);


        Player player = new Player(100, 100, P_MAX_HEALTH);


        Inventory inventory = new Inventory();
        Attack attack = new Attack(0, 0, 400, 100, false, 0, 0, player, 10, 10, 1, null, 1);
        attack.onCollisionEffect = ((entity) -> {
            if (entity instanceof LivingEntity) {
                attack.damage = 0;
                if (attack.caster instanceof Player) {
                    ((Player) attack.caster).onSingleJump = true;
                    if (player.direction == PlayerDirection.DOWN){
                        player.velocityY = -player.jumpSpeed;
                    }
                }
            }
        });
        Weapon sword = new Weapon(attack, null, null);
        inventory.getWeapons().add(sword);
        inventory.mainWeapon = sword;

        HashSet<Entity>[][] chunks = new HashSet[CHUNK_NX][CHUNK_NY];
        for (int i = 0; i < chunks.length; i++) {
            for (int j = 0; j < chunks[0].length; j++) {
                chunks[i][j] = new HashSet<>();
            }
        }
        List<Entity> entities = new ArrayList<>();
        entities.add(player);
        entities.add(new Block(0, 0, 10000, 100));
        entities.add(new Block(0, 2000, 10000, 100));
        entities.add(new Block(0, 0, 100, 10000));
        entities.add(new Block(10000, 0, 100, 10000));
        entities.add(new LivingEntity(1000, 100, 500, 500, true, true, G_VELOCITY_LIMIT_X, G_VELOCITY_LIMIT_Y, G_FRICTION, G_AIR_FRICTION, 1000, 1000, 1000, 1000,true));
        for (Entity entity : entities) {
            insertNewEntity(entity, chunks);
        }
        return new Game(camera, player, inventory, chunks);
    }
}
