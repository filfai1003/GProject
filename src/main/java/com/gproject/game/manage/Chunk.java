package com.gproject.game.manage;

import com.gproject.game.entities.Entity;
import com.gproject.game.render.Animation;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;

public class Chunk implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public HashSet<Entity> entities = new HashSet<>();
    public HashSet<Animation> animations = new HashSet<>();

    public Chunk() {}
}
