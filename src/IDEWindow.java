import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

public class IDEWindow extends JFrame {

    JPanel cmdPanel;
    JPanel textEditor;
    JPanel filePanel;
    JPanel buttonPanel;

    /**
     * Basic constructor used to set up the JFrame for the entire program
     *
     * @param name
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

        // Instantiate JPanels
        cmdPanel = new JPanel();
        textEditor = new JPanel();
        filePanel = new JPanel();
        buttonPanel = new JPanel();


        SpringLayout layout = new SpringLayout();
        JPanel IDEPanel = new JPanel(layout);

        // Layout for Button Panel
        layout.putConstraint(SpringLayout.NORTH, buttonPanel, 5, SpringLayout.NORTH, IDEPanel);
        layout.putConstraint(SpringLayout.WEST, buttonPanel, 5, SpringLayout.WEST, IDEPanel);

        // Layout for FilePanel
        layout.putConstraint(SpringLayout.NORTH, filePanel, 5, SpringLayout.SOUTH, buttonPanel);
        layout.putConstraint(SpringLayout.WEST, filePanel, 5, SpringLayout.WEST, IDEPanel);

        // Layout for textEditor
        layout.putConstraint(SpringLayout.NORTH, textEditor, 5, SpringLayout.SOUTH, buttonPanel);
        layout.putConstraint(SpringLayout.WEST, textEditor, 5, SpringLayout.EAST, filePanel);

        // Layout for cmdPanel
        layout.putConstraint(SpringLayout.NORTH, cmdPanel, 5, SpringLayout.SOUTH, filePanel);
        layout.putConstraint(SpringLayout.WEST, cmdPanel, 5, SpringLayout.WEST, IDEPanel);

        // Set up the default sizes of the JPanels
        buttonPanel.setPreferredSize(new Dimension(width-10, 20));
        filePanel.setPreferredSize(new Dimension(150, 500));
        textEditor.setPreferredSize(new Dimension(width-165, 500));
        cmdPanel.setPreferredSize(new Dimension(width-10, (height-585)));

        buttonPanel.setBackground(Color.BLACK);
        filePanel.setBackground(Color.ORANGE);
        textEditor.setBackground(Color.YELLOW);
        cmdPanel.setBackground(Color.BLUE);

        IDEPanel.add(buttonPanel);
        IDEPanel.add(filePanel);
        IDEPanel.add(textEditor);
        IDEPanel.add(cmdPanel);

        add(IDEPanel);

        setJMenuBar(setUpMenuBar());

        repaint();

        setVisible(true);
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
        JMenu file = new JMenu("File");

        menuBar.add(file);

        JMenu newMenu = new JMenu("New");
            JMenuItem newWindow = new JMenuItem("New Window");
            JMenuItem textFile = new JMenuItem("Text File");
            JMenuItem javaFile = new JMenuItem("Java File");
            JMenuItem cFile = new JMenuItem("C File");
            JMenuItem cppFile = new JMenuItem("C++ File");
            newMenu.add(newWindow);
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

        JMenuItem exit = new JMenuItem("Close Window");
            exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            exit.addActionListener(new MenuActionListener("Close Window"));

        JMenuItem quit = new JMenuItem("Quit IDE");
            quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            quit.addActionListener(new MenuActionListener("Quit IDE"));

        file.add(newMenu);
        file.add(openFile);
        file.add(saveFile);
        file.add(exit);
        file.add(quit);

        return menuBar;
    }
}
