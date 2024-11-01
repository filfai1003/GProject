package com.gproject.game.render;

import com.gproject.game.entities.Entity;
import com.gproject.game.entities.LivingEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Animation {

    private static final int FPS = 30;

    public String path;
    public double x, y;
    public double width, height;
    public int frames;
    public Entity entity;
    public double time;
    private boolean hasStatusPath;
    public Animation overWrite;
    public String status = " ";

    public Animation(Entity entity, double y, double x, String path) {
        this.entity = entity;
        this.y = y;
        this.x = x;
        this.width = entity.width;
        this.height = entity.height;
        this.path = path;
        updateFrames();
    }

    public Animation(String path, double y, double x, int width, int height) {
        this.path = path;
        this.y = y;
        this.x = x;
        this.width = width;
        this.height = height;
        updateFrames();
    }

    public void update(double seconds) {
        time += seconds;
        if (overWrite != null) {
            overWrite.update(seconds);
        }
    }

    public void updateFrames(){
        String dirPath = path;

        if (entity != null) {
            dirPath = getEntityPath();

            hasStatusPath = Files.exists(Path.of(dirPath));

            if (!hasStatusPath) {
                status = " ";
                dirPath = getAnimationPath();
            } else {
                status = entity.status;
            }
        }

        try {
            frames = (int) Files.list(Path.of(dirPath)).filter(p -> p.toString().endsWith(".png")).count();
        } catch (IOException e) {
            System.err.println("Errore nel conteggio dei frame in " + dirPath + ": " + e.getMessage());
            frames = 1;
        }
        frames = Math.max(1, frames);
        time = 0;
    }

    private String getEntityPath() {
        if (entity instanceof LivingEntity) {
            String direction = ((LivingEntity) entity).d == 1 ? "r" : "l";
            return path + "/" + entity.status + "/" + direction;
        }
        return path + "/" + entity.status;
    }
    private String getAnimationPath() {
        if (entity instanceof LivingEntity) {
            String direction = ((LivingEntity) entity).d == 1 ? "r" : "l";
            return path + "/" + status + "/" + direction;
        }
        return path + "/" + status;
    }

    public String getImagePath() {
        if (overWrite != null) {
            if (overWrite.time > (double) frames /FPS){
                overWrite = null;
                updateFrames();
            } else {
                return overWrite.getImagePath();
            }
        }

        int currentFrame = (int) (time * FPS) % frames;

        if (!hasStatusPath) {
            return String.format("%s/%d.png", path, currentFrame);
        }

        if (entity instanceof LivingEntity) {
            String direction = ((LivingEntity) entity).d == 1 ? "r" : "l";
            return String.format("%s/%s/%s/%d.png", path, status, direction, currentFrame);
        } else {
            return String.format("%s/%s/%d.png", path, status, currentFrame);
        }
    }
}
