package Client;

public enum Commands {
    // help the server side to recognize the client's event
    PRESS_MOUSE(-1),
    RELEASE_MOUSE(-2),
    PRESS_KEY(-3),
    RELEASE_KEY(-4),
    MOVE_MOUSE(-5),
    SCROLL_MOUSE(-6);

    private int abbrev;
    Commands(int abbrev) {
        this.abbrev = abbrev;
    }

    public int getAbbrev() {
        return abbrev;
    }
}
