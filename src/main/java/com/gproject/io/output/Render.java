package com.gproject.io.output;

import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

public class Render {

    private static final String DEFAULT_TEXTURE_PATH = "assets/default.png";
    private static final Map<String, Integer> textureMap = new HashMap<>();

    /**
     * Carica una texture se non è già presente nella mappa.
     *
     * @param path il percorso della texture da caricare
     * @return l'ID della texture OpenGL
     */
    public static int loadTexture(String path) {
        if (textureMap.containsKey(path)) {
            return textureMap.get(path);
        }
        int textureID = loadTextureFromFile(path);
        textureMap.put(path, textureID);
        return textureID;
    }

    /**
     * Funzione principale per renderizzare un'immagine.
     *
     * @param x la posizione X dell'immagine
     * @param y la posizione Y dell'immagine
     * @param w la larghezza dell'immagine (se -1, mantiene il rapporto d'aspetto)
     * @param h l'altezza dell'immagine (se -1, mantiene il rapporto d'aspetto)
     * @param path il percorso della texture da caricare
     */
    public static void renderImage(float x, float y, float w, float h, String path) {
        // Usa la texture predefinita se il path è null o vuoto
        if (path == null || path.isEmpty()) {
            path = DEFAULT_TEXTURE_PATH;
        }

        // Carica la texture (se già presente nella mappa, non verrà caricata nuovamente)
        int textureID = loadTexture(path);

        // Ottieni le dimensioni originali dell'immagine per mantenere il rapporto d'aspetto
        int[] imageSize = getImageSize(path);
        float imageWidth = imageSize[0];
        float imageHeight = imageSize[1];

        // Se w o h sono -1, calcola le dimensioni mantenendo il rapporto d'aspetto
        if (w == -1 && h == -1) {
            w = imageWidth;
            h = imageHeight;
        } else if (w == -1) {
            w = h * (imageWidth / imageHeight);
        } else if (h == -1) {
            h = w * (imageHeight / imageWidth);
        }

        // Bind della texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        // Renderizza l'immagine
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0); GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(1, 0); GL11.glVertex2f(x + w, y);
        GL11.glTexCoord2f(1, 1); GL11.glVertex2f(x + w, y + h);
        GL11.glTexCoord2f(0, 1); GL11.glVertex2f(x, y + h);
        GL11.glEnd();
    }

    /**
     * Carica una texture da file usando STB.
     *
     * @param path il percorso dell'immagine da caricare
     * @return l'ID della texture OpenGL
     */
    private static int loadTextureFromFile(String path) {
        ByteBuffer imageBuffer;
        int width, height;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            // Carica l'immagine con 4 canali (RGBA)
            imageBuffer = STBImage.stbi_load(path, w, h, comp, 4); // 4 canali RGBA
            if (imageBuffer == null) {
                System.err.println("Failed to load texture file: " + path + ". Loading default texture.");
                return loadTexture(DEFAULT_TEXTURE_PATH);  // Carica la texture di default
            }

            width = w.get();
            height = h.get();
        }

        // Genera una texture ID e bind alla texture
        int textureID = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        // Parametri di texture
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        // Carica la texture con RGBA (che include il canale alpha per la trasparenza)
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageBuffer);

        // Libera l'immagine dalla memoria
        STBImage.stbi_image_free(imageBuffer);

        return textureID;
    }



    /**
     * Ottiene le dimensioni di un'immagine senza caricare la texture nella GPU.
     *
     * @param path il percorso della texture
     * @return array con larghezza e altezza dell'immagine
     */
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
