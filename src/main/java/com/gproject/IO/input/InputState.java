package com.gproject.IO.input;

public class InputState {

    private boolean pressed;
    private boolean held;
    private boolean released;
    private boolean justReleased;

    public InputState() {
        pressed = false;
        held = false;
        released = true;
        justReleased = false;
    }

    // Metodi getter
    public boolean isPressed() {
        return pressed;
    }

    public boolean isHeld() {
        return held;
    }

    public boolean isReleased() {
        return released;
    }

    public boolean isJustReleased() {
        return justReleased;
    }

    public void update(boolean isKeyDown) {
        if (isKeyDown) {
            if (!held) {
                pressed = true;
                held = true;
            } else {
                pressed = false;
            }
            released = false;
            justReleased = false;
        } else {
            if (held) {
                released = true;
                held = false;
            } else {
                released = false;
                justReleased = false;
            }
            if (!held && !released) {
                justReleased = true;
            }
        }
    }
}
