/*
int currentTab = IDEWindow.textEditor.getSelectedIndex();
TextEditorPanel temp = (TextEditorPanel) IDEWindow.textEditor.getComponentAt(currentTab);
String text = temp.getText();
int counter=1;
for(int i=0;i<text.length();i++) {
    if(text.charAt(i)==('\n')) {
        counter++;
    }
}
System.out.println(counter);
*/


import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */
public class ToolbarFunctions {

    public static void open(IDEWindow ideWindow) {

        System.out.println("Opening File");

        int tabNumber = ideWindow.textEditor.getSelectedIndex();
        TextEditorPanel panel = (TextEditorPanel) ideWindow.textEditor.getComponentAt(tabNumber);
        panel.setOpenedFile();

        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        int option = fileChooser.showOpenDialog(panel);

        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                FileReader reader;
                File file = fileChooser.getSelectedFile();
                reader = new FileReader(file);

                BufferedReader in = new BufferedReader(reader);
                StringBuilder stringBuffer = new StringBuilder();
                String string;

                while ((string = in.readLine()) != null) {
                    stringBuffer.append(string).append("\n");
                }
                panel.addText(stringBuffer);
                panel.setSaved(file);

                panel.tabbedPaneTab.setTitle(file.getName());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void open(File file, IDEWindow ideWindow) {

        System.out.println("Opening File from JTree");
        TabbedPaneTab tabbedPaneTab = new TabbedPaneTab(file.getName(), ideWindow.numberForTextWindows++, ideWindow);
        TextEditorPanel temp = new TextEditorPanel(ideWindow.numberOfTextWindows,
                ideWindow.textEditor, ideWindow, tabbedPaneTab);
        ideWindow.textEditor.add(file.getName(), temp);
        ideWindow.textEditor.setTabComponentAt(ideWindow.numberOfTextWindows-1, tabbedPaneTab);
        temp.setOpenedFile();

        try {
            FileReader reader;
            reader = new FileReader(file);

            BufferedReader in = new BufferedReader(reader);
            StringBuilder stringBuffer = new StringBuilder();
            String string;

            while ((string = in.readLine()) != null) {
                stringBuffer.append(string).append("\n");
            }
            temp.addText(stringBuffer);
            temp.setSaved(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(Object o, IDEWindow ideWindow) {

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
        int tabNumber = ideWindow.textEditor.getSelectedIndex();
        TextEditorPanel panel = (TextEditorPanel) ideWindow.textEditor.getComponentAt(tabNumber);

        if (panel.getFile() == null) {
            saveAs(ideWindow);
        } else {
            try {
                File file = panel.getFile();
                PrintWriter out = new PrintWriter(file);
                out.write(panel.getText());
                out.close();
                ideWindow.textEditor.setTitleAt(tabNumber, file.getName());
                panel.setSaved(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveAs(IDEWindow ideWindow) {

        int tabNumber = ideWindow.textEditor.getSelectedIndex();
        TextEditorPanel panel = (TextEditorPanel) ideWindow.textEditor.getComponentAt(tabNumber);

        JFileChooser fileChooser = new JFileChooser();
        if (ideWindow.textEditor.getTitleAt(tabNumber).contains("New File")) {
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
                ideWindow.textEditor.setTitleAt(tabNumber, file.getName());
                panel.setSaved(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Object o, IDEWindow ideWindow) {

        System.out.println("Closing Window");

        if (ideWindow.textEditor.getTabCount() <= 0) {
            ideWindow.dispose();
        } else {

            int tabNumber = ideWindow.textEditor.getSelectedIndex();
            TextEditorPanel panel = (TextEditorPanel) ideWindow.textEditor.getComponentAt(tabNumber);

            if (!panel.getEdited()) {
                ideWindow.textEditor.remove(tabNumber);
                ideWindow.numberOfTextWindows--;
            } else if (panel.getSaved()) {
                ideWindow.textEditor.remove(tabNumber);
                ideWindow.numberOfTextWindows--;
            } else {
                int reply = JOptionPane.showConfirmDialog(null, "Do you want to save?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (reply == JOptionPane.YES_OPTION) {
                    save(o, ideWindow);
                } else if (reply == JOptionPane.NO_OPTION) {
                    ideWindow.textEditor.remove(tabNumber);
                    ideWindow.numberOfTextWindows--;
                }
            }
        }
    }

    public static void quit(IDEWindow ideWindow) {

        System.exit(0);
    }

    public static void newWindow(IDEWindow ideWindow) {

        IDE.counter++;
        new IDEWindow("IDE " + IDE.counter);
    }

    public static void newTextFile(IDEWindow ideWindow) {
        // TODO Need to add it so it splitPanel works with JTextEditorPane
        TabbedPaneTab tabbedPaneTab;
        if(ideWindow.numberOfTextWindows == 0) {
            tabbedPaneTab = new TabbedPaneTab("New File ", ideWindow.numberForTextWindows++, ideWindow);
        } else {
            tabbedPaneTab = new TabbedPaneTab("New File ", ideWindow.numberForTextWindows++ , ideWindow);
        }
        TextEditorPanel temp = new TextEditorPanel(ideWindow.numberOfTextWindows,
                ideWindow.textEditor, ideWindow, tabbedPaneTab );
        ideWindow.textEditor.add("New File " + ideWindow.numberOfTextWindows, temp);
        ideWindow.textEditor.setTabComponentAt(ideWindow.numberOfTextWindows-1,tabbedPaneTab);
    }

    public static void undo(IDEWindow ideWindow) {

        int number = ideWindow.textEditor.getSelectedIndex();
        TextEditorPanel panel = (TextEditorPanel) ideWindow.textEditor.getComponentAt(number);

        if (panel.undoManager.canUndo()) {
            panel.undoManager.undo();
        }
    }

    public static void redo(IDEWindow ideWindow) {

        int number = ideWindow.textEditor.getSelectedIndex();
        TextEditorPanel panel = (TextEditorPanel) ideWindow.textEditor.getComponentAt(number);

        if (panel.undoManager.canRedo()) {
            panel.undoManager.redo();
        }
    }

    public static void openTerminal(IDEWindow ideWindow) {
        ideWindow.jspWhole.setDividerLocation(ideWindow.jspWholeDefualtLocation);
    }


    public static void cannotFind(IDEWindow ideWindow) {

        System.out.println("Unknown Function");
    }

    public static Window findWindow(Component c) {

        if (c instanceof Window) {
            return (Window) c;
        } else if (c instanceof JPopupMenu) {
            JPopupMenu pop = (JPopupMenu) c;
            return findWindow(pop.getInvoker());
        } else {
            Container parent = c.getParent();
            return parent == null ? null : findWindow(parent);
        }
    }
}
