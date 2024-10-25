package com.gproject.game.Physics;

import com.gproject.game.entities.Block;
import com.gproject.game.entities.Entity;
import com.gproject.game.entities.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.gproject.game.Physics.Costants.*;

public class Physics {

    public static void update(Player player, HashSet<Entity>[][] chunks, double seconds) {
        int firstChunksX = (int) Math.floor(player.getX() / CHUNK_SIZE) - CHUNKS_TO_UPDATE;
        int firstChunksY = (int) Math.floor(player.getY() / CHUNK_SIZE) - CHUNKS_TO_UPDATE;
        int lastChunksX = (int) Math.floor(player.getX() / CHUNK_SIZE) + CHUNKS_TO_UPDATE + 1;
        int lastChunksY = (int) Math.floor(player.getY() / CHUNK_SIZE) + CHUNKS_TO_UPDATE + 1;

        firstChunksX = Math.max(0, firstChunksX);
        firstChunksY = Math.max(0, firstChunksY);
        lastChunksX = Math.min(chunks.length, lastChunksX);
        lastChunksY = Math.min(chunks[0].length, lastChunksY);

        // Change entity chunk
        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {
                manageChangeOfChunk(chunks, i, j);
            }
        }

        // Gravitation and speedUpdate
        int count = 0;
        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {
                for (Entity entity : chunks[i][j]) {
                    if (!(entity instanceof Block)) {
                        if (Math.floor(entity.getX() / CHUNK_SIZE) == i && Math.floor(entity.getY() / CHUNK_SIZE) == j) {
                            updateGravity(entity, seconds);
                            applyVelocity(entity, seconds);
                            count++;
                        }
                    }

                }
            }
            if (count > 1) {
                System.out.println(count); // TODO quando si passano i chunk viene chiiamato 2 volte updateGravity e applyVelocity
            }
        }

        // Collision Entities-Entities
        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {
                if ((i % 4 == 1 && j % 2 == 1) || (i % 4 == 3 && j % 2 == 0)) {
                    manageCollisionEntity2Entity(chunks, i, j);
                }
            }
        }
    }

    private static void manageChangeOfChunk(HashSet<Entity>[][] chunks, int i, int j) {
        List<Entity> entities = new ArrayList<>(chunks[i][j]);
        for (Entity entity : entities) {
            if (entity.getX() > i * CHUNK_SIZE + CHUNK_SIZE || entity.getX() + entity.getWidth() < i * CHUNK_SIZE || entity.getY() > j * CHUNK_SIZE + CHUNK_SIZE || entity.getY() + entity.getHeight() < j * CHUNK_SIZE) {
                chunks[i][j].remove(entity);
                int startI = (int) (entity.getX() / CHUNK_SIZE);
                int startJ = (int) (entity.getY() / CHUNK_SIZE);
                int endI = (int) ((entity.getX() + entity.getWidth()) / CHUNK_SIZE);
                int endJ = (int) ((entity.getY() + entity.getHeight()) / CHUNK_SIZE);
                for (int l = startI - 1; l <= endI + 1; l++) {
                    for (int m = startJ - 1; m <= endJ + 1; m++) {
                        if (l >= 0 && l < chunks.length && m >= 0 && m < chunks[0].length) {
                            chunks[l][m].add(entity);
                        }
                    }
                }
            }
        }
    }

    private static void updateGravity(Entity entity, double seconds) {
        if (entity.isAffectedByGravity()) {
            entity.setVelocityY(entity.getVelocityY() + GRAVITY * seconds);
        }
    }

    private static void applyVelocity(Entity entity, double seconds) {
        double velocityX = entity.getVelocityX();
        double velocityY = entity.getVelocityY();

        if (Math.abs(velocityX) > entity.getVelocityLimit()) {
            velocityX = (velocityX > 0) ? entity.getVelocityLimit() : -entity.getVelocityLimit();
        }
        if (Math.abs(velocityY) > entity.getVelocityLimit()) {
            velocityY = (velocityY > 0) ? entity.getVelocityLimit() : -entity.getVelocityLimit();
        }

        entity.setX(entity.getX() + velocityX * seconds);
        entity.setY(entity.getY() + velocityY * seconds);

        entity.setVelocityX(velocityX);
        entity.setVelocityY(velocityY);
    }

    private static void manageCollisionEntity2Entity(HashSet<Entity>[][] chunks, int i, int j) {
        HashSet<Entity> ents = new HashSet<>();
        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                int chunkX = i + k;
                int chunkY = j + l;
                if (chunkX >= 0 && chunkX < chunks.length && chunkY >= 0 && chunkY < chunks[0].length) {
                    ents.addAll(chunks[chunkX][chunkY]);
                }
            }
        }

        List<Entity> entities = new ArrayList<>(ents);
        for (int k = 0; k < entities.size(); k++) {
            Entity entity1 = entities.get(k);
            for (int l = k; l < entities.size(); l++) {
                Entity entity2 = entities.get(l);
                if (entity1.isCollidable() && entity2.isCollidable()) {
                    double left1 = entity1.getX();
                    double right1 = entity1.getX() + entity1.getWidth();
                    double top1 = entity1.getY();
                    double bottom1 = entity1.getY() + entity1.getHeight();

                    double left2 = entity2.getX();
                    double right2 = entity2.getX() + entity2.getWidth();
                    double top2 = entity2.getY();
                    double bottom2 = entity2.getY() + entity2.getHeight();

                    if (right1 > left2 && left1 < right2 && bottom1 > top2 && top1 < bottom2) {
                        double overlapX = Math.min(right1, right2) - Math.max(left1, left2);
                        double overlapY = Math.min(bottom1, bottom2) - Math.max(top1, top2);

                        if (overlapX < overlapY) {
                            double shiftX = overlapX / 2;
                            if (entity1 instanceof Block || entity2 instanceof Block) {
                                shiftX = overlapX;
                                entity1.setVelocityX(0);
                                entity2.setVelocityX(0);
                            }
                            shiftX *= 1.01;
                            if (left1 < left2) {
                                entity1.setX(entity1.getX() - shiftX);
                                entity2.setX(entity2.getX() + shiftX);
                            } else {
                                entity1.setX(entity1.getX() + shiftX);
                                entity2.setX(entity2.getX() - shiftX);
                            }
                        } else {
                            double shiftY = overlapY / 2;
                            if (entity1 instanceof Block || entity2 instanceof Block) {
                                shiftY = overlapY;
                                entity1.setVelocityY(0);
                                entity2.setVelocityY(0);
                            }
                            shiftY *= 1.01;
                            if (top1 < top2) {
                                entity1.setY(entity1.getY() - shiftY);
                                entity2.setY(entity2.getY() + shiftY);
                            } else {
                                entity1.setY(entity1.getY() + shiftY);
                                entity2.setY(entity2.getY() - shiftY);
                            }

                        }
                    }
                }
            }
        }
    }
}