package com.gproject.menu;

import java.util.ArrayList;

public class MenuTree {
    public static MenuNode createMainMenuTree() {
        MenuNode mainMenuNode = new MenuNode("Main", null, new ArrayList<>());

        MenuNode node1 = new MenuNode("Play", mainMenuNode, new ArrayList<>());
        mainMenuNode.getChildrens().add(node1);

        MenuNode node2 = new MenuNode("Option", mainMenuNode, new ArrayList<>());
        mainMenuNode.getChildrens().add(node2);

        MenuNode node21 = new MenuNode("FOV", node2, new ArrayList<>());
        node2.getChildrens().add(node21);
        MenuNode node22 = new MenuNode("Sensibility", node2, new ArrayList<>());
        node2.getChildrens().add(node22);
        MenuNode node23 = new MenuNode("Resolution", node2, new ArrayList<>());
        node2.getChildrens().add(node23);


        MenuNode node3 = new MenuNode("Exit", mainMenuNode, new ArrayList<>());
        mainMenuNode.getChildrens().add(node3);

        return mainMenuNode;
    }

    public static MenuNode createPauseMenuTree() {
        MenuNode mainMenuNode = new MenuNode("Main", null, new ArrayList<>());

        MenuNode node1 = new MenuNode("Play", mainMenuNode, new ArrayList<>());
        mainMenuNode.getChildrens().add(node1);

        MenuNode node2 = new MenuNode("Option", mainMenuNode, new ArrayList<>());
        mainMenuNode.getChildrens().add(node2);

        MenuNode node21 = new MenuNode("FOV", node2, new ArrayList<>());
        node2.getChildrens().add(node21);
        MenuNode node22 = new MenuNode("Sensibility", node2, new ArrayList<>());
        node2.getChildrens().add(node22);
        MenuNode node23 = new MenuNode("Resolution", node2, new ArrayList<>());
        node2.getChildrens().add(node23);


        MenuNode node3 = new MenuNode("Exit", mainMenuNode, new ArrayList<>());
        mainMenuNode.getChildrens().add(node3);

        return mainMenuNode;
    }
}
