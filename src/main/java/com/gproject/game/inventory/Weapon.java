package com.gproject.game.inventory;

import com.gproject.game.entities.Attack;

public class Weapon {
    protected Attack baseAttack;
    protected Attack heavyAttack;
    protected Attack specialAttack;

    public Weapon(Attack baseAttack, Attack heavyAttack, Attack specialAttack) {
        this.baseAttack = baseAttack;
        this.heavyAttack = heavyAttack;
        this.specialAttack = specialAttack;
    }
}
