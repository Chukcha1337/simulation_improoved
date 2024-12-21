package actions;

import entities.Entity;
import entities.unalive.Rock;
import supportClasses.Coordinate;

public class RockSpawnAction extends SpawnAction {
    private final static double MAX_ROCKS_MULTIPLIER = 0.03;

    @Override
    protected Class<? extends Entity> getCurrentEntityClass() {
        return Rock.class;
    }

    @Override
    protected double getMaxQuantityMultiplier() {
        return MAX_ROCKS_MULTIPLIER;
    }

    @Override
    protected Entity createNewEntity(Coordinate coordinate) {
        return new Rock();
    }


}
