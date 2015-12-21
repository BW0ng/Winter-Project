import javax.swing.*;
import java.awt.*;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

// TODO Implement a JTextPane. Used for more advanced and multiple fonts

public class TextEditorPanel extends JPanel {
    protected JTextPane pane;
    protected JScrollPane scrollPane;
    protected boolean isSaved = false;
    public TextEditorPanel(int width, int height) {
        pane = new JTextPane();
        pane.setPreferredSize(new Dimension(width, height));

        scrollPane = new JScrollPane(pane);

        add(scrollPane, BorderLayout.CENTER);
    }
}