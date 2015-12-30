import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import static javax.swing.SpringLayout.*;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

public class IDEWindow extends JFrame {

    JPanel cmdPanel;
    protected static JTabbedPane textEditor;
    JPanel filePanel;
    JPanel buttonPanel;


    protected static int spaceBetweenComponents = 5;
    protected static int numberOfTextWindows;
    protected static ArrayList<TextEditorPanel> textEditorPanels;
    /**
     * Basic constructor used to set up the JFrame for the entire program
     *
     * @param name Name of the IDE Window
     */
    public IDEWindow(String name) {
        int width;
        int height;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        width = (int)(7*screenSize.getWidth()/8);
        height = (int)(7*screenSize.getHeight()/8);

        setTitle(name);
        setSize(width, height);
        setLocation(65 + (IDE.counter*5), 50 + (IDE.counter*5));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        textEditorPanels = new ArrayList<TextEditorPanel>();
        numberOfTextWindows = 0;

        // Instantiate JPanels
        cmdPanel = new JPanel();
        textEditor = new JTabbedPane();
        filePanel = new JPanel();
        buttonPanel = new JPanel();


        SpringLayout layout = new SpringLayout();
        final JPanel IDEPanel = new JPanel(layout);

        IDEPanel.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("Component Resized");
                System.out.printf("Height: %d, Width: %d\n\n", getHeight(), getWidth());
                updatePanel(getSize());
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        // Layout for Button Panel
        layout.putConstraint(NORTH, buttonPanel, 5, NORTH, IDEPanel);
        layout.putConstraint(WEST, buttonPanel, 5, WEST, IDEPanel);

        // Layout for FilePanel
        layout.putConstraint(NORTH, filePanel, 5, SOUTH, buttonPanel);
        layout.putConstraint(WEST, filePanel, 5, WEST, IDEPanel);

        // Layout for textEditor
        layout.putConstraint(NORTH, textEditor, 5, SOUTH, buttonPanel);
        layout.putConstraint(WEST, textEditor, 5, EAST, filePanel);

        // Layout for cmdPanel
        layout.putConstraint(NORTH, cmdPanel, 5, SOUTH, filePanel);
        layout.putConstraint(WEST, cmdPanel, 5, WEST, IDEPanel);


        buttonPanel.setBackground(Color.BLACK);

        FilePanel fileTree = new FilePanel(filePanel);
        filePanel.add(fileTree);

        cmdPanel.add(new CmdPanel(cmdPanel));

        TextEditorPanel temp = new TextEditorPanel(numberOfTextWindows, textEditor);
        textEditor.addTab("New File", temp);

        updatePanel(getSize());

        JSplitPane middle = new JSplitPane(HORIZONTAL_SPLIT, filePanel, textEditor);
        JSplitPane top = new JSplitPane(VERTICAL_SPLIT, buttonPanel, middle);
        JSplitPane whole = new JSplitPane(VERTICAL_SPLIT, top, cmdPanel);

        middle.setOneTouchExpandable(true);
        whole.setOneTouchExpandable(true);
        middle.setResizeWeight(0.5);
        whole.setResizeWeight(0.5);
        top.setDividerSize(0);

        IDEPanel.add(whole);

        add(IDEPanel);

        setJMenuBar(setUpMenuBar());

        repaint();

        setVisible(true);

        textEditorPanels.add(temp);

    }

    /**
     * Returns a basic JMenuBar to be added to the JFrame
     *
     * File - New, Open, Save, Exit Text Window, and Quit
     *      New - New Window, Text File, Java File, C File, C++ File
     *
     *
     * @return JMenuBar to be added to the JFrame
     */
    public JMenuBar setUpMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.setFocusable(false);

        JMenu file = new JMenu("File");

        JMenu newMenu = new JMenu("New");
            JMenuItem newWindow = new JMenuItem("New Window");
            JMenuItem textFile = new JMenuItem("Text File");
                textFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                textFile.addActionListener(new MenuActionListener("Text File"));
            JMenuItem javaFile = new JMenuItem("Java File");
            JMenuItem cFile = new JMenuItem("C File");
            JMenuItem cppFile = new JMenuItem("C++ File");
            newMenu.add(newWindow);
            newMenu.add(new JPopupMenu.Separator());
            newMenu.add(textFile);
            newMenu.add(javaFile);
            newMenu.add(cFile);
            newMenu.add(cppFile);

            newWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | KeyEvent.SHIFT_DOWN_MASK));
            newWindow.addActionListener(new MenuActionListener("New Window"));
        JMenuItem openFile = new JMenuItem("Open");
            openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            openFile.addActionListener(new MenuActionListener("Open"));

        JMenuItem saveFile = new JMenuItem("Save");
            saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            saveFile.addActionListener(new MenuActionListener("Save"));

        JMenuItem saveAsFile = new JMenuItem("Save As");
        saveAsFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | KeyEvent.SHIFT_DOWN_MASK));
        saveAsFile.addActionListener(new MenuActionListener("Save As"));


        JMenuItem exit = new JMenuItem("Close Window");
            exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            exit.addActionListener(new MenuActionListener("Close Window"));

        JMenuItem quit = new JMenuItem("Quit IDE");
            quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            quit.addActionListener(new MenuActionListener("Quit IDE"));

        file.add(newMenu);
        file.add(openFile);
        file.add(saveFile);
        file.add(saveAsFile);
        file.add(new JPopupMenu.Separator());
        file.add(exit);
        file.add(quit);

        menuBar.add(file);

        return menuBar;
    }
    public void updatePanel(Dimension dimension) {
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        int buttonPanelWidth = width - (2*spaceBetweenComponents);
        int buttonPanelHeight = 20;
        int filePanelWidth = width/5;
        int filePanelHeight = 3*height/4;
        int textEditorWidth = width - (filePanelWidth + (2*spaceBetweenComponents));
        int textEditorHeight = 3*height/4;
        int cmdPanelWidth = width - (2*spaceBetweenComponents);
        int cmdPanelHeight = height - (buttonPanelHeight + filePanelHeight + (2*spaceBetweenComponents) + 30);

        // Set up the default sizes of the JPanels
        buttonPanel.setPreferredSize(new Dimension(buttonPanelWidth, buttonPanelHeight));
        filePanel.setPreferredSize(new Dimension(filePanelWidth, filePanelHeight));
        textEditor.setPreferredSize(new Dimension(textEditorWidth, textEditorHeight));
        cmdPanel.setPreferredSize(new Dimension(cmdPanelWidth, cmdPanelHeight));

        repaint();

    }
}
