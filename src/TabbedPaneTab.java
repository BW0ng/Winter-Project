import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

public class TabbedPaneTab extends JPanel {
    private JButton isEditedButton;
    private JPanel panel;
    private JLabel label;
    private String title;
    public int ID;
    private boolean hasIcon;
    public TabbedPaneTab(final String title,final int ID ,final IDEWindow ideWindow) {
        this.title=title;
        this.ID=ID;
        panel = this;
        label = new JLabel(title);
        label.setOpaque(false);
        ImageIcon closeButtonIcon;
        ImageIcon closeButtonHoverIcon;
        ImageIcon isEdited;
        if (System.getProperty("user.dir").contains("/src")) {
            closeButtonIcon = new ImageIcon("../resources/closebutton.png");
            closeButtonHoverIcon = new ImageIcon("../resources/closebuttonhover.png");
            isEdited = new ImageIcon("../resources/Super-Mario-Pixel.png");
        } else {
            closeButtonIcon = new ImageIcon("resources/closebutton.png");
            closeButtonHoverIcon = new ImageIcon("resources/closebuttonhover.png");
            isEdited = new ImageIcon("resources/Super-Mario-Pixel.png");
        }
        JButton close = new JButton(closeButtonIcon);
        close.setRolloverIcon(closeButtonHoverIcon);
        close.setBorderPainted(false);
        close.setPreferredSize(new Dimension(15,15));

        isEditedButton = new JButton(isEdited);
        isEditedButton.setBorderPainted(false);
        isEditedButton.setPreferredSize(new Dimension(16, 16));
        //close.addActionListener(new MenuActionListener("Close Window", ideWindow));
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Closing Tab");

                if (ideWindow.textEditor.getTabCount() <= 0) {
                    ideWindow.dispose();
                } else {
                    int tabNumber=0;
                    for(int i=0;i<ideWindow.textEditor.getTabCount();i++) {
                        TabbedPaneTab temp = (TabbedPaneTab) ideWindow.textEditor.getTabComponentAt(i);
                        if(ID == temp.ID) {
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
        // Adds a border of 5 pixels between components
        setLayout(new BorderLayout(5, 5));

        add(label, BorderLayout.CENTER);
        add(close, BorderLayout.EAST);
        removeIcon();

        setOpaque(false);
    }
    public void addIcon() {
        hasIcon = true;
        panel.add(isEditedButton, BorderLayout.WEST);
        panel.revalidate();
    }
    public void removeIcon() {
        if(hasIcon()) {
            panel.remove(isEditedButton);
            panel.revalidate();
        }
        hasIcon = false;
    }
    public boolean hasIcon() {
        return hasIcon;
    }
    public void setTitle(String title) {
        label.setText(title);
        panel.revalidate();
    }
}