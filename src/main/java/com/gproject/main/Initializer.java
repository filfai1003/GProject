package com.gproject.main;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Initializer {
    private long window;

    public void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        window = glfwCreateWindow(800, 800, "2D Game", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Unable to create the GLFW window");
        }
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
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
    }


    public void cleanup() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public static void main(String[] args) {
        Initializer initializer = new Initializer();
        initializer.init();
        GameSyncronizer.start(initializer.window);
        initializer.cleanup();
    }
}