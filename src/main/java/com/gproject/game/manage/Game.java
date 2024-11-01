package com.gproject.game.manage;

import com.gproject.game.render.Camera;
import com.gproject.game.entities.Player;
import com.gproject.game.entities.PlayerDirection;
import com.gproject.game.inventory.Inventory;
import com.gproject.io.input.KeyState;

import java.io.*;
import java.util.*;

import static com.gproject.main.GameSync.menu;

public class Game {

    public boolean debugMode = true;
    private Camera camera;
    private Player player;
    private Inventory inventory;
    private Chunk[][] chunks;

    public Game(Camera camera, Player player, Inventory inventory, Chunk[][] chunks) {
        this.camera = camera;
        this.player = player;
        this.inventory = inventory;
        this.chunks = chunks;
    }

    public Game(boolean newGame) {
        chunks = new Chunk[100][100];
        if (newGame) {
            Game g = NewGame.initialize();
            this.player = g.player;
            this.inventory = g.inventory;
            this.chunks = g.chunks;
            this.camera = g.camera;
        } else {
            loadGame();
        }
    }

    private void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            player = (Player) ois.readObject();
            camera = (Camera) ois.readObject();
            inventory = (Inventory) ois.readObject();
            chunks = (Chunk[][]) ois.readObject();
            System.out.println("Game loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load game: " + e.getMessage());
            Game g = NewGame.initialize();
            this.player = g.player;
            this.inventory = g.inventory;
            this.chunks = g.chunks;
            this.camera = g.camera;
        }
    }

    private void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame.dat"))) {
            oos.writeObject(player);
            oos.writeObject(camera);
            oos.writeObject(inventory);
            oos.writeObject(chunks);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
        }
    }

    public void update(Map<String, Object> inputs, double seconds) {
        seconds = Math.min(seconds, 0.03);
        camera.update(player, seconds);
        inventory.update(seconds);

        if (inputs.get("DEBUG_MODE") == KeyState.JUST_PRESSED) {
            debugMode = !debugMode;
        }
        if (inputs.get("ESC") == KeyState.JUST_PRESSED) {
            saveGame();
            menu();
        }
        if (inputs.get("UP") == KeyState.JUST_PRESSED) {
            camera.zoom();
        }
        if (inputs.get("DOWN") == KeyState.JUST_PRESSED) {
            camera.deZoom();
        }
        if (inputs.get("RIGHT") == KeyState.JUST_PRESSED) {
            inventory.changeMainWeapon();
        }
        if (inputs.get("LEFT") == KeyState.JUST_PRESSED) {
            inventory.changeSecondWeapon();
        }
        if (inputs.get("DASH") == KeyState.JUST_PRESSED) {
            player.dash();
        }
        if (inputs.get("JUMP") == KeyState.JUST_PRESSED) {
            player.jump();
        }
        if ((inputs.get("GO_R") == KeyState.JUST_PRESSED || inputs.get("GO_R") == KeyState.HELD) && !(inputs.get("GO_L") == KeyState.JUST_PRESSED || inputs.get("GO_L") == KeyState.HELD)) {
            player.goRight(seconds);
        }
        if ((inputs.get("GO_L") == KeyState.JUST_PRESSED || inputs.get("GO_L") == KeyState.HELD) && !(inputs.get("GO_R") == KeyState.JUST_PRESSED || inputs.get("GO_R") == KeyState.HELD)) {
            player.goLeft(seconds);
        }
        if ((inputs.get("GO_U") == KeyState.JUST_PRESSED || inputs.get("GO_U") == KeyState.HELD) && !(inputs.get("GO_D") == KeyState.JUST_PRESSED || inputs.get("GO_D") == KeyState.HELD)) {
            player.direction = PlayerDirection.UP;
        }
        if ((inputs.get("GO_D") == KeyState.JUST_PRESSED || inputs.get("GO_D") == KeyState.HELD) && !(inputs.get("GO_U") == KeyState.JUST_PRESSED || inputs.get("GO_U") == KeyState.HELD)) {
            player.direction = PlayerDirection.DOWN;
        }
        if (inputs.get("ATTACK_1") == KeyState.JUST_PRESSED) {
            if (inventory.mainWeapon != null) {
                inventory.mainWeapon.baseAttack(this);
            }
        }
        if (inputs.get("ATTACK_2") == KeyState.JUST_PRESSED) {
            if (inventory.mainWeapon != null) {
                inventory.mainWeapon.heavyAttack(this);
            }
        }
        if (inputs.get("ATTACK_3") == KeyState.JUST_PRESSED) {
            if (inventory.mainWeapon != null) {
                inventory.mainWeapon.specialAttack(this);
            }
        }
        if (inputs.get("ATTACK_4") == KeyState.JUST_PRESSED) {
            if (inventory.secondWeapon != null) {
                inventory.secondWeapon.specialAttack(this);
            }
        }

        PhysicsAndLogic.update(seconds, this);
    }

    public Player getPlayer() {
        return player;
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

    public Camera getCamera() {
        return camera;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
