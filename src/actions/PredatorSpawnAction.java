package actions;

import entities.Entity;
import entities.alive.Predator;
import supportClasses.Coordinate;

public class PredatorSpawnAction extends SpawnAction {
    private final static double MAX_PREDATORS_MULTIPLIER = 0.004;

    @Override
    protected Class<? extends Entity> getCurrentEntityClass() {
        return Predator.class;
    }

    @Override
    protected double getMaxQuantityMultiplier() {
        return MAX_PREDATORS_MULTIPLIER;
    }

    @Override
    protected Entity createNewEntity(Coordinate coordinate) {
        return new Predator();
    }
}
