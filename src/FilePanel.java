import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.Vector;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

public class FilePanel extends JPanel {

    protected JPanel pane;

    /**
     * Construct a FileTree
     */
    public FilePanel(JPanel panel, final IDEWindow ideWindow) {

        pane = panel;
        setLayout(new BorderLayout());
        // Make a tree list with all the nodes, and make it a JTree
        JTree tree = new JTree(addNodes(null, new File(System.getProperty("user.dir"))));

        // Add a listener
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) e
                        .getPath().getLastPathComponent();

                File file = null;

                if(node.isLeaf()) {
                    file = new File(node.getParent() + "/" + node);
                    ToolbarFunctions.open(file, ideWindow);

                }
            }
        });

        setSize(panel.getPreferredSize());
        // Lastly, put the JTree into a JScrollPane.
        JScrollPane scrollpane = new JScrollPane();
        scrollpane.getViewport().add(tree);

        add(BorderLayout.CENTER, scrollpane);

    }

    /**
     * Add nodes from under "dir" into curTop. Highly recursive.
     */
    DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {

        String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
        if (curTop != null) { // should only be null at root
            curTop.add(curDir);
        }
        Vector<String> ol = new Vector<String>();
        String[] tmp = dir.list();

        for (String aTmp : tmp) ol.addElement(aTmp);

        Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
        File f;
        Vector<String> files = new Vector<String>();
        // Make two passes, one for Dirs and one for Files. This is #1.
        for (int i = 0; i < ol.size(); i++) {
            String thisObject = ol.elementAt(i);
            String newPath;
            if (curPath.equals("."))
                newPath = thisObject;
            else
                newPath = curPath + File.separator + thisObject;
            if ((f = new File(newPath)).isDirectory())
                addNodes(curDir, f);
            else
                files.addElement(thisObject);
        }
        // Pass two: for files.
        for (int fnum = 0; fnum < files.size(); fnum++)
            curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
        return curDir;
    }

    public Dimension getMinimumSize() {

        return new Dimension(15, 200);
    }

    public Dimension getMaximumSize() {

        return new Dimension(250, 700);
    }

    public Dimension getPreferredSize() {

        int height = pane.getSize().height - 5;
        int width = pane.getSize().width;
        return new Dimension(width, height);
    }
}