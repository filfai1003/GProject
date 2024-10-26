package com.gproject.game;

public class Costants {
    // PLAYER
    public static final int P_VELOCITY_LIMIT = 1200;    // Limite di velocità massimo per evitare movimenti troppo rapidi e poco controllabili.
    public static final int P_ACCELERATION = 2000;      // Accelera rapidamente, ma non troppo per mantenere il controllo sul personaggio.
    public static final int P_FRICTION = 1500;          // Frizione elevata per fermare velocemente il giocatore sul suolo.
    public static final int P_AIR_FRICTION = 200;       // Bassa frizione in aria per mantenere un po' d'inerzia nel movimento.

    public static final int P_WIDTH = 50;               // Larghezza del personaggio
    public static final int P_HEIGHT = 180;             // Altezza del personaggio

    public static final int P_JUMP_SPEED = 1800;        // Altezza del salto (più alta rispetto al movimento orizzontale per salti precisi).
    public static final double P_COYOTE_TIME = 0.15;    // Tempo di "coyote" leggermente più lungo per salti più permissivi.
    public static final int P_DASH_SPEED = 4000;        // Velocità della schivata rapida per evitare nemici o ostacoli.
    public static final double P_DASH_TIME = 0.2;       // Durata breve per non rompere il flusso di gioco.
    public static final double P_DASH_RELOAD_TIME = 1;  // Tempo di ricarica della schivata.

    public static final int P_MAX_HEALTH = 1000;        // Vita massima ridotta per incentivare sfida.

    // GENERAL
    public static final double G_COYOTE_TIME = 0.15;    // Coyote time global

    // PHYSICS
    public static final int GRAVITY = 4000;             // Gravità abbassata per salti più fluidi.


    // STRUCTURAL
    public static final int CHUNK_SIZE = 1000;
    public static final int CHUNKS_TO_UPDATE = 1;
    public static final int CHUNKS_TO_RENDER = 1;
}
