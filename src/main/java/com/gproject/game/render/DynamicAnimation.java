package com.gproject.game.render;

public class DynamicAnimation extends Animation {

    private double overrideTime = 0;
    public String overridePath;

    public DynamicAnimation(double x, double y, double width, double height, int fps, int frames, String basePath) {
        super(x, y, width, height, fps, frames, true, basePath);
    }

    // Metodo per impostare un'animazione specifica per un certo periodo
    public void startOverrideAnimation(double duration, String overridePath) {
        this.overridePath = overridePath;
        this.time = 0;
        this.overrideTime = duration;
    }

    @Override
    public int getCurrentIndex() {
        if (isOverriding()) {
            overrideTime -= (1.0 / fps);
        }
        return super.getCurrentIndex();
    }

    public boolean isOverriding() {
        return overrideTime > 0;
    }
}