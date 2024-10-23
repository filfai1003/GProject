package com.gproject.io.output;

import com.gproject.game.Camera;
import com.gproject.game.Game;
import com.gproject.game.entities.Entity;
import com.gproject.game.entities.Player;

import java.util.List;

import static com.gproject.game.Physics.Costants.*;
import static com.gproject.io.output.Render.renderImage;

public class GameRender {

    public static void render(Game game) {
        Player player = game.getPlayer();
        List<Entity>[][] chunks = game.getChunks();
        Camera camera = game.getCamera();
        boolean[][] blocks = game.getBlocks();

        int firstChunksX = (int) Math.floor(player.getX() / CHUNK_SIZE) - CHUNKS_TO_RENDER;
        int firstChunksY = (int) Math.floor(player.getY() / CHUNK_SIZE) - CHUNKS_TO_RENDER;
        int lastChunksX = (int) Math.floor(player.getX() / CHUNK_SIZE) + CHUNKS_TO_RENDER + 1;
        int lastChunksY = (int) Math.floor(player.getY() / CHUNK_SIZE) + CHUNKS_TO_RENDER + 1;

        firstChunksX = Math.max(0, firstChunksX);
        firstChunksY = Math.max(0, firstChunksY);
        lastChunksX = Math.min(chunks.length, lastChunksX);
        lastChunksY = Math.min(chunks[0].length, lastChunksY);

        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {
                for (int k = 0; k < chunks[i][j].size(); k++) {
                    Entity entity = chunks[i][j].get(k);
                    int[] imageParameter;
                    imageParameter = camera.adapt((int) entity.getX(), (int) entity.getY(), entity.getWidth(), entity.getHeight());
                    renderImage(imageParameter[0], imageParameter[1], imageParameter[2], imageParameter[3], "assets/colors/red.png");
                }
            }
        }

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (blocks[i][j]) {
                    int[] imageParameter;
                    imageParameter = camera.adapt((int) i*BLOCK_SIZE, (int) j*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    renderImage(imageParameter[0], imageParameter[1], imageParameter[2], imageParameter[3], "assets/colors/black.png");
                }
            }
        }
    }
    // TODO 8 Gestisci animazioni e assets corretti
}
