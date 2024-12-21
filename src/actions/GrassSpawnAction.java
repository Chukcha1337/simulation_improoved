package actions;

import entities.Entity;
import entities.unalive.Grass;
import supportClasses.Coordinate;

public class GrassSpawnAction extends SpawnAction {
    private final static double MAX_GRASS_MULTIPLIER = 0.06;

    @Override
    protected Class<? extends Entity> getCurrentEntityClass() {
        return Grass.class;
    }

    @Override
    protected double getMaxQuantityMultiplier() {
        return MAX_GRASS_MULTIPLIER;
    }

    @Override
    protected Entity createNewEntity(Coordinate coordinate) {
        return new Grass();
    }
}
