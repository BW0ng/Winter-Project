
import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;

public class IDEWindow extends JFrame{
    public IDEWindow(String name) {
      Toolkit kit = Toolkit.getDefaultToolkit();
      Dimension screenSize = kit.getScreenSize();

      setTitle(name);
      setSize((int)(7*screenSize.getWidth()/8), (int)(7*screenSize.getHeight()/8));
      setLocation(65, 50);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      SpringLayout layout = new SpringLayout();
      JPanel IDEPanel = new JPanel(layout);
      //CMDPanel cmdPanel = new CMDPanel();
      //TextEditorPanel textEditorPanel = new TextEditorPanel);
      JLabel label = new JLabel("Hello");
      JLabel label1 = new JLabel("Bye");
      JLabel label2 = new JLabel("Felicia");

      IDEPanel.add(label);
      IDEPanel.add(label1);
      IDEPanel.add(label2);

      layout.putConstraint(SpringLayout.EAST, label, 5, SpringLayout.EAST, IDEPanel);
      layout.putConstraint(SpringLayout.NORTH, IDEPanel, 5, SpringLayout.NORTH, label);


      


      setVisible(true);
    }
}
