package com.gproject.game.entities;

import com.gproject.game.manage.Game;

import static com.gproject.game.manage.Costants.*;
import static com.gproject.game.entities.PlayerDirection.LEFT;
import static com.gproject.game.entities.PlayerDirection.RIGHT;

public class Player extends LivingEntity {
    public static final int P_SPEED_LIMIT_X = 1200;
    public static final int P_SPEED_LIMIT_Y = 0;
    public static final int P_ACCELERATION = 2000;
    public static final int P_FRICTION = 2000;
    public static final int P_AIR_FRICTION = 1000;

    public static final int P_WIDTH = 50;
    public static final int P_HEIGHT = 180;

    public static final int P_JUMP_SPEED = 1800;
    public static final double P_COYOTE_TIME = 0.15;
    public static final int P_DASH_SPEED = 4000;
    public static final double P_DASH_TIME = 0.25;
    public static final double P_DASH_RELOAD_TIME = 0.5;

    public static final int P_MAX_HEALTH = 1000;
    public static final int P_MAX_VIGOR = 500;
    public static final int P_MAX_CHARGES = 10;

    public static final int P_VIGOR_RELOAD = 200;
    public static final double P_VIGOR_WAIT_TIME = 0.5;
    public static final int P_JUMP_VIGOR = 100;
    public static final int P_DASH_VIGOR = 100;


    // Player-specific attributes
    public int charge = 0;
    public double vigor = 0;
    public int maxVigor = P_MAX_VIGOR;
    public int maxCharge = P_MAX_CHARGES;
    public boolean onSingleJump = true;
    private double lastDash = P_DASH_RELOAD_TIME;
    private double lastJump = 0;
    public PlayerDirection direction = RIGHT;
    // TODO implementa stamina

    // Constructor
    public Player(double x, double y) {
        super(x, y, P_WIDTH, P_HEIGHT, true, true, P_SPEED_LIMIT_X, 0, P_FRICTION, P_AIR_FRICTION, P_MAX_HEALTH, P_ACCELERATION, P_JUMP_SPEED, false);
    }

    // Update method
    @Override
    public void update(double seconds, Game game) {
        if (lastDash > P_VIGOR_WAIT_TIME && lastJump > P_VIGOR_WAIT_TIME){
            vigor = Math.min(vigor + P_VIGOR_RELOAD * seconds, maxVigor);
        }
        super.update(seconds, game);
        lastDash += seconds;
        lastJump += seconds;
    }

    // Velocity application with dash mechanic
    @Override
    public void applyVelocity(double seconds) {
        if (lastDash < P_DASH_TIME) {
            if (Math.abs(velocityX) > P_DASH_SPEED) {
                velocityX = (velocityX > 0) ? P_DASH_SPEED : -P_DASH_SPEED;
            }
            velocityY = 0;
        } else {
            if (Math.abs(velocityX) > P_SPEED_LIMIT_X) {
                velocityX = (velocityX > 0) ? P_SPEED_LIMIT_X : -P_SPEED_LIMIT_X;
            }
        }
        if (Math.abs(velocityY) > G_VELOCITY_LIMIT_Y) {
            velocityY = (velocityY > 0) ? G_VELOCITY_LIMIT_Y : -G_VELOCITY_LIMIT_Y;
        }

        x += velocityX * seconds;
        y += velocityY * seconds;

    }

    @Override
    public void goLeft(double seconds) {
        double netAcceleration = acceleration + (isOnGround() ? friction : airFriction);
        this.velocityX -= netAcceleration * seconds;
        if (velocityX < -velocityLimitX && lastDash > P_DASH_TIME) {
            velocityX = -velocityLimitX;
        }
        direction = LEFT;
        d = -1;
    }

    @Override
    public void goRight(double seconds) {
        double netAcceleration = acceleration + (isOnGround() ? friction : airFriction);
        velocityX += netAcceleration * seconds;
        if (velocityX > velocityLimitX && lastDash > P_DASH_TIME) {
            velocityX = velocityLimitX;
        }
        direction = RIGHT;
        d = 1;
    }

    // Jump method with double jump mechanic
    @Override
    public void jump() {
        if (vigor > P_JUMP_VIGOR) {
            if (isOnGround()) {
                this.velocityY = -jumpSpeed;
                lastOnGround = 1;
                vigor -= P_JUMP_VIGOR;
                lastJump = 0;
                return;
            }
            if (onSingleJump) {
                this.velocityY = -this.jumpSpeed;
                onSingleJump = false;
                vigor -= P_JUMP_VIGOR;
                lastJump = 0;
            }
        }
    }

    // Dash method
    public void dash() {
        if (vigor > P_DASH_VIGOR) {
            if (lastDash > P_DASH_RELOAD_TIME) {
                velocityX = d * P_DASH_SPEED;
                lastDash = 0;
                vigor -= P_DASH_VIGOR;
            }
        }
    }

    public void addCharge(int charges) {
        this.charge = Math.min(this.charge + charges, maxCharge);
    }
    public void removeCharge(int charges) {
        this.charge = Math.max(this.charge - charges, 0);
    }

    // Override for ground check with coyote time
    @Override
    public boolean isOnGround() {
        return lastOnGround < P_COYOTE_TIME;
    }
}
