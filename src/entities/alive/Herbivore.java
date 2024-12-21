package entities.alive;

import actions.HerbivoreSpawnAction;
import actions.SpawnAction;
import entities.Creature;
import entities.unalive.Grass;
import supportClasses.Coordinate;
import supportClasses.WorldMap;

public class Herbivore extends Creature {

    public Herbivore() {
        health = 4;
        maxHealth = 4;
        speed = 3;
        stepsLeft = speed;
        levelOfHunger = 2;
        isWishToReproduce = false;
        ateThisTurn = false;
        isAlive = true;
        this.food = Grass.class;
    }

    @Override
    protected void reproduce(WorldMap worldMap) {
        SpawnAction producer = new HerbivoreSpawnAction();
        producer.reproduce(worldMap, this);
    }

    @Override
    protected void eat(WorldMap worldMap, Coordinate coordinate) {
        takeStep(worldMap, coordinate);
        renewCreatureParameters(1, 1);
    }


}


