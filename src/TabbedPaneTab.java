import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

public class TabbedPaneTab extends JPanel {
    String title;
    public TabbedPaneTab(String title, IDEWindow ideWindow) {
        this.title=title;
        JLabel label = new JLabel("<html><font color='black'>" + title + "</font></html>");
        ImageIcon icon = new ImageIcon("../resources/closebutton.png");
        JButton close = new JButton(icon);
        close.setBackground(Color.BLACK);
        close.setPreferredSize(new Dimension(10,10));
        //close.addActionListener(new MenuActionListener("Close Window", ideWindow));
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Closing Window");

                if (ideWindow.textEditor.getTabCount() <= 0) {
                    ideWindow.dispose();
                } else {
                    int tabNumber=0;
                    for(int i=0;i<ideWindow.textEditor.getTabCount();i++) {
                        System.out.println(title + ":" + ideWindow.textEditor.getTitleAt(i));
                        if(title.equals(ideWindow.textEditor.getTitleAt(i))) {
                            tabNumber = i;
                        }
                    }

                    //int tabNumber = ideWindow.textEditor.getSelectedIndex();
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
                            ToolbarFunctions.save(ideWindow.textEditor.getTabComponentAt(tabNumber), ideWindow);
                        } else if (reply == JOptionPane.NO_OPTION) {
                            ideWindow.textEditor.remove(tabNumber);
                            ideWindow.numberOfTextWindows--;
                        }
                    }
                }
            }
        });
        add(label);
        add(close);
    }
}