package entities.alive;

import actions.PredatorSpawnAction;
import actions.SpawnAction;
import entities.Creature;
import supportClasses.Coordinate;
import supportClasses.WorldMap;

import java.util.Random;

public class Predator extends Creature {
    private static final Random random = new Random();

    public Predator() {
        health = 9;
        maxHealth = 9;
        speed = 3;
        levelOfHunger = 1;
        isWishToReproduce = false;
        ateThisTurn = false;
        isAlive = true;
        this.food = Herbivore.class;
    }

    @Override
    protected void reproduce(WorldMap worldMap) {
        SpawnAction producer = new PredatorSpawnAction();
        producer.reproduce(worldMap, this);
    }

    @Override
    protected void eat(WorldMap worldMap, Coordinate coordinate) {
        if (isAttackSuccessful(5)) {
            Herbivore herbivore = (Herbivore) worldMap.get(coordinate);
            int attackDamage = 3;
            herbivore.reduceHealth(attackDamage);
            if (herbivore.getHealth() <= 0) {
                kill(herbivore);
                takeStep(worldMap, coordinate);
                renewCreatureParameters(2, 4);
            } else {
                decreaseStepsLeft();
            }
        }
        decreaseStepsLeft();
    }

    private boolean isAttackSuccessful(int chanceOfSuccessfulAttackFromZeroToNine) {
        return random.nextInt(10) < chanceOfSuccessfulAttackFromZeroToNine;
    }
}
