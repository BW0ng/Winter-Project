import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */
public class ButtonPanelButton extends JButton implements ActionListener {

    private IDEWindow ideWindow;

    public ButtonPanelButton(String string, ImageIcon icon, IDEWindow ideWindow) {
        super(icon);
        addActionListener(this);
        setActionCommand(string);

        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);

        this.ideWindow = ideWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Open")) {
            ToolbarFunctions.open(ideWindow);
        } else if (e.getActionCommand().equals("Save")) {
            ToolbarFunctions.save(e.getSource(), ideWindow);
        } else if (e.getActionCommand().equals("Undo")) {
            ToolbarFunctions.undo(ideWindow);
        } else if (e.getActionCommand().equals("Redo")) {
            ToolbarFunctions.redo(ideWindow);
        } else if(e.getActionCommand().equals("Open Terminal")) {
            ToolbarFunctions.openTerminal(ideWindow);
        } else {
            System.out.println("Unknown Button");
        }
    }
    public Dimension getPreferredSize() {
        return new Dimension(20, 20);
    }
}
