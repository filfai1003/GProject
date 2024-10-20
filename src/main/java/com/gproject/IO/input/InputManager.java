package com.gproject.IO.input;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {

    private final Map<String, InputState> inputStates;
    private Map<String, Integer> keyBindings;

    public InputManager() {
        inputStates = new HashMap<>();
        keyBindings = new HashMap<>();

        keyBindings = loadKeyBinding();
        for (String action : keyBindings.keySet()) {
            inputStates.put(action, new InputState());
        }
    }

    public Map<String, Integer> loadKeyBinding() {
        // Carica il file JSON se esiste, altrimenti usa i valori predefiniti
        try (Reader reader = new FileReader("keybindings.json")) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Integer>>(){}.getType();
            keyBindings = gson.fromJson(reader, type);
        } catch (IOException e) {
            keyBindings = new HashMap<>();
            keyBindings.put("UP", GLFW_KEY_UP);
            keyBindings.put("DOWN", GLFW_KEY_DOWN);
            keyBindings.put("ENTER", GLFW_KEY_ENTER);
        }
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

    public void saveKeyBindings() {
        try (Writer writer = new FileWriter("keybindings.json")) {
            Gson gson = new Gson();
            gson.toJson(keyBindings, writer);
        } catch (IOException ignored) {}
    }

    public Map<String, InputState> getInputStates() {
        return inputStates;
    }

    public Map<String, Integer> getKeyBindings() {
        return keyBindings;
    }
}
