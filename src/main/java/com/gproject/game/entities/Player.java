package com.gproject.game.entities;

import static com.gproject.game.Costants.*;

public class Player extends LivingEntity {

    // Player-specific attributes
    public boolean onSingleJump = true;
    private double lastDash = P_DASH_RELOAD_TIME;
    private int direction = 1;

    // Constructor
    public Player(double x, double y, int health) {
        super(x, y, P_WIDTH, P_HEIGHT, true, true, P_VELOCITY_LIMIT_X, P_VELOCITY_LIMIT_Y, P_FRICTION, P_AIR_FRICTION, health, P_MAX_HEALTH, P_ACCELERATION, P_JUMP_SPEED, false);
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
            velocityLimitX = P_DASH_SPEED;
        }
        super.applyVelocity(seconds);
        if (lastDash < P_DASH_TIME) {
            velocityLimitX = P_VELOCITY_LIMIT_X;
        }
    }

    @Override
    public void goLeft(double seconds) {
        super.goLeft(seconds);
        direction = -1;
    }

    @Override
    public void goRight(double seconds) {
        super.goRight(seconds);
        direction = 1;
    }

    // Jump method with double jump mechanic
    @Override
    public void jump() {
        if (isOnGround()) {
            this.velocityY = -jumpSpeed;
            lastOnGround = 1;
            return;
        }
        //if (onSingleJump) { TODO
            this.velocityY = -this.jumpSpeed;
            onSingleJump = false;
        //}
    }

    // Dash method
    public void dash() {
        if (lastDash > P_DASH_RELOAD_TIME) {
            velocityX = direction * P_DASH_SPEED;
            lastDash = 0;
        }
    }

    // Override for ground check with coyote time
    @Override
    public boolean isOnGround() {
        return lastOnGround < P_COYOTE_TIME;
    }
}
