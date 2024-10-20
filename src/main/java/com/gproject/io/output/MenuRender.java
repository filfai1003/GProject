package com.gproject.io.output;

import com.gproject.menu.Menu;
import com.gproject.menu.MenuNode;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glRasterPos2f;

public class MenuRender {

    public static void render(Menu menu) {
        MenuNode currentNode = menu.getCurrentNode();
        int selectedIndex = menu.getSelectedIndex();

        for (int i = 0; i < currentNode.getChildren().size(); i++) {
            MenuNode childNode = currentNode.getChildren().get(i);

            // Imposta il colore per l'opzione corrente
            if (i == selectedIndex) {
                glColor3f(1.0f, 1.0f, 0.0f);
            } else {
                glColor3f(1.0f, 1.0f, 1.0f);
            }

            drawText(100, 100 - (i * 20), childNode.getTitle());
        }
    }

    private static void drawText(int x, int y, String text) {
        glRasterPos2f(x, y); // Imposta la posizione del testo

        for (int i = 0; i < text.length(); i++) {
            // Rendering di ciascun carattere
            // GLUT_BITMAP_8_BY_13 è uno stile di font semplice di GLUT
            glutBitmapCharacter(GLUT.GLUT_BITMAP_8_BY_13, text.charAt(i));
        }
    }
}
