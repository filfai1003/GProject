package com.gproject.menu;

import com.gproject.main.GameSync;

import java.util.Map;

public class MenuTree {
    public static MenuNode initializeMenu() {
        // Root node for the main menu
        MenuNode root = new MenuNode("Main Menu");

        // Option to start the game
        MenuNode startGame = new MenuNode("Start Game");
        MenuNode newGame = new MenuNode("New Game", () -> GameSync.play(true));
        MenuNode loadGame = new MenuNode("Load Game", () -> GameSync.play(false));
        startGame.addChild(newGame);
        startGame.addChild(loadGame);

        // Main settings node
        MenuNode settings = new MenuNode("Settings");

        // Video settings
        MenuNode videoSettings = new MenuNode("Video");
        MenuNode hudSize = new MenuNode("HUD Size", 10, 100, 50, 10);
        MenuNode brightness = new MenuNode("Brightness", 1, 10, 5, 1);
        MenuNode cameraSpeed = new MenuNode("Camera Speed", 0, 20, 10, 1);
        MenuNode showFPS = new MenuNode("Show FPS", 0, 1, 0, 1);
        MenuNode frameRate = new MenuNode("Frame Rate Max", 30, 240, 60, 30);
        videoSettings.addChild(hudSize);
        videoSettings.addChild(brightness);
        videoSettings.addChild(cameraSpeed);
        videoSettings.addChild(showFPS);
        videoSettings.addChild(frameRate);

        // Audio settings
        MenuNode audioSettings = new MenuNode("Audio");
        MenuNode masterVolume = new MenuNode("Master Volume", 0, 100, 50, 5);
        MenuNode musicVolume = new MenuNode("Music Volume", 0, 100, 50, 5);
        MenuNode effectsVolume = new MenuNode("Effects Volume", 0, 100, 50, 5);
        audioSettings.addChild(masterVolume);
        audioSettings.addChild(musicVolume);
        audioSettings.addChild(effectsVolume);

        // Add video and audio settings to the settings node
        settings.addChild(videoSettings);
        settings.addChild(audioSettings);

        // Exit option
        MenuNode exit = new MenuNode("Exit", GameSync::exit);

        // Add nodes to the main menu
        root.addChild(startGame);
        root.addChild(settings);
        root.addChild(exit);

        // Load saved values and apply them to the menu
        Map<String, Integer> loadedValues = SettingsManager.loadAdjustableValues();
        SettingsManager.applyLoadedValues(root, loadedValues);

        return root;
    }
}
