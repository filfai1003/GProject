package com.gproject.menu;

import com.gproject.IO.input.InputManager;
import com.gproject.IO.input.InputState;
import com.gproject.core.GameStateManager;

import java.util.Map;

public class ChangeKeyManager {

    private String command;
    private InputManager inputManager;
    private GameStateManager stateManager;

    public ChangeKeyManager(String command, InputManager inputManager, GameStateManager stateManager) {
        this.command = command;
        this.inputManager = inputManager;
        this.stateManager = stateManager;
    }

    public void update(Map<String, InputState> input) {
        for (Map.Entry<String, InputState> entry : input.entrySet()) {
            if (entry.getValue().isPressed()) {
                int newKey = inputManager.getKeyBindings().get(entry.getKey());
                inputManager.getKeyBindings().put(command, newKey);
                inputManager.saveKeyBindings();
                stateManager.changeState(); // TODO si deve cambaire lo stato allo stato precedente e al punto di arborescenza precedente (Forse un modo e' passare in parametro il nodo e lo stato precedente per poi reimpostarlo con questi 2 valori)
                break;
            }
        }
    }
    // TODO
}
