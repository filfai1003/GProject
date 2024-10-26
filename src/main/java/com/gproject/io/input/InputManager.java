package com.gproject.io.input;

import org.lwjgl.glfw.GLFW;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public class InputManager {

    private long window;
    private Map<Integer, KeyState> keyStates = new HashMap<>();
    private double mouseX, mouseY;

    private static final int KEY_W = GLFW.GLFW_KEY_W;
    private static final int KEY_A = GLFW.GLFW_KEY_A;
    private static final int KEY_S = GLFW.GLFW_KEY_S;
    private static final int KEY_D = GLFW.GLFW_KEY_D;
    private static final int KEY_E = GLFW.GLFW_KEY_S;
    private static final int KEY_Q = GLFW.GLFW_KEY_D;

    private static final int KEY_UP = GLFW.GLFW_KEY_UP;
    private static final int KEY_DOWN = GLFW.GLFW_KEY_DOWN;
    private static final int KEY_LEFT = GLFW.GLFW_KEY_LEFT;
    private static final int KEY_RIGHT = GLFW.GLFW_KEY_RIGHT;

    private static final int KEY_SPACE = GLFW.GLFW_KEY_SPACE;
    private static final int KEY_ENTER = GLFW.GLFW_KEY_ENTER;
    private static final int KEY_ESCAPE = GLFW.GLFW_KEY_ESCAPE;

    private static final int MOUSE_BUTTON_LEFT = GLFW.GLFW_MOUSE_BUTTON_1;
    private static final int MOUSE_BUTTON_RIGHT = GLFW.GLFW_MOUSE_BUTTON_2;

    public InputManager(long window) {
        this.window = window;
        initializeKeyStates();
    }

    private void initializeKeyStates() {
        keyStates.put(KEY_W, KeyState.RELEASED);
        keyStates.put(KEY_A, KeyState.RELEASED);
        keyStates.put(KEY_S, KeyState.RELEASED);
        keyStates.put(KEY_D, KeyState.RELEASED);
        keyStates.put(KEY_E, KeyState.RELEASED);
        keyStates.put(KEY_Q, KeyState.RELEASED);

        keyStates.put(KEY_UP, KeyState.RELEASED);
        keyStates.put(KEY_DOWN, KeyState.RELEASED);
        keyStates.put(KEY_LEFT, KeyState.RELEASED);
        keyStates.put(KEY_RIGHT, KeyState.RELEASED);

        keyStates.put(KEY_SPACE, KeyState.RELEASED);
        keyStates.put(KEY_ENTER, KeyState.RELEASED);
        keyStates.put(KEY_ESCAPE, KeyState.RELEASED);

        keyStates.put(MOUSE_BUTTON_LEFT, KeyState.RELEASED);
        keyStates.put(MOUSE_BUTTON_RIGHT, KeyState.RELEASED);
    }

    public void update() {
        glfwPollEvents();
        for (Map.Entry<Integer, KeyState> entry : keyStates.entrySet()) {
            int key = entry.getKey();
            KeyState currentState = entry.getValue();

            boolean isPressed;

            if (key == MOUSE_BUTTON_LEFT || key == MOUSE_BUTTON_RIGHT) {
                isPressed = GLFW.glfwGetMouseButton(window, key) == GLFW.GLFW_PRESS;
            } else {
                isPressed = GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
            }

            if (isPressed) {
                if (currentState == KeyState.RELEASED || currentState == KeyState.JUST_RELEASED) {
                    keyStates.put(key, KeyState.JUST_PRESSED);
                } else {
                    keyStates.put(key, KeyState.HELD);
                }
            } else {
                if (currentState == KeyState.HELD || currentState == KeyState.JUST_PRESSED) {
                    keyStates.put(key, KeyState.JUST_RELEASED);
                } else {
                    keyStates.put(key, KeyState.RELEASED);
                }
            }
        }

        double[] xPos = new double[1];
        double[] yPos = new double[1];
        GLFW.glfwGetCursorPos(window, xPos, yPos);
        mouseX = xPos[0];
        mouseY = yPos[0];
    }

    public Map<String, Object> getInput() {
        Map<String, Object> inputs = new HashMap<>();

        // Tasti WASDEQ
        inputs.put("KB_W", keyStates.get(KEY_W));
        inputs.put("KB_A", keyStates.get(KEY_A));
        inputs.put("KB_S", keyStates.get(KEY_S));
        inputs.put("KB_D", keyStates.get(KEY_D));
        inputs.put("KB_E", keyStates.get(KEY_E));
        inputs.put("KB_Q", keyStates.get(KEY_Q));

        // Tasti Freccia (UP, LEFT, DOWN, RIGHT)
        inputs.put("KB_UP", keyStates.get(KEY_UP));
        inputs.put("KB_LEFT", keyStates.get(KEY_LEFT));
        inputs.put("KB_DOWN", keyStates.get(KEY_DOWN));
        inputs.put("KB_RIGHT", keyStates.get(KEY_RIGHT));

        // Altri tasti
        inputs.put("KB_SPACE", keyStates.get(KEY_SPACE));
        inputs.put("KB_ENTER", keyStates.get(KEY_ENTER));
        inputs.put("KB_ESC", keyStates.get(KEY_ESCAPE));

        // Tasti del mouse
        inputs.put("MOUSE_LEFT", keyStates.get(MOUSE_BUTTON_LEFT));
        inputs.put("MOUSE_RIGHT", keyStates.get(MOUSE_BUTTON_RIGHT));

        // Posizione del mouse
        inputs.put("MOUSE_X", mouseX);
        inputs.put("MOUSE_Y", mouseY);

        return inputs;
    }


    private KeyState mergeKeyStates(KeyState keyState1, KeyState keyState2) {
        if (keyState1 == KeyState.JUST_PRESSED || keyState2 == KeyState.JUST_PRESSED) {
            return KeyState.JUST_PRESSED;
        }
        if (keyState1 == KeyState.HELD || keyState2 == KeyState.HELD) {
            return KeyState.HELD;
        }
        if (keyState1 == KeyState.JUST_RELEASED || keyState2 == KeyState.JUST_RELEASED) {
            return KeyState.JUST_RELEASED;
        }
        return KeyState.RELEASED;
    }
}
