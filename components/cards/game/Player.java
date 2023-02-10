package components.cards.game;

import java.awt.*;

import static components.cards.game.GamePanel.GAME_UNITS;
import static components.cards.game.GamePanel.UNIT_SIZE;

/**
 * The model representation of the Player.
 */
public final class Player {

    /**
     * The X coordinates of its body.
     */
    private final int[] X;

    /**
     * The Y coordinates of its body.
     */
    private final int[] Y;

    /**
     * The COLOR of its body.
     */
    private final Color COLOR;

    /**
     * The NAME of the player.
     */
    private final String NAME;

    /**
     * The direction of the player.
     */
    private char direction;

    /**
     *
     * @param name the player's NAME
     * @param COLOR the player's COLOR
     * @param player the number of player (its either 1 or 2)*
     *               Instates the Player with the given parameters,
     *               then based on the player's number places the player on the game's environment and sets its direction.
     */
    public Player(String name, Color COLOR, char player) {
        this.COLOR = COLOR;
        this.NAME = name;

        X = new int[GAME_UNITS];
        Y = new int[GAME_UNITS];

        if (player == '1') {
            direction = 'R';
        } else {
            direction = 'L';
            X[0] = 500 - UNIT_SIZE;
            Y[0] = 500 - UNIT_SIZE;
        }
    }

    /**
     *
     * @param moves number of elapsed moves
     *
     *              Move the player into its direction on the game's environment.
     */
    public void move(int moves) {
        for (int i = moves; i > 0; i--) {
            X[i] = X[i - 1];
            Y[i] = Y[i - 1];
        }

        switch (direction) {
            case 'U' -> Y[0] = Y[0] - UNIT_SIZE;
            case 'D' -> Y[0] = Y[0] + UNIT_SIZE;
            case 'L' -> X[0] = X[0] - UNIT_SIZE;
            case 'R' -> X[0] = X[0] + UNIT_SIZE;
        }
    }

    /**
     *
     * @param direction desired direction of the player (based on keystroke)
     */
    public void setDirection(char direction) {
        if (direction == 'U' && this.direction == 'D') return;
        if (direction == 'D' && this.direction == 'U') return;
        if (direction == 'L' && this.direction == 'R') return;
        if (direction == 'R' && this.direction == 'L') return;

        this.direction = direction;
    }

    /**
     *
     * @return the NAME of the Player
     */
    public String getName() {
        return NAME;
    }

    /**
     *
     * @return the COLOR of the Player
     */

    public Color getColor() {
        return COLOR;
    }

    /**
     *
     * @return X coordinate array
     */
    public int[] getX() {
        return X;
    }

    /**
     *
     * @return Y coordinate array
     */
    public int[] getY() {
        return Y;
    }
}
