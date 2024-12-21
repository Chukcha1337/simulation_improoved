package supportClasses;

public enum Commands {
    START("g"),
    PAUSE("p"),
    RESUME("r"),
    STOP("s");

    private final String command;

    Commands(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return  command;
    }
}
