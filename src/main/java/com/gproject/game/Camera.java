package com.gproject.game;

import com.gproject.game.entities.Player;
import org.lwjgl.glfw.GLFW;

import static com.gproject.main.GameSyncronizer.window;
import static com.gproject.menu.Settings.cameraSpeed;

public class Camera {

    // Camera position and zoom level
    protected double x, y;
    protected double zoom;

    // Constructor
    public Camera(double x, double y, double zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }

    // Update camera position to follow the player with smooth interpolation
    public void update(Player player, double seconds) {
        int[] w = new int[1];
        int[] h = new int[1];
        GLFW.glfwGetWindowSize(window, w, h);

        int xObjective = (int) (player.x + player.getWidth() / 2 - w[0] / 2);
        int yObjective = (int) (player.y + player.getHeight() / 2 - h[0] / 2);

        xObjective = (int) (xObjective + w[0] / 2 * (1 - 1 / zoom));
        yObjective = (int) (yObjective + h[0] / 2 * (1 - 1 / zoom));

        double lerpFactor = Math.min(1.0, seconds * cameraSpeed);
        if (cameraSpeed == 20) {
            lerpFactor = 1;
        }

        this.x += (xObjective - this.x) * lerpFactor;
        this.y += (yObjective - this.y) * lerpFactor;
    }

    // Adapts an object's position and size based on the camera's position and zoom level
    public int[] adapt(int x, int y, int width, int height) {
        int nWidth = (int) (width * zoom);
        int nHeight = (int) (height * zoom);
        int nX = (int) ((x - this.x) * zoom);
        int nY = (int) ((y - this.y) * zoom);
        return new int[] {nX, nY, nWidth, nHeight};
    }

    // Zoom in
    public void zoom() {
        zoom *= Math.sqrt(2);
    }

    // Zoom out
    public void deZoom() {
        zoom /= Math.sqrt(2);
    }

    public double getZoom() {
        return zoom;
    }
}
