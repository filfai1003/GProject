package com.gproject.game.entities;

public class Player extends LivingEntity {
    public Player(float x, float y, int width, int height, boolean affectedByGravity, boolean collidable, float mass, int health, int maxHealth, int maxSpeed, int acceleration, boolean enemy) {
        super(x, y, width, height, affectedByGravity, collidable, mass, health, maxHealth, maxSpeed, acceleration, enemy);
    }
}
