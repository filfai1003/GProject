package com.gproject.game.manage;

import com.gproject.game.entities.Entity;
import com.gproject.game.render.Animation;

import java.util.HashSet;

public class Chunk {
    public HashSet<Entity> entities = new HashSet<>();
    public HashSet<Animation> animations = new HashSet<>();

    public Chunk() {
    }
}
