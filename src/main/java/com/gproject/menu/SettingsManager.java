package com.gproject.menu;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.gproject.menu.Settings.*;

public class SettingsManager {

    public static Map<String, Integer> loadAdjustableValues() {
        Map<String, Integer> adjustableValues = new HashMap<>();
        File file = new File("settings.txt");

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("settings.txt file not found, created a new one.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return adjustableValues;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("=")) {
                    String[] parts = line.split("=");
                    String key = parts[0].trim();
                    int value = Integer.parseInt(parts[1].trim());
                    adjustableValues.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        applyChanges(adjustableValues);
        return adjustableValues;
    }

    public static void applyLoadedValues(MenuNode node, Map<String, Integer> loadedValues) {
        if (loadedValues.containsKey(node.getTitle())) {
            node.setCurrentValue(loadedValues.get(node.getTitle()));
        }

        for (MenuNode child : node.getChildren()) {
            applyLoadedValues(child, loadedValues);
        }
    }

    public static void saveAdjustableValues(MenuNode node) {
        Map<String, Integer> loadedValues = loadAdjustableValues();

        if (node.isAdjustable()) {
            loadedValues.put(node.getTitle(), node.getCurrentValue());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("settings.txt"))) {
            for (Map.Entry<String, Integer> entry : loadedValues.entrySet()) {
                writer.write(entry.getKey() + " = " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        applyChanges(loadedValues);
    }

    public static void applyChanges(Map<String, Integer> settings) {
        // Display settings
        if (settings.containsKey("HUD Size")) {
            int hudSize = settings.get("HUD Size");
            setHudDimension(hudSize);
        }
        if (settings.containsKey("Brightness")) {
            int brightness = settings.get("Brightness");
            setBrightness(brightness);
        }
        if (settings.containsKey("Camera Speed")) {
            int cameraSpeed = settings.get("Camera Speed");
            setCameraSpeed(cameraSpeed);
        }
        if (settings.containsKey("Show FPS")) {
            int showFPS = settings.get("Show FPS");
            setShowFPS(showFPS);
        }
        if (settings.containsKey("Frame Rate Max")) {
            int maxFrameRate = settings.get("Frame Rate Max");
            setMaxFrameRate(maxFrameRate);
        }

        // Audio settings
        if (settings.containsKey("Master Volume")) {
            int masterVolume = settings.get("Master Volume");
            setMasterAudioLevel(masterVolume);
        }
        if (settings.containsKey("Music Volume")) {
            int musicVolume = settings.get("Music Volume");
            setMusicAudioLevel(musicVolume);
        }
        if (settings.containsKey("Effects Volume")) {
            int effectsVolume = settings.get("Effects Volume");
            setEffectAudioLevel(effectsVolume);
        }
    }
}
