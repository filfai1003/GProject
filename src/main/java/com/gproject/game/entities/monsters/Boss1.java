package com.gproject.game.entities.monsters;

import com.gproject.game.entities.Entity;
import com.gproject.game.entities.LivingEntity;
import com.gproject.game.entities.Relation;
import com.gproject.game.entities.Sequence;
import com.gproject.game.manage.Game;

public class Boss1 extends LivingEntity {
    private static final int BOSS1_WIDTH = 500;
    private static final int BOSS1_HEIGHT = 500;

    private static final int BOSS1_N_SEQUENCE= 3;
    private static final int BOSS1_MIN_MOVEMENT= 3;
    private static final int BOSS1_MAX_MOVEMENT= 9;

    public Boss1(double x, double y) {
        super(x, y, BOSS1_WIDTH, BOSS1_HEIGHT, false, true, 0, 0, 0, 0, 5000, 0, 0, Relation.ENEMY);
    }

    @Override
    public void update(double seconds, Game game) {
        if (sequence == null) {
            int nextSequence = (int) (Math.random() * BOSS1_N_SEQUENCE);
            int movement = BOSS1_MIN_MOVEMENT + (int) (Math.random() * (BOSS1_MAX_MOVEMENT-BOSS1_MIN_MOVEMENT));
            baseSequence(movement);
        }
        super.update(seconds, game);
    }

    private void baseSequence(int n) {
        Sequence s = null, s1 = null;

        if (Math.random() > 0.5) {
            n++;
        }
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                s = new Sequence(100, 0, 1, 1, 1, null, null, s1);
                s1 = s;
            } else {
                s = new Sequence(-100, 0, 1, 1, 1, null, null, s1);
                s1 = s;
            }
        }
        sequence = s;
    }

    @Override
    public void onCollision(Entity other) {
        if (sequence != null) {
            sequence = sequence.next;
        }
    }
}
