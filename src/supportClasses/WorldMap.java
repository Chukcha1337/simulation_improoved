package supportClasses;

import entities.Entity;
import java.util.*;

public class WorldMap {
    private final int ROWS;
    private final int COLUMNS;
    private final double MAX_MAP_DISTANCE;
    private final Map<Coordinate, Entity> WORLD_MAP = new Hashtable<>();

    public WorldMap(int rows, int columns) {
        this.ROWS = rows;
        this.COLUMNS = columns;
        this.MAX_MAP_DISTANCE = Math.sqrt((Math.pow(rows, 2) + Math.pow(columns, 2)));
    }

    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public double getMapMaxDistance() {
        return MAX_MAP_DISTANCE;
    }

    public void put(Coordinate coordinate, Entity entity) {
        WORLD_MAP.put(coordinate,entity);
    }

    public Entity get(Coordinate coordinate) {
        return WORLD_MAP.get(coordinate);
    }

    public void remove(Coordinate coordinate) {
        WORLD_MAP.remove(coordinate);
    }

    public Coordinate getCoordinate(Entity entity) {
        return WORLD_MAP.entrySet().stream().filter(e -> e.getValue().equals(entity)).findFirst().get().getKey();
    }

    public List<Entity> getAll() {
        return WORLD_MAP.values().stream().toList();
    }

    public boolean isEmpty(Coordinate coordinate) {
        return !WORLD_MAP.containsKey(coordinate);
    }

    public boolean isNonValid(Coordinate coordinate) {
        return (coordinate.getRow() < 0 ||
                coordinate.getRow() >= ROWS ||
                coordinate.getColumn() < 0 ||
                coordinate.getColumn() >= COLUMNS);
    }

    public Coordinate getRandomEmptyPlace(Random random) {
        while (true) {
            Coordinate randomCoordinate = new Coordinate(random.nextInt(this.getRows()), random.nextInt(this.getColumns()));
            if (this.isEmpty(randomCoordinate)) {
                return randomCoordinate;
            }
        }
    }

    public Set<Coordinate> getNearestLocations(Coordinate coordinate) {
        Set<Coordinate> nearestLocations = new HashSet<>();
        nearestLocations.add(new Coordinate(coordinate.getRow() + 1, coordinate.getColumn()));
        nearestLocations.add(new Coordinate(coordinate.getRow() - 1, coordinate.getColumn()));
        nearestLocations.add(new Coordinate(coordinate.getRow(), coordinate.getColumn() + 1));
        nearestLocations.add(new Coordinate(coordinate.getRow(), coordinate.getColumn() - 1));
        return nearestLocations;
    }

    public double getShortestPathDistance(Coordinate firstCoordinate, Coordinate secondCoordinate) {
        return Math.sqrt((Math.pow(firstCoordinate.getColumn() - secondCoordinate.getColumn(), 2) + Math.pow(firstCoordinate.getRow() - secondCoordinate.getRow(), 2)));
    }

}

