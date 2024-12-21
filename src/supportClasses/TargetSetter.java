package supportClasses;

import entities.Creature;
import entities.Entity;
import java.util.HashSet;
import java.util.Set;

public class TargetSetter {
    private final WorldMap worldMap;
    private final Creature creature;
    private final Set<Coordinate> AllTargets;

    public TargetSetter(WorldMap worldMap, Creature creature) {
        this.worldMap = worldMap;
        this.creature = creature;
        AllTargets = new HashSet<>();
        locateAllTargets();
    }

    public void removeTarget(Coordinate target) {
        AllTargets.remove(target);
    }

    private void locateAllTargets() {
        for (Entity entity : worldMap.getAll()) {
            if (entity.getClass().equals(creature.getFood())) {
                AllTargets.add(worldMap.getCoordinate(entity));
            }
        }
    }

    public Coordinate setTarget(Coordinate creatureCurrentCoordinate) {
        double ShortestDistance = worldMap.getMapMaxDistance();
        Coordinate targetCoordinate = creatureCurrentCoordinate;
        for (Coordinate coordinate : AllTargets) {
            double distanceToCurrentTarget = worldMap.getShortestPathDistance(creatureCurrentCoordinate, coordinate);
            if (distanceToCurrentTarget < ShortestDistance) {
                ShortestDistance = distanceToCurrentTarget;
                targetCoordinate = coordinate;
            }
        }
        return targetCoordinate;
    }
}
