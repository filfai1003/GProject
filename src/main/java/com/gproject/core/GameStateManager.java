package com.gproject.core;

import com.gproject.gameLogic.GameLoop;
import com.gproject.IO.output.input.InputState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameStateManager {
    private List<GameState> currentState;
    private GameLoop gameLoop;

    public GameStateManager() {
        this.currentState = new ArrayList<>();
        this.currentState.add(GameState.MENU);
    }

    public boolean pushState(GameState newState) {
        return this.currentState.add(newState);
    }

    public GameState pullState() {return this.currentState.removeLast();}

    public void update(Map<String, InputState> input) {
        switch (currentState.getLast()) {
            case MENU:
                updateMenu();
                break;
            case PLAYING:
                updateGame();
                break;
            case OPTIONS:
                updateOptions();
                break;
            case PAUSE:
                updatePause();
                break;
            case EXIT:
                System.exit(0);
                break;
        }
    }

    public void render() {
        switch (currentState.getLast()) {
            case MENU:
                renderMenu();
                break;
            case PLAYING:
                renderGame();
                break;
            case OPTIONS:
                renderOptions();
                break;
            case PAUSE:
                renderPause();
                break;
        }
    }

    private void updateMenu() {
        // TODO Logica del menu
    }

    private void updateGame() {
        if (gameLoop != null) {
            gameLoop.update();
        }
        throw new RuntimeException("gameLoop is null");
    }

    private void updateOptions() {
        // TODO Logica delle opzioni
    }

    private void updatePause() {
        // TODO Logica della pausa
    }

    private void renderMenu() {
        // TODO Rendering del menu
    }

    private void renderGame() {
        // TODO Rendering del gioco (es. giocatore, nemici, ecc.)
    }

    private void renderOptions() {
        // TODO Rendering delle opzioni
    }

    private void renderPause() {
        // TODO Rendering della schermata di pausa
    }
}
