package com.gproject.io.output;

import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static com.gproject.menu.Settings.hudDimension;

public class Render {

    private static final String DEFAULT_TEXTURE_PATH = "assets/images/default.png";
    private static final Map<String, Integer> textureMap = new HashMap<>();

    public static int loadTexture(String path) {
        if (textureMap.containsKey(path)) {
            return textureMap.get(path);
        }
        int textureID = loadTextureFromFile(path);
        textureMap.put(path, textureID);
        return textureID;
    }

    public static void renderImage(float x, float y, float w, float h, String path) {
        if (path == null || path.isEmpty()) {
            path = DEFAULT_TEXTURE_PATH;
        }

        int textureID = loadTexture(path);

        int[] imageSize = getImageSize(path);
        float imageWidth = imageSize[0];
        float imageHeight = imageSize[1];

        if (w == -1 && h == -1) {
            w = imageWidth;
            h = imageHeight;
        } else if (w == -1) {
            w = h * (imageWidth / imageHeight);
        } else if (h == -1) {
            h = w * (imageHeight / imageWidth);
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0); GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(1, 0); GL11.glVertex2f(x + w, y);
        GL11.glTexCoord2f(1, 1); GL11.glVertex2f(x + w, y + h);
        GL11.glTexCoord2f(0, 1); GL11.glVertex2f(x, y + h);
        GL11.glEnd();
    }

    public static void renderText(float x, float y, int size, String text, boolean center) {
        size = (int) (size * hudDimension);

        float totalWidth = 0;
        for (char c : text.toCharArray()) {
            if (c != ' ') {
                String imagePath = "assets/images/char/" + c + ".png";
                int[] imageSize = getImageSize(imagePath);
                float imageWidth = imageSize[0];
                float imageHeight = imageSize[1];
                float scaledWidth = size * (imageWidth / imageHeight);
                totalWidth += scaledWidth;
            } else {
                totalWidth += (float) size / 2;
            }
        }

        float xOffset = center ? x - (totalWidth / 2) : x;

        for (char c : text.toCharArray()) {
            if (c != ' ') {
                String imagePath = "assets/images/char/" + c + ".png";

                int[] imageSize = getImageSize(imagePath);
                float imageWidth = imageSize[0];
                float imageHeight = imageSize[1];
                float scaledWidth = size * (imageWidth / imageHeight);

                renderImage(xOffset, y, -1, size, imagePath); // Sottrai size da y

                xOffset += scaledWidth;
            } else {
                xOffset += (float) size / 2;
            }
        }
    }



    private static int loadTextureFromFile(String path) {
        ByteBuffer imageBuffer;
        int width, height;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            imageBuffer = STBImage.stbi_load(path, w, h, comp, 4); // 4 canali RGBA
            if (imageBuffer == null) {
                System.err.println("Failed to load texture file: " + path + ". Loading default texture.");
                return loadTexture(DEFAULT_TEXTURE_PATH);  // Carica la texture di default
            }

            width = w.get();
            height = h.get();
        }

        int textureID = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageBuffer);

        STBImage.stbi_image_free(imageBuffer);

        return textureID;
    }

    private static int[] getImageSize(String path) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            STBImage.stbi_info(path, w, h, comp);

            return new int[]{w.get(), h.get()};
        }
    }
}
