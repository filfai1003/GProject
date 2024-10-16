package com.gproject.core;

import com.gproject.gameLogic.GameLoop;
import com.gproject.IO.input.InputState;
import com.gproject.menu.MenuManager;
import com.gproject.menu.MenuTree;

import java.util.*;

public class GameStateManager {
    private Deque<GameState> currentState;
    private GameLoop gameLoop;
    private MenuManager menuManager;

    public GameStateManager() {
        this.currentState = new ArrayDeque<>();
        this.currentState.add(GameState.MENU);
    }

    public boolean pushState(GameState newState) {
        return this.currentState.add(newState);
    }

    public GameState pullState() {
        return this.currentState.removeLast();  // Metodo di Deque
    }

    public void update(Map<String, InputState> input) {
        switch (currentState.getLast()) {  // Metodo di Deque
            case MENU:
                updateMenu(input);
                break;
            case PLAYING:
                updateGame(input);
                break;
            case PAUSE:
                updatePause(input);
                break;
            case EXIT:
                System.exit(0);
                break;
        }
    }

    public void render() {
        switch (currentState.getLast()) {  // Metodo di Deque
            case MENU:
                renderMenu();
                break;
            case PLAYING:
                renderGame();
                break;
            case PAUSE:
                renderPause();
                break;
        }
    }

    private void updateMenu(Map<String, InputState> input) {
        if (menuManager == null) {
            menuManager = new MenuManager(MenuTree.createMainMenuTree());
        }
        menuManager.update(input);
    }

    private void updateGame(Map<String, InputState> input) {
        if (gameLoop == null) {
            throw new RuntimeException("gameLoop is null");
        }
        gameLoop.update(input);
    }

    private void updatePause(Map<String, InputState> input) {
        if (menuManager == null) {
            menuManager = new MenuManager(MenuTree.createPauseMenuTree());
        }
        menuManager.update(input);
    }

    private void renderMenu() {
        if (menuManager == null) {
            throw new RuntimeException("menuManager is null");
        }
        System.out.println(menuManager.getCurrentMenuNode().getName());
        for (int i = 0; i < menuManager.getCurrentMenuNode().getChildrens().size(); i++) {
            String txt = "";
            if (menuManager.getChildrenSelected() == i) {
                txt += " *";
            }
            txt += menuManager.getCurrentMenuNode().getChildrens().get(i).getName();
            System.out.println(txt);
        }
    }

    private void renderGame() {
        // TODO Rendering del gioco (es. giocatore, nemici, ecc.)
    }

    private void renderPause() {
        // TODO Rendering della schermata di pausa
    }
}
