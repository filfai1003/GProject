package com.gproject.game.entities;

public class Sequence {
    private double xOffste, yOffste, widthMultiplier, heightMultiplier, timer, time;
    private Runnable onstart, onEnd;
    private Sequence next;

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
        entity.width = entity.width/(widthMultiplier*((timer-time)/timer));
        entity.height = entity.height/(heightMultiplier*((timer-time)/timer));
        if (timer <= 0){
            if (onEnd != null) {
                onEnd.run();
            }
            entity.width = entity.width*widthMultiplier;
            entity.height = entity.height*heightMultiplier;
            entity.sequence = next;
            return;
        }
        if (time == timer) {
            onstart.run();
        }
        timer -= seconds;
        entity.x += xOffste*seconds/timer;
        entity.y += yOffste*seconds/timer;
        entity.width = entity.width*widthMultiplier*((timer-time)/timer);
        entity.height = entity.height*heightMultiplier*((timer-time)/timer);
    }
}
