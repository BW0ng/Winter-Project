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
    protected static JTextPane pane;
    protected static JTabbedPane panel;
    protected static JScrollPane scrollPane;
    private boolean isSaved = false;
    protected int number;
    private String textSaved;
    private File file;
    public TextEditorPanel(int number, JTabbedPane panel) {
        this.number = number;
        this.panel = panel;
        file = null;
        IDEWindow.numberOfTextWindows++;

        setLayout(new BorderLayout());

        pane = new JTextPane();
        pane.setPreferredSize(panel.getPreferredSize());
        pane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(textSaved!= null && textSaved.equals(pane.getText())) {
                    isSaved = true;
                } else {
                    isSaved = false;
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(textSaved!= null && textSaved.equals(pane.getText())) {
                    isSaved = true;
                } else {
                    isSaved = false;
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(textSaved!= null && textSaved.equals(pane.getText())) {
                    isSaved = true;
                } else {
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
        pane.setText(string.toString());
    }
    public boolean getSaved() {
        return isSaved;
    }

}