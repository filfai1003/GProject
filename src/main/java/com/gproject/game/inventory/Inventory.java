package com.gproject.game.inventory;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Inventory implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Weapon mainWeapon, secondWeapon;
    private Item item1, item2;
    private List<Weapon> weapons;

    public Inventory(Weapon mainWeapon, Weapon secondWeapon, Item item1, Item item2, List<Weapon> weapons) {
        this.mainWeapon = mainWeapon;
        this.secondWeapon = secondWeapon;
        this.item1 = item1;
        this.item2 = item2;
        this.weapons = weapons;
    }
}
