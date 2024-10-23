package com.gproject.game.Physics;

import com.gproject.game.entities.Entity;
import com.gproject.game.entities.Player;

import java.util.ArrayList;
import java.util.List;

import static com.gproject.game.Physics.Costants.*;

public class Physics {

    public static void update(Player player, List<Entity>[][] chunks, boolean[][] blocks, double seconds) {
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
        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {
                for (Entity entity : chunks[i][j]) {
                    updateGravity(entity, seconds);
                    applyVelocity(entity, seconds);
                }
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

        // Collision Entities-Blocks
        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {
                for (Entity entity : chunks[i][j]) {
                    if (entity.isCollidable()) {
                        manageCollisionEntity2Blocks(entity, blocks);
                    }
                }
            }
        }
    }

    private static void manageChangeOfChunk(List<Entity>[][] chunks, int i, int j) {
        for (int k = 0; k < chunks[i][j].size(); k++) {
            Entity entity = chunks[i][j].get(k);
            if (entity.getX() < i * CHUNK_SIZE || (i + 1) * CHUNK_SIZE < entity.getX() || entity.getY() < j * CHUNK_SIZE || (j + 1) * CHUNK_SIZE < entity.getY()) {
                chunks[i][j].remove(entity);
                k--;
                int newI = (int) (entity.getX() / CHUNK_SIZE);
                int newJ = (int) (entity.getY() / CHUNK_SIZE);
                if (newI >= 0 && newI < chunks.length && newJ >= 0 && newJ < chunks[0].length) {
                    chunks[newI][newJ].add(entity);
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

    private static void manageCollisionEntity2Entity(List<Entity>[][] chunks, int i, int j) {
        List<Entity> entities = new ArrayList<>();
        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                int chunkX = i + k;
                int chunkY = j + l;
                if (chunkX >= 0 && chunkX < chunks.length && chunkY >= 0 && chunkY < chunks[0].length) {
                    entities.addAll(chunks[chunkX][chunkY]);
                }
            }
        }

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
                            if (left1 < left2) {
                                entity1.setX(entity1.getX() - shiftX);
                                entity2.setX(entity2.getX() + shiftX);
                            } else {
                                entity1.setX(entity1.getX() + shiftX);
                                entity2.setX(entity2.getX() - shiftX);
                            }
                        } else {
                            double shiftY = overlapY / 2;
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

    private static void manageCollisionEntity2Blocks(Entity entity, boolean[][] blocks) {
        int startX = (int) Math.floor(entity.getX() / BLOCK_SIZE);
        int endX = (int) Math.floor((entity.getX() + entity.getWidth()) / BLOCK_SIZE);
        int startY = (int) Math.floor(entity.getY() / BLOCK_SIZE);
        int endY = (int) Math.floor((entity.getY() + entity.getHeight()) / BLOCK_SIZE);

        startX = Math.max(0, startX);
        endX = Math.min(blocks.length - 1, endX);
        startY = Math.max(0, startY);
        endY = Math.min(blocks[0].length - 1, endY);

        double topDistance = entity.getY() - startY * BLOCK_SIZE;
        double bottomDistance = endY * BLOCK_SIZE - (entity.getY() + entity.getHeight());
        double rightDistance = entity.getX() - startX * BLOCK_SIZE;
        double leftDistance = endX * BLOCK_SIZE - (entity.getX() + entity.getWidth());

        boolean topCollision = false;
        boolean bottomCollision = false;
        boolean rightCollision = false;
        boolean leftCollision = false;


        try {
            for (int i = startX+1; i < endX; i++) {
                if (blocks[i][startY] && entity.getVelocityY() < 0) {
                    topCollision = true;

                }
                if (blocks[i][endY] && entity.getVelocityY() > 0) {
                    bottomCollision = true;
                }
            }
            for (int i = startY+1; i < endY; i++) {
                if (blocks[startX][i] && entity.getVelocityX() < 0) {
                    leftCollision = true;
                }
                if (blocks[endX][i] && entity.getVelocityX() > 0) {
                    rightCollision = true;
                }
            }

            if (topCollision && !bottomCollision || topCollision && topDistance < bottomDistance) {
                entity.setY(startY * BLOCK_SIZE + BLOCK_SIZE);
                entity.setVelocityY(0);
            } else if (bottomCollision) {
                entity.setY(endY * BLOCK_SIZE - entity.getHeight());
                entity.setVelocityY(0);
            }
            if (leftCollision && !rightCollision || leftCollision && leftDistance < rightDistance) {
                entity.setX(startX * BLOCK_SIZE + BLOCK_SIZE);
                entity.setVelocityX(0);
            } else if (rightCollision) {
                entity.setX(endX * BLOCK_SIZE - entity.getWidth());
                entity.setVelocityX(0);
            }
        } catch (Exception e) {
        }
    }
}