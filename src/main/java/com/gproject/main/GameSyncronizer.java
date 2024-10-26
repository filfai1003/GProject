package com.gproject.main;

import com.gproject.game.Game;
import com.gproject.io.input.Input;
import com.gproject.io.output.GameRender;
import com.gproject.io.output.MenuRender;
import com.gproject.io.output.Sound;
import com.gproject.menu.Menu;

import static com.gproject.menu.Settings.frameDuration;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;

public class GameSyncronizer {

    public static long window;
    private static GameState state = GameState.MENU;
    private static Menu menu;
    private static Game game;
    private static float deltaTime = 0;

    public static void start(long w) {
        window = w;
        menu();
        Input.initialize(window);
        Sound.playMusic("assets/sound/base.wav");

        long lastFrameTime = System.nanoTime();


        while (!glfwWindowShouldClose(window) && state != GameState.EXIT) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Input.update();

            long currentFrameTime = System.nanoTime();
            deltaTime = (currentFrameTime - lastFrameTime) / 1_000_000_000.0f;

            switch (state) {
                case MENU:
                    menu.update(Input.getInput());
                    MenuRender.render(menu);
                    break;

                case PLAY:
                    lastFrameTime = System.nanoTime();
                    game.update(Input.getInput(), deltaTime);
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



}
