package com.gproject.game.entities;

import static com.gproject.game.Costants.*;
import static com.gproject.game.entities.PlayerDirection.LEFT;
import static com.gproject.game.entities.PlayerDirection.RIGHT;

public class Player extends LivingEntity {

    // Player-specific attributes
    public int charge = 0;
    public int maxCharge = P_CHARGES;
    public boolean onSingleJump = true;
    private double lastDash = P_DASH_RELOAD_TIME;
    public PlayerDirection direction = RIGHT;
    public int d = 1;
    // TODO implementa stamina

    // Constructor
    public Player(double x, double y) {
        super(x, y, P_WIDTH, P_HEIGHT, true, true, P_VELOCITY_LIMIT_X, P_VELOCITY_LIMIT_Y, P_FRICTION, P_AIR_FRICTION, P_MAX_HEALTH, P_MAX_HEALTH, P_ACCELERATION, P_JUMP_SPEED, false, null);
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
            velocityY = 0;
        }
        super.applyVelocity(seconds);
        if (lastDash < P_DASH_TIME) {
            velocityLimitX = P_VELOCITY_LIMIT_X;
        }
    }

    @Override
    public void goLeft(double seconds) {
        super.goLeft(seconds);
        direction = LEFT;
        d = -1;
    }

    @Override
    public void goRight(double seconds) {
        super.goRight(seconds);
        direction = RIGHT;
        d = 1;
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
    public void dash() {
        if (lastDash > P_DASH_RELOAD_TIME) {
            velocityX = d * P_DASH_SPEED;
            lastDash = 0;
        }
    }

    public void addCharge(int charges) {
        for (int i = 0; i < charges; i++) {
            if (charge < maxCharge){
                charge++;
            } else {
                return;
            }
        }
    }

    // Override for ground check with coyote time
    @Override
    public boolean isOnGround() {
        return lastOnGround < P_COYOTE_TIME;
    }
}
