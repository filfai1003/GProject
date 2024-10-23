package com.gproject.game;

import com.gproject.game.entities.Player;
import org.lwjgl.glfw.GLFW;

import static com.gproject.main.GameSyncronizer.window;
import static com.gproject.menu.Settings.cameraSpeed;
import static com.gproject.menu.Settings.getCameraSpeed;

public class Camera {
    private double x, y;
    private double zoom;

    public Camera(double x, double y, double zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }

    public void update(Player player, double seconds) {
        int[] w = new int[1];
        int[] h = new int[1];
        GLFW.glfwGetWindowSize(window, w, h);
        int xObjective = (int) (player.getX() - w[0]/2);
        int yObjective = (int) (player.getY() - h[0]/2);
        xObjective = (int) (xObjective + w[0]/2*(1-1/zoom));
        yObjective = (int) (yObjective + h[0]/2*(1-1/zoom));

        double lerpFactor = Math.min(1.0, seconds * getCameraSpeed());
        if (cameraSpeed == 20){
            lerpFactor = 1;
        }

        this.x += (xObjective - this.x) * lerpFactor;
        this.y += (yObjective - this.y) * lerpFactor;
    }

    public int[] adapt(int x, int y, int width, int height) {
        int nWidth = (int) (width*zoom);
        int nHeight = (int) (height*zoom);
        int nX = (int) ((x - this.x) * zoom);
        int nY = (int) ((y - this.y) * zoom);
        return new int[] {nX, nY, nWidth, nHeight};
    }

    public void zoom(){
        zoom *= 1.25;
    }
    public void deZoom(){
        zoom /= 1.25;
    }
}
