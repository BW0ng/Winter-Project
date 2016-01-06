import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import static javax.swing.SpringLayout.*;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

public class IDEWindow extends JFrame {

    protected IDEWindow ideWindow;
    protected JTabbedPane textEditor;
    protected int spaceBetweenComponents = 5;
    protected int numberOfTextWindows;
    JPanel cmdPanel;
    JPanel filePanel;
    JPanel buttonPanel;

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

        width = (int) (7 * screenSize.getWidth() / 8);
        height = (int) (7 * screenSize.getHeight() / 8);

        setTitle(name);
        setSize(width, height);
        setLocation(65 + (IDE.counter * 5), 50 + (IDE.counter * 5));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        FilePanel fileTree = new FilePanel(filePanel, this);
        filePanel.add(fileTree);

        cmdPanel.add(new CmdPanel(cmdPanel));

        TabbedPaneTab tabbedPaneTab = new TabbedPaneTab("New File", this);
        TextEditorPanel temp = new TextEditorPanel(numberOfTextWindows, textEditor, this, tabbedPaneTab);
        textEditor.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        textEditor.addTab("New File", temp);
        textEditor.setTabComponentAt(0, tabbedPaneTab);

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

        ideWindow = this;

    }

    /**
     * Returns a basic JMenuBar to be added to the JFrame
     *
     * @return JMenuBar to be added to the JFrame
     */
    public JMenuBar setUpMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        menuBar.setFocusable(false);

        JMenu file = addFileMenu();
        JMenu edit = addEditMenu();

        menuBar.add(file);
        menuBar.add(edit);

        return menuBar;
    }

    /**
     * Method to set up the File Menu of the MenuBar
     *
     * @return JMenu for setUpMenuBar to add to menuBar
     */
    public JMenu addFileMenu() {

        JMenu file = new JMenu("File");
        //file.setMnemonic(KeyEvent.VK_F);
        //Underlines 'F' int "File"
        //Does NOT create a shortcut! must do that elsewhere

        JMenu newMenu = new JMenu("New");
        JMenuItem newWindow = new JMenuItem("New Window");
        JMenuItem textFile = new JMenuItem("Text File");
        textFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        textFile.addActionListener(new MenuActionListener("Text File", this));
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
        newWindow.addActionListener(new MenuActionListener("New Window", this));

        JMenuItem openFile = new JMenuItem("Open");
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        openFile.addActionListener(new MenuActionListener("Open", this));

        JMenuItem saveFile = new JMenuItem("Save");
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveFile.addActionListener(new MenuActionListener("Save", this));

        JMenuItem saveAsFile = new JMenuItem("Save As");
        saveAsFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | KeyEvent.SHIFT_DOWN_MASK));
        saveAsFile.addActionListener(new MenuActionListener("Save As", this));


        JMenuItem exit = new JMenuItem("Close Window");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        exit.addActionListener(new MenuActionListener("Close Window", this));

        JMenuItem quit = new JMenuItem("Quit IDE");
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        quit.addActionListener(new MenuActionListener("Quit IDE", this));

        file.add(newMenu);
        file.add(openFile);
        file.add(saveFile);
        file.add(saveAsFile);
        file.add(new JPopupMenu.Separator());
        file.add(exit);
        file.add(quit);

        return file;
    }

    /**
     * Method to set up the Edit Menu of the MenuBar
     *
     * @return JMenu for setUpMenuBar to add to menuBar
     */
    public JMenu addEditMenu() {

        JMenu edit = new JMenu("Edit");

        JMenuItem undo = new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        undo.addActionListener(new MenuActionListener("Undo", this));

        JMenuItem redo = new JMenuItem("Redo");
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | KeyEvent.SHIFT_DOWN_MASK));
        redo.addActionListener(new MenuActionListener("Redo", this));

        edit.add(undo);
        edit.add(redo);

        return edit;
    }

    public void updatePanel(Dimension dimension) {

        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        int buttonPanelWidth = width - (2 * spaceBetweenComponents);
        int buttonPanelHeight = 20;
        int filePanelWidth = width / 5;
        int filePanelHeight = 3 * height / 4;
        int textEditorWidth = width - (filePanelWidth + (2 * spaceBetweenComponents));
        int textEditorHeight = 3 * height / 4;
        int cmdPanelWidth = width - (2 * spaceBetweenComponents);
        int cmdPanelHeight = height - (buttonPanelHeight + filePanelHeight + (2 * spaceBetweenComponents) + 30);

        // Set up the default sizes of the JPanels
        buttonPanel.setPreferredSize(new Dimension(buttonPanelWidth, buttonPanelHeight));
        filePanel.setPreferredSize(new Dimension(filePanelWidth, filePanelHeight));
        textEditor.setPreferredSize(new Dimension(textEditorWidth, textEditorHeight));
        cmdPanel.setPreferredSize(new Dimension(cmdPanelWidth, cmdPanelHeight));

        repaint();

    }
}
