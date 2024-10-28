package com.gproject.game;

import com.gproject.game.entities.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.gproject.game.Costants.*;

public class PhysicsAndLogic {

    public static void update(Player player, HashSet<Entity>[][] chunks, double seconds) {
        int firstChunksX = (int) (player.x / CHUNK_SIZE) - CHUNKS_TO_UPDATE;
        int firstChunksY = (int) (player.y / CHUNK_SIZE) - CHUNKS_TO_UPDATE;
        int lastChunksX = (int) (player.x / CHUNK_SIZE) + CHUNKS_TO_UPDATE + 1;
        int lastChunksY = (int) (player.y / CHUNK_SIZE) + CHUNKS_TO_UPDATE + 1;

        firstChunksX = Math.max(0, firstChunksX);
        firstChunksY = Math.max(0, firstChunksY);
        lastChunksX = Math.min(chunks.length, lastChunksX);
        lastChunksY = Math.min(chunks[0].length, lastChunksY);

        chunks[(int) (player.x / CHUNK_SIZE)][(int) (player.y / CHUNK_SIZE)].add(player);

        // Change entity chunk
        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {
                manageChangeOfChunk(chunks, i, j);
            }
        }

        HashSet<Entity> entities = new HashSet<>();
        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {
                entities.addAll(chunks[i][j]);
            }
        }
        for (Entity entity : entities) {
            if (!(entity instanceof Block)) {
                entity.update(seconds);
                entity.applyVelocity(seconds);
            }
        }
        manageCollision(entities);

    }

    private static void manageChangeOfChunk(HashSet<Entity>[][] chunks, int i, int j) {
        List<Entity> entities = new ArrayList<>(chunks[i][j]);
        for (Entity entity : entities) {
            if (entity.isToRemove()) {
                chunks[i][j].remove(entity);
            } else {
                int startI = (int) (entity.x / CHUNK_SIZE) - 1;
                int startJ = (int) (entity.y / CHUNK_SIZE) - 1;
                int endI = (int) ((entity.x + entity.width) / CHUNK_SIZE) + 1;
                int endJ = (int) ((entity.y + entity.height) / CHUNK_SIZE) + 1;
                if (i < startI || i > endI || j < startJ || j > endJ) {
                    chunks[i][j].remove(entity);
                    for (int l = startI; l <= endI; l++) {
                        for (int m = startJ; m <= endJ; m++) {
                            if (l >= 0 && l < chunks.length && m >= 0 && m < chunks[0].length) {
                                chunks[l][m].add(entity);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void manageCollision(HashSet<Entity> ents) {
        List<Entity> entities = new ArrayList<>(ents);
        for (int k = 0; k < entities.size(); k++) {
            Entity entity1 = entities.get(k);
            for (int l = k+1; l < entities.size(); l++) {
                Entity entity2 = entities.get(l);
                if (entity1.isAffectByCollision() && entity2.isAffectByCollision()) {

                    double left1 = entity1.x;
                    double right1 = entity1.x + entity1.width;
                    double top1 = entity1.y;
                    double bottom1 = entity1.y + entity1.height;

                    double left2 = entity2.x;
                    double right2 = entity2.x + entity2.width;

                    double top2 = entity2.y;
                    double bottom2 = entity2.y + entity2.height;

                    if (right1 > left2 && left1 < right2 && bottom1 > top2 && top1 < bottom2) {
                        entity1.onCollision(entity2);
                        entity2.onCollision(entity1);

                        if (entity1 instanceof LivingEntity && entity2 instanceof LivingEntity) {
                            resolveCollision2Entity(entity1, entity2, right1, right2, left1, left2, top1, top2, bottom1, bottom2);
                        } else if (entity1 instanceof LivingEntity && entity2 instanceof Block) {
                            resolveCollisionEntityBlock(entity1, right1, right2, left1, left2, top1, top2, bottom1, bottom2);
                        } else if (entity1 instanceof Block && entity2 instanceof LivingEntity) {
                            resolveCollisionEntityBlock(entity2, right2, right1, left2, left1, top2, top1, bottom2, bottom1);
                        }
                    }
                }
            }
        }
    }

    private static void resolveCollision2Entity(Entity entity1, Entity entity2, double right1, double right2, double left1, double left2, double top1, double top2, double bottom1, double bottom2) {
        double overlapX = Math.min(right1, right2) - Math.max(left1, left2);
        double overlapY = Math.min(bottom1, bottom2) - Math.max(top1, top2);

        if (overlapX < overlapY) {
            double shiftX = overlapX / 2;
            if (left1 < left2) {
                entity1.x -= shiftX;
                entity2.x += shiftX;
            } else {
                entity1.x += shiftX;
                entity2.x -= shiftX;
            }

            // Equalize the velocities in the x-direction
            double averageVelocityX = (entity1.velocityX + entity2.velocityX) / 2;
            entity1.velocityX = averageVelocityX;
            entity2.velocityX = averageVelocityX;

        } else {
            double shiftY = overlapY / 2;
            if (top1 < top2) {
                entity1.y -= shiftY;
                entity2.y += shiftY;
            } else {
                entity1.y += shiftY;
                entity2.y -= shiftY;
            }

            // Equalize the velocities in the y-direction
            double tmp = entity1.velocityY;
            entity1.velocityY = entity2.velocityY;
            entity2.velocityY = tmp;
        }
    }


    private static void resolveCollisionEntityBlock(Entity entity1, double right1, double right2, double left1, double left2, double top1, double top2, double bottom1, double bottom2) {
        double overlapX = Math.min(right1, right2) - Math.max(left1, left2);
        double overlapY = Math.min(bottom1, bottom2) - Math.max(top1, top2);

        if (overlapX < overlapY) {
            double shiftX = overlapX;
            entity1.velocityX = 0; // Set horizontal velocity to 0 on collision

            if (left1 < left2) {
                entity1.x -= shiftX; // Move entity1 left by the overlap amount
            } else {
                entity1.x += shiftX; // Move entity1 right by the overlap amount
            }
        } else {
            double shiftY = overlapY;

            // If entity1 is a Player, reset jump capability
            if (entity1 instanceof Player) {
                ((Player) entity1).onSingleJump = true;
            }

            // Reset lastOnGround and vertical velocity
            entity1.lastOnGround = 0;
            entity1.velocityY = 0; // Set vertical velocity to 0 on collision

            if (top1 < top2) {
                entity1.y -= shiftY; // Move entity1 up by the overlap amount
            } else {
                entity1.y += shiftY; // Move entity1 down by the overlap amount
            }
        }
    }


    public static void insertNewEntity(Entity entity, HashSet<Entity>[][] chunks){
        int startI = (int) (entity.x / CHUNK_SIZE) - 1;
        int startJ = (int) (entity.y / CHUNK_SIZE) - 1;
        int endI = (int) ((entity.x + entity.width) / CHUNK_SIZE) + 1;
        int endJ = (int) ((entity.y + entity.height) / CHUNK_SIZE) + 1;
        for (int l = startI; l <= endI; l++) {
            for (int m = startJ; m <= endJ; m++) {
                if (l >= 0 && l < chunks.length && m >= 0 && m < chunks[0].length) {
                    chunks[l][m].add(entity);
                }
            }
        }
    }
}