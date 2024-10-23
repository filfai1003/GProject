package com.gproject.io.output;

import com.gproject.menu.Menu;
import com.gproject.menu.MenuNode;
import org.lwjgl.glfw.GLFW;

import static com.gproject.main.GameSyncronizer.window;
import static org.lwjgl.opengl.GL11.glOrtho;

public class MenuRender {

    public static void render(Menu menu) {

        int[] w = new int[1];
        int[] h = new int[1];
        GLFW.glfwGetWindowSize(window, w, h);

        MenuNode currentNode = menu.getCurrentNode();
        int selectedIndex = menu.getSelectedIndex();

        int offset = 0;
        for (int i = 0; i < currentNode.getChildren().size(); i++) {
            MenuNode childNode = currentNode.getChildren().get(i);

            offset += 80;
            if (i == selectedIndex) {
                Render.renderText(w[0]/2, offset, 60, "-" + childNode.getTitle() + "-", true);
                if (childNode.isAdjustable()){
                    offset += 60;
                    Render.renderText(w[0]/2, offset, 50, String.valueOf(childNode.getCurrentValue()), true);
                }
            } else {
                Render.renderText(w[0]/2, offset, 50, childNode.getTitle(), true);
            }
        }
    }
}
