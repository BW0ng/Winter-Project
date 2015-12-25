import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

// TODO All MenuBars are not working when clicked. It works for shortcuts. Object passed in is MenuBar
public class MenuActionListener implements ActionListener {
    String menuItem;

    /**
     * Constructor for the actionListener for the Toolbar
     * @param menuItem
     */
    public MenuActionListener(String menuItem) {
        this.menuItem = menuItem;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Implement the functions when the menuItem is selected
        // TODO Need to fix this.

        String s = e.getActionCommand();
        if (s.equals("Open")) {
            ToolbarFunctions.open();

        }
        else if (s.equals("Save")) {
            ToolbarFunctions.save(e.getSource());

        }
        else if (s.equals("Close Window")) {
            ToolbarFunctions.close(e.getSource());
        }
        else if (s.equals("Quit IDE")) {
            ToolbarFunctions.quit();

        }
        else if (s.equals("New Window")) {
            ToolbarFunctions.newWindow();
        }
        else if (s.equals("Text Window")) {
            ToolbarFunctions.newTextWindow();
            System.out.println("New Text Window");
        }
        else {
            System.out.println(s);
            ToolbarFunctions.cannotFind();

        }
    }
}
