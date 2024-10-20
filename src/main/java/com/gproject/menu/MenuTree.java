package com.gproject.menu;

import static com.gproject.main.GameSyncronizer.exit;
import static com.gproject.main.GameSyncronizer.play;

public class MenuTree {

    public static MenuNode initializeMenu() {
        MenuNode root = new MenuNode("Main Menu");

        // Creazione dei nodi del menu
        MenuNode startGame = new MenuNode("Start Game");
        MenuNode settings = new MenuNode("Settings");

        // Sotto-menu "Settings"
        MenuNode frameRate = new MenuNode("Frame Rate Max", 30, 240, 60, 30);
        MenuNode brightness = new MenuNode("Brightness", 0, 10, 5, 1);
        settings.addChild(frameRate);
        settings.addChild(brightness);

        // Nodo "Exit"
        MenuNode exit = new MenuNode("Exit", () -> exit());

        // Aggiunta dei livelli sotto "Start Game"
        for (int i = 1; i <= 5; i++) {
            int level = i;  // Creazione di una variabile finale per usarla nella lambda
            MenuNode levelNode = new MenuNode("Level " + level, () -> play(level));
            startGame.addChild(levelNode);
        }

        // Aggiunta dei nodi principali al menu radice
        root.addChild(startGame);
        root.addChild(settings);
        root.addChild(exit);

        return root;
    }
}
