import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuActionListener implements ActionListener {
    String menuItem;
    public MenuActionListener(String menuItem) {
        this.menuItem = menuItem;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Implement the functions when the menuItem is selected
        System.out.println(e.getActionCommand());
    }
}
