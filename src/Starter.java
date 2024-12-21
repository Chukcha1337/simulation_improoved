import supportClasses.Simulation;
import supportClasses.WorldMap;

public class Starter {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(new WorldMap(15, 15));
        simulation.startSimulation();
    }
}