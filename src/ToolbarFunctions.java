import javax.swing.*;
import java.awt.*;

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
    public static void save() {
        System.out.println("Saving File");
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
            w.dispose();
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
