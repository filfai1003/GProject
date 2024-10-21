package com.gproject.main;

import com.gproject.game.Game;
import com.gproject.io.input.InputManager;
import com.gproject.io.output.GameRender;
import com.gproject.io.output.MenuRender;
import com.gproject.menu.Menu;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

public class GameSyncronizer {

    public static long window;
    private static long lastFrameTime;
    private static int maxFramerate;
    private static GameState state = GameState.MENU;
    private static Menu menu;
    private static Game game;

    public static void start(long w) {
        window = w;
        InputManager inputManager = new InputManager(window);
        menu();

        lastFrameTime = System.nanoTime();
        long frameDuration = 1_000_000_000L / maxFramerate;

        while (state != GameState.EXIT) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            inputManager.update();

            long currentFrameTime = System.nanoTime();
            float deltaTime = (currentFrameTime - lastFrameTime) / 1_000_000_000.0f;

            switch (state) {
                case MENU:
                    menu.update(inputManager.getInput());
                    MenuRender.render(menu);
                    break;

                case PLAY:
                    game.update(inputManager.getInput(), deltaTime);
                    GameRender.render();
                    break;
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

            lastFrameTime = System.nanoTime();
        }
    }

    public static void menu() {
        menu = new Menu();
        state = GameState.MENU;
    }

    public static void play(int level) {
        game = new Game(level);
        state = GameState.PLAY;
    }

    public static void exit() {
        state = GameState.EXIT;
    }

    public static void setMaxFrameRate(int maxFramerate) {
        GameSyncronizer.maxFramerate = maxFramerate;
    }
}
