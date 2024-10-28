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
        Player player = new Player(100, 100);
        Inventory inventory = new Inventory();

        // Creazione dell'attacco della spada
        Attack baseAttack = new Attack(0, 0, 400, 100, false, 0, 0, player, 1, 2000, 1, null, 1);
        baseAttack.onCollisionEffect = (entity -> {
            if (entity instanceof LivingEntity) {
                if (baseAttack.caster instanceof Player) {
                    ((Player) baseAttack.caster).onSingleJump = true;
                    if (player.direction == PlayerDirection.DOWN) {
                        player.velocityY = -player.jumpSpeed;
                    }
                }
            }
        });

        Weapon sword = new Weapon("sword", baseAttack, null, null, null);
        sword.passifEffect = (seconds -> {
            if (sword.timer > 1) {
                ((Player) baseAttack.caster).onSingleJump = true;
                sword.timer = 0;
            }
            sword.timer += seconds;
        });

        inventory.getWeapons().add(sword);
        inventory.mainWeapon = sword;

        // Creazione della griglia di blocchi (cubo)
        HashSet<Entity>[][] chunks = new HashSet[CHUNK_NX][CHUNK_NY];
        for (int i = 0; i < chunks.length; i++) {
            for (int j = 0; j < chunks[0].length; j++) {
                chunks[i][j] = new HashSet<>();
            }
        }

        List<Entity> entities = new ArrayList<>();
        entities.add(player);

        // Blocchi di bordo per creare un cubo
        int cubeSize = 5000;
        for (int x = 0; x <= cubeSize; x += 100) {
            for (int y = 0; y <= cubeSize; y += 100) {
                if (x == 0 || x == cubeSize || y == 0 || y == cubeSize) {
                    entities.add(new Block(x, y, 100, 100));
                }
            }
        }

        // Entità nemica che segue il giocatore e attacca
        LivingEntity enemy = new LivingEntity(2000, 2000, 100, 100, true, false, G_VELOCITY_LIMIT_X, G_VELOCITY_LIMIT_Y, G_FRICTION, G_AIR_FRICTION, 500, 500, G_ACCELERATION, G_ACCELERATION , true, null);
        enemy.trigger = (player1, seconds) -> {
            double distance = Math.sqrt(Math.pow(player1.x - enemy.x, 2) + Math.pow(player1.y - enemy.y, 2));

            if (distance > 10000) {
                // Nemico si avvicina al giocatore
                if (player1.x > enemy.x + enemy.width) {
                    enemy.goRight(seconds);
                } else if (player1.x + player1.width < enemy.x) {
                    enemy.goLeft(seconds);
                }
            } else if (enemy.lastAttack > 1){
                enemy.lastAttack = 0;
                // Attacco del nemico diretto verso il giocatore
                double attackVelocityX = player1.x > enemy.x ? 1000 : -1000; // Determina la direzione dell'attacco
                Attack enemyAttack = new Attack(
                        enemy.x + (attackVelocityX > 0 ? enemy.width : -0), // Posiziona l'attacco in base alla direzione
                        enemy.y,  // Mantiene l'attacco allo stesso livello del nemico
                        50,       // Larghezza dell'attacco
                        50,       // Altezza dell'attacco
                        false,    // Non è influenzato dalla gravità
                        1000,        // Nessun limite di velocità sull'asse Y
                        1000,        // Nessun limite di velocità sull'asse Y
                        enemy,    // Caster è il nemico
                        2,        // Danno dell'attacco
                        500,      // Knockback dell'attacco
                        0.5,      // Durata dell'attacco
                        null,     // Nessun effetto specifico per la collisione
                        1         // Tempo di ricarica
                );

                // Imposta la velocità orizzontale dell'attacco
                enemyAttack.velocityX = attackVelocityX;

                // Inserisce l'attacco nel gioco
                insertNewEntity(enemyAttack, chunks);
            }
        };

        entities.add(enemy);

        // Inserimento di tutte le entità nei chunk
        for (Entity entity : entities) {
            insertNewEntity(entity, chunks);
        }

        return new Game(camera, player, inventory, chunks);
    }

}
