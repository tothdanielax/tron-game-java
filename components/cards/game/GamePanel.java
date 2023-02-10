package components.cards.game;

import components.cards.AbstractPanel;
import db.ScoreboardDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

/**
 * The type Game panel.
 */
public final class GamePanel extends AbstractPanel implements ActionListener {

    /**
     * The constant INSTANCE, to ensure the Singleton behavior (only one INSTANCE can exist).
     */
    public static final GamePanel INSTANCE = new GamePanel();

    /**
     * The size of the players body.
     */
    public static final int UNIT_SIZE = 5;

    /**
     * The units of the game's environment.
     */
    public static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

    /**
     * The Delay between the players moves.
     */
    private final int DELAY = 60;

    /**
     * "Back to menu" button reference.
     */
    private final JButton OPTION = new JButton("Back to menu");

    /**
     * The object of Player1.
     */
    private Player player1;

    /**
     * The object of Player2.
     */
    private Player player2;

    /**
     * If the game is (still) running the value is true.
     */
    private boolean running = false;

    /**
     * The shown timer's object.
     */
    private Timer timer;


    /**
     * The number of the elapsed moves of the players (both players move in the same time).
     */
    private int moves = 1;

    /**
     * The name of the game winner's.
     */
    private String winnersName;

    /**
     * Private constructor to ensure its Singleton behavior.
     * Adds key listener, what is in relationship with the player's movement.
     * Adds listeners to the JButtons, what if clicked, the parent's layout (MainFrame -> CardLayout) will change the shown card based on the clicked JButton.
     */
    private GamePanel() {
        super();

        addKeyListener(new GameKeyAdapter());

        OPTION.addActionListener(l -> {
            JPanel parent = (JPanel) getParent();
            CardLayout layout = (CardLayout) parent.getLayout();
            layout.show(parent, "OPTION_PANEL");

            setVisible(false);
            repaint();
            revalidate();
        });

        add(OPTION, BorderLayout.SOUTH);
        OPTION.setVisible(false);
    }

    /**
     * Sets players attributes.
     */
    private void setPlayers() {
        winnersName = null;

        String playerName1 = JOptionPane.showInputDialog("Player 1 name: ");
        Color playerColor1 = JColorChooser.showDialog(null, "Choose a color for " + playerName1, Color.RED);

        String playerName2 = JOptionPane.showInputDialog("Player 2 name: ");
        Color playerColor2 = JColorChooser.showDialog(null, "Choose a color for " + playerName2, Color.BLUE);

        player1 = new Player(playerName1, playerColor1, '1');
        player2 = new Player(playerName2, playerColor2, '2');
    }

    /**
     * Play game, by setting up players attributes and starting a new timer.
     */
    public void playGame() {
        setPlayers();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     * @param g the <code>Graphics</code> object to protect
     *          Paints the game's environment.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * @param g Graphics object of game's environment.
     *          Paints the players individually if the game is still running, else jumps to gameOver(g).
     */
    private void draw(Graphics g) {
        if (running) {
            drawPlayer1(g);
            drawPlayer2(g);
            OPTION.setVisible(false);
        } else if(player1 != null){
            gameOver(g);
        }
    }

    /**
     * @param g Graphics object of game's environment.
     *          Draws player1.
     */
    private void drawPlayer1(Graphics g) {
        int[] x = player1.getX();
        int[] y = player1.getY();

        for (int i = 0; i < moves; i++) {
            g.setColor(player1.getColor());
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
    }

    /**
     * @param g Graphics object of game's environment.
     *          Draws player2.
     */
    private void drawPlayer2(Graphics g) {
        int[] x = player2.getX();
        int[] y = player2.getY();

        for (int i = 0; i < moves; i++) {
            g.setColor(player2.getColor());
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
    }

    /**
     * @param g Graphics object of game's environment.
     *          Draws Game Over interface with the winner's name,
     *          and sets the OPTION button visible.
     */
    private void gameOver(Graphics g) {

        g.setColor(Color.red);
        g.setFont(FONT);
        g.drawString("Game Over.", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);

        g.setColor(Color.green);
        g.drawString("Winner is " + winnersName, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 + g.getFontMetrics().getHeight());


        if(moves != 1) {
            ScoreboardDB.incrementScoreByName(winnersName);
            OPTION.setVisible(true);
        }

        moves = 1;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            player1.move(moves);
            player2.move(moves);

            moves++;

            checkGameOver();
        }

        repaint();
    }

    /**
     * Checks if the game is over.
     */
    private void checkGameOver() {
        int[] x1 = player1.getX();
        int[] y1 = player1.getY();
        int[] x2 = player2.getX();
        int[] y2 = player2.getY();

        for (int i = moves; i > 0; i--) {
            checkSelfCrash(x1[0], y1[0], x1[i], y1[i], 1);
            checkSelfCrash(x2[0], y2[0], x2[i], y2[i], 2);

            checkPlayersCrash(x1[0], y1[0], x2[i], y2[i], 1);
            checkPlayersCrash(x2[0], y2[0], x2[i], y2[i], 2);
        }

        if (running) {
            checkOutOfBounds();
        } else {
            timer.stop();
        }
    }

    /**
     * @param playerHeadX       the X coordinate of the player's head/beginning
     * @param playerHeadY       the Y coordinate of the player's head/beginning
     * @param otherPlayersPrevX the X coordinate of the other player's previous (or current) body
     * @param otherPlayersPrevY the Y coordinate of the other player's previous (or current) body
     * @param playerNumber      the number of the player (it's either 1 or 2)
     *                          <p>
     *                          Checks if the players crashed into the other player or not.
     */
    private void checkPlayersCrash(int playerHeadX, int playerHeadY, int otherPlayersPrevX, int otherPlayersPrevY, int playerNumber) {

        if (playerHeadX == otherPlayersPrevX && playerHeadY == otherPlayersPrevY) {
            running = false;

            if (playerNumber == 1) {
                System.out.println("Player 1 crashed into Player 2");
                winnersName = player2.getName();
            } else {
                System.out.println("Player 2 crashed into Player 1");
                winnersName = player1.getName();
            }
        }
    }

    /**
     * @param headX        the X coordinate of the player's head/beginning
     * @param headY        the Y coordinate of the player's head/beginning
     * @param prevX        one of the X coordinate of the player's body
     * @param prevY        one of the Y coordinate of the player's body
     * @param playerNumber the number of the player (it's either 1 or 2)
     *                     <p>
     *                     Checks if the player crashed into itself.
     */
    private void checkSelfCrash(int headX, int headY, int prevX, int prevY, int playerNumber) {
        if (headX == prevX && headY == prevY) {
            running = false;

            if (playerNumber == 1) {
                System.out.println("Player 1 crashed into itself");
                winnersName = player2.getName();
            } else {
                System.out.println("Player 2 crashed into itself");
                winnersName = player1.getName();
            }

        }
    }

    /**
     * Checks if either player is out of the game's environmental bounds.
     */
    private void checkOutOfBounds() {

        int[] x1 = player1.getX();
        int[] y1 = player1.getY();
        int[] x2 = player2.getX();
        int[] y2 = player2.getY();

        if (x1[0] < 0) {
            running = false;
            winnersName = player2.getName();

        } else if (x2[0] < 0) {
            running = false;
            winnersName = player1.getName();
        }

        if (x1[0] > SCREEN_WIDTH) {
            running = false;
            winnersName = player2.getName();
        } else if (x2[0] > SCREEN_WIDTH) {
            running = false;
            winnersName = player1.getName();

        }

        if (y1[0] < 0) {
            running = false;
            winnersName = player2.getName();
        } else if (y2[0] < 0) {
            running = false;
            winnersName = player1.getName();
        }

        if (y1[0] > SCREEN_HEIGHT) {
            winnersName = player2.getName();
        } else if (y2[0] > SCREEN_HEIGHT) {
            running = false;
            winnersName = player1.getName();
        }
    }

    /**
     * The class for listening for keystrokes.
     */
    private class GameKeyAdapter extends KeyAdapter {

        /**
         * @param e the event to be processed (the key pressed)
         *          <p>
         *          Decides based on the pressed key to which player pressed it, then sets its direction.
         */

        @Override
        public void keyPressed(KeyEvent e) {
            if (running) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> player1.setDirection('L');
                    case KeyEvent.VK_RIGHT -> player1.setDirection('R');
                    case KeyEvent.VK_UP -> player1.setDirection('U');
                    case KeyEvent.VK_DOWN -> player1.setDirection('D');
                    case KeyEvent.VK_A -> player2.setDirection('L');
                    case KeyEvent.VK_D -> player2.setDirection('R');
                    case KeyEvent.VK_W -> player2.setDirection('U');
                    case KeyEvent.VK_S -> player2.setDirection('D');
                    default -> {
                    }
                }
            }
        }
    }

}
