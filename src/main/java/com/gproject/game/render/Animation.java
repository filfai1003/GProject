package com.gproject.game.render;

public class Animation {
    public double x, y, width, height;
    protected int fps, frames;
    public double time = 0;
    protected boolean endLess;
    public String path;
    protected boolean toremove = false;

    public Animation(double x, double y, double width, double height, int fps, int frames, boolean endLess, String path) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fps = fps;
        this.frames = frames;
        this.endLess = endLess;
        this.path = path;
    }

    public void update(double seconds) {
        time += seconds;
    }

    public int getCurrentIndex(){
        int ret = (int) (time*fps);
        if (ret > frames) {
            if (!endLess) {
                toremove = true;
                time -= (double) frames / fps;
                ret = (int) (time*fps);
            }
            else {
                time -= (double) frames / fps;
                ret = (int) (time*fps);
            }
        }
        return ret;
    }

    public String getPath() {
        return path;
    }
}
