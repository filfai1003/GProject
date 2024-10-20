package com.gproject.main;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Initializer {
    private long window;

    public void init() {
        // Inizializza GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Specifica la versione di OpenGL: 3.3 con profilo di compatibilità
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);

        // Crea una finestra di 800x800
        window = glfwCreateWindow(800, 800, "2D Game", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Unable to create the GLFW window");
        }

        // Imposta il contesto corrente
        glfwMakeContextCurrent(window);

        // Crea le capacità OpenGL (richiede un contesto attivo)
        GL.createCapabilities();

        // Abilita il blending per la trasparenza
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Imposta il colore di default per la pulizia del buffer (nero trasparente)
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Imposta la proiezione ortografica per il rendering 2D
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 800, 800, 0, -1, 1);  // Coordinate 2D
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        // Abilita il rendering delle texture
        glEnable(GL_TEXTURE_2D);

        // Ottieni le informazioni sul monitor principale
        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode videoMode = glfwGetVideoMode(monitor);

        // Se possibile, centra la finestra nello schermo
        if (videoMode != null) {
            int screenWidth = videoMode.width();
            int screenHeight = videoMode.height();
            int windowWidth = 800;
            int windowHeight = 800;
            int posX = (screenWidth - windowWidth) / 2;
            int posY = (screenHeight - windowHeight) / 2;
            glfwSetWindowPos(window, posX, posY);
        }

        // Mostra la finestra
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
