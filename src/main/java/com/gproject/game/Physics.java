package com.gproject.game;

import com.gproject.game.entities.Entity;

import java.util.List;

import static com.gproject.game.Costants.GRAVITY;
import static com.gproject.game.Costants.VELOCITYLIMIT;

public class Physics {

    public static void update(List<Entity> entities, double seconds) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.update();
            if (entity.isAffectedByGravity()){
                updateGravity(entity, seconds);
            }
            if (entity.isCollidable()){
                for (int j = i+1; j < entities.size(); j++) {
                    Entity entity2 = entities.get(j);
                    entity.resolveCollision(entity2);
                }
            }
        }
        for (Entity entity : entities) {
            applyVelocity(entity);
        }
    }

    private static void updateGravity(Entity entity, double seconds) {
        if (entity.isAffectedByGravity()){
            entity.setVelocityY(entity.getVelocityY() + GRAVITY * seconds);
        }
    }

    private static void applyVelocity(Entity entity){
        if (entity.getVelocityX() > VELOCITYLIMIT){
            entity.setVelocityX(VELOCITYLIMIT);
        } else if (-entity.getVelocityX() > VELOCITYLIMIT) {
            entity.setVelocityX(-VELOCITYLIMIT);
        }
        if (entity.getVelocityY() > VELOCITYLIMIT){
            entity.setVelocityY(VELOCITYLIMIT);
        } else if (-entity.getVelocityY() > VELOCITYLIMIT) {
            entity.setVelocityY(-VELOCITYLIMIT);
        }

        entity.setX(entity.getX() + entity.getVelocityX());
        entity.setY(entity.getY() + entity.getVelocityY());
    }
}