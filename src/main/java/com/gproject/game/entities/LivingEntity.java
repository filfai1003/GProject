package com.gproject.game.entities;

import com.gproject.game.manage.Game;

public class LivingEntity extends Entity {

    // Attributi di salute
    public int health;
    public int maxHealth;

    // Limiti di velocità e movimento
    protected int velocityLimitX, velocityLimitY;
    protected double acceleration;
    public int jumpSpeed;

    // Attributi di stato
    public double lastAttack = 0;
    public int d = 1;  // Variabile utilizzata per determinare la direzione o lo stato dell'entità (valore non specificato)

    /**
     * Costruttore per LivingEntity.
     *
     * @param x Posizione iniziale sull'asse x.
     * @param y Posizione iniziale sull'asse y.
     * @param width Larghezza dell'entità.
     * @param height Altezza dell'entità.
     * @param affectedByGravity Specifica se è influenzata dalla gravità.
     * @param affectByCollision Specifica se è influenzata dalle collisioni.
     * @param velocityLimitX Limite di velocità sull'asse x.
     * @param velocityLimitY Limite di velocità sull'asse y.
     * @param friction Attrito dell'entità.
     * @param airFriction Attrito dell'entità nell'aria.
     * @param maxHealth Salute massima dell'entità.
     * @param acceleration Accelerazione dell'entità.
     * @param jumpSpeed Velocità di salto dell'entità.
     * @param enemy Indica se l'entità è un nemico.
     */
    public LivingEntity(double x, double y, int width, int height, boolean affectedByGravity,
                        boolean affectByCollision, int velocityLimitX, int velocityLimitY,
                        int friction, int airFriction, int maxHealth, int acceleration,
                        int jumpSpeed, boolean enemy) {
        super(x, y, width, height, affectedByGravity, affectByCollision, friction, airFriction, enemy);
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.velocityLimitX = velocityLimitX;
        this.velocityLimitY = velocityLimitY;
        this.acceleration = acceleration;
        this.jumpSpeed = jumpSpeed;
    }

    // Metodi di aggiornamento

    /**
     * Aggiorna lo stato dell'entità, incluso il tempo trascorso dall'ultimo attacco.
     *
     * @param seconds Tempo trascorso dall'ultimo aggiornamento.
     * @param game Istanza del gioco.
     */
    @Override
    public void update(double seconds, Game game) {
        super.update(seconds, game);
        lastAttack += seconds;
        if (health <= 0) {
            toRemove = true;  // Rimuove l'entità se la salute è esaurita
        }
    }

    // Metodi di movimento

    /**
     * Muove l'entità verso destra applicando l'accelerazione.
     *
     * @param seconds Tempo trascorso dall'ultimo aggiornamento.
     */
    public void goRight(double seconds) {
        status = "run";
        double netAcceleration = acceleration + (isOnGround() ? getFriction() : getAirFriction());
        velocityX += netAcceleration * seconds;
        if (velocityX > velocityLimitX) {
            velocityX = velocityLimitX;
        }
    }

    /**
     * Muove l'entità verso sinistra applicando l'accelerazione.
     *
     * @param seconds Tempo trascorso dall'ultimo aggiornamento.
     */
    public void goLeft(double seconds) {
        status = "run";
        double netAcceleration = acceleration + (isOnGround() ? getFriction() : getAirFriction());
        this.velocityX -= netAcceleration * seconds;
        if (velocityX < -velocityLimitX) {
            velocityX = -velocityLimitX;
        }
    }

    /**
     * Muove l'entità verso il basso con accelerazione.
     *
     * @param seconds Tempo trascorso dall'ultimo aggiornamento.
     */
    public void goDown(double seconds) {
        double netAcceleration = acceleration + getAirFriction();
        velocityY += netAcceleration * seconds;
        if (velocityY > velocityLimitY) {
            velocityY = velocityLimitY;
        }
    }

    /**
     * Muove l'entità verso l'alto con accelerazione.
     *
     * @param seconds Tempo trascorso dall'ultimo aggiornamento.
     */
    public void goUp(double seconds) {
        double netAcceleration = acceleration - getAirFriction();
        velocityY -= netAcceleration * seconds;
        if (velocityY < -velocityLimitY) {
            velocityY = -velocityLimitY;
        }
    }

    /**
     * Effettua un salto se l'entità è a terra.
     */
    public void jump() {
        status = "jump";
        if (isOnGround()) {
            this.velocityY = -jumpSpeed;
            lastOnGround = 1;  // Resetta il tempo trascorso da quando è stata a terra
        }
    }

    @Override
    public String toString() {
        return "LivingEntity{" +
                "health=" + health +
                '}';
    }
}
