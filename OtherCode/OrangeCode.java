/*
  File Name(s):
    OrangeCode
    Window
  Purpose:
    OrangeCode:
      - Main File, init program
    Window:
      - Window for Program
  Created By:
    Topher Thomas - 11/24/2015
  Lasted Edited:
    Topher Thomas - 11/24/2015
*/


/*
  New Window: OrangeCode oc = new OrangeCode();
*/

//OrangeCode Imports

import javax.imageio.ImageIO;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

//Window Imports

public class OrangeCode extends JFrame {

    private Window window;
    private StartWindow startWindow;

    public OrangeCode(Boolean startUp) { //Completed
        Toolkit kit = Toolkit.getDefaultToolkit(); //Fit Screen
        Dimension screenSize = kit.getScreenSize(); // Fit Screen
        if (startUp) {
            setSize((2 * screenSize.width) / 10, (2 * screenSize.height) / 10);
            setUndecorated(true);
            startWindow = new StartWindow();
            startWindow.setBackground(new Color(242, 125, 0));
            add(startWindow);
            setLocationRelativeTo(null);
            setVisible(true);
            long delay = System.currentTimeMillis() / 1000;
            while (((System.currentTimeMillis() / 1000) - delay) < 4) {
            }
            setVisible(false);
            new OrangeCode(false);
        } else {
            setTitle("Orange Code");
            setSize((2 * screenSize.width) / 3, (2 * screenSize.height) / 3);
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window = new Window();
            //window.setBackground(new Color(242,125,0));
            add(window);
            setVisible(true);
        }
    }

    public static void main(String[] args) { //Completed
        new OrangeCode(true);
    }

    private class StartWindow extends JPanel {

        BufferedImage logo;

        public StartWindow() {

            try {
                URL imageLocation = this.getClass().getResource("Resources/StartUpImage/startUpImage.png");
                logo = ImageIO.read(imageLocation);
            } catch (IOException e) {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Error: StartWindow.StartWindow()");
                    System.exit(0);
                }
            }
        }

        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            g.drawImage(logo, 0, 0, getWidth(), getHeight(), null);
            g.setFont(new Font("TimesRoman", Font.BOLD + Font.ITALIC, 12));
            g.drawString("Version: N/A", getWidth() - 80, getHeight() - 10);
        }
    }

    private class Window extends JPanel {

        OrangeCompiler compiler;
        CompiledCode compiledCode;
        String fileName = "null";
        int x = getWidth();
        int y = getHeight();
        JMenuBar menuBar;
        JMenu fileMenu;
        JMenu codeMenu;
        GridBagLayout layout;
        GridBagConstraints c;
        JTextArea textEditor;
        JTextArea terminal;
        JTree syntaxTree;
        JTree workPlaceTree;


        public Window() {

            compiler = new OrangeCompiler();
            setLayout();
        }

        private void setLayout() {

            layout = new GridBagLayout();
            c = new GridBagConstraints();
            setLayout(layout);
            addMenuBar();
            addTextEditor();
            addWorkPlaceBox();
            addSyntaxBox();
            addTerminal();
        }

        private void addMenuBar() {

            menuBar = new JMenuBar();
            addFileMenu();
            addCodeMenu("null");
            menuBar.add(fileMenu);
            menuBar.add(codeMenu);
            setJMenuBar(menuBar);
            menuBar.setSize(400, 10);
        }

        private void addCodeMenu(String fileNameToAdd) {

            codeMenu = new JMenu("Code");
            JMenuItem compileMenuItem = new JMenuItem("Compile");
            codeMenu.add(compileMenuItem);
            compileMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    compileCode();
                }
            });
            if (!fileNameToAdd.equals("null")) {
                System.out.println("Here");
                JMenuItem fileNameMenuItem = new JMenuItem(fileNameToAdd);
                codeMenu.add(fileNameMenuItem);
                fileNameMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //compileCode();
                        System.out.println(fileNameToAdd);
                    }
                });
                updateUI();
            }
        }

        private void addFileMenu() {

            fileMenu = new JMenu("File");
            JMenuItem saveMenuItem = new JMenuItem("Save");
            JMenuItem loadMenuItem = new JMenuItem("Load");
            JMenuItem quitMenuItem = new JMenuItem("Quit");
            fileMenu.add(saveMenuItem);
            fileMenu.add(loadMenuItem);
            fileMenu.add(quitMenuItem);
            saveMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    saveFile();
                }
            });
            loadMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    loadFile();
                }
            });
            quitMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    System.out.println("quit");
                    System.exit(0);
                }
            });
        }

        private void saveFile() {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int result = fileChooser.showSaveDialog(window);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    PrintWriter out = new PrintWriter(fileChooser.getSelectedFile());
                    out.write(textEditor.getText());
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void loadFile() {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"), "Resources/Code"));
            int result = fileChooser.showOpenDialog(window);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    this.fileName = file.getName();
                    BufferedReader in = new BufferedReader(new FileReader(file));
                    String line;
                    String lineTotal = "";
                    while ((line = in.readLine()) != null) {
                        lineTotal += line + "\n";
                    }
                    textEditor.setText(lineTotal);
                    in.close();
                } catch (IOException e) {
                    System.out.println("IOException");
                }
            }
            addCodeMenu(fileName);
        }

        public ArrayList<File> listf(String directoryName, ArrayList<File> files) {

            File directory = new File(directoryName);
            System.out.println(directory.getName());
            // get all the files from a directory
            File[] fList = directory.listFiles();
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                    System.out.println(file.getName());
                } else if (file.isDirectory()) {
                    listf(file.getAbsolutePath(), files);
                }
            }
            return files;
        }

        private void addWorkPlaceBox() {
            //create the root node
            //ArrayList<File> files = new ArrayList<>();
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Code");

            //files = listf("Resources/Code/", files);


            //create the tree by passing in the root node
            workPlaceTree = new JTree(root);
            c.fill = GridBagConstraints.BOTH;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.gridx = 2;
            c.gridy = 0;
            add(new JScrollPane(workPlaceTree), c);
        }

        private void addSyntaxBox() {
            //create the root node
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Orange Code Syntax & Helpful tips for using OC");
            //create the child nodes
            DefaultMutableTreeNode basicNode = new DefaultMutableTreeNode("Basic Commands");
            basicNode.add(new DefaultMutableTreeNode("End Method/Program"));
            basicNode.add(new DefaultMutableTreeNode("Print Commands"));
            DefaultMutableTreeNode compilerNode = new DefaultMutableTreeNode("Imports");
            compilerNode.add(new DefaultMutableTreeNode("Scanner"));
            compilerNode.add(new DefaultMutableTreeNode("Timer"));
            DefaultMutableTreeNode variablesNode = new DefaultMutableTreeNode("Variables");
            variablesNode.add(new DefaultMutableTreeNode("Boolean"));
            variablesNode.add(new DefaultMutableTreeNode("Double"));
            variablesNode.add(new DefaultMutableTreeNode("Integer"));
            variablesNode.add(new DefaultMutableTreeNode("Method"));
            variablesNode.add(new DefaultMutableTreeNode("String"));

            //add the child nodes to the root node
            root.add(compilerNode);
            root.add(basicNode);
            root.add(variablesNode);

            //create the tree by passing in the root node
            syntaxTree = new JTree(root);
            syntaxTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent e) {

                    String terminalText = terminal.getText();
                    terminal.setText(terminalText + "\n" + e.getPath().toString() + "\n");
                }
            });
            c.fill = GridBagConstraints.BOTH;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.gridx = 2;
            c.gridy = 1;
            add(new JScrollPane(syntaxTree), c);
        }

        private void addTextEditor() {

            textEditor = new JTextArea(5, 6);
            int tabSize = textEditor.getTabSize();
            tabSize = 2;
            textEditor.setTabSize(tabSize);
            textEditor.setEditable(true);
            c.fill = GridBagConstraints.BOTH;
            c.weightx = .5;
            c.weighty = .5;
            c.gridwidth = 2;
            c.gridheight = 3;
            c.gridx = 0;
            c.gridy = 0;
            add(new JScrollPane(textEditor), c);
        }

        private void addTerminal() {

            terminal = new JTextArea(10, 0);
            terminal.setEditable(false);
            c.fill = GridBagConstraints.BOTH;
            //c.weightx = 0.0;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.gridx = 2;
            c.gridy = 2;
            add(new JScrollPane(terminal), c);
        }

        private void compileCode() {

            compiledCode = compiler.compile(fileName, textEditor.getText());
            String terminalText = terminal.getText();
            terminalText += "\n";

            String fileName = compiledCode.getFileName();
            ArrayList<OrangeClass> classes = compiledCode.getClasses();

            for (int i = 0; i < classes.size(); i++) {
                if (classes.get(i).getName().equals(fileName)) {
                    OrangeClass startingClass = classes.get(i);
                    classes.remove(i);
                    classes.add(0, startingClass);
                }
            }

            boolean endOfCode = false;
            while (endOfCode) {

            }

            terminalText += "\n";
            terminal.setText(terminalText);
        }

    }
}
