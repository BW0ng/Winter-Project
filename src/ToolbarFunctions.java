import javax.swing.*;
import java.awt.*;
import java.io.*;

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

        int tabNumber = IDEWindow.textEditor.getSelectedIndex();
        TextEditorPanel panel = (TextEditorPanel)IDEWindow.textEditor.getComponentAt(tabNumber);


        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        int option = fileChooser.showOpenDialog(panel);

        if(option == JFileChooser.APPROVE_OPTION) {
            try {
                FileReader reader;
                File file = fileChooser.getSelectedFile();
                reader = new FileReader(file);

                BufferedReader in = new BufferedReader(reader);
                StringBuilder stringBuffer = new StringBuilder();
                String string;

                while((string = in.readLine()) != null) {
                    stringBuffer.append(string).append("\n");
                }
                panel.addText(stringBuffer);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function for saving a file
     */
    public static void save(Object o) {

        // TODO Need to fix Saving function so it saves.
        /*
        if(o instanceof Component) {

            Window w = findWindow((Component) o);

            if(w.getFocusOwner() instanceof JTextPane) {
                //Not Getting Here
                System.out.println("Reached loop");

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
        */
        int tabNumber = IDEWindow.textEditor.getSelectedIndex();
        TextEditorPanel panel = (TextEditorPanel)IDEWindow.textEditor.getComponentAt(tabNumber);

        if(panel.getFile() == null) {
            saveAs();
        } else {
            try {
                File file = panel.getFile();
                PrintWriter out = new PrintWriter(file);
                out.write(panel.getText());
                out.close();
                IDEWindow.textEditor.setTitleAt(tabNumber, file.getName());
                panel.setSaved(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveAs() {
        int tabNumber = IDEWindow.textEditor.getSelectedIndex();
        TextEditorPanel panel = (TextEditorPanel)IDEWindow.textEditor.getComponentAt(tabNumber);

        JFileChooser fileChooser = new JFileChooser();
        if(IDEWindow.textEditor.getTitleAt(tabNumber).contains("New File")) {
            File file = new File(System.getProperty("user.dir") + "/Untitled.txt");
            fileChooser.setSelectedFile(file);
        } else {
            fileChooser.setSelectedFile(panel.getFile());
        }
        int result = fileChooser.showSaveDialog(panel);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                PrintWriter out = new PrintWriter(file);
                out.write(panel.getText());
                out.close();
                IDEWindow.textEditor.setTitleAt(tabNumber, file.getName());
                panel.setSaved(file);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Close one IDE window. Uses the findWindow method
     * to find current window in focus
     * @param o The ActionEvent passed in
     */
    public static void close(Object o) {
        System.out.println("Closing Window");

        if(IDEWindow.textEditor.getTabCount() <= 0) {
            if(o instanceof Component) {
                Window w = findWindow((Component) o);
                assert w != null;
                w.dispose();
            }
        } else {

            int tabNumber = IDEWindow.textEditor.getSelectedIndex();
            TextEditorPanel panel = (TextEditorPanel) IDEWindow.textEditor.getComponentAt(tabNumber);

            if(!panel.getEdited()) {
                IDEWindow.textEditor.remove(tabNumber);
            } else if(panel.getSaved()) {
                IDEWindow.textEditor.remove(tabNumber);
            } else {
                int reply = JOptionPane.showConfirmDialog(null, "Do you want to save?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if(reply == JOptionPane.YES_OPTION) {
                    save(o);
                } else if(reply == JOptionPane.NO_OPTION) {
                    IDEWindow.textEditor.remove(tabNumber);
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

    public static void newTextFile() {
        // TODO Need to add it so it splitPanel works with JTextEditorPane
        final ImageIcon icon = new ImageIcon("../resources/Super-Mario-Pixel.png");
        TextEditorPanel temp = new TextEditorPanel(IDEWindow.numberOfTextWindows,
                    IDEWindow.textEditor);
        IDEWindow.textEditor.addTab("New File " + IDEWindow.numberOfTextWindows, icon, temp);
    }

    /**
     * The default function if the toolbar name is not recognized
     */
    public static void cannotFind() {
        System.out.println("Unknown Function");
    }

    /**
     * Returns the current window to dispose of it
     * @param c The ActionEvent passed in from the Listener
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
