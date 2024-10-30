package com.gproject.game.entities;

import com.gproject.game.manage.Game;
import com.gproject.game.render.DynamicAnimation;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;

import static com.gproject.game.manage.Costants.*;

public abstract class Entity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Posizione e dimensioni
    public double x, y;
    public double width, height;

    // Velocità
    public double velocityX, velocityY;

    // Attributi fisici
    private final boolean affectedByGravity;
    private final boolean affectByCollision;
    private final double friction;
    private final double airFriction;

    // Stato
    public String status = " ";
    private final boolean enemy;
    protected boolean toRemove;

    // Gestione del tempo e delle animazioni
    public double lastOnGround = 0;
    public HashSet<DynamicAnimation> dynamicAnimations;

    /**
     * Costruttore principale per un'entità.
     *
     * @param x Posizione iniziale sull'asse x.
     * @param y Posizione iniziale sull'asse y.
     * @param width Larghezza dell'entità.
     * @param height Altezza dell'entità.
     * @param affectedByGravity Specifica se è influenzata dalla gravità.
     * @param affectByCollision Specifica se è influenzata dalle collisioni.
     * @param friction Attrito dell'entità.
     * @param airFriction Attrito dell'entità nell'aria.
     * @param enemy Indica se l'entità è un nemico.
     */
    public Entity(double x, double y, int width, int height, boolean affectedByGravity, boolean affectByCollision,
                  int friction, int airFriction, boolean enemy) {
        this(x, y, width, height, affectedByGravity, affectByCollision, friction, airFriction, enemy, new HashSet<>());
        DynamicAnimation d = new DynamicAnimation(0, 0, width, height, 1, 1, "");
        dynamicAnimations.add(d);
    }

    /**
     * Costruttore secondario per un'entità con animazioni.
     */
    public Entity(double x, double y, int width, int height, boolean affectedByGravity, boolean affectByCollision,
                  int friction, int airFriction, boolean enemy, HashSet<DynamicAnimation> dynamicAnimations) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.affectedByGravity = affectedByGravity;
        this.affectByCollision = affectByCollision;
        this.friction = friction;
        this.airFriction = airFriction;
        this.enemy = enemy;
        this.dynamicAnimations = dynamicAnimations;
    }

    // Metodi di aggiornamento

    /**
     * Aggiorna lo stato dell'entità, applicando gravità e attrito.
     *
     * @param seconds Tempo trascorso dall'ultimo aggiornamento.
     * @param game Istanza del gioco.
     */
    public void update(double seconds, Game game) {
        lastOnGround += seconds;
        if (affectedByGravity) {
            velocityY += GRAVITY * seconds;
        }
        applyFriction(seconds);
        applyVelocity(seconds);

        for (DynamicAnimation dynamicAnimation : dynamicAnimations) {
            dynamicAnimation.time += seconds;
        }
    }

    /**
     * Applica l'attrito e l'attrito dell'aria in base alla condizione dell'entità.
     *
     * @param seconds Tempo trascorso dall'ultimo aggiornamento.
     */
    private void applyFriction(double seconds) {
        if (isOnGround()) {
            if (velocityX > 0) {
                velocityX = Math.max(0, velocityX - friction * seconds);
            } else if (velocityX < 0) {
                velocityX = Math.min(0, velocityX + friction * seconds);
            }
        } else {
            if (velocityX > 0) {
                velocityX = Math.max(0, velocityX - airFriction * seconds);
            } else if (velocityX < 0) {
                velocityX = Math.min(0, velocityX + airFriction * seconds);
            }
        }
        if (velocityY > 0) {
            velocityY = Math.max(0, velocityY - airFriction * seconds);
        } else if (velocityY < 0) {
            velocityY = Math.min(0, velocityY + airFriction * seconds);
        }
    }

    /**
     * Applica la velocità dell'entità alle sue coordinate.
     *
     * @param seconds Tempo trascorso dall'ultimo aggiornamento.
     */
    public void applyVelocity(double seconds) {
        if (Math.abs(velocityX) > G_VELOCITY_LIMIT_X) {
            velocityX = (velocityX > 0) ? G_VELOCITY_LIMIT_X : -G_VELOCITY_LIMIT_X;
        }
        if (Math.abs(velocityY) > G_VELOCITY_LIMIT_Y) {
            velocityY = (velocityY > 0) ? G_VELOCITY_LIMIT_Y : -G_VELOCITY_LIMIT_Y;
        }

        x += velocityX * seconds;
        y += velocityY * seconds;
    }

    // Metodi di interazione

    /**
     * Definisce il comportamento su collisione con un'altra entità.
     *
     * @param other L'entità con cui collide.
     */
    public void onCollision(Entity other) {}

    // Getters e metodi di stato

    public boolean isAffectedByCollision() {
        return affectByCollision;
    }

    public double getFriction() {
        return friction;
    }

    public double getAirFriction() {
        return airFriction;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public boolean isToRemove() {
        return toRemove;
    }

    public boolean isOnGround() {
        return lastOnGround < G_COYOTE_TIME;
    }
}
