package com.gproject.game.entities;

import com.gproject.game.entities.blocks.Block;
import com.gproject.game.manage.Game;

import java.util.HashSet;
import java.util.function.Consumer;

public class Attack extends Entity implements Cloneable {

    // Proprietà principali dell'attacco
    private final Entity caster;                  // Entità che ha lanciato l'attacco
    private final int damage;                     // Danno inflitto agli obiettivi
    private final int knockback;                  // Intensità del knockback applicato
    private final boolean meleeAttack;                  // Indica se l'attacco è melee
    private double duration;                      // Durata dell'attacco

    // Tempo di ricarica e gestione delle collisioni
    public double reloadTime;                     // Tempo di ricarica tra un attacco e l'altro
    public transient Consumer<Entity> onCollisionEffect; // Effetto speciale su collisione
    private HashSet<Entity> damagedEntities = new HashSet<>(); // Entità già danneggiate

    /**
     * Costruttore per l'attacco.
     *
     * @param x Posizione iniziale sull'asse x.
     * @param y Posizione iniziale sull'asse y.
     * @param width Larghezza dell'attacco.
     * @param height Altezza dell'attacco.
     * @param affectedByGravity Specifica se l'attacco è influenzato dalla gravità.
     * @param caster L'entità che lancia l'attacco.
     * @param damage Danno inflitto.
     * @param knockback Knockback applicato all'obiettivo.
     * @param meleeAttack Indica se l'attacco è melee.
     * @param duration Durata dell'attacco.
     * @param onCollisionEffect Effetto su collisione con l'obiettivo.
     * @param reloadTime Tempo di ricarica prima di un nuovo attacco.
     */
    public Attack(double x, double y, int width, int height, boolean affectedByGravity, Entity caster,
                  int damage, int knockback, boolean meleeAttack, double duration,
                  Consumer<Entity> onCollisionEffect, double reloadTime) {
        super(x, y, width, height, affectedByGravity, true, 0, 0, caster.isEnemy());
        this.caster = caster;
        this.damage = damage;
        this.knockback = knockback;
        this.meleeAttack = meleeAttack;
        this.duration = duration;
        this.onCollisionEffect = onCollisionEffect;
        this.reloadTime = reloadTime;
    }

    // Metodi principali

    /**
     * Aggiorna lo stato dell'attacco, riducendo la durata.
     *
     * @param seconds Tempo trascorso dall'ultimo aggiornamento.
     * @param game Istanza corrente del gioco.
     */
    @Override
    public void update(double seconds, Game game) {
        super.update(seconds, game);
        duration -= seconds;
        if (duration < 0) {
            toRemove = true;
        }
    }

    /**
     * Gestisce la collisione con un'altra entità.
     *
     * @param other L'entità con cui collide.
     */
    @Override
    public void onCollision(Entity other) {
        super.onCollision(other);

        if (other == caster || this.isEnemy() == other.isEnemy() || damagedEntities.contains(other)) {
            return;
        }

        if (!(other instanceof Block)) {
            damagedEntities.add(other);
            if (other instanceof LivingEntity) {
                applyDamageAndKnockback((LivingEntity) other);
                if (onCollisionEffect != null) {
                    onCollisionEffect.accept(other);
                }
            }
        }
    }

    /**
     * Applica danno e knockback all'entità colpita.
     *
     * @param target Entità che riceve il danno e il knockback.
     */
    private void applyDamageAndKnockback(LivingEntity target) {
        target.health -= damage;

        if ((meleeAttack && caster.x + caster.width / 2 < target.x + target.width / 2) ||
                (x + width / 2 < target.x + target.width / 2)) {
            target.velocityX = knockback;
        } else {
            target.velocityX = -knockback;
        }
        target.velocityY = -knockback;
    }

    /**
     * Clona l'attacco, copiando tutte le proprietà rilevanti.
     *
     * @return Un clone dell'attacco corrente.
     */
    @Override
    public Attack clone() {
        try {
            Attack clonedAttack = (Attack) super.clone();
            clonedAttack.damagedEntities = new HashSet<>(this.damagedEntities);

            if (this.onCollisionEffect != null) {
                clonedAttack.onCollisionEffect = this.onCollisionEffect;
            }
            return clonedAttack;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Errore di clonazione nell'attacco", e);
        }
    }
}
