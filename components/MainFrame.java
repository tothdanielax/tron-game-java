package components;

import components.cards.game.GamePanel;
import components.cards.option.OptionPanel;
import components.cards.scoreboard.ScorePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * The type Main frame.
 */
public final class MainFrame extends JFrame {

    /**
     * The only instance of OptionPanel.
     */
    private final OptionPanel OPTION_PANEL = OptionPanel.INSTANCE;

    /**
     * The only instance of ScorePanel.
     */
    private final ScorePanel SCORE_PANEL = ScorePanel.INSTANCE;

    /**
     * The only instance of GamePanel.
     */
    private final GamePanel GAME_PANEL = GamePanel.INSTANCE;

    /**
     * Instantiates a new Main frame.
     */
    public MainFrame() {
        setUpLayout();
        setTitle("Tron");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/data/tron_logo.jpg").getImage());
        setVisible(true);
    }

    /**
     * Set up the frame's Card layout and packs the cards inside it.
     */
    private void setUpLayout() {
        setLayout(new CardLayout());

        GAME_PANEL.addComponentListener(new ComponentListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                GAME_PANEL.playGame();
            }

            @Override
            public void componentResized(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });


        SCORE_PANEL.addComponentListener(new ComponentListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                SCORE_PANEL.showLastTenScores();
            }

            @Override
            public void componentResized(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });

        add(OPTION_PANEL, "OPTION_PANEL");
        add(GAME_PANEL, "GAME_PANEL");
        add(SCORE_PANEL, "SCORE_PANEL");

        CardLayout layout = (CardLayout) getContentPane().getLayout();
        layout.show(this.getContentPane(), "OPTION_PANEL");

    }

}
