package com.gproject.game.entities;

public class Entity {

    protected double x, y;
    protected int width, height;
    protected double velocityX, velocityY;
    protected boolean affectedByGravity;
    protected boolean collidable;
    protected final int id;
    protected float mass;

    private static int nextId = 0;

    public Entity(float x, float y, int width, int height, boolean affectedByGravity, boolean collidable, float mass) {
        this.id = generateId();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityX = 0;
        this.velocityY = 0;
        this.affectedByGravity = affectedByGravity;
        this.collidable = collidable;
        this.mass = mass;
    }

    public void update() {}

    private synchronized int generateId() {
        return nextId++;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public boolean isAffectedByGravity() {
        return affectedByGravity;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public void resolveCollision(Entity entity) {
        // TODO entity.resolveCollision
    }
}

