package components.cards.scoreboard;

import components.cards.AbstractPanel;
import db.PlayerData;
import db.ScoreboardDB;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The type Score panel.
 */
public final class ScorePanel extends AbstractPanel {

    /**
     * The constant INSTANCE, to ensure the Singleton behavior (only one INSTANCE can exist).
     */
    public static final ScorePanel INSTANCE = new ScorePanel();

    /**
     * "Back to MENU" button reference.
     */
    private final JButton MENU = new JButton("Back to MENU");

    /**
     * Private constructor to ensure its Singleton behavior.
     * Adds listeners to the JButtons, what if clicked, the parent's layout (MainFrame -> CardLayout) will change the shown card based on the clicked JButton.
     */
    private ScorePanel() {
        super();

        MENU.addActionListener(l -> {
            JPanel parent = (JPanel) getParent();
            CardLayout layout = (CardLayout) parent.getLayout();
            layout.show(parent, "OPTION_PANEL");
        });

        add(MENU, BorderLayout.SOUTH);
    }

    /**
     * Shows last ten scores from the connected database from top left to bottom left by Descending Order.
     */
    public void showLastTenScores() {
        ArrayList<PlayerData> playerDataList = new ArrayList<>(ScoreboardDB.getLastTenScores());

        Graphics g = this.getGraphics();
        FontMetrics fm = g.getFontMetrics();

        g.setColor(Color.red);
        g.setFont(FONT);

        int allHeight = 50;

        for(PlayerData playerData : playerDataList) {
            String name = playerData.getName();
            int score = playerData.getScore();

            g.drawString("Name: " + name + ", Score: " + score, 0, allHeight);

            allHeight += fm.getHeight() + 10;
        }

    }

}
