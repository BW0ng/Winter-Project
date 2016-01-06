import javax.swing.*;
import java.awt.*;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */
public class ButtonPanel extends JPanel {

    private IDEWindow ideWindow;
    private String directory;

    public ButtonPanel(IDEWindow ideWindow) {
        this.ideWindow = ideWindow;

        if(System.getProperty("user.dir").contains("/src")) {
            directory = "../resources/";
        } else {
            directory = "resources/";
        }
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        ButtonPanelButton open = new ButtonPanelButton("Open",
                new ImageIcon(directory + "Open-icon.png"), ideWindow);
        ButtonPanelButton save = new ButtonPanelButton("Save",
                new ImageIcon(directory + "Save-icon.png"), ideWindow);
        ButtonPanelButton undo = new ButtonPanelButton("Undo",
                new ImageIcon(directory + "Undo-icon.png"), ideWindow);
        ButtonPanelButton redo = new ButtonPanelButton("Redo",
                new ImageIcon(directory + "Redo-icon.png"), ideWindow);

        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(3, 20));

        add(open);
        add(save);
        add(separator);
        add(undo);
        add(redo);


    }
    public Dimension getPreferredSize() {
        return new Dimension(15, 15);
    }
}
