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
            if (weapon.passifEffect != null) {
                weapon.passifEffect.accept(seconds);
            }
        }
    }

    public void changeMainWeapon(){
        int index = weapons.indexOf(mainWeapon);
        index++;
        if (index >= weapons.size()) {
            if (index == weapons.size()) {
                mainWeapon = null;
                return;
            }
            index = 0;
        }
        mainWeapon = weapons.get(index);
    }

    public void changeSecondWeapon(){
        int index = weapons.indexOf(secondWeapon);
        index++;
        if (index >= weapons.size()) {
            if (index == weapons.size()) {
                secondWeapon = null;
                return;
            }
            index = 0;
        }
        secondWeapon = weapons.get(index);
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }
}
