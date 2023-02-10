package components.cards.option;

import components.cards.AbstractPanel;

import javax.swing.*;
import java.awt.*;

/**
 * The type Option panel.
 */
public final class OptionPanel extends AbstractPanel {

    /**
     * The constant INSTANCE, to ensure the Singleton behavior (only one INSTANCE can exist).
     */
    public static final OptionPanel INSTANCE = new OptionPanel();

    /**
     * "Play" button reference.
     */
    private final JButton PLAY_GAME = new JButton("Play");

    /**
     * "Scoreboard" button reference.
     */
    private final JButton SCOREBOARD = new JButton("Scoreboard");

    /**
     * Reference to the panel, what holds PLAY_GAME and scoreBoard JButtons.
     */
    private final JPanel SUBPANEL = new JPanel(new GridLayout(1, 2));

    /**
     * Reference to the JLabel what contains the Wallpaper.
     */
    private final JLabel IMG_LABEL = new JLabel();

    /**
     * Private constructor to ensure its Singleton behavior.
     * Adds listeners to the JButtons, what if clicked, the parent's layout (MainFrame -> CardLayout) will change the shown card based on the clicked JButton.
     */
    private OptionPanel() {
        super();

        PLAY_GAME.addActionListener(l -> {
            JPanel parent = (JPanel) getParent();
            CardLayout layout = (CardLayout) parent.getLayout();
            layout.show(parent, "GAME_PANEL");
        });

        SCOREBOARD.addActionListener(l -> {
            JPanel parent = (JPanel) getParent();
            CardLayout layout = (CardLayout) parent.getLayout();
            layout.show(parent, "SCORE_PANEL");
        });

        SUBPANEL.setPreferredSize(new Dimension(SCREEN_WIDTH, 100));
        SUBPANEL.add(PLAY_GAME);
        SUBPANEL.add(SCOREBOARD);

        IMG_LABEL.setIcon(new ImageIcon("src/data/tron_wallpaper.jpg"));
        IMG_LABEL.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        add(SUBPANEL, BorderLayout.SOUTH);
        add(IMG_LABEL, BorderLayout.NORTH);
    }
}
