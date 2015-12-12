import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;

public class SplashScreen extends JWindow {
      private static JProgressBar progressBar;
      private static int count = 1;
      private static int TIMER_PAUSE = 60;
      private static int PROGBAR_MAX = 105;
      private static Timer progressBarTimer;


      public SplashScreen() {
            createSplash();
      }
      private void createSplash() {

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            // TODO Need to figure out how to do relative addressing aka ("../resouces/SplashScreen.jpg") and not just put it in the classes folder
            JLabel splashImage = new JLabel(new ImageIcon(getClass().getResource("SplashScreen.jpg")));
            panel.add(splashImage);

            progressBar = new JProgressBar();
            progressBar.setMaximum(PROGBAR_MAX);
            progressBar.setForeground(new Color(2, 8, 54));
            progressBar.setBorder(BorderFactory.createLineBorder(Color.black));
            panel.add(progressBar, BorderLayout.SOUTH);

            this.setContentPane(panel);
            this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);

            startProgressBar();
      }
      private void startProgressBar() {
            progressBarTimer = new Timer(TIMER_PAUSE, new ActionListener() {
                  public void actionPerformed(ActionEvent arg0) {
                        progressBar.setValue(count);
                        if(PROGBAR_MAX == count) {
                              SplashScreen.this.dispose();
                              progressBarTimer.stop();
                              IDE.newIDEWindow();
                        }
                        count++;
                  }
            });
            progressBarTimer.start();
      }
}
