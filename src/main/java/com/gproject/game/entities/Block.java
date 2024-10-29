package com.gproject.game.entities;

import com.gproject.game.manage.Game;

public class Block extends Entity {
    public Block(double x, double y, int width, int height) {
        super(x, y, width, height, false, true, 0, 0);
    }

    @Override
    public void update(double seconds, Game game) {}
}
