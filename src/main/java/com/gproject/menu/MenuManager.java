package com.gproject.menu;

import com.gproject.IO.input.InputState;

import java.util.Map;

public class MenuManager {
    private MenuNode currentMenuNode;
    private int childrenSelected;

    public MenuManager(MenuNode currentMenuNode) {
        this.currentMenuNode = currentMenuNode;
        this.childrenSelected = 0;
    }

    public void update(Map<String, InputState> input) {
        if (input.get("ENTER").isPressed()) {
            select();
        } else if (input.get("UP").isPressed()) {
            up();
        } else if (input.get("DOWN").isPressed()) {
            down();
        }
    }

    public boolean up() {
        if (childrenSelected > 0 || (currentMenuNode.getFather() != null && childrenSelected == 0)) {
            childrenSelected--;
            return true;
        }
        return false;
    }

    public boolean down() {
        if (childrenSelected < currentMenuNode.getChildrens().size() - 1) {
            childrenSelected++;
            return true;
        }
        return false;
    }

    public boolean back() {
        if (currentMenuNode.getFather() == null) {
            return false;
        }
        currentMenuNode = currentMenuNode.getFather();
        childrenSelected = -1;
        return true;
    }

    public boolean select() {
        if (childrenSelected < -1 || childrenSelected >= currentMenuNode.getChildrens().size()) {
            throw new IndexOutOfBoundsException("childrenSelected is out of bounds: " + childrenSelected + "/" + currentMenuNode.getChildrens().size());
        } else if (childrenSelected == -1) {
            return back();
        } else if (currentMenuNode.getChildrens().get(childrenSelected) == null) {
            return false;
        }
        currentMenuNode = currentMenuNode.getChildrens().get(childrenSelected);
        childrenSelected = -1;
        return true;
    }

    public MenuNode getCurrentMenuNode() {
        return currentMenuNode;
    }

    public int getChildrenSelected() {
        return childrenSelected;
    }
}
