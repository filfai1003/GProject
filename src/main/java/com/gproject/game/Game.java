package com.gproject.game;

import com.gproject.game.entities.Block;
import com.gproject.game.entities.Entity;
import com.gproject.game.entities.Player;
import com.gproject.game.inventory.Inventory;
import com.gproject.io.input.KeyState;

import java.io.*;
import java.util.*;

import static com.gproject.game.Costants.CHUNK_SIZE;
import static com.gproject.game.Costants.P_MAX_HEALTH;
import static com.gproject.main.GameSyncronizer.menu;

public class Game {
    private Camera camera;
    private Player player;
    private Inventory inventory;
    private HashSet<Entity>[][] chunks;

    public Game(boolean newGame) {
        chunks = new HashSet[100][100];
        if (newGame) {
            newGame();
        } else {
            loadGame();
        }
    }

    private void newGame() {
        camera = new Camera(0, 0, 0.5);
        player = new Player(100, 100, P_MAX_HEALTH);
        inventory = null; // TODO

        // Initialize chunks
        for (int i = 0; i < chunks.length; i++) {
            for (int j = 0; j < chunks[0].length; j++) {
                chunks[i][j] = new HashSet<>();
            }
        }

        // Add initial entities
        List<Entity> entities = new ArrayList<>();
        entities.add(player);
        entities.add(new Block(0, 0, 10000, 100));
        entities.add(new Block(0, 10000, 10000, 100));
        entities.add(new Block(0, 0, 100, 10000));
        entities.add(new Block(10000, 0, 100, 10000));

        // Populate chunks with entities
        for (Entity entity : entities) {
            int startI = (int) (entity.x / CHUNK_SIZE) - 1;
            int startJ = (int) (entity.y / CHUNK_SIZE) - 1;
            int endI = (int) ((entity.x + entity.getWidth()) / CHUNK_SIZE) + 1;
            int endJ = (int) ((entity.y + entity.getHeight()) / CHUNK_SIZE) + 1;
            for (int l = startI; l <= endI; l++) {
                for (int m = startJ; m <= endJ; m++) {
                    if (l >= 0 && l < chunks.length && m >= 0 && m < chunks[0].length) {
                        chunks[l][m].add(entity);
                    }
                }
            }
        }
    }

    private void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            player = (Player) ois.readObject();
            camera = (Camera) ois.readObject();
            inventory = (Inventory) ois.readObject();
            chunks = (HashSet<Entity>[][]) ois.readObject();
            System.out.println("Game loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load game: " + e.getMessage());
            newGame();
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
        seconds = Math.min(seconds, 0.0334);
        camera.update(player, seconds);

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
        if (inputs.get("DASH") == KeyState.JUST_PRESSED) {
            player.dash();
        }
        if (inputs.get("JUMP") == KeyState.JUST_PRESSED) {
            player.jump();
        }
        if (inputs.get("GO_R") == KeyState.JUST_PRESSED || inputs.get("GO_R") == KeyState.HELD) {
            player.goRight(seconds);
        }
        if (inputs.get("GO_L") == KeyState.JUST_PRESSED || inputs.get("GO_L") == KeyState.HELD) {
            player.goLeft(seconds);
        }

        // TODO manage inventory

        /* TODO prossima sessione:
        *   Crea l'inizializazione di un arma base e di un arma speciale tipo rampino/jetpack per gestire colpi e effetti
        *   Crea oggetti per verificare effetti e altro
        *   Quindi anche gestione input per inventario, armi e oggetti
        *   Aggiungi anche la direzione in cui sta guardando il giocatore per determinare la direzione del colpo/attacco
        *   Ricorda mi mettere un oggetto spada che permetta di ricaricare il double jump in caso di colpo in aria
        * */

        Physics.update(player, chunks, seconds);
    }

    public Player getPlayer() {
        return player;
    }

    public HashSet<Entity>[][] getChunks() {
        return chunks;
    }

    public Camera getCamera() {
        return camera;
    }
}
