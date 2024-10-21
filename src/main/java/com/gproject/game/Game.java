package com.gproject.game;

import com.gproject.game.entities.Entity;

import java.util.List;
import java.util.Map;

public class Game {
    private Entity player;
    private List<Entity> entities;

    public Game(int level) {
        // TODO Game Constructor
    }

    public void update(Map<String, Object> inputs, double seconds) {
        seconds = Math.min(seconds, 0.04);
        // TODO Game.update
        // manage input
        Physics.update(entities, seconds);
    }
}
