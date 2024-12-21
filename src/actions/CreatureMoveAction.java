package actions;

import entities.Creature;
import entities.Entity;
import supportClasses.WorldMap;

public class CreatureMoveAction extends Action {

    @Override
    public void execute(WorldMap worldMap) {
        for (Entity entity : worldMap.getAll()) {
            if (entity instanceof Creature && ((Creature) entity).isAlive()) {
                ((Creature) entity).makeMove(worldMap);
            }
        }
    }
}
