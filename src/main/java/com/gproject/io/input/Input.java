package com.gproject.io.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWGamepadState;

import java.util.HashMap;
import java.util.Map;

import static com.gproject.io.input.KeyBinding.*;
import static org.lwjgl.glfw.GLFW.*;

public class Input {

    public static long window;
    private static final Map<Integer, KeyState> keyStates = new HashMap<>();
    private static double mouseX, mouseY;

    public static void initialize(long windowHandle) {
        window = windowHandle;

        keyStates.put(GO_U, KeyState.RELEASED);
        keyStates.put(GO_D, KeyState.RELEASED);
        keyStates.put(GO_L, KeyState.RELEASED);
        keyStates.put(GO_R, KeyState.RELEASED);
        keyStates.put(JUMP, KeyState.RELEASED);
        keyStates.put(DASH, KeyState.RELEASED);
        keyStates.put(USE_ITEM_1, KeyState.RELEASED);
        keyStates.put(USE_ITEM_2, KeyState.RELEASED);

        keyStates.put(UP, KeyState.RELEASED);
        keyStates.put(LEFT, KeyState.RELEASED);
        keyStates.put(DOWN, KeyState.RELEASED);
        keyStates.put(RIGHT, KeyState.RELEASED);

        keyStates.put(KB_ENTER, KeyState.RELEASED);
        keyStates.put(KB_ESC, KeyState.RELEASED);
        keyStates.put(DEBUG_MODE, KeyState.RELEASED);

        keyStates.put(ATTACK_1, KeyState.RELEASED);
        keyStates.put(ATTACK_2, KeyState.RELEASED);
        keyStates.put(ATTACK_3, KeyState.RELEASED);
    }

    public static void update() {
        glfwPollEvents();
        for (Map.Entry<Integer, KeyState> entry : keyStates.entrySet()) {
            int key = entry.getKey();
            KeyState currentState = entry.getValue();

            boolean isPressed;
            if (key >= 0 && key <= GLFW_MOUSE_BUTTON_LAST) {
                isPressed = GLFW.glfwGetMouseButton(window, key) == GLFW.GLFW_PRESS;
            } else if (key >= GLFW.GLFW_KEY_SPACE && key <= GLFW.GLFW_KEY_LAST) {
                isPressed = GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
            } else {
                isPressed = isControllerButtonPressed(key) || isControllerAxisPressed(key);
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

    public static Map<String, Object> getInput() {
        Map<String, Object> inputs = new HashMap<>();

        inputs.put("GO_U", keyStates.get(GO_U));
        inputs.put("GO_D", keyStates.get(GO_D));
        inputs.put("GO_L", keyStates.get(GO_L));
        inputs.put("GO_R", keyStates.get(GO_R));
        inputs.put("JUMP", keyStates.get(JUMP));
        inputs.put("DASH", keyStates.get(DASH));
        inputs.put("USE_ITEM_1", keyStates.get(USE_ITEM_1));
        inputs.put("USE_ITEM_2", keyStates.get(USE_ITEM_2));

        inputs.put("UP", keyStates.get(UP));
        inputs.put("LEFT", keyStates.get(LEFT));
        inputs.put("DOWN", keyStates.get(DOWN));
        inputs.put("RIGHT", keyStates.get(RIGHT));

        inputs.put("ENTER", keyStates.get(KB_ENTER));
        inputs.put("ESC", keyStates.get(KB_ESC));
        inputs.put("DEBUG_MODE", keyStates.get(DEBUG_MODE));

        inputs.put("ATTACK_1", keyStates.get(ATTACK_1));
        inputs.put("ATTACK_2", keyStates.get(ATTACK_2));
        inputs.put("ATTACK_3", keyStates.get(ATTACK_3));

        inputs.put("MOUSE_X", mouseX);
        inputs.put("MOUSE_Y", mouseY);

        return inputs;
    }

    private static boolean isControllerButtonPressed(int key) {
        if (!GLFW.glfwJoystickPresent(GLFW.GLFW_JOYSTICK_1)) {
            return false;
        }
        GLFWGamepadState state = GLFWGamepadState.create();
        if (GLFW.glfwGetGamepadState(GLFW.GLFW_JOYSTICK_1, state)) {
            if (key >= GLFW.GLFW_GAMEPAD_BUTTON_A && key <= GLFW.GLFW_GAMEPAD_BUTTON_LAST) {
                return state.buttons(key) == GLFW.GLFW_PRESS;
            }
        }
        return false;
    }

    private static boolean isControllerAxisPressed(int key) {
        if (!GLFW.glfwJoystickPresent(GLFW.GLFW_JOYSTICK_1)) {
            return false;
        }

        GLFWGamepadState state = GLFWGamepadState.create();
        if (GLFW.glfwGetGamepadState(GLFW.GLFW_JOYSTICK_1, state)) {
            final float AXIS_THRESHOLD = 0.3f; // TODO aggiungere impostazioni per gestire controller

            return switch (key) {
                case GLFW.GLFW_GAMEPAD_AXIS_LEFT_X ->
                        Math.abs(state.axes(GLFW.GLFW_GAMEPAD_AXIS_LEFT_X)) > AXIS_THRESHOLD;
                case GLFW.GLFW_GAMEPAD_AXIS_LEFT_Y ->
                        Math.abs(state.axes(GLFW.GLFW_GAMEPAD_AXIS_LEFT_Y)) > AXIS_THRESHOLD;
                case GLFW.GLFW_GAMEPAD_AXIS_RIGHT_X ->
                        Math.abs(state.axes(GLFW.GLFW_GAMEPAD_AXIS_RIGHT_X)) > AXIS_THRESHOLD;
                case GLFW.GLFW_GAMEPAD_AXIS_RIGHT_Y ->
                        Math.abs(state.axes(GLFW.GLFW_GAMEPAD_AXIS_RIGHT_Y)) > AXIS_THRESHOLD;
                case GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER ->
                        state.axes(GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER) > AXIS_THRESHOLD;
                case GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER ->
                        state.axes(GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER) > AXIS_THRESHOLD;
                default -> false;
            };
        }

        return false;
    }
}