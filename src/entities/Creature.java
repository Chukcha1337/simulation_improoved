package entities;

import supportClasses.Coordinate;
import supportClasses.PathBuilder;
import supportClasses.WorldMap;

import java.util.List;

public abstract class Creature extends Entity {
    protected int health;
    protected int speed;
    protected int stepsLeft;
    protected int maxHealth;
    protected int levelOfHunger;
    protected boolean isAlive;
    protected boolean ateThisTurn;
    protected boolean isWishToReproduce;
    protected Class<?> food;

    public void makeMove(WorldMap worldMap) {
        if (health < Math.round((float) maxHealth /2)) {
            setWishToReproduce(false);
        }
        ateThisTurn = false;
        stepsLeft = getSpeed();
        PathBuilder pathBuilder = new PathBuilder(worldMap, this);
        while (stepsLeft > 0) {
            List<Coordinate> path = pathBuilder.getPath();
            if (path.isEmpty()) {
                break;
            }
            path.removeLast();
            Coordinate nextStep = path.getLast();
            if (worldMap.isEmpty(nextStep)) {
                takeStep(worldMap, nextStep);
            } else if (worldMap.get(nextStep).getClass() == this.food) {
                eat(worldMap, nextStep);
            } else if (reproductionIsPossible(worldMap,nextStep)) {
                reproduce(worldMap);
                break;
            }
        }
        renewParametersAfterTurn(worldMap);

    }
    private void setWishToReproduce(boolean isWishToReproduce) {
        this.isWishToReproduce = isWishToReproduce;
    }

    private int getSpeed() {
        return speed;
    }

    private void renewParametersAfterTurn(WorldMap worldMap) {
        if (!ateThisTurn) {
            levelOfHunger++;
        }
        switch (levelOfHunger) {
            case 0, 1 -> setWishToReproduce(true);
            case 2, 3 -> setWishToReproduce(false);
            default -> {
                setWishToReproduce(false);
                reduceHealth(1);
            }
        }
        if (health <= 0) {
            this.isAlive = false;
            worldMap.remove(worldMap.getCoordinate(this));
        }
    }

    private boolean reproductionIsPossible(WorldMap worldMap, Coordinate nextStep) {
        return (worldMap.get(nextStep).getClass() == this.getClass() &&
                isWishToReproduce &&
                ((Creature) worldMap.get(nextStep)).isWishToReproduce());
    }

    public void decreaseStepsLeft() {
        stepsLeft--;
    }

    public void reduceHealth(int damage) {
        health -= damage;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public String showAim() {
        if(isWishToReproduce) {
            return "s";
        } else {
            return "e";
        }
    }

    public boolean isWishToReproduce() {
        return isWishToReproduce;
    }

    public void reverseWishToReproduce() {
        isWishToReproduce = !isWishToReproduce;
    }

    public int getHealth() {
        return health;
    }

    public Class<?> getFood() {
        return food;
    }

    protected void kill(Creature creature) {
        creature.isAlive = false;
    }

    protected void recoverHealth(int recover) {
        health += recover;
    }

    protected void takeStep(WorldMap worldMap, Coordinate coordinate) {
        Coordinate from = worldMap.getCoordinate(this);
        worldMap.put(coordinate, this);
        worldMap.remove(from);
        decreaseStepsLeft();
    }

    protected void renewCreatureParameters (int maxHungerDecrease, int maxHealNumber) {
        if (levelOfHunger > maxHungerDecrease) {
            levelOfHunger -= maxHungerDecrease;
        } else {
            levelOfHunger = 0;
        }
        ateThisTurn = true;
        if (health <= (maxHealth - maxHealNumber)) {
            recoverHealth(maxHealNumber);
        }  else {
            health = maxHealth;
        }
    }

    protected abstract void reproduce(WorldMap worldMap);

    protected abstract void eat(WorldMap worldMap, Coordinate coordinate);
}
