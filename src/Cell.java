import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Cell {
    private JButton button;
    private boolean hidden = true;
    private boolean bomb = false;
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

    public JButton getButton() {
        return this.button;
    }

    /* Functions */
    public void updateButton() {

        ImageIcon icon = new ImageIcon("assets/covered.png");

        if (this.bomb) {
            icon = new ImageIcon("assets/bomb.png");
        } else if (!this.bomb && !this.hidden) {
            icon = new ImageIcon("assets/" + Integer.toString(this.getBombNumber()) + ".png");
        }

        this.button.setIcon(new ImageIcon(icon.getImage().getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
    }
}
