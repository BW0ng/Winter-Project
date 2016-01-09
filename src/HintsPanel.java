import javax.swing.*;
import java.awt.*;

/**
 * Brandon Wong
 * Winter-Project
 */
public class HintsPanel extends JPanel {

    public HintsPanel() {
        setLayout(new BorderLayout());

        JLabel hintLabel = new JLabel("Hints");

        add(hintLabel, BorderLayout.CENTER);
    }
}
