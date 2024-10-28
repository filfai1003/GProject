package com.gproject.game.inventory;

import com.gproject.game.Game;

import java.io.Serial;
import java.io.Serializable;
import java.util.function.Consumer;

public class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int quantity;
    private final Consumer<Game> effect;

    public Item(int quantity, Consumer<Game> effect) {
        this.quantity = quantity;
        this.effect = effect;
    }
}
