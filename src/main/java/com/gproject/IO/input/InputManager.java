package com.gproject.IO.input;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {

    private Map<String, InputState> inputStates;
    private Map<String, Integer> keyBindings;

    public InputManager() {
        inputStates = new HashMap<>();
        keyBindings = new HashMap<>();

        keyBindings = loadKeyBinding();
        for (String action : keyBindings.keySet()) {
            inputStates.put(action, new InputState());
        }
    }

    public Map<String, Integer> loadKeyBinding(){
        // TODO Carica l'associazione dei tasti da file di salvataggio json se disponibile
        keyBindings = new HashMap<>();

        keyBindings.put("UP", GLFW_KEY_W);
        keyBindings.put("DOWN", GLFW_KEY_S);
        keyBindings.put("LEFT", GLFW_KEY_A);
        keyBindings.put("RIGHT", GLFW_KEY_D);
        keyBindings.put("ENTER", GLFW_KEY_ENTER);

        return keyBindings;
    }

    public void updateInputs(long window) {
        for (Map.Entry<String, Integer> entry : keyBindings.entrySet()) {
            String action = entry.getKey();
            int key = entry.getValue();
            boolean isKeyDown = glfwGetKey(window, key) == GLFW_PRESS;
            inputStates.get(action).update(isKeyDown);
        }
    }

    public Map<String, InputState> getInputStates() {
        return inputStates;
    }

    public Map<String, Integer> getKeyBindings() {
        return keyBindings;
    }
}
