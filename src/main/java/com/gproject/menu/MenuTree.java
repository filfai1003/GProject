package com.gproject.menu;

import com.gproject.core.GameState;
import com.gproject.core.GameStateManager;

import java.util.ArrayList;

public class MenuTree {
    public static MenuNode createMainMenuTree(GameStateManager stateManager) {
        MenuNode mainMenuNode = new MenuNode("Main", null, new ArrayList<>());


        MenuNode node1 = new MenuNode("Play", mainMenuNode, new ArrayList<>());
        mainMenuNode.getChildrens().add(node1);

        MenuNode node2 = new MenuNode("Option", mainMenuNode, new ArrayList<>());
        mainMenuNode.getChildrens().add(node2);

        MenuNode node3 = new MenuNode("Exit", mainMenuNode, () -> {
            stateManager.changeState(GameState.EXIT);
        });
        mainMenuNode.getChildrens().add(node3);


        MenuNode node21 = new MenuNode("Change Keys", mainMenuNode, new ArrayList<>());
        node2.getChildrens().add(node21);

        MenuNode node211 = new MenuNode("UP", mainMenuNode, () -> {
            stateManager.changeState(GameState.CHANGEKEY);
            stateManager.setChangeKeyManager("UP");
        });
        node21.getChildrens().add(node211);
        MenuNode node212 = new MenuNode("DOWN", mainMenuNode, () -> {
            stateManager.changeState(GameState.CHANGEKEY);
            stateManager.setChangeKeyManager("DOWN");
        });
        node21.getChildrens().add(node212);
        MenuNode node213 = new MenuNode("SELECT", mainMenuNode, () -> {
            stateManager.changeState(GameState.CHANGEKEY);
            stateManager.setChangeKeyManager("SELECT");
        });
        node21.getChildrens().add(node213);

        return mainMenuNode;
    }

    public static MenuNode createPauseMenuTree(GameStateManager stateManager) {
        // TODO
        return null;
    }
}
