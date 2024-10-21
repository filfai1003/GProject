package com.gproject.menu;

import com.gproject.io.input.KeyState;

import java.util.Map;

public class Menu {

    private MenuNode currentNode;
    private int selectedIndex = 0;

    public Menu() {
        this.currentNode = MenuTree.initializeMenu();
    }

    public void update(Map<String, Object> inputs){

        if (inputs.get("KB_UP") == KeyState.JUST_PRESSED) {
            selectedIndex = Math.max(0, selectedIndex-1);
        }
        if (inputs.get("KB_DOWN") == KeyState.JUST_PRESSED) {
            selectedIndex = Math.min(selectedIndex+1, currentNode.getChildren().size()-1);
        }

        if (inputs.get("KB_LEFT") == KeyState.JUST_PRESSED) {
            if (currentNode.getChildren().get(selectedIndex).isAdjustable()){
                currentNode.getChildren().get(selectedIndex).decrementValue();
            }
        }
        if (inputs.get("KB_RIGHT") == KeyState.JUST_PRESSED) {
            if (currentNode.getChildren().get(selectedIndex).isAdjustable()){
                currentNode.getChildren().get(selectedIndex).incrementValue();
            }
        }

        if (inputs.get("KB_ENTER") == KeyState.JUST_PRESSED) {
            if (currentNode.getChildren().get(selectedIndex).isRunnable()){
                currentNode.getChildren().get(selectedIndex).executeAction();
            } else if (!currentNode.getChildren().get(selectedIndex).getChildren().isEmpty()){
                currentNode = currentNode.getChildren().get(selectedIndex);
                selectedIndex = 0;
            }
        }

        if (inputs.get("KB_ESC") == KeyState.JUST_PRESSED) {
            if (currentNode.getParent() != null){
                currentNode = currentNode.getParent();
            }
        }
    }

    public MenuNode getCurrentNode() {
        return currentNode;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}
