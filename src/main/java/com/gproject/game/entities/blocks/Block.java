package com.gproject.game.entities.blocks;

import com.gproject.game.entities.Entity;
import com.gproject.game.entities.Relation;
import com.gproject.game.manage.Game;

import java.util.HashMap;

public class Block extends Entity {

    public Block(double x, double y, int width, int height) {
        super(x, y, width, height, false, true, 0, 0, Relation.BLOCK);
    }

    @Override
    public void update(double seconds, Game game) {}
}
