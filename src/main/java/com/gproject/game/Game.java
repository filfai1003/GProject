package com.gproject.game;

import com.gproject.game.Physics.Chunk;
import com.gproject.game.Physics.Physics;
import com.gproject.game.entities.Block;
import com.gproject.game.entities.Player;

import java.util.Map;

public class Game {
    private Player player;
    private Chunk[][] chunks;
    private Block[][] blocks;

    public Game(int level) {
        // TODO Game Constructor
    }

    public void update(Map<String, Object> inputs, double seconds) {
        seconds = Math.min(seconds, 0.04);
        // TODO Game.update
        // manage input
        // manage ia and player
        Physics.update(player, chunks, blocks, seconds);
    }
}
