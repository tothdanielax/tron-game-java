package db;

public class PlayerData {
    private final String NAME;

    private final int SCORE;
    public PlayerData(String name, int score) {
        this.NAME = name;
        this.SCORE = score;
    }

    public String getName() {
        return NAME;
    }

    public int getScore() {
        return SCORE;
    }
}
