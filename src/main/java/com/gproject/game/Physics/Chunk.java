package com.gproject.game.Physics;

import com.gproject.game.entities.Block;
import com.gproject.game.entities.Entity;

import java.util.List;

public class Chunk {
    private int x, y;
    private List<Entity> entities;

    public void resolveCollision(Entity entity) {
        // TODO Chunk.resolveCollision
    }

    public List<Entity> getEntities() {
        return entities;
    }

}
