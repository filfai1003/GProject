package com.gproject.main;

import com.gproject.game.Game;
import com.gproject.io.input.InputManager;
import com.gproject.io.output.GameRender;
import com.gproject.io.output.MenuRender;
import com.gproject.menu.Menu;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;

public class GameSyncronizer {

    public static long window;
    private static boolean showFPS = false;
    private static long frameDuration = 1_000_000_000L / 30;
    private static GameState state = GameState.MENU;
    private static Menu menu;
    private static Game game;
    private static float deltaTime = 0;

    public static void start(long w) {
        window = w;
        InputManager inputManager = new InputManager(window);
        menu();

        long lastFrameTime = System.nanoTime();

        while (!glfwWindowShouldClose(window) && state != GameState.EXIT) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            inputManager.update();

            long currentFrameTime = System.nanoTime();
            deltaTime = (currentFrameTime - lastFrameTime) / 1_000_000_000.0f;

            switch (state) {
                case MENU:
                    menu.update(inputManager.getInput());
                    MenuRender.render(menu);
                    break;

                case PLAY:
                    lastFrameTime = System.nanoTime();
                    game.update(inputManager.getInput(), deltaTime);
                    GameRender.render(game);
                    break;
                case EXIT:
            }

            glfwSwapBuffers(window);

            long timeToSleep = frameDuration - (System.nanoTime() - currentFrameTime);
            if (timeToSleep > 0) {
                try {
                    Thread.sleep(timeToSleep / 1_000_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void menu() {
        if (menu == null) {
            menu = new Menu();
        }
        state = GameState.MENU;
    }

    public static void play() {
        if (game == null) {
            game = new Game();
        }
        state = GameState.PLAY;
    }

    public static void exit() {
        state = GameState.EXIT;
    }

    public static int getFrameRate() {
        if (deltaTime > 0) {
            int frameRate = Math.round(1.0f / deltaTime);
            return (int) (Math.floor(frameRate / 10.0f) * 10);
        }
        return 0;
    }

    public static boolean getShowFPS() {
        return showFPS;
    }

    public static void setMaxFrameRate(int maxFramerate) {
        frameDuration = 1_000_000_000L / maxFramerate;
    }

    public static void setShowFPS(int sFPS) {
        if (sFPS == 0) {
            showFPS = false;
        } else if (sFPS == 1) {
            showFPS = true;
        }
    }

}
