package entities.alive;

import entities.Creature;
import entities.unalive.Grass;
import supportClasses.Coordinate;
import supportClasses.WorldMap;

public class Herbivore extends Creature {

    public Herbivore() {
        health = 4;
        speed = 3;
        stepsLeft = speed;
        isAlive = true;
        this.food = Grass.class;
    }

    @Override
    protected void eat(WorldMap worldMap, Coordinate coordinate) {
        takeStep(worldMap, coordinate);
    }
}


