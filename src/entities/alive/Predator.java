package entities.alive;

import entities.Creature;
import supportClasses.Coordinate;
import supportClasses.WorldMap;

import java.util.Random;

public class Predator extends Creature {
    private final int attackDamage = 4;

    public Predator() {
        health = 9;
        speed = 3;
        isAlive = true;
        this.food = Herbivore.class;
    }

    @Override
    protected void eat(WorldMap worldMap, Coordinate coordinate) {
        Random rand = new Random();
        if (rand.nextInt(10) > 4) {
            Herbivore herbivore = (Herbivore) worldMap.get(coordinate);
            herbivore.reduceHealth(attackDamage);
            if (herbivore.getHealth() <= 0) {
                kill(herbivore);
                takeStep(worldMap, coordinate);
            } else decreaseStepsLeft();
        }
        decreaseStepsLeft();

    }
}
