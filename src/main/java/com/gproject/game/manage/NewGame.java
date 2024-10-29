package com.gproject.game.manage;

import com.gproject.game.render.Camera;
import com.gproject.game.entities.*;
import com.gproject.game.entities.monster.Mage;
import com.gproject.game.inventory.Inventory;
import com.gproject.game.inventory.Weapon;
import com.gproject.game.inventory.waepons.Sword;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.gproject.game.manage.Costants.*;
import static com.gproject.game.manage.PhysicsAndLogic.insertNewEntity;

public class NewGame {
    public static Game initialize(){
        Camera camera = new Camera(0, 0, 0.5);

        Player player = new Player(100, 100);

        Inventory inventory = new Inventory();
        Weapon sword = new Sword(player);
        inventory.getWeapons().add(sword);
        inventory.mainWeapon = sword;

        // Creazione della griglia di blocchi (cubo)
        Chunk[][] chunks = new Chunk[CHUNK_NX][CHUNK_NY];
        for (int i = 0; i < chunks.length; i++) {
            for (int j = 0; j < chunks[0].length; j++) {
                chunks[i][j] = new Chunk();
            }
        }

        List<Entity> entities = new ArrayList<>();
        entities.add(player);

        entities.add(new Block(0, 0, 5000, 100));
        entities.add(new Block(0, 5000, 5000, 100));
        entities.add(new Block(0, 0, 100, 5000));
        entities.add(new Block(5000, 0, 100, 5000));
        entities.add(new Mage(300, 300, true));

        // Inserimento di tutte le entità nei chunk
        for (Entity entity : entities) {
            insertNewEntity(entity, chunks);
        }

        return new Game(camera, player, inventory, chunks);
    }

}
