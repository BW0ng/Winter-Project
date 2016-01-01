import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.io.*;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

// TODO Implement a JTextPane. Used for more advanced and multiple fonts

public class TextEditorPanel extends JPanel {

    protected int number = 0;
    protected JTextPane pane = null;
    protected JScrollPane scrollPane;
    protected UndoManager undoManager;
    protected JPanel self = null;
    private JTabbedPane panel = null;
    private boolean isSaved = false;
    private boolean edited = false;
    private String textSaved;
    private File file;
    private IDEWindow ideWindow;

    public TextEditorPanel(final int number, final JTabbedPane panel, final IDEWindow ideWindow) {

        this.ideWindow = ideWindow;
        this.number = number;
        this.panel = panel;
        file = null;
        ideWindow.numberOfTextWindows++;
        undoManager = new UndoManager();

        final ImageIcon icon;
        if (System.getProperty("user.dir").contains("/src")) {
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
                if (textSaved != null && textSaved.equals(pane.getText())) {
                    isSaved = true;

                    removeIcon();

                } else {
                    panel.setIconAt(number, icon);
                    panel.repaint();
                    System.out.println("Insert Update");
                    isSaved = false;
                }
                System.out.printf("Tab Number in Insert: %d%n%n", number);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                edited = true;
                if (textSaved != null && textSaved.equals(pane.getText())) {
                    isSaved = true;

                    removeIcon();

                } else {
                    panel.setIconAt(number, icon);
                    System.out.println("Remove Update");
                    isSaved = false;
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                edited = true;
                if (textSaved != null && textSaved.equals(pane.getText())) {
                    isSaved = true;

                    removeIcon();

                } else {
                    panel.setIconAt(number, icon);
                    System.out.println("Changed Update");
                    isSaved = false;
                }
            }
        });
        pane.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {

                undoManager.addEdit(e.getEdit());
            }
        });

        // AutoIndention
        AbstractDocument doc = (AbstractDocument) (pane.getDocument());
        doc.setDocumentFilter(new TextEditorFilter());

        // TODO Fix Tab Sizes

        scrollPane = new JScrollPane();
        scrollPane.getViewport().add(pane);

        setSize(panel.getPreferredSize());
        add(BorderLayout.CENTER, scrollPane);

        self = this;

    }

    public TextEditorPanel(File file, IDEWindow ideWindow) {
        TextEditorPanel panel = new TextEditorPanel(ideWindow.numberOfTextWindows, ideWindow.textEditor, ideWindow);
        panel.open(file);
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

    public File getFile() {

        return file;
    }

    public void addText(StringBuilder string) {

        System.out.printf("Tab Number: %d%n%n", number);
        pane.setText(string.toString());
    }

    public boolean getSaved() {

        return isSaved;
    }

    public void setSaved(File file) {

        isSaved = true;
        textSaved = pane.getText();
        this.file = file;
        removeIcon();
    }

    public boolean getEdited() {

        return edited;
    }
    public void removeIcon() {
        int number = ideWindow.textEditor.getSelectedIndex();
        TextEditorPanel panel = (TextEditorPanel) ideWindow.textEditor.getComponentAt(number);
        ideWindow.textEditor.remove(number);
        ideWindow.textEditor.add(panel.self, number);
        ideWindow.textEditor.setTitleAt(number, panel.getFile().getName());
    }
    public void open(File file) {

        System.out.println("Opening File");
            try {
                FileReader reader;
                reader = new FileReader(file);

                BufferedReader in = new BufferedReader(reader);
                StringBuilder stringBuffer = new StringBuilder();
                String string;

                while ((string = in.readLine()) != null) {
                    stringBuffer.append(string).append("\n");
                }
                addText(stringBuffer);
                setSaved(file);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}