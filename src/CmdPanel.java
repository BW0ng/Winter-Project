import javax.swing.*;
import java.awt.*;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

// TODO Implement a JTextPane. Used for more advanced and multiple fonts

public class CmdPanel extends JPanel {

    JPanel panel;

    public CmdPanel(JPanel panel) {

        this.panel = panel;
        setBackground(Color.red);
    }

    public Dimension getMinimumSize() {

        return new Dimension(15, 30);
    }

    public Dimension getPreferredSize() {

        int height = panel.getSize().height;
        int width = panel.getSize().width;
        return new Dimension(width, height);
    }
}