package com.gproject.menu;

import com.gproject.main.GameSyncronizer;

import java.util.Map;

public class MenuTree {

    public static MenuNode initializeMenu() {
        MenuNode root = new MenuNode("Main Menu");

        MenuNode startGame = new MenuNode("Start Game", () -> GameSyncronizer.play());

        MenuNode settings = new MenuNode("Settings");
        MenuNode frameRate = new MenuNode("Frame Rate Max", 30, 240, 60, 30);
        MenuNode brightness = new MenuNode("Brightness", 0, 10, 5, 1);
        MenuNode showFPS = new MenuNode("Show FPS", 0, 1, 0, 1);
        MenuNode cameraSpeed = new MenuNode("Camera Speed", 1, 20, 0, 1);
        settings.addChild(frameRate);
        settings.addChild(brightness);
        settings.addChild(showFPS);
        settings.addChild(cameraSpeed);

        MenuNode exit = new MenuNode("Exit", () -> GameSyncronizer.exit());

        root.addChild(startGame);
        root.addChild(settings);
        root.addChild(exit);

        Map<String, Integer> loadedValues = SettingsManager.loadAdjustableValues();
        SettingsManager.applyLoadedValues(root, loadedValues);

        return root;
    }
}
