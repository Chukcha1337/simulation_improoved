package actions;

import entities.Entity;
import supportClasses.Coordinate;
import supportClasses.WorldMap;
import java.util.Random;

public abstract class SpawnAction extends Action {

    @Override
    public void execute(WorldMap worldMap) {
        int counter = (int) worldMap.getAll().stream().filter(a -> a.getClass().equals(getCurrentEntityClass())).count();
        while (counter <= getMaximumQuantity(worldMap)) {
            Coordinate coordinate = worldMap.getRandomEmptyPlace(new Random());
            worldMap.put(coordinate, createNewEntity(coordinate));
            counter++;
        }
    }

    public void reproduce (WorldMap worldMap, Entity entity) {
        for (Coordinate coordinate : worldMap.getNearestLocations(worldMap.getCoordinate(entity))) {
            if (worldMap.isEmpty(coordinate)) {
                worldMap.put(coordinate, createNewEntity(coordinate));
                break;
            }
        }
    }

    private int getMaximumQuantity(WorldMap worldMap) {
        return (int) Math.ceil(worldMap.getRows() * worldMap.getColumns() * getMaxQuantityMultiplier());
    }

    protected abstract Class<? extends Entity> getCurrentEntityClass();

    protected abstract double getMaxQuantityMultiplier();

    protected abstract Entity createNewEntity(Coordinate coordinate);

}
