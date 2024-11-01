package com.gproject.game.entities.blocks;

import com.gproject.game.entities.Entity;
import com.gproject.game.manage.Game;
import com.gproject.game.render.DynamicAnimation;

import java.util.HashMap;

public class Block extends Entity {

    public Block(double x, double y, int width, int height) {
        super(x, y, width, height, false, true, 0, 0, false, new HashMap<>());
        dynamicAnimations.put(" ", new DynamicAnimation(0, 0, width, height, 1, 1, "assets/images/Blocks/Block1"));
    }

    @Override
    public void update(double seconds, Game game) {}
}
