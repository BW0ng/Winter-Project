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
    protected TabbedPaneTab tabbedPaneTab;
    protected UndoManager undoManager;
    protected JPanel self = null;
    private JTabbedPane panel = null;
    private boolean isSaved = false;
    private boolean edited = false;
    private boolean openedFile = false;
    private String textSaved;
    private File file;
    private IDEWindow ideWindow;

    public TextEditorPanel(final int number, final JTabbedPane panel,
                           final IDEWindow ideWindow, TabbedPaneTab tabbedPaneTab) {

        this.tabbedPaneTab = tabbedPaneTab;
        this.ideWindow = ideWindow;
        this.number = number;
        this.panel = panel;
        file = null;
        ideWindow.numberOfTextWindows++;
        undoManager = new UndoManager();

        setLayout(new BorderLayout());

        pane = new JTextPane();
        pane.setPreferredSize(panel.getPreferredSize());
        pane.getDocument().addDocumentListener(new DocumentListener() {
            // TODO Add code to insert an icon when not saved
            @Override
            public void insertUpdate(DocumentEvent e) {
                edited = true;
                TextEditorPanel panel = (TextEditorPanel)ideWindow.textEditor
                        .getComponentAt(ideWindow.textEditor.getSelectedIndex());

                if (openedFile || (textSaved == null && pane.getText().equals(""))
                        ||(textSaved != null && textSaved.equals(pane.getText()))) {
                    isSaved = true;
                    openedFile = false;

                    if(panel.tabbedPaneTab.hasIcon()) {
                        panel.tabbedPaneTab.removeIcon();
                    }

                } else {
                    panel.tabbedPaneTab.addIcon();
                    isSaved = false;
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                edited = true;
                TextEditorPanel panel = (TextEditorPanel)ideWindow.textEditor
                        .getComponentAt(ideWindow.textEditor.getSelectedIndex());

                if (openedFile || (textSaved == null && pane.getText().equals(""))
                        ||(textSaved != null && textSaved.equals(pane.getText()))) {
                    isSaved = true;
                    openedFile = false;

                    if(panel.tabbedPaneTab.hasIcon()) {
                        panel.tabbedPaneTab.removeIcon();
                    }

                } else {
                    panel.tabbedPaneTab.addIcon();
                    isSaved = false;
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                edited = true;
                TextEditorPanel panel = (TextEditorPanel)ideWindow.textEditor
                        .getComponentAt(ideWindow.textEditor.getSelectedIndex());

                if (openedFile || (textSaved == null && pane.getText().equals(""))
                        ||(textSaved != null && textSaved.equals(pane.getText()))) {
                    isSaved = true;
                    openedFile = false;

                    if(panel.tabbedPaneTab.hasIcon()) {
                        panel.tabbedPaneTab.removeIcon();
                    }

                } else {
                    panel.tabbedPaneTab.addIcon();
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
        pane.setText(string.toString());
    }

    public boolean getSaved() {

        return isSaved;
    }

    public void setSaved(File file) {
        isSaved = true;
        textSaved = pane.getText();
        this.file = file;
        /*
        if(ideWindow.tabbedPaneTab.hasIcon()) {
            ideWindow.tabbedPaneTab.removeIcon();
        }
        */
    }

    public boolean getEdited() {

        return edited;
    }
    public void setOpenedFile() {
        openedFile = true;
    }
}