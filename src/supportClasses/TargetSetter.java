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
        if (creature.isWishToReproduce()) {
            locateAllReadyToBreed();
        } else {
            locateAllFood();
        }
    }

    private void locateAllReadyToBreed() {
        for (Entity entity : worldMap.getAll()) {
            if (isThisCreatureWantToBreedToo(entity)) {
                AllTargets.add(worldMap.getCoordinate(entity));
            }
        }
    }

    private void locateAllFood() {
        for (Entity entity : worldMap.getAll()) {
            if (entity.getClass().equals(creature.getFood())) {
                AllTargets.add(worldMap.getCoordinate(entity));
            }
        }
    }

    private boolean isThisCreatureWantToBreedToo(Entity entity) {
        return (entity.getClass().equals(creature.getClass())
                && !entity.equals(creature)
                && ((Creature) entity).isWishToReproduce());
    }

}
