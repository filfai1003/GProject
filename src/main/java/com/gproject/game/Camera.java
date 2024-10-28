package com.gproject.game;

import com.gproject.game.entities.Player;
import org.lwjgl.glfw.GLFW;

import java.io.Serial;
import java.io.Serializable;

import static com.gproject.main.GameSync.window;
import static com.gproject.menu.Settings.cameraSpeed;

public class Camera implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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

        int xObjective = (int) (player.x + (double) player.width / 2 - (double) w[0] / 2);
        int yObjective = (int) (player.y + (double) player.height / 2 - (double) h[0] / 2);

        xObjective = (int) (xObjective + (double) w[0] / 2 * (1 - 1 / zoom));
        yObjective = (int) (yObjective + (double) h[0] / 2 * (1 - 1 / zoom));

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
}
