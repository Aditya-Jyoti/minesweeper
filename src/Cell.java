import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Cell {
    private JButton button;
    private boolean hidden = true;
    private boolean bomb = false;
    private boolean flagged = false;
    private int bombNumber = 0;
    private Map<String, Integer> position;

    public Cell(int xIdx, int yIdx, JButton button) {
        this.button = button;
        this.position = new HashMap<String, Integer>() {
            {
                put("xIdx", xIdx);
                put("yIdx", yIdx);
            }
        };
    }

    /* Setter */

    public void setHidden(boolean isHidden) {
        this.hidden = isHidden;
    }

    public void setBombNumber(int newBombNumber) {
        this.bombNumber = newBombNumber;
    }

    public void setBomb(boolean isBomb) {
        this.bomb = isBomb;
    }

    public void setFlagged(boolean isFlagged) {
        this.flagged = isFlagged;
    }

    /* Getter */

    public Map<String, Integer> getPosition() {
        return this.position;
    }

    public int getBombNumber() {
        return this.bombNumber;
    }

    public boolean getBomb() {
        return this.bomb;
    }

    public boolean getHidden() {
        return this.hidden;
    }

    public boolean getFlagged() {
        return this.flagged;
    }

    public JButton getButton() {
        return this.button;
    }

    /* Functions */
    public void updateButton() {

        ImageIcon icon = new ImageIcon("assets/covered.png");

        if (!this.hidden) {
            if (this.bomb) {
                icon = new ImageIcon("assets/bomb.png");
            } else {
                icon = new ImageIcon("assets/" + Integer.toString(this.getBombNumber()) + ".png");
            }
        }
        if (this.flagged) {
            icon = new ImageIcon("assets/flagged.png");
        }

        this.button.setIcon(new ImageIcon(icon.getImage().getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
    }

    public void showBomb() {
        ImageIcon icon = new ImageIcon("assets/bomb.png");
        this.button.setIcon(new ImageIcon(icon.getImage().getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
    }
}
