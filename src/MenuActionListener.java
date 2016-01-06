import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

// TODO All MenuBars are not working when clicked. It works for shortcuts. Object passed in is MenuBar
public class MenuActionListener implements ActionListener {

    String menuItem;
    IDEWindow ideWindow;

    public MenuActionListener(String menuItem, IDEWindow ideWindow) {

        this.menuItem = menuItem;
        this.ideWindow = ideWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Implement the functions when the menuItem is selected


        String s = e.getActionCommand();
        if (s.equals("Open")) {
            ToolbarFunctions.open(ideWindow);

        } else if (s.equals("Save")) {
            System.out.println("Save");
            ToolbarFunctions.save(e.getSource(), ideWindow);

        } else if (s.equals("Save As")) {
            System.out.println("Save As");
            ToolbarFunctions.saveAs(ideWindow);
        } else if (s.equals("Close Window")) {
            ToolbarFunctions.close(e.getSource(), ideWindow);
        } else if (s.equals("Quit IDE")) {
            ToolbarFunctions.quit(ideWindow);

        } else if (s.equals("New Window")) {
            ToolbarFunctions.newWindow(ideWindow);
        } else if (s.equals("Text File")) {
            ToolbarFunctions.newTextFile(ideWindow);
            System.out.println("New Text File");
        } else if (s.equals("Undo")) {
            ToolbarFunctions.undo(ideWindow);
        } else if (s.equals("Redo")) {
            ToolbarFunctions.redo(ideWindow);
        } else {
            System.out.println(s);
            ToolbarFunctions.cannotFind(ideWindow);

        }
    }
}
