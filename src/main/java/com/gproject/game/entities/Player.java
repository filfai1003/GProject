package com.gproject.game.entities;

import com.gproject.game.manage.Game;
import com.gproject.game.render.DynamicAnimation;

import java.util.HashMap;


import static com.gproject.game.manage.Costants.*;
import static com.gproject.game.entities.PlayerDirection.LEFT;
import static com.gproject.game.entities.PlayerDirection.RIGHT;

public class Player extends LivingEntity {

    // Costanti per le proprietà del giocatore
    private static final int P_WIDTH = 50;
    private static final int P_HEIGHT = 180;
    private static final int P_MAX_HEALTH = 1000;
    private static final int P_MAX_VIGOR = 500;
    private static final int P_MAX_CHARGES = 10;

    // Movimento e velocità
    private static final int P_SPEED_LIMIT_X = 1200;
    private static final int P_SPEED_LIMIT_Y = 0;
    private static final int P_ACCELERATION = 2000;
    private static final int P_FRICTION = 2000;
    private static final int P_AIR_FRICTION = 1000;
    private static final int P_JUMP_SPEED = 1800;
    private static final double P_COYOTE_TIME = 0.15;

    // Attributi per abilità di Dash
    private static final int P_DASH_SPEED = 4000;
    private static final double P_DASH_TIME = 0.25;
    private static final double P_DASH_RELOAD_TIME = 0.5;

    // Vigor e consumo per abilità
    private static final int P_VIGOR_RELOAD = 200;
    private static final double P_VIGOR_WAIT_TIME = 0.5;
    private static final int P_JUMP_VIGOR = 100;
    private static final int P_DASH_VIGOR = 100;

    // Attributi specifici del giocatore
    public int charge = 0;
    private double vigor = 0;
    private final int maxVigor = P_MAX_VIGOR;
    private final int maxCharge = P_MAX_CHARGES;
    public boolean onSingleJump = true;
    private double lastDash = P_DASH_RELOAD_TIME;
    private double lastJump = 0;
    public PlayerDirection direction = RIGHT;

    /**
     * Costruttore per il giocatore.
     *
     * @param x Posizione iniziale sull'asse x.
     * @param y Posizione iniziale sull'asse y.
     */
    public Player(double x, double y) {
        super(x, y, P_WIDTH, P_HEIGHT, true, true, P_SPEED_LIMIT_X, P_SPEED_LIMIT_Y,
                P_FRICTION, P_AIR_FRICTION, P_MAX_HEALTH, P_ACCELERATION, P_JUMP_SPEED, false);
        DynamicAnimation d = new DynamicAnimation(0, 0, width, height, 5, 7, "assets/images/player");
        HashMap<String, DynamicAnimation> animations = new HashMap<>();
        animations.put("", d);
        dynamicAnimations = animations;
    }

    /**
     * Aggiorna lo stato del giocatore e gestisce il recupero del vigor.
     *
     * @param seconds Tempo trascorso dall'ultimo aggiornamento.
     * @param game Istanza del gioco.
     */
    @Override
    public void update(double seconds, Game game) {
        if (lastDash > P_VIGOR_WAIT_TIME && lastJump > P_VIGOR_WAIT_TIME) {
            vigor = Math.min(vigor + P_VIGOR_RELOAD * seconds, maxVigor);
        }
        super.update(seconds, game);
        lastDash += seconds;
        lastJump += seconds;
    }

    /**
     * Applica la velocità del giocatore, tenendo conto dell'abilità Dash.
     *
     * @param seconds Tempo trascorso dall'ultimo aggiornamento.
     */
    @Override
    public void applyVelocity(double seconds) {
        if (lastDash < P_DASH_TIME) {
            velocityX = (velocityX > 0) ? P_DASH_SPEED : -P_DASH_SPEED;
            velocityY = 0;
        } else if (Math.abs(velocityX) > P_SPEED_LIMIT_X) {
            velocityX = (velocityX > 0) ? P_SPEED_LIMIT_X : -P_SPEED_LIMIT_X;
        }

        if (Math.abs(velocityY) > G_VELOCITY_LIMIT_Y) {
            velocityY = (velocityY > 0) ? G_VELOCITY_LIMIT_Y : -G_VELOCITY_LIMIT_Y;
        }

        x += velocityX * seconds;
        y += velocityY * seconds;
    }

    // Metodi di movimento

    @Override
    public void goLeft(double seconds) {
        status = "run";
        velocityX -= (acceleration + (isOnGround() ? getFriction() : getAirFriction())) * seconds;
        if (velocityX < -velocityLimitX && lastDash > P_DASH_TIME) {
            velocityX = -velocityLimitX;
        }
        direction = LEFT;
        d = -1;
    }

    @Override
    public void goRight(double seconds) {
        status = "run";
        velocityX += (acceleration + (isOnGround() ? getFriction() : getAirFriction())) * seconds;
        if (velocityX > velocityLimitX && lastDash > P_DASH_TIME) {
            velocityX = velocityLimitX;
        }
        direction = RIGHT;
        d = 1;
    }

    /**
     * Gestisce il salto del giocatore, includendo la possibilità di doppio salto.
     */
    @Override
    public void jump() {
        if (vigor > P_JUMP_VIGOR) {
            if (isOnGround()) {
                velocityY = -jumpSpeed;
                lastOnGround = 1;
                vigor -= P_JUMP_VIGOR;
                lastJump = 0;
            } else if (onSingleJump) {
                velocityY = -jumpSpeed;
                onSingleJump = false;
                vigor -= P_JUMP_VIGOR;
                lastJump = 0;
            }
        }
    }

    /**
     * Gestisce l'abilità di Dash del giocatore.
     */
    public void dash() {
        if (vigor > P_DASH_VIGOR && lastDash > P_DASH_RELOAD_TIME) {
            dynamicAnimations.get("").startOverrideAnimation(P_DASH_TIME, new DynamicAnimation(0, 0, width, height, (int) (17/P_DASH_TIME), 17, "assets/images/player/dash"));
            velocityX = d * P_DASH_SPEED;
            lastDash = 0;
            vigor -= P_DASH_VIGOR;
        }
    }

    // Metodi di gestione delle cariche

    public void addCharge(int charges) {
        this.charge = Math.min(this.charge + charges, maxCharge);
    }

    public void removeCharge(int charges) {
        this.charge = Math.max(this.charge - charges, 0);
    }

    // Override per verifica su terra con coyote time

    @Override
    public boolean isOnGround() {
        return lastOnGround < P_COYOTE_TIME;
    }

    // Getters per attributi del vigor e delle cariche

    public double getVigor() {
        return vigor;
    }

    public int getMaxVigor() {
        return maxVigor;
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    @Override
    public String getStatus() {
        if (!isOnGround() && lastDash > P_DASH_TIME + P_COYOTE_TIME) {
            status = "jump";
        }
        return super.getStatus();
    }
}
