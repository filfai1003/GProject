package com.gproject.game.manage;

import com.gproject.game.entities.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.gproject.game.manage.Costants.*;

public class PhysicsAndLogic {

    public static void update(double seconds, Game game) {
        Player player = game.getPlayer();
        Chunk[][] chunks = game.getChunks();

        int firstChunksX = (int) (player.x / CHUNK_SIZE) - CHUNKS_TO_UPDATE;
        int firstChunksY = (int) (player.y / CHUNK_SIZE) - CHUNKS_TO_UPDATE;
        int lastChunksX = (int) (player.x / CHUNK_SIZE) + CHUNKS_TO_UPDATE + 1;
        int lastChunksY = (int) (player.y / CHUNK_SIZE) + CHUNKS_TO_UPDATE + 1;

        firstChunksX = Math.max(0, firstChunksX);
        firstChunksY = Math.max(0, firstChunksY);
        lastChunksX = Math.min(chunks.length, lastChunksX);
        lastChunksY = Math.min(chunks[0].length, lastChunksY);

        chunks[(int) (player.x / CHUNK_SIZE)][(int) (player.y / CHUNK_SIZE)].entities.add(player);

        // Change entity chunk
        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {
                manageChangeOfChunk(chunks, i, j);
            }
        }

        HashSet<Entity> entities = new HashSet<>();
        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {
                entities.addAll(chunks[i][j].entities);
            }
        }
        for (Entity entity : entities) {
            if (!(entity instanceof Block)) {
                entity.update(seconds, game);
                entity.applyVelocity(seconds);
            }
        }
        manageCollision(entities);

    }

    private static void manageChangeOfChunk(Chunk[][] chunks, int i, int j) {
        List<Entity> entities = new ArrayList<>(chunks[i][j].entities);
        for (Entity entity : entities) {
            if (entity.isToRemove()) {
                chunks[i][j].entities.remove(entity);
            } else {
                int startI = (int) (entity.x / CHUNK_SIZE) - 1;
                int startJ = (int) (entity.y / CHUNK_SIZE) - 1;
                int endI = (int) ((entity.x + entity.width) / CHUNK_SIZE) + 1;
                int endJ = (int) ((entity.y + entity.height) / CHUNK_SIZE) + 1;
                if (i < startI || i > endI || j < startJ || j > endJ) {
                    chunks[i][j].entities.remove(entity);
                    for (int l = startI; l <= endI; l++) {
                        for (int m = startJ; m <= endJ; m++) {
                            if (l >= 0 && l < chunks.length && m >= 0 && m < chunks[0].length) {
                                chunks[l][m].entities.add(entity);
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
            for (int l = k + 1; l < entities.size(); l++) {
                Entity entity2 = entities.get(l);

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
                        if (entity1.isAffectByCollision() && entity2.isAffectByCollision()) {
                            resolveCollision2Entity(entity1, entity2, right1, right2, left1, left2, top1, top2, bottom1, bottom2);
                        }
                    } else if (entity1 instanceof LivingEntity && entity2 instanceof Block) {
                        resolveCollisionEntityBlock(entity1, right1, right2, left1, left2, top1, top2, bottom1, bottom2);
                    } else if (entity1 instanceof Block && entity2 instanceof LivingEntity) {
                        resolveCollisionEntityBlock(entity2, right2, right1, left2, left1, top2, top1, bottom2, bottom1);
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
            double avg = (entity1.velocityX + entity2.velocityX) / 2;
            entity1.velocityX = avg;
            entity2.velocityX = avg;

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
            double avg = (entity1.velocityY + entity2.velocityY) / 2;
            entity1.velocityY = avg;
            entity2.velocityY = avg;
        }
    }


    private static void resolveCollisionEntityBlock(Entity entity1, double right1, double right2, double left1, double left2, double top1, double top2, double bottom1, double bottom2) {
        double overlapX = Math.min(right1, right2) - Math.max(left1, left2);
        double overlapY = Math.min(bottom1, bottom2) - Math.max(top1, top2);

        if (overlapX < overlapY) {
            double shiftX = overlapX;

            if (left1 < left2) {
                entity1.x -= shiftX;
                if (entity1.velocityX > 0) {
                    entity1.velocityX = 0;
                }
            } else {
                entity1.x += shiftX;
                if (entity1.velocityX < 0) {
                    entity1.velocityX = 0;
                }
            }
        } else {
            double shiftY = overlapY;

            if (entity1 instanceof Player) {
                ((Player) entity1).onSingleJump = true;
            }


            if (top1 < top2) {
                entity1.y -= shiftY;
                entity1.lastOnGround = 0;
                if (entity1.velocityY > 0) {
                    entity1.velocityY = 0;
                }
            } else {
                entity1.y += shiftY;
                if (entity1.velocityY < 0) {
                    entity1.velocityY = 0;
                }
            }
        }
    }


    public static void insertNewEntity(Entity entity, Chunk[][] chunks) {
        int startI = (int) (entity.x / CHUNK_SIZE) - 1;
        int startJ = (int) (entity.y / CHUNK_SIZE) - 1;
        int endI = (int) ((entity.x + entity.width) / CHUNK_SIZE) + 1;
        int endJ = (int) ((entity.y + entity.height) / CHUNK_SIZE) + 1;
        for (int l = startI; l <= endI; l++) {
            for (int m = startJ; m <= endJ; m++) {
                if (l >= 0 && l < chunks.length && m >= 0 && m < chunks[0].length) {
                    chunks[l][m].entities.add(entity);
                }
            }
        }
    }
}