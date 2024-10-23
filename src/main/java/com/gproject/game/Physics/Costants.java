package com.gproject.game.Physics;

public class Costants {
    // Physics
    public static final double VELOCITY_LIMIT = 1000;
    public static final double GRAVITY = 800;

    public static final int BLOCK_SIZE = 10;
    public static final int CHUNK_SIZE = 1000;
    public static final int CHUNKS_TO_UPDATE = 5;
    public static final int CHUNKS_TO_RENDER = 1;
    public static final int BLOCKS_TO_RENDER = CHUNKS_TO_RENDER * CHUNK_SIZE / BLOCK_SIZE;
}
