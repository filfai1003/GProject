package com.gproject.game.entities.monster;

import com.gproject.game.manage.Game;
import com.gproject.game.entities.*;

import static com.gproject.game.manage.PhysicsAndLogic.insertNewEntity;

public class Mage extends LivingEntity {
    private static final int MAGE_WIDTH = 200;
    private static final int MAGE_HEIGHT = 200;

    private static final boolean MAGE_AFFECT_BY_GRAFITY = true;
    private static final boolean MAGE_AFFECT_BY_COLLISION = true;

    private static final int MAGE_SPEED_LIMIT_X = 1000;
    private static final int MAGE_SPEED_LIMIT_Y = 0;

    private static final int MAGE_FRICTION = 2000;
    private static final int MAGE_AIR_FRICTION = 1000;

    private static final int MAGE_MAX_HEALTH = 1000;

    private static final int MAGE_ACCELERATION = 2000;
    private static final int MAGE_JUMP_SPEED = 2000;

    private static final int MAGE_FIRE_TIME = 2;
    private static final int MAGE_FIRE_SPEED = 2000;

    private double lastAttack = 0;

    public Mage(double x, double y, boolean enemy) {
        super(x, y, MAGE_WIDTH, MAGE_HEIGHT, MAGE_AFFECT_BY_GRAFITY, MAGE_AFFECT_BY_COLLISION, MAGE_SPEED_LIMIT_X, MAGE_SPEED_LIMIT_Y, MAGE_FRICTION, MAGE_AIR_FRICTION, MAGE_MAX_HEALTH, MAGE_ACCELERATION, MAGE_JUMP_SPEED, enemy);
    }

    @Override
    public void update(double seconds, Game game) {
        super.update(seconds, game);
        lastAttack += seconds;
        double distanceX = game.getPlayer().x + game.getPlayer().width/2 - x - width/2;
        double distanceY = game.getPlayer().y + game.getPlayer().height  - y - height;
        if (distanceX > 1000) {
            if (distanceY < -10){
                jump();
            }
            goRight(seconds);
        } else if (distanceX < -1000) {
            if (distanceY < -10){
                jump();
            }
            goLeft(seconds);
        } else if (lastAttack > MAGE_FIRE_TIME) {
            lastAttack = 0;
            insertNewEntity(mageAttack(this, game.getPlayer()), game.getChunks());
        }
    }

    private static Attack mageAttack(Entity caster, Entity obejctive){
        double startX = caster.x + (double) MAGE_WIDTH / 2;
        double startY = caster.y - 75;

        double targetX = obejctive.x + obejctive.width / 2;
        double targetY = obejctive.y + obejctive.height / 2;

        double deltaX = targetX - startX;
        double deltaY = targetY - startY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double speedX = (deltaX / distance) * MAGE_FIRE_SPEED;
        double speedY = (deltaY / distance) * MAGE_FIRE_SPEED;

        Attack attack = new Attack(startX, startY, 50, 50, false, caster, 1, 1000, 1, null, 0);
        attack.velocityX = speedX;
        attack.velocityY = speedY;
        return attack;
    }

}
