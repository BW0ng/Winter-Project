import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen extends JWindow {
      private static JProgressBar progressBar;
      private static int count = 1;
      private static int TIMER_PAUSE = 60;
      private static int PROGBAR_MAX = 105;
      private static Timer progressBarTimer;

    /**
     * Constructor for the startup screen
     */
      public SplashScreen() {
            createSplash();
      }

    /**
     * Method to create the startup screen
     */
      private void createSplash() {

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            // TODO Need to figure out how to do relative addressing aka ("../resouces/SplashScreen.jpg")
            // TODO and not just put it in the classes folder

            ImageIcon icon = new ImageIcon("../resources/SplashScreen.jpg");
            JLabel splashImage = new JLabel(icon);
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

    /**
     * Method to show the progress bar and to move it
     */
      private void startProgressBar() {
          if(IDE.testing) {
              progressBarTimer = new Timer(1, new ActionListener() {
                  public void actionPerformed(ActionEvent arg0) {

                      progressBar.setValue(count);
                      if (PROGBAR_MAX == count) {
                          SplashScreen.this.dispose();
                          progressBarTimer.stop();
                          IDE.newIDEWindow();
                      }
                      count++;
                  }
              });
              progressBarTimer.start();
          } else {
              progressBarTimer = new Timer(TIMER_PAUSE, new ActionListener() {
                  public void actionPerformed(ActionEvent arg0) {

                      progressBar.setValue(count);
                      if (PROGBAR_MAX == count) {
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
}
