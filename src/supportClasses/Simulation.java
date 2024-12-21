package supportClasses;

import actions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulation {
    private static final String START = "g";
    private static final String PAUSE = "p";
    private static final String RESUME = "r";
    private static final String STOP = "q";
    private static final Scanner scanner = new Scanner(System.in);
    private final WorldMap worldMap;
    private final MapPrinter mapPrinter;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private volatile boolean isSimulationPaused = false;
    private volatile boolean isSimulationRunning = true;
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
            while (isSimulationRunning) {
                synchronized (lock) {
                    while (isSimulationPaused) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                try {
                    nextTurn();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Simulation interrupted");
                }
            }
            System.out.println("Симуляция закончена");
        });

        Thread controlThread = new Thread(() -> {
            while (isSimulationRunning) {
                String command = scanner.nextLine().trim();
                switch (command.toLowerCase()) {
                    case PAUSE -> pauseSimulation();
                    case RESUME -> resumeSimulation();
                    case STOP -> stopSimulation();
                    default -> System.out.printf("Неизвестная команда, введите только ('%s'), ('%s') или ('%s')", PAUSE, RESUME, STOP);
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
            isSimulationPaused = true;
            System.out.printf("Симуляция приостановлена, введите ('%s') для продолжения\n", RESUME);
        }
    }

    private void resumeSimulation() {
        synchronized (lock) {
            isSimulationPaused = false;
            lock.notify();
            System.out.printf("Симуляция продолжается, введите ('%s') для паузы или ('%s') для выхода \n", PAUSE, STOP);;
        }
    }

    private void stopSimulation() {
        isSimulationRunning = false;
        resumeSimulation();
        System.out.println("Симуляция в процессе остановки, миру конец...");
    }





}
