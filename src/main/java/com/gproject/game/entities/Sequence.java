package com.gproject.game.entities;

public class Sequence {
    private double xOffste, yOffste, widthMultiplier, heightMultiplier, timer, time;
    private Runnable onstart, onEnd;
    public Sequence next;

    public Sequence(double xOffste, double yOffste, double widthMultiplier, double heightMultiplier, double time, Runnable onstart, Runnable onEnd, Sequence next) {
        this.xOffste = xOffste;
        this.yOffste = yOffste;
        this.widthMultiplier = widthMultiplier;
        this.heightMultiplier = heightMultiplier;
        this.timer = time;
        this.time = time;
        this.onstart = onstart;
        this.onEnd = onEnd;
        this.next = next;
    }

    public void update(double seconds, Entity entity) {
        if (time == timer) {
            if (onstart != null) {
                onstart.run();
            }
        }
        if (timer <= 0){
            if (onEnd != null) {
                onEnd.run();
            }
            entity.sequence = next;
            return;
        }
        timer -= seconds;
        entity.x += xOffste*seconds/timer;
        entity.y += yOffste*seconds/timer;
        // TODO widthMultiplier & heightMultiplier
    }
}
