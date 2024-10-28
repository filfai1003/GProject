package com.gproject.game.inventory;

import com.gproject.game.Game;
import com.gproject.game.entities.Attack;
import com.gproject.game.entities.Entity;
import com.gproject.game.entities.Player;
import com.gproject.game.entities.PlayerDirection;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;

import static com.gproject.game.PhysicsAndLogic.insertNewEntity;

public class Weapon implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected Attack baseAttack;
    protected Attack heavyAttack;
    protected Attack specialAttack;
    protected double lastAttack;

    public Weapon(Attack baseAttack, Attack heavyAttack, Attack specialAttack) {
        this.baseAttack = baseAttack;
        this.heavyAttack = heavyAttack;
        this.specialAttack = specialAttack;
    }

    public void use (Game game) {
        HashSet<Entity>[][] chunks = game.getChunks();
        Player player = game.getPlayer();
        if (lastAttack > baseAttack.reloadTime){
            lastAttack = 0;
            Attack attack = baseAttack.clone();
            if (player.direction == PlayerDirection.RIGHT) {
                attack.x = player.width + attack.x;
                attack.y = player.height/2 -  attack.height/2;
            }
            else if (player.direction == PlayerDirection.LEFT) {
                attack.x = -attack.x - attack.width;
                attack.y =  player.height /2 -  attack.height/2;
            }
            else if (player.direction == PlayerDirection.DOWN) {
                double tmp = attack.width;
                attack.width = attack.height;
                attack.height = tmp;
                attack.x =  player.width /2 -  attack.width/2;
                attack.y = player.height + attack.y;
            }
            else if (player.direction == PlayerDirection.UP) {
                double tmp = attack.width;
                attack.width = attack.height;
                attack.height = tmp;
                attack.x =  player.width /2 -  attack.width/2;
                attack.y = -attack.y - attack.height;
            }
            attack.x = player.x + attack.x;
            attack.y = player.y + attack.y;
            insertNewEntity(attack, chunks);
        }
    }
}
