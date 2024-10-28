package com.gproject.io.output;

import com.gproject.game.Camera;
import com.gproject.game.Game;
import com.gproject.game.entities.Entity;
import com.gproject.game.entities.LivingEntity;
import com.gproject.game.entities.Player;
import com.gproject.main.GameSync;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.gproject.game.Costants.*;
import static com.gproject.io.output.Render.renderImage;
import static com.gproject.io.output.Render.renderText;
import static com.gproject.main.GameSync.window;
import static com.gproject.menu.Settings.showFPS;
import static org.lwjgl.opengl.GL11.glColor4f;

public class GameRender {

    public static void render(Game game) {

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

        HashSet<Entity> entities = new HashSet<>();

        for (int i = firstChunksX; i < lastChunksX; i++) {
            for (int j = firstChunksY; j < lastChunksY; j++) {

                if ((i + j) % 2 == 0) {
                    glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
                    int[] imageParameter = camera.adapt( i*CHUNK_SIZE,  j*CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE);
                    renderImage(imageParameter[0], imageParameter[1], imageParameter[2], imageParameter[3], "assets/images/colors/red.png");
                    glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                }

                entities.addAll(chunks[i][j]);
            }
        }

        for (Entity entity : entities) {
            int[] imageParameter = camera.adapt((int) entity.x, (int) entity.y, (int) entity.width, (int) entity.height);
            renderImage(imageParameter[0], imageParameter[1], imageParameter[2], imageParameter[3], "assets/images/colors/red.png");
            if (entity instanceof LivingEntity) {
                renderText(imageParameter[0], imageParameter[1], 20, entity.toString(), false);
            }
        }

        int[] w = new int[1];
        int[] h = new int[1];
        GLFW.glfwGetWindowSize(window, w, h);

        renderImage(20, h[0]-120, 100, 100, "assets/images/inventory/back.png");
        if (game.getInventory().item1 != null) {
            renderImage(40, h[0]-100, 60, 60, "assets/images/inventory/" + game.getInventory().item1.name + ".png");
        }
        renderImage(20, h[0]-240, 100, 100, "assets/images/inventory/back.png");
        if (game.getInventory().item2 != null) {
            renderImage(40, h[0]-220, 60, 60, "assets/images/inventory/" + game.getInventory().item2.name + ".png");
        }
        renderImage(140, h[0]-120, 100, 100, "assets/images/inventory/back.png");
        if (game.getInventory().secondWeapon != null) {
            renderImage(160, h[0]-100, 60, 60, "assets/images/inventory/" + game.getInventory().secondWeapon.name + ".png");
        }
        renderImage(140, h[0]-240, 100, 100, "assets/images/inventory/back.png");
        if (game.getInventory().mainWeapon != null) {
            renderImage(160, h[0]-220, 60, 60, "assets/images/inventory/" + game.getInventory().mainWeapon.name + ".png");
        }

        if (showFPS){
            renderText(0,0,50, String.valueOf(GameSync.getFrameRate()), false);
        }
    }
}
