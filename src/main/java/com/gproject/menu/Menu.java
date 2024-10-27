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

        if (inputs.get("UP") == KeyState.JUST_PRESSED) {
            selectedIndex = Math.max(0, selectedIndex-1);
        }
        if (inputs.get("DOWN") == KeyState.JUST_PRESSED) {
            selectedIndex = Math.min(selectedIndex+1, currentNode.getChildren().size()-1);
        }

        if (inputs.get("LEFT") == KeyState.JUST_PRESSED) {
            if (currentNode.getChildren().get(selectedIndex).isAdjustable()){
                currentNode.getChildren().get(selectedIndex).decrementValue();
            }
        }
        if (inputs.get("RIGHT") == KeyState.JUST_PRESSED) {
            if (currentNode.getChildren().get(selectedIndex).isAdjustable()){
                currentNode.getChildren().get(selectedIndex).incrementValue();
            }
        }

        if (inputs.get("ENTER") == KeyState.JUST_PRESSED) {
            if (currentNode.getChildren().get(selectedIndex).isRunnable()){
                currentNode.getChildren().get(selectedIndex).executeAction();
            } else if (!currentNode.getChildren().get(selectedIndex).getChildren().isEmpty()){
                currentNode = currentNode.getChildren().get(selectedIndex);
                selectedIndex = 0;
            }
        }

        if (inputs.get("ESC") == KeyState.JUST_PRESSED) {
            if (currentNode.getParent() != null){
                MenuNode last = currentNode;
                currentNode = currentNode.getParent();
                selectedIndex = currentNode.getChildren().indexOf(last);
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
