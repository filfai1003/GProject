package com.gproject.io.output;

import com.gproject.game.Camera;
import com.gproject.game.Game;
import com.gproject.game.entities.Entity;
import com.gproject.game.entities.Player;
import com.gproject.main.GameSyncronizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.gproject.game.Costants.*;
import static com.gproject.io.output.Render.renderImage;
import static com.gproject.io.output.Render.renderText;
import static org.lwjgl.opengl.GL11.glColor4f;

public class GameRender {

    public static void render(Game game) {
        if (GameSyncronizer.getShowFPS()){
            renderText(0,0,50, String.valueOf(GameSyncronizer.getFrameRate()), false);
        }

        Player player = game.getPlayer();
        HashSet<Entity>[][] chunks = game.getChunks();
        Camera camera = game.getCamera();

        int firstChunksX = (int) Math.floor(player.x / CHUNK_SIZE) - CHUNKS_TO_RENDER;
        int firstChunksY = (int) Math.floor(player.y / CHUNK_SIZE) - CHUNKS_TO_RENDER;
        int lastChunksX = (int) Math.floor(player.x / CHUNK_SIZE) + CHUNKS_TO_RENDER + 1;
        int lastChunksY = (int) Math.floor(player.y / CHUNK_SIZE) + CHUNKS_TO_RENDER + 1;

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

                List<Entity> entities = new ArrayList<>(chunks[i][j]);
                for (Entity entity : entities) {
                    int[] imageParameter = camera.adapt((int) entity.x, (int) entity.y, entity.getWidth(), entity.getHeight());
                    renderImage(imageParameter[0], imageParameter[1], imageParameter[2], imageParameter[3], "assets/colors/red.png");
                }
            }
        }
    }
    // TODO 8 Gestisci animazioni e assets corretti
}
