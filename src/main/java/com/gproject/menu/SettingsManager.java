package com.gproject.menu;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
    }

}
