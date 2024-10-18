package com.gproject.core;

import com.gproject.IO.input.InputManager;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game {
    private long window;
    private GameStateManager gameStateManager;
    private InputManager inputManager;

    public void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);  // OpenGL 3.3
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        window = glfwCreateWindow(800, 800, "2D Game", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Unable to create the GLFW window");
        }
        glfwMakeContextCurrent(window);
        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode videoMode = glfwGetVideoMode(monitor);
        if (videoMode != null) {
            int screenWidth = videoMode.width();
            int screenHeight = videoMode.height();
            int windowWidth = 800;
            int windowHeight = 800;
            int posX = (screenWidth - windowWidth) / 2;
            int posY = (screenHeight - windowHeight) / 2;
            glfwSetWindowPos(window, posX, posY);
        }
        glfwShowWindow(window);
        if (glfwGetCurrentContext() == 0) {
            throw new RuntimeException("Failed to set current OpenGL context.");
        }
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        gameStateManager = new GameStateManager();
        inputManager = new InputManager();
    }

    public void loop() {
        while (!glfwWindowShouldClose(window)) {
            inputManager.updateInputs(window);
            gameStateManager.update(inputManager.getInputStates());

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            gameStateManager.render();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public void cleanup() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.init();
        game.loop();
        game.cleanup();
    }
}