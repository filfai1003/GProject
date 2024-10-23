package com.gproject.game;

public class Camera {
    private double x, y;
    private double zoom;

    public Camera(double x, double y, double zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
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
