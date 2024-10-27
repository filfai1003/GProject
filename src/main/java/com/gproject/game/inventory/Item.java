package com.gproject.game.inventory;

import com.gproject.game.Game;

import java.util.function.Consumer;

public class Item {
    private int quantity;
    private final Consumer<Game> effect;

    public Item(int quantity, Consumer<Game> effect) {
        this.quantity = quantity;
        this.effect = effect;
    }
}
