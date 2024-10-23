package com.gproject.game;

import com.gproject.game.Physics.Physics;
import com.gproject.game.entities.Entity;
import com.gproject.game.entities.Player;
import com.gproject.io.input.KeyState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private Camera camera;
    private Player player;
    private List<Entity>[][] chunks;
    private boolean[][] blocks;

    public Game() {
        camera = new Camera(0, 0, 0.5);
        player = new Player(550, 500, 10, 20, true, true, 100, 100, 100, false);
        chunks = new List[10][10];
        for (int i = 0; i < chunks.length; i++) {
            for (int j = 0; j < chunks[0].length; j++) {
                chunks[i][j] = new ArrayList<>();
            }
        }
        blocks = new boolean[100][80];
        for (int i = 50; i < 60; i++) {
            blocks[50][i] = true;
            blocks[60][i] = true;
            blocks[i][60] = true;
        }
        chunks[0][0].add(player);
        // TODO 9 Game Constructor
    }

    public void update(Map<String, Object> inputs, double seconds) {
        seconds = Math.min(seconds, 0.0334);
        if (inputs.get("KB_UP") == KeyState.JUST_PRESSED) {
            camera.zoom();
        }
        if (inputs.get("KB_DOWN") == KeyState.JUST_PRESSED) {
            camera.deZoom();
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
        Physics.update(player, chunks, blocks, seconds);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Entity>[][] getChunks() {
        return chunks;
    }

    public Camera getCamera() {
        return camera;
    }

    public boolean[][] getBlocks() {
        return blocks;
    }
}
