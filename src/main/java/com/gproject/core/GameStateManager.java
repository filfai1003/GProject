package com.gproject.core;

import com.gproject.IO.input.InputManager;
import com.gproject.IO.output.GameRendering;
import com.gproject.IO.output.MenuRendering;
import com.gproject.gameLogic.GameLoop;
import com.gproject.IO.input.InputState;
import com.gproject.menu.ChangeKeyManager;
import com.gproject.menu.MenuManager;
import com.gproject.menu.MenuTree;

import java.util.*;


public class GameStateManager {
    private final MenuRendering menuRendering;
    private final GameRendering gameRendering;
    private final InputManager inputManager;
    private GameState currentState;
    private MenuManager menuManager;
    private ChangeKeyManager changeKeyManager;
    private GameLoop gameLoop;

    public GameStateManager(InputManager inputManager) {
        this.inputManager = inputManager;
        this.menuRendering = new MenuRendering();
        this.gameRendering = new GameRendering();
        this.menuManager = new MenuManager(MenuTree.createMainMenuTree(this));
        this.currentState = GameState.MENU;
    }

    public void update() {
        Map<String, InputState> input = inputManager.getInputStates();
        switch (currentState) {
            case MENU, PAUSE:
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
                menuRendering.renderMenu();
                break;
            case CHANGEKEY:
                menuRendering.renderKeyChange();
                break;
            case PLAYING:
                gameRendering.renderGame();
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

    public void changeState(GameState currentState) {
        this.currentState = currentState;
        switch (currentState) {
            case MENU:
                if (this.menuManager == null) {
                    this.menuManager = new MenuManager(MenuTree.createMainMenuTree(this));
                }
                this.gameLoop = null;
                this.changeKeyManager = null;
                break;
            case PAUSE:
                if (this.menuManager == null) {
                    this.menuManager = new MenuManager(MenuTree.createPauseMenuTree(this));
                }
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

    public void setChangeKeyManager(String key, GameState currentState) {
        this.changeKeyManager = new ChangeKeyManager(key, this.inputManager, this,  currentState);
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
