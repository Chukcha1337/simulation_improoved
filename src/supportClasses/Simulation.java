package supportClasses;

import actions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Simulation {
    private static final String START = "g";
    private static final String PAUSE = "p";
    private static final String RESUME = "r";
    private static final String STOP = "s";
    private static final Scanner scanner = new Scanner(System.in);
    private final WorldMap worldMap;
    private final MapPrinter mapPrinter;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private volatile boolean paused = false;
    private volatile boolean running = true;
    private final Object lock = new Object();

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.mapPrinter = new MapPrinter(worldMap, new CounterOfIterations());
        this.initActions = new ArrayList<>();
        this.turnActions = new ArrayList<>();
    }

    public void startSimulation() {
        createActions();
        makeInitActions();
        System.out.printf("""
                Приветствую Вас в Симуляции, для старта введите ('%s')
                Для паузы во время симуляции введите ('%s')
                Для возобновления симуляции во время паузы введите ('%s')
                Для остановки симуляции введите ('%s')
                """, START, PAUSE, RESUME, STOP);
        while (true) {
            String decision = scanner.nextLine().trim().toLowerCase();
            if (decision.equals(START)) {
                break;
            } else {
                System.out.printf("Вы ввели неизвестную команду! Для старта симуляции введите только ('%s')\n", START);
            }
        }
        Thread currentSimulation = new Thread(() -> {
            while (running) {
                synchronized (lock) {
                    while (paused) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                try {
                    Thread.sleep(500);
                    nextTurn();
                } catch (InterruptedException e) {
                    System.out.println("Simulation interrupted");
                }
            }
            System.out.println("Simulation stopped.");
        });

        Thread controlThread = new Thread(() -> {
            while (running) {
                String command = scanner.nextLine().trim();
                switch (command.toLowerCase()) {
                    case "p" -> pauseSimulation();
                    case "r" -> resumeSimulation();
                    case "q" -> stopSimulation();
                    default -> System.out.println("Invalid command");
                }

            }
        });
        currentSimulation.start();
        controlThread.start();
        try {
            controlThread.join();
            currentSimulation.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void createActions() {
        initActions.add(new RockSpawnAction());
        initActions.add(new TreeSpawnAction());
        initActions.add(new GrassSpawnAction());
        initActions.add(new HerbivoreSpawnAction());
        initActions.add(new PredatorSpawnAction());
        turnActions.add(new CreatureMoveAction());
        turnActions.add(new GrassSpawnAction());
        turnActions.add(new HerbivoreSpawnAction());
    }

    private void makeInitActions() {
        for (Action action : initActions) {
            action.execute(worldMap);
        }
    }

    private void nextTurn() {
        for (Action action : turnActions) {
            action.execute(worldMap);
        }
        mapPrinter.printMap();
    }

    private void pauseSimulation() {
        synchronized (lock) {
            paused = true;
            System.out.println("Simulation paused.");
        }
    }

    private void resumeSimulation() {
        synchronized (lock) {
            paused = false;
            lock.notify();
            System.out.println("Simulation continued.");
        }
    }

    private void stopSimulation() {
        running = false;
        resumeSimulation();
        System.out.println("Simulation stopping...");
    }





}
