package com.gproject.io.output;

import com.gproject.game.Camera;
import com.gproject.game.Game;
import com.gproject.game.entities.Entity;
import com.gproject.game.entities.Player;
import com.gproject.main.GameSyncronizer;

import java.util.List;

import static com.gproject.game.Physics.Costants.*;
import static com.gproject.io.output.Render.renderImage;
import static com.gproject.io.output.Render.renderText;
import static org.lwjgl.opengl.GL11.glColor4f;

public class GameRender {

    public static void render(Game game) {
        if (GameSyncronizer.getShowFPS()){
            renderText(0,0,50, String.valueOf(GameSyncronizer.getFrameRate()), false);
        }

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

                if ((i + j) % 2 == 0) {
                    glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
                    int[] imageParameter = camera.adapt( i*CHUNK_SIZE,  j*CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE);
                    renderImage(imageParameter[0], imageParameter[1], imageParameter[2], imageParameter[3], "assets/colors/red.png");
                    glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                }

                for (int k = 0; k < chunks[i][j].size(); k++) {
                    Entity entity = chunks[i][j].get(k);
                    int[] imageParameter = camera.adapt((int) entity.getX(), (int) entity.getY(), entity.getWidth(), entity.getHeight());
                    renderImage(imageParameter[0], imageParameter[1], imageParameter[2], imageParameter[3], "assets/colors/red.png");
                }
            }
        }

        int firstBlocksX = (int) Math.floor(player.getX() / BLOCK_SIZE) - BLOCKS_TO_RENDER;
        int firstBlockY = (int) Math.floor(player.getY() / BLOCK_SIZE) - BLOCKS_TO_RENDER;
        int lastBlockX = (int) Math.floor(player.getX() / BLOCK_SIZE) + BLOCKS_TO_RENDER + 1;
        int lastBlockY = (int) Math.floor(player.getY() / BLOCK_SIZE) + BLOCKS_TO_RENDER + 1;

        firstBlocksX = Math.max(0, firstBlocksX);
        firstBlockY = Math.max(0, firstBlockY);
        lastBlockX = Math.min(blocks.length, lastBlockX);
        lastBlockY = Math.min(blocks[0].length, lastBlockY);

        for (int i = firstBlocksX; i < lastBlockX; i++) {
            for (int j = firstBlockY; j < lastBlockY; j++) {
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
