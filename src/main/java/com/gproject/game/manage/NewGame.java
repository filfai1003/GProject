package com.gproject.game.manage;

import com.gproject.game.entities.blocks.Block;
import com.gproject.game.entities.monsters.Boss1;
import com.gproject.game.inventory.waepons.Grapling;
import com.gproject.game.inventory.waepons.Shield;
import com.gproject.game.inventory.waepons.Sword;
import com.gproject.game.render.Camera;
import com.gproject.game.entities.*;
import com.gproject.game.inventory.Inventory;

import static com.gproject.game.manage.Costants.*;
import static com.gproject.game.manage.PhysicsAndLogic.insertNewEntity;

public class NewGame {
    public static Game initialize(){
        Chunk[][] chunks = new Chunk[CHUNK_NX][CHUNK_NY];
        for (int i = 0; i < chunks.length; i++) {
            for (int j = 0; j < chunks[0].length; j++) {
                chunks[i][j] = new Chunk();
            }
        }

        Player player = new Player(5000, 4800);
        insertNewEntity(player, chunks);

        Inventory inventory = new Inventory();
        inventory.getWeapons().add(new Sword(player));
        inventory.getWeapons().add(new Grapling(player));
        inventory.getWeapons().add(new Shield(player));

        Camera camera = new Camera(0, 0, 0.05);

        insertNewEntity(new Block(0, 0, 500, 5000), chunks);
        insertNewEntity(new Block(0, 5000, 10000, 500), chunks);
        insertNewEntity(new Block(9500, 0, 500, 5000), chunks);
        insertNewEntity(new Boss1(200, 100), chunks);

        return new Game(camera, player, inventory, chunks);
    }

}
