package supportClasses;

import entities.Creature;
import entities.Entity;
import java.util.HashSet;
import java.util.Set;

public class TargetSetter {
    private final WorldMap worldMap;
    private final Creature creature;
    private final Set<Coordinate> AllTargets;
    private boolean wasAttemptToSwitchTarget;

    public TargetSetter(WorldMap worldMap, Creature creature) {
        this.worldMap = worldMap;
        this.creature = creature;
        wasAttemptToSwitchTarget = false;
        AllTargets = new HashSet<>();
        locateAllTargets();
    }

    public boolean getWasAttemptToSwitchTarget() {
        return wasAttemptToSwitchTarget;
    }

    public void switchTargetClass() {
        wasAttemptToSwitchTarget = true;
        creature.reverseWishToReproduce();
        AllTargets.clear();
        locateAllTargets();
    }

    public void removeTarget(Coordinate target) {
        AllTargets.remove(target);
    }

    private void locateAllTargets() {
        Class<?> target = chooseOfTarget();
        if (target.equals(creature.getClass())) {
            for (Entity entity : worldMap.getAll()) {
                if (entity.getClass().equals(target) && !entity.equals(creature) && ((Creature) entity).isWishToReproduce()) {
                    AllTargets.add(worldMap.getCoordinate(entity));
                }
            }
        } else if (target.equals(creature.getFood())) {
            for (Entity entity : worldMap.getAll()) {
                if (entity.getClass().equals(target)) {
                    AllTargets.add(worldMap.getCoordinate(entity));
                }
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

    private Class<?> chooseOfTarget() {
        if (creature.isWishToReproduce()) {
            return creature.getClass();
        }
        return creature.getFood();
    }
}
