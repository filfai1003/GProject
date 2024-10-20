package com.gproject.core;

import com.gproject.IO.input.InputManager;
import com.gproject.gameLogic.GameLoop;
import com.gproject.IO.input.InputState;
import com.gproject.menu.ChangeKeyManager;
import com.gproject.menu.MenuManager;
import com.gproject.menu.MenuTree;

import java.util.*;

public class GameStateManager {
    private InputManager inputManager;
    private GameState currentState;
    private MenuManager menuManager;
    private ChangeKeyManager changeKeyManager;
    private GameLoop gameLoop;

    public GameStateManager(InputManager inputManager) {
        this.currentState = GameState.MENU;
        this.menuManager = new MenuManager(MenuTree.createMainMenuTree(this));
    }

    public void update() {
        Map<String, InputState> input = inputManager.getInputStates();
        switch (currentState) {
            case MENU:
                updateMenuOrPause(input);
                break;
            case PAUSE:
                updateMenuOrPause(input);
                break;
            case PLAYING:
                updateGame(input);
                break;
            case CHANGEKEY:
                updateKeyChange(input);
                break;
        }
    }

    public void render() {
        switch (currentState) {
            case MENU, PAUSE:
                renderMenuOrPause();
                break;
            case PLAYING:
                renderGame();
                break;
            case CHANGEKEY:
                renderKeyChange();
                break;
        }
    }

    private void updateMenuOrPause(Map<String, InputState> input) {
        menuManager.update(input);
    }

    private void updateGame(Map<String, InputState> input) {
        gameLoop.update(input);
    }

    private void updateKeyChange(Map<String, InputState> input) {
        changeKeyManager.update(input);
    }

    private void renderMenuOrPause() {
        // TODO Rendering della schermata dei menu
    }

    private void renderGame() {
        // TODO Rendering del gioco (es. giocatore, nemici, ecc.)
    }

    private void renderKeyChange() {
        // TODO Rendering della schermata del cambio dei tasti
    }

    public void changeState(GameState currentState) {
        this.currentState = currentState;
        switch (currentState) {
            case MENU:
                this.menuManager = new MenuManager(MenuTree.createMainMenuTree(this));
                this.gameLoop = null;
                this.changeKeyManager = null;
                break;
            case PAUSE:
                this.menuManager = new MenuManager(MenuTree.createPauseMenuTree(this));
                this.changeKeyManager = null;
                break;
            case PLAYING:
                // TODO crea gameloop
                this.menuManager = null;
                this.changeKeyManager = null;
                break;
            case EXIT:
                System.exit(0);
                break;
        }
    }

    public void setChangeKeyManager(String key) {
        this.changeKeyManager = new ChangeKeyManager(key, this.inputManager);
    }
}
