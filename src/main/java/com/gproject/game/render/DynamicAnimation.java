package com.gproject.game.render;

public class DynamicAnimation extends Animation {

    private double overrideTime = 0;
    private DynamicAnimation baseAnimation;

    public DynamicAnimation(double x, double y, double width, double height, int fps, int frames, String basePath) {
        super(x, y, width, height, fps, frames, true, basePath);
    }

    @Override
    public void update(double seconds) {
        super.update(seconds);
        overrideTime -= seconds;
        if (baseAnimation != null && overrideTime <= 0) {
            x = baseAnimation.x;
            y = baseAnimation.y;
            width = baseAnimation.width;
            height = baseAnimation.height;
            fps = baseAnimation.fps;
            frames = baseAnimation.frames;
            path = baseAnimation.path;
            toremove = baseAnimation.toremove;
            time = 0;
            baseAnimation= null;
        }
    }

    // Metodo per impostare un'animazione specifica per un certo periodo come override
    public void startOverrideAnimation(double duration, DynamicAnimation overrideAnimation) {
        DynamicAnimation t = new DynamicAnimation(x, y, width, height, fps, frames, path);

        x = overrideAnimation.x;
        y = overrideAnimation.y;
        width = overrideAnimation.width;
        height = overrideAnimation.height;
        fps = overrideAnimation.fps;
        frames = overrideAnimation.frames;
        path = overrideAnimation.path;
        toremove = overrideAnimation.toremove;
        time = 0;

        baseAnimation = t;
        overrideTime = duration;
    }

    public boolean isOverriding(){
        return baseAnimation != null;
    }
}
