package com.gproject.menu;

import com.gproject.IO.input.InputManager;
import com.gproject.IO.input.InputState;
import com.gproject.core.GameState;
import com.gproject.core.GameStateManager;

import java.util.Map;

public class ChangeKeyManager {

    private final String command;
    private final InputManager inputManager;
    private final GameStateManager stateManager;
    private final GameState lastGameState;

    public ChangeKeyManager(String command, InputManager inputManager, GameStateManager stateManager, GameState lastGameState) {
        this.command = command;
        this.inputManager = inputManager;
        this.stateManager = stateManager;
        this.lastGameState = lastGameState;
    }

    public void update(Map<String, InputState> input) {
        for (Map.Entry<String, InputState> entry : input.entrySet()) {
            if (entry.getValue().isPressed()) {
                int newKey = inputManager.getKeyBindings().get(entry.getKey());
                inputManager.getKeyBindings().put(command, newKey);
                inputManager.saveKeyBindings();
                stateManager.changeState(lastGameState);
                break;
            }
        }
    }

    public String getCommand() {
        return command;
    }
}
