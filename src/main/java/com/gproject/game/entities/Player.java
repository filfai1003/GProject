package com.gproject.game.entities;

import static com.gproject.game.Costants.*;

public class Player extends LivingEntity {

    // Player-specific attributes
    public boolean onSingleJump = true;
    private double lastDash = 0;

    // Constructor
    public Player(double x, double y, int health) {
        super(x, y, P_WIDTH, P_HEIGHT, true, true, P_VELOCITY_LIMIT, P_FRICTION, P_AIR_FRICTION, health, P_MAX_HEALTH, P_ACCELERATION, P_JUMP_SPEED, false);
    }

    // Update method
    @Override
    public void update(double seconds) {
        super.update(seconds);
        lastDash += seconds;
    }

    // Velocity application with dash mechanic
    @Override
    public void applyVelocity(double seconds) {
        if (lastDash < P_DASH_TIME) {
            velocityLimit = P_DASH_SPEED;
        }

        if (Math.abs(velocityX) > velocityLimit) {
            velocityX = (velocityX > 0) ? velocityLimit : -velocityLimit;
        }
        if (Math.abs(velocityY) > velocityLimit) {
            velocityY = (velocityY > 0) ? velocityLimit : -velocityLimit;
        }

        x += velocityX * seconds;
        y += velocityY * seconds;

        if (lastDash < P_DASH_TIME) {
            velocityLimit = P_VELOCITY_LIMIT;
        }
    }

    // Jump method with double jump mechanic
    @Override
    public void jump() {
        if (isOnGround()) {
            this.velocityY = -jumpSpeed;
            lastOnGround = 1;
            return;
        }
        if (onSingleJump) {
            this.velocityY = -this.jumpSpeed;
            onSingleJump = false;
        }
    }

    // Dash method
    public boolean dash(int direction) {
        if (lastDash > P_DASH_RELOAD_TIME) {
            velocityX = direction * P_DASH_SPEED;
            lastDash = 0;
            return true;
        }
        return false;
    }

    // Override for ground check with coyote time
    @Override
    public boolean isOnGround() {
        return lastOnGround < P_COYOTE_TIME;
    }
}
