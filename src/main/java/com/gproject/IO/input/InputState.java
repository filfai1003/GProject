package com.gproject.IO.input;

public class InputState {

    private boolean pressed;
    private boolean held;
    private boolean justReleased;
    private boolean released;

    public InputState() {
        pressed = false;
        held = false;
        justReleased = false;
        released = true;
    }

    // Metodi getter
    public boolean isPressed() {
        return pressed;
    }

    public boolean isHeld() {
        return held;
    }

    public boolean isJustReleased() {
        return justReleased;
    }

    public boolean isReleased() {
        return released;
    }

    public void update(boolean isKeyDown) {
        if (isKeyDown) {
            if (pressed){
                pressed = false;
                held = true;
                justReleased = false;
                released = false;
            } else {
                pressed = true;
                held = false;
                justReleased = false;
                released = false;
            }
        } else {
            if (justReleased){
                pressed = false;
                held = false;
                justReleased = false;
                released = true;
            } else {
                pressed = false;
                held = false;
                justReleased = true;
                released = false;
            }
        }
    }
}
