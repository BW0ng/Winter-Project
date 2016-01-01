import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

// TODO Implement a JTextPane. Used for more advanced and multiple fonts

public class TextEditorPanel extends JPanel {
    protected static JTextPane pane = null;
    private static JTabbedPane panel;
    protected static JScrollPane scrollPane;
    protected JPanel self = null;
    private boolean isSaved = false;
    private boolean edited = false;
    final protected int number;
    private String textSaved;
    private File file;
    public TextEditorPanel(final int number, final JTabbedPane panel) {
        this.number = number;
        TextEditorPanel.panel = panel;
        file = null;
        IDEWindow.numberOfTextWindows++;

        final ImageIcon icon;
        if(System.getProperty("user.dir").contains("/src")) {
            icon = new ImageIcon("../resources/Super-Mario-Pixel.png");

        } else {
            icon = new ImageIcon("resources/Super-Mario-Pixel.png");

        }

        setLayout(new BorderLayout());

        pane = new JTextPane();
        pane.setPreferredSize(panel.getPreferredSize());
        pane.getDocument().addDocumentListener(new DocumentListener() {
            // TODO Add code to insert an icon when not saved
            @Override
            public void insertUpdate(DocumentEvent e) {
                edited = true;
                if(textSaved!= null && textSaved.equals(pane.getText())) {
                    isSaved = true;
                    int number = IDEWindow.textEditor.getSelectedIndex();
                    IDEWindow.textEditor.remove(number);
                    IDEWindow.textEditor.add(self, number);

                } else {
                    panel.setIconAt(number,icon);
                    panel.repaint();
                    System.out.println("Insert Update");
                    isSaved = false;
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                edited = true;
                if(textSaved!= null && textSaved.equals(pane.getText())) {
                    isSaved = true;

                    int number = IDEWindow.textEditor.getSelectedIndex();
                    IDEWindow.textEditor.remove(number);
                    IDEWindow.textEditor.add(self, number);

                } else {
                    panel.setIconAt(number,icon);
                    System.out.println("Remove Update");
                    isSaved = false;
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                edited = true;
                if(textSaved!= null && textSaved.equals(pane.getText())) {
                    isSaved = true;

                    int number = IDEWindow.textEditor.getSelectedIndex();
                    IDEWindow.textEditor.remove(number);
                    IDEWindow.textEditor.add(self, number);

                } else {
                    panel.setIconAt(number,icon);
                    System.out.println("Changed Update");
                    isSaved = false;
                }
            }
        });

        // AutoIndention
        AbstractDocument doc = (AbstractDocument)(pane.getDocument());
        doc.setDocumentFilter(new TextEditorFilter());

        // TODO Fix Tab Sizes

        scrollPane = new JScrollPane();
        scrollPane.getViewport().add(pane);

        setSize(panel.getPreferredSize());
        add(BorderLayout.CENTER, scrollPane);

        self = this;

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
    public void setSaved(File file) {
        isSaved = true;
        textSaved = pane.getText();
        this.file = file;
    }
    public File getFile() {
        return file;
    }

    public void addText(StringBuilder string) {
        System.out.printf("Tab Number: %d", number);
        pane.setText(string.toString());
    }
    public boolean getSaved() {
        return isSaved;
    }
    public boolean getEdited() {
        return edited;
    }
}