package components.cards;

import javax.swing.*;
import java.awt.*;

/**
 * The superclass of OptionPanel, GamePanel and ScorePanel.
 */
public abstract class AbstractPanel extends JPanel {

    /**
     * The panel's Screen width.
     */
    protected static final int SCREEN_WIDTH = 500;

    /**
     * The panel's Screen height.
     */
    protected static final int SCREEN_HEIGHT = 500;

    /**
     * The Font used in the panels.
     */
    protected final Font FONT = new Font("Ink Free", Font.BOLD, 20);


    /**
     * Instantiates a new Abstract panel.
     */
    protected AbstractPanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setFocusable(true);
        setLayout(new BorderLayout());
        setBackground(Color.black);
    }

}
