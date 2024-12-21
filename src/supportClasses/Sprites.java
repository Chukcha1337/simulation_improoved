package supportClasses;

public enum Sprites {
    GRASS("\uD83C\uDF3E"),
    ROCK("\uD83E\uDEA8"),
    TREE("\uD83C\uDF33"),
    PREDATOR("\uD83D\uDC3A"),
    HERBIVORE("\uD83D\uDC07"),
    EMPTY_PLACE(" \uD83D\uDFE9 ");

    private final String sprite;

    Sprites(String sprite) {
        this.sprite = sprite;
    }

    public String getSprite() {
        return sprite;
    }
}
