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

    public void up() {
        if (childrenSelected > 0 || (currentMenuNode.getFather() != null && childrenSelected == 0)) {
            childrenSelected--;
        }
    }

    public void down() {
        if (childrenSelected < currentMenuNode.getChildrens().size() - 1) {
            childrenSelected++;
        }
    }

    public void back() {
        if (currentMenuNode.getFather() == null) {
            return;
        }
        currentMenuNode = currentMenuNode.getFather();
        childrenSelected = -1;
    }

    public void select() {
        if (childrenSelected < -1 || childrenSelected >= currentMenuNode.getChildrens().size()) {
            throw new IndexOutOfBoundsException("childrenSelected is out of bounds: " + childrenSelected + "/" + currentMenuNode.getChildrens().size());
        } else if (childrenSelected == -1) {
            back();
            return;
        }

        MenuNode selectedNode = currentMenuNode.getChildrens().get(childrenSelected);

        if (selectedNode.isActionNode()) {
            selectedNode.getAction().run();
        } else if (selectedNode.getChildrens() != null) {
            currentMenuNode = selectedNode;
            childrenSelected = -1;
        }
    }

    public MenuNode getCurrentMenuNode() {
        return currentMenuNode;
    }

    public int getChildrenSelected() {
        return childrenSelected;
    }
}
