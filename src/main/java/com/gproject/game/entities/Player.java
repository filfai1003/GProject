package com.gproject.game.entities;

public class Player extends LivingEntity {
    // TODO 5
    public Player(float x, float y, int width, int height, boolean affectedByGravity, boolean collidable, int health, int maxHealth, int acceleration, boolean enemy) {
        super(x, y, width, height, affectedByGravity, collidable, health, maxHealth, acceleration, enemy);
    }
}
