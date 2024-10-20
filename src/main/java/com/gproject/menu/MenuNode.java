package com.gproject.menu;

import java.util.List;

public class MenuNode {
    private final String name;
    private final MenuNode father;
    private final List<MenuNode> childrens;
    private final Runnable action;

    // Constructor for submenu nodes
    public MenuNode(String name, MenuNode father, List<MenuNode> children) {
        this.name = name;
        this.father = father;
        this.childrens = children;
        this.action = null; // No action for submenu
    }

    // Constructor for action nodes
    public MenuNode(String name, MenuNode father, Runnable action) {
        this.name = name;
        this.father = father;
        this.childrens = null;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public MenuNode getFather() {
        return father;
    }

    public List<MenuNode> getChildrens() {
        return childrens;
    }

    public Runnable getAction() {
        return action;
    }

    public boolean isActionNode() {
        return action != null;
    }
}
