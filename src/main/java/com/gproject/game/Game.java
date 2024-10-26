package com.gproject.game;

import com.gproject.game.entities.Block;
import com.gproject.game.entities.Entity;
import com.gproject.game.entities.Player;
import com.gproject.io.input.KeyState;

import java.util.*;

import static com.gproject.game.Costants.P_MAX_HEALTH;
import static com.gproject.main.GameSyncronizer.menu;

public class Game {
    private Camera camera;
    private Player player;
    private HashSet<Entity>[][] chunks;

    public Game() {
        camera = new Camera(0, 0, 0.5);
        player = new Player(550, 500, P_MAX_HEALTH);
        chunks = new HashSet[100][100];
        for (int i = 0; i < chunks.length; i++) {
            for (int j = 0; j < chunks[0].length; j++) {
                chunks[i][j] = new HashSet<Entity>();
            }
        }
        chunks[0][0].add(player);

        chunks[0][0].add(new Block(0, 1010, 5000, 100));
        // TODO 9 Game Constructor
    }

    public void update(Map<String, Object> inputs, double seconds) {
        seconds = Math.min(seconds, 0.0334);
        camera.update(player, seconds);
        if (inputs.get("KB_ESC") == KeyState.JUST_PRESSED) {
            menu();
        }
        if (inputs.get("KB_UP") == KeyState.JUST_PRESSED) {
            camera.zoom();
        }
        if (inputs.get("KB_DOWN") == KeyState.JUST_PRESSED) {
            camera.deZoom();
        }
        if (inputs.get("KB_SPACE") == KeyState.JUST_PRESSED) {
            if ((inputs.get("KB_D") == KeyState.JUST_PRESSED || inputs.get("KB_D") == KeyState.HELD) && !(inputs.get("KB_A") == KeyState.JUST_PRESSED || inputs.get("KB_A") == KeyState.HELD)) {
                player.dash(1);
            } else if (!(inputs.get("KB_D") == KeyState.JUST_PRESSED || inputs.get("KB_D") == KeyState.HELD) && (inputs.get("KB_A") == KeyState.JUST_PRESSED || inputs.get("KB_A") == KeyState.HELD)) {
                player.dash(-1);
            }
        }
        if (inputs.get("KB_W") == KeyState.JUST_PRESSED) {
            player.jump();
        }
        if (inputs.get("KB_D") == KeyState.JUST_PRESSED || inputs.get("KB_D") == KeyState.HELD) {
            player.goRight(seconds);
        }
        if (inputs.get("KB_A") == KeyState.JUST_PRESSED || inputs.get("KB_A") == KeyState.HELD) {
            player.goLeft(seconds);
        }
        // TODO 5 manage input
        // TODO 5 manage entities
        // TODO 5 manage player
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
