package com.gproject.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuNode {
    private String title;
    private List<MenuNode> children;
    private MenuNode parent;
    private int minValue;
    private int maxValue;
    private int currentValue;
    private int stepValue;
    private Runnable action;

    public MenuNode(String title) {
        this.title = title;
        this.children = new ArrayList<>();
    }

    public MenuNode(String title, int minValue, int maxValue, int initialValue, int stepValue) {
        this.title = title;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = initialValue;
        this.stepValue = stepValue;
        this.children = new ArrayList<>();
    }

    public MenuNode(String title, Runnable action) {
        this.title = title;
        this.action = action;
        this.children = new ArrayList<>();
    }

    public void addChild(MenuNode child) {
        child.setParent(this);
        children.add(child);
    }

    public void setParent(MenuNode parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public List<MenuNode> getChildren() {
        return children;
    }

    public MenuNode getParent() {
        return parent;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void incrementValue() {
        if (currentValue < maxValue) {
            currentValue += stepValue;
        }
    }

    public void decrementValue() {
        if (currentValue > minValue) {
            currentValue -= stepValue;
        }
    }

    public boolean isAdjustable() {
        return minValue != maxValue;
    }

    public void executeAction() {
        if (action != null) {
            action.run();
        }
    }

    public boolean isRunnable() {
        return action != null;
    }
}
