import javax.swing.*;
import java.awt.*;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

// TODO Implement a JTextPane. Used for more advanced and multiple fonts

public class CmdPanel extends JPanel {

    JPanel panel;

    public CmdPanel(JPanel panel, IDEWindow ideWindow) {
        this.panel = panel;

        AbstractTerminalFrame abstractTerminalFrame = new AbstractTerminalFrame(panel, ideWindow);

        add(abstractTerminalFrame.frame);
        System.out.printf("Height: %d, Width: %d in cmdPanel%n%n",
                (int)getPreferredSize().getHeight(), (int)getPreferredSize().getWidth());
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