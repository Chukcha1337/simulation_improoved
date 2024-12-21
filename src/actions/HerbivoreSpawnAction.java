package actions;

import entities.Entity;
import entities.alive.Herbivore;
import supportClasses.Coordinate;

public class HerbivoreSpawnAction extends SpawnAction {
    private final static double MAX_HERBIVORES_MULTIPLIER = 0.020;

    @Override
    protected Class<? extends Entity> getCurrentEntityClass() {
        return Herbivore.class;
    }

    @Override
    protected double getMaxQuantityMultiplier() {
        return MAX_HERBIVORES_MULTIPLIER;
    }

    @Override
    protected Entity createNewEntity(Coordinate coordinate) {
        return new Herbivore();
    }
}


