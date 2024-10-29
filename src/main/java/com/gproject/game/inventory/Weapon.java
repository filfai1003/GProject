package com.gproject.game.inventory;

import com.gproject.game.manage.Chunk;
import com.gproject.game.manage.Game;
import com.gproject.game.entities.Attack;
import com.gproject.game.entities.Entity;
import com.gproject.game.entities.Player;
import com.gproject.game.entities.PlayerDirection;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.function.Consumer;

import static com.gproject.game.manage.PhysicsAndLogic.insertNewEntity;

public class Weapon implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public String name;

    protected Attack baseAttack;
    protected Attack heavyAttack;
    protected Attack specialAttack;
    public transient Consumer<Double> passifEffect;

    public Weapon(String name, Attack baseAttack, Attack heavyAttack, Attack specialAttack, Consumer<Double> passifEffect) {
        this.name = name;
        this.baseAttack = baseAttack;
        this.heavyAttack = heavyAttack;
        this.specialAttack = specialAttack;
        this.passifEffect = passifEffect;
    }

    public void baseAttack(Game game) {
        if (baseAttack == null) {
            return;
        }
        Chunk[][] chunks = game.getChunks();
        Player player = game.getPlayer();
        if (player.lastAttack > baseAttack.reloadTime){
            player.lastAttack = 0;
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
                tmp = attack.velocityX;
                attack.velocityX = attack.velocityY;
                attack.velocityY = tmp;
                attack.x =  player.width /2 -  attack.width/2;
                attack.y = player.height + attack.y;
            }
            else if (player.direction == PlayerDirection.UP) {
                double tmp = attack.width;
                attack.width = attack.height;
                attack.height = tmp;
                tmp = attack.velocityX;
                attack.velocityX = attack.velocityY;
                attack.velocityY = tmp;
                attack.x =  player.width /2 -  attack.width/2;
                attack.y = -attack.y - attack.height;
            }
            attack.x = player.x + attack.x;
            attack.y = player.y + attack.y;
            insertNewEntity(attack, chunks);
        }
    }

    public void heavyAttack(Game game) {
        if (heavyAttack == null) {
            return;
        }
        Chunk[][] chunks = game.getChunks();
        Player player = game.getPlayer();
        if (player.lastAttack > heavyAttack.reloadTime){
            player.lastAttack = 0;
            Attack attack = heavyAttack.clone();
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
                tmp = attack.velocityX;
                attack.velocityX = attack.velocityY;
                attack.velocityY = tmp;
                attack.x =  player.width /2 -  attack.width/2;
                attack.y = player.height + attack.y;
            }
            else if (player.direction == PlayerDirection.UP) {
                double tmp = attack.width;
                attack.width = attack.height;
                attack.height = tmp;
                tmp = attack.velocityX;
                attack.velocityX = attack.velocityY;
                attack.velocityY = tmp;
                attack.x =  player.width /2 -  attack.width/2;
                attack.y = -attack.y - attack.height;
            }
            attack.x = player.x + attack.x;
            attack.y = player.y + attack.y;
            insertNewEntity(attack, chunks);
        }
    }

    public void specialAttack(Game game) {
        if (specialAttack == null) {
            return;
        }
        Chunk[][] chunks = game.getChunks();
        Player player = game.getPlayer();
        if (player.lastAttack > specialAttack.reloadTime){
            player.lastAttack = 0;
            Attack attack = specialAttack.clone();
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
                tmp = attack.velocityX;
                attack.velocityX = attack.velocityY;
                attack.velocityY = tmp;
                attack.x =  player.width /2 -  attack.width/2;
                attack.y = player.height + attack.y;
            }
            else if (player.direction == PlayerDirection.UP) {
                double tmp = attack.width;
                attack.width = attack.height;
                attack.height = tmp;
                tmp = attack.velocityX;
                attack.velocityX = attack.velocityY;
                attack.velocityY = tmp;
                attack.x =  player.width /2 -  attack.width/2;
                attack.y = -attack.y - attack.height;
            }
            attack.x = player.x + attack.x;
            attack.y = player.y + attack.y;
            insertNewEntity(attack, chunks);
        }
    }
}
