import javax.swing.*;
import java.awt.*;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

// TODO Implement a JTextPane. Used for more advanced and multiple fonts

public class TextEditorPanel extends JPanel {
    protected static JTextPane pane;
    protected static JScrollPane scrollPane;
    protected static boolean isSaved = false;
    protected int number;
    public TextEditorPanel(int width, int height, int number) {
        this.number = number;
        IDEWindow.numberOfTextWindows++;

        pane = new JTextPane();
        pane.setPreferredSize(new Dimension(width, height));

        scrollPane = new JScrollPane(pane);

        add(scrollPane, BorderLayout.CENTER);
    }
    public String getText() {
        return pane.getText();
    }
}