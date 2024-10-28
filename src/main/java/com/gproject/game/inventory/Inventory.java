package com.gproject.game.inventory;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public Weapon mainWeapon, secondWeapon;
    public Item item1, item2;
    private List<Weapon> weapons;

    public Inventory() {
        this.weapons = new ArrayList<>();
    }

    public void update(double seconds){
        for (Weapon weapon: weapons) {
            weapon.passifEffect.accept(seconds);
        }
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }
}
