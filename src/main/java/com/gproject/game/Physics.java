package com.gproject.game;

import com.gproject.game.entities.Block;
import com.gproject.game.entities.Entity;
import com.gproject.game.entities.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.gproject.game.Costants.*;

public class Physics {

    public static void update(Player player, HashSet<Entity>[][] chunks, double seconds) {
        int firstChunksX = (int) Math.floor(player.x / CHUNK_SIZE) - CHUNKS_TO_UPDATE;
        int firstChunksY = (int) Math.floor(player.y / CHUNK_SIZE) - CHUNKS_TO_UPDATE;
        int lastChunksX = (int) Math.floor(player.x / CHUNK_SIZE) + CHUNKS_TO_UPDATE + 1;
        int lastChunksY = (int) Math.floor(player.y / CHUNK_SIZE) + CHUNKS_TO_UPDATE + 1;

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
            if (entity.x > i * CHUNK_SIZE + CHUNK_SIZE || entity.x + entity.getWidth() < i * CHUNK_SIZE || entity.y > j * CHUNK_SIZE + CHUNK_SIZE || entity.y + entity.getHeight() < j * CHUNK_SIZE) {
                chunks[i][j].remove(entity);
                int startI = (int) (entity.x / CHUNK_SIZE);
                int startJ = (int) (entity.y / CHUNK_SIZE);
                int endI = (int) ((entity.x + entity.getWidth()) / CHUNK_SIZE);
                int endJ = (int) ((entity.y + entity.getHeight()) / CHUNK_SIZE);
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
                if (entity1.isAffectByCollision() && entity2.isAffectByCollision()) {

                    double left1 = entity1.x;
                    double right1 = entity1.x + entity1.getWidth();
                    double top1 = entity1.y;
                    double bottom1 = entity1.y + entity1.getHeight();

                    double left2 = entity2.x;
                    double right2 = entity2.x + entity2.getWidth();
                    double top2 = entity2.y;
                    double bottom2 = entity2.y + entity2.getHeight();

                    if (right1 > left2 && left1 < right2 && bottom1 > top2 && top1 < bottom2) {
                        double overlapX = Math.min(right1, right2) - Math.max(left1, left2);
                        double overlapY = Math.min(bottom1, bottom2) - Math.max(top1, top2);

                        if (overlapX < overlapY) {
                            double shiftX = overlapX / 2;
                            if (entity1 instanceof Block || entity2 instanceof Block) {
                                shiftX = overlapX;
                                entity1.velocityX = 0;
                                entity2.velocityX = 0;
                            }
                            if (left1 < left2) {
                                entity1.setX(entity1.x - shiftX);
                                entity2.setX(entity2.x + shiftX);
                            } else {
                                entity1.setX(entity1.x + shiftX);
                                entity2.setX(entity2.x - shiftX);
                            }
                        } else {
                            double shiftY = overlapY / 2;
                            if (entity1 instanceof Block || entity2 instanceof Block) {
                                shiftY = overlapY;
                                if (entity1 instanceof Player){
                                    ((Player) entity1).onSingleJump = true;
                                }
                                entity1.lastOnGround = 0;
                                entity1.velocityY = 0;
                                if (entity2 instanceof Player){
                                    ((Player) entity2).onSingleJump = true;
                                }
                                entity2.lastOnGround = 0;
                                entity2.velocityY = 0;
                            }
                            if (top1 < top2) {
                                entity1.setY(entity1.y - shiftY);
                                entity2.setY(entity2.y + shiftY);
                            } else {
                                entity1.setY(entity1.y + shiftY);
                                entity2.setY(entity2.y - shiftY);
                            }
                        }
                    }
                }
            }
        }
    }
}