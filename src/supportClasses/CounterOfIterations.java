package supportClasses;

public class CounterOfIterations {
    private int iterations;

    public CounterOfIterations() {
        iterations = 0;
    }

    public String getCounter() {
        return "It's " + iterations + " iteration";
    }

    public void increaseCounter() {
        iterations++;
    }
}
