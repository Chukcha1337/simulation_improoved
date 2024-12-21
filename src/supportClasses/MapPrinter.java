package supportClasses;

import entities.Creature;
import entities.Entity;
import entities.alive.Herbivore;
import entities.alive.Predator;
import entities.unalive.Grass;
import entities.unalive.Rock;
import entities.unalive.Tree;

public class MapPrinter {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_GREEN_BACKGROUND = "\033[0;102m";
    private final WorldMap worldMap;
    private final CounterOfIterations counterOfIterations;

    public MapPrinter(WorldMap worldMap, CounterOfIterations counterOfIterations) {
        this.worldMap = worldMap;
        this.counterOfIterations = counterOfIterations;
    }

    public void printMap() {
        for (int rows = 0; rows < worldMap.getRows(); rows++) {
            StringBuilder line = new StringBuilder();
            for (int columns = 0; columns < worldMap.getColumns(); columns++) {
                Coordinate coordinate = new Coordinate(columns, rows);
                if (worldMap.isEmpty(coordinate)) {
                    line.append(getSpriteForEmptyPlace());
                } else {
                    Entity entity = worldMap.get(coordinate);
                    line.append(getEntitySprite(entity));
                }
            }
            System.out.println(line);
        }
        counterOfIterations.increaseCounter();
        System.out.println(counterOfIterations.getCounter());
    }

    private String getEntitySprite(Entity entity) {
        if (entity instanceof Herbivore) {
            return ANSI_GREEN_BACKGROUND + ANSI_BLACK + ((Creature) entity).getHealth() +Sprites.HERBIVORE.getSprite() + ((Herbivore) entity).showAim() + ANSI_RESET;
        }
        if (entity instanceof Predator) {
            return ANSI_GREEN_BACKGROUND + ANSI_BLACK + ((Creature) entity).getHealth() + Sprites.PREDATOR.getSprite() + ((Predator) entity).showAim() + ANSI_RESET;
        }
        if (entity instanceof Rock) {
            return ANSI_GREEN_BACKGROUND + " " + Sprites.ROCK.getSprite() + " " +ANSI_RESET;
        }
        if (entity instanceof Tree) {
            return ANSI_GREEN_BACKGROUND + " " + Sprites.TREE.getSprite() + " " + ANSI_RESET;
        }
        if (entity instanceof Grass) {
            return ANSI_GREEN_BACKGROUND + " " + Sprites.GRASS.getSprite() + " " + ANSI_RESET;
        }
        return " error ";
    }

    private String getSpriteForEmptyPlace() {
        return (ANSI_GREEN_BACKGROUND + Sprites.EMPTY_PLACE.getSprite() + ANSI_RESET);
    }
}
