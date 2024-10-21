package com.gproject.menu;

import com.gproject.main.GameSyncronizer;

import java.util.Map;

public class MenuTree {

    public static MenuNode initializeMenu() {
        MenuNode root = new MenuNode("Main Menu");

        MenuNode startGame = new MenuNode("Start Game");
        MenuNode settings = new MenuNode("Settings");

        MenuNode frameRate = new MenuNode("Frame Rate Max", 30, 240, 60, 30);
        MenuNode brightness = new MenuNode("Brightness", 0, 10, 5, 1);
        settings.addChild(frameRate);
        settings.addChild(brightness);

        MenuNode exit = new MenuNode("Exit", () -> GameSyncronizer.exit());

        for (int i = 1; i <= 5; i++) {
            int level = i;
            MenuNode levelNode = new MenuNode("Level " + level, () -> GameSyncronizer.play(level));
            startGame.addChild(levelNode);
        }

        root.addChild(startGame);
        root.addChild(settings);
        root.addChild(exit);

        Map<String, Integer> loadedValues = SettingsManager.loadAdjustableValues();
        SettingsManager.applyLoadedValues(root, loadedValues);

        return root;
    }
}
