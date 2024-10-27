package com.gproject.menu;

import static com.gproject.io.output.Sound.setEffectsVolume;
import static com.gproject.io.output.Sound.setMusicVolume;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Settings {

    // Display settings
    public static float hudDimension = 1;
    public static int brightness = 5;
    public static int cameraSpeed = 5;
    public static boolean showFPS = false;
    public static long frameDuration = 1_000_000_000L / 30;

    // Audio settings
    public static float masterAudioLevel = 1;
    public static float musicAudioLevel = 1;
    public static float effectAudioLevel = 1;


    // Video settings methods
    public static void setHudDimension(float dimension) {
        hudDimension = dimension / 50;
    }
    public static void setBrightness(int brightness) {
        Settings.brightness = brightness;
        float adjustedBrightness = 0.1f + (brightness / 10.0f) * 0.9f;
        glClearColor(adjustedBrightness, adjustedBrightness, adjustedBrightness, 1.0f);
    }
    public static void setCameraSpeed(int cameraSpeed) {
        Settings.cameraSpeed = cameraSpeed;
    }
    public static void setShowFPS(int sFPS) {
        showFPS = sFPS == 1;
    }
    public static void setMaxFrameRate(int maxFramerate) {
        frameDuration = 1_000_000_000L / maxFramerate;
    }


    // Audio settings methods
    public static void setMasterAudioLevel(float level) {
        masterAudioLevel = level / 100;
        setMusicVolume();
        setEffectsVolume();
    }
    public static void setMusicAudioLevel(float level) {
        musicAudioLevel = level / 100;
        setMusicVolume();
    }
    public static void setEffectAudioLevel(float level) {
        effectAudioLevel = level / 100;
        setEffectsVolume();
    }
}
