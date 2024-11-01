package com.gproject.game.inventory.waepons;

import com.gproject.game.entities.Attack;
import com.gproject.game.entities.Player;
import com.gproject.game.inventory.Weapon;

public class Shield extends Weapon {

    public Shield(Player caster) {
        super("Sword", baseAttack(caster), null, null, null);
    }


    private static Attack baseAttack(Player caster) {
        Attack baseAttack = new Attack(0, 10, 50, 100, false, caster, 0, 0, false, 1, null, 1);
        baseAttack.affectByCollision = true;
        return baseAttack;
    }
}
