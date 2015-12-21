import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */
public class ToolbarFunctions {

    /**
     * Function for opening a file
     */
    public static void open() {
        System.out.println("Opening File");
    }

    /**
     * Function for saving a file
     */
    public static void save(Object o) {
        // TODO Need to fix Saving function so it saves.
        System.out.println("Saving File with Dialogue ");
        System.out.println(o.getClass().getName());
        if(o instanceof Component) {
            Window w = findWindow((Component) o);

            if(w.getFocusOwner() instanceof JTextPane) {

                JFileChooser fileChooser = new JFileChooser();
                int fileChooserSelection = fileChooser.showSaveDialog(w.getFocusOwner());

                if (fileChooserSelection == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getName();

                    if(!fileName.contains(".")) {
                        fileName += ".txt";
                    }
                    try {
                        FileWriter fileWriter = new FileWriter(fileName);
                        System.out.println((w.getMostRecentFocusOwner().getName()));
                        //fileWriter.write(((TextEditorPanel) (w.getFocusOwner())).getText());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ((IDEWindow) w).isSaved = true;
                }
            }

        }
    }

    /**
     * Close one IDE window. Uses the findWindow method
     * to find current window in focus
     * @param o
     */
    public static void close(Object o) {
        System.out.println("Closing Window");
        if(o instanceof Component) {
            Window w = findWindow((Component) o);
            if(((IDEWindow) w).isSaved) {
                // TODO Need to add it so it exits the JEditorTextPane
                w.dispose();
            } else {
                int reply = JOptionPane.showConfirmDialog(null, "Do you want to save?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if(reply == JOptionPane.YES_OPTION) {
                    save(o);
                } else if(reply == JOptionPane.NO_OPTION) {
                    // TODO Need to add it so it exits the JEditorTextPane
                    w.dispose();
                }
            }
        }
    }

    /**
     * Quit IDE program
     */
    public static void quit() {
        System.exit(0);
    }

    /**
     * Create a new IDE window
     */
    public static void newWindow() {
        IDE.counter++;
        new IDEWindow("IDE " + IDE.counter);
    }

    public static void newTextWindow() {
        // TODO Need to add it so it splitPanel works with JTextEditorPane
        TextEditorPanel temp = new TextEditorPanel(
                IDEWindow.textWidth, IDEWindow.textHeight, IDEWindow.numberOfTextWindows);
        IDEWindow.textEditor.addTab("New File " + IDEWindow.numberOfTextWindows, temp);

    }

    /**
     * The default function if the toolbar name is not recognized
     */
    public static void cannotFind() {
        System.out.println("Unknown Function");
    }

    /**
     * Returns the current window to dispose of it
     * @param c
     * @return The current window that is in focus
     */
    public static Window findWindow(Component c) {
        if(c instanceof Window) {
            return (Window) c;
        } else if(c instanceof JPopupMenu) {
            JPopupMenu pop = (JPopupMenu) c;
            return findWindow(pop.getInvoker());
        } else {
            Container parent = c.getParent();
            return parent == null ? null: findWindow(parent);
        }
    }
}
