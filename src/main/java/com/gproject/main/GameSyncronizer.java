package com.gproject.main;

import com.gproject.io.input.InputManager;
import com.gproject.io.output.MenuRender;
import com.gproject.menu.Menu;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

public class GameSyncronizer {

    private static GameState state = GameState.MENU;
    private static Menu menu;

    public static void start(long window) {
        InputManager inputManager = new InputManager(window);
        menu();
        while (state != GameState.EXIT) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            inputManager.update();

            switch (state) {
                case MENU:
                    menu.update(inputManager.getInput());
                    MenuRender.render(menu);
                    break;

                case PLAY:
                    break;
            }

            glfwSwapBuffers(window);
        }
    }


    public static void menu() {
        menu = new Menu();
        state = GameState.MENU;
    }

    public static void play(int level) {
        state = GameState.PLAY;
    }

    public static void exit() {
        state = GameState.EXIT;
    }
}
