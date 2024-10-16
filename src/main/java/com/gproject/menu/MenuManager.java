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
        if (input.get("UP").isPressed()) {
            if (childrenSelected > 0 || (currentMenuNode.getFather() != null && childrenSelected == 0)) {
                childrenSelected--;
            }
        } else if (input.get("DOWN").isPressed()) {
            if (childrenSelected < currentMenuNode.getChildrens().size() - 1) {
                childrenSelected++;
            }
        }
    }

    public void select() {
        if (childrenSelected < -1 || childrenSelected >= currentMenuNode.getChildrens().size()) {
            throw new IndexOutOfBoundsException("childrenSelected is out of bounds: " + childrenSelected + "/" + currentMenuNode.getChildrens().size());
        }
        if (childrenSelected == -1) {
            back();
            return;
        }
        if (currentMenuNode.getChildrens().get(childrenSelected) == null){
            throw new IndexOutOfBoundsException("currentNode childrenSelected is null");
        }
        currentMenuNode = currentMenuNode.getChildrens().get(childrenSelected);
    }

    public void back() {
        if (currentMenuNode.getFather() == null) {
            throw new IndexOutOfBoundsException("currentNode father is null");
        }
        currentMenuNode = currentMenuNode.getFather();
    }

    public MenuNode getCurrentMenuNode() {
        return currentMenuNode;
    }

    public int getChildrenSelected() {
        return childrenSelected;
    }
}
