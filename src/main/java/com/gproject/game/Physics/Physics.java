package com.gproject.game.Physics;

import com.gproject.game.entities.Block;
import com.gproject.game.entities.Entity;
import com.gproject.game.entities.Player;

import java.util.ArrayList;
import java.util.List;

import static com.gproject.game.Physics.Costants.*;

public class Physics {

    public static void update(Player player, Chunk[][] chuncks, Block[][] blocks, double seconds) {
        int firstChunksX = (int) Math.floor(player.getX()/ CHUNK_SIZE)-CHUNKS_TO_UPDATE;
        int firstChunksY = (int) Math.floor(player.getY()/ CHUNK_SIZE)-CHUNKS_TO_UPDATE;
        int lastChunksX = (int) Math.floor(player.getX()/ CHUNK_SIZE)+CHUNKS_TO_UPDATE+1;
        int lastChunksY = (int) Math.floor(player.getY()/ CHUNK_SIZE)+CHUNKS_TO_UPDATE+1;

        firstChunksX = Math.max(0, firstChunksX);
        firstChunksY = Math.max(0, firstChunksY);
        lastChunksX = Math.min(chuncks.length, lastChunksX);
        lastChunksY = Math.min(chuncks[0].length, lastChunksY);

        // Gravitation and speedUpdate
        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {
                for (Entity entity : chuncks[i][j].getEntities()){
                    updateGravity(entity, seconds);
                    applyVelocity(entity, seconds);
                }
            }
        }

        // Collision Entities-Entities
        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {
                if ((i%4==1 && j%2==1) || (i%4==3 && j%2==0)) {
                    List<Entity> entities = new ArrayList<>();
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if (i+k > 0 && i+k < chuncks.length && j+l > 0 && j+l < chuncks[0].length) {
                                for (Entity entity : chuncks[i+k][j+l].getEntities()){
                                    entities.add(entity);
                                }
                            }
                        }
                    }
                    for (Entity entity1 : entities) {
                        for (Entity entity2 : entities) {
                            if (entity1 != entity2) {
                                if (entity1.getX() + entity1.getWidth() > entity2.getX() && entity1.getX() < entity2.getX()) {
                                    if (entity1.getY() + entity1.getHeight() > entity2.getY() && entity1.getY() < entity2.getY()){
                                        // TODO resolve collision
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Collision Entities-Blocks
        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {
                for (Entity entity : chuncks[i][j].getEntities()){
                    int startX = (int) Math.floor(entity.getX() / BLOCK_SIZE);
                    int endX = (int) Math.floor((entity.getX() + entity.getWidth()) / BLOCK_SIZE);
                    int startY = (int) Math.floor(entity.getY() / BLOCK_SIZE);
                    int endY = (int) Math.floor((entity.getY() + entity.getHeight()) / BLOCK_SIZE);

                    startX = Math.max(0, startX);
                    endX = Math.min(blocks.length - 1, endX);
                    startY = Math.max(0, startY);
                    endY = Math.min(blocks[0].length - 1, endY);

                    for (int k = startX; k < endX; k++) {
                        for (int l = startY; l < endY; l++) {
                            if (blocks[k][l] != null){
                                if (k == startX || k == endX || k == startY || k == endY) {
                                    // TODO resolve collision
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void updateGravity(Entity entity, double seconds) {
        if (entity.isAffectedByGravity()){
            entity.setVelocityY(entity.getVelocityY() + GRAVITY * seconds);
        }
    }

    private static void applyVelocity(Entity entity, double seconds) {
        double velocityX = entity.getVelocityX();
        double velocityY = entity.getVelocityY();

        if (Math.abs(velocityX) > VELOCITY_LIMIT) {
            velocityX = (velocityX > 0) ? VELOCITY_LIMIT : -VELOCITY_LIMIT;
        }
        if (Math.abs(velocityY) > VELOCITY_LIMIT) {
            velocityY = (velocityY > 0) ? VELOCITY_LIMIT : -VELOCITY_LIMIT;
        }

        entity.setX(entity.getX() + velocityX * seconds);
        entity.setY(entity.getY() + velocityY * seconds);

        entity.setVelocityX(velocityX);
        entity.setVelocityY(velocityY);
    }
}