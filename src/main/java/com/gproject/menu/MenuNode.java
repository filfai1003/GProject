package com.gproject.menu;

import java.util.List;

public class MenuNode {
    private String name;
    private MenuNode father;
    private List<MenuNode> childrens;

    public MenuNode(String name, MenuNode father, List<MenuNode> children) {
        this.name = name;
        this.father = father;
        this.childrens = children;
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
}
