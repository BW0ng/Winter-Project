import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Brandon Wong
 * CWID: 11557849
 * CS2133 Section:01
 */
public class MenuActionListener implements ActionListener {
    String menuItem;
    public MenuActionListener(String menuItem) {
        this.menuItem = menuItem;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(menuItem);
    }
}
