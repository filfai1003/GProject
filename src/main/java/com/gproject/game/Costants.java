package com.gproject.game;

public class Costants {
    // PLAYER
    public static final int P_VELOCITY_LIMIT = 500;
    public static final int P_ACCELERATION = 500;
    public static final int P_FRICTION = 500;
    public static final int P_AIR_FRICTION = 0;

    public static final int P_WIDTH = 50;
    public static final int P_HEIGHT = 180;

    public static final int P_JUMP_SPEED = 500;
    public static final double P_COYOTE_TIME = 0.1;
    public static final int P_DASH_SPEED = 1000;
    public static final double P_DASH_TIME = 0.25;
    public static final double P_DASH_RELOAD_TIME = 1;

    public static final int P_MAX_HEALTH = 2500;

    // GENERAL
    public static final double G_COYOTE_TIME = 0.1;

    // PHYSiSCS
    public static final int GRAVITY = 200;

    // STRUCTURAL
    public static final int CHUNK_SIZE = 1000;
    public static final int CHUNKS_TO_UPDATE = 1;
    public static final int CHUNKS_TO_RENDER = 1;
}
