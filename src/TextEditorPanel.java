import javax.swing.*;
import java.awt.*;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

// TODO Implement a JTextPane. Used for more advanced and multiple fonts

public class TextEditorPanel extends JPanel {
    protected static JTextPane pane;
    protected static JTabbedPane panel;
    protected static JScrollPane scrollPane;
    protected static boolean isSaved = false;
    protected int number;
    public TextEditorPanel(int number, JTabbedPane panel) {
        this.number = number;
        this.panel = panel;
        IDEWindow.numberOfTextWindows++;

        setLayout(new BorderLayout());

        pane = new JTextPane();
        pane.setPreferredSize(panel.getPreferredSize());


        scrollPane = new JScrollPane();
        scrollPane.getViewport().add(pane);

        setSize(panel.getPreferredSize());
        add(BorderLayout.CENTER, scrollPane);

    }
    public String getText() {
        return pane.getText();
    }

    public Dimension getMinimumSize() {

        return new Dimension(15, 200);
    }

    public Dimension getPreferredSize() {

        int height = panel.getSize().height - 5;
        int width = panel.getSize().width;
        return new Dimension(width, height);
    }
}