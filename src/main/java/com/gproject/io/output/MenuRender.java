package com.gproject.io.output;

import com.gproject.menu.Menu;
import com.gproject.menu.MenuNode;

import static org.lwjgl.opengl.GL11.glColor3f;

public class MenuRender {

    public static void render(Menu menu) {
        MenuNode currentNode = menu.getCurrentNode();
        int selectedIndex = menu.getSelectedIndex();

        for (int i = 0; i < currentNode.getChildren().size(); i++) {
            MenuNode childNode = currentNode.getChildren().get(i);
            if (i == selectedIndex) {
                glColor3f(1.0f, 1.0f, 0.0f); // Usa GL11
            } else {
                glColor3f(1.0f, 1.0f, 1.0f); // Usa GL11
            }
            String imageName = childNode.getTitle();
            Render.renderImage(50, i * 60, -1, 50, "assets/menu/" + imageName + ".png");
        }
    }
}
