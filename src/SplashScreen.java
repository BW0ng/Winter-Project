import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen extends JWindow {

    private JProgressBar progressBar;
    private int count = 1;
    private int TIMER_PAUSE = 60;
    private int PROGBAR_MAX = 105;
    private Timer progressBarTimer;

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

        JLabel splashImage;

        if (System.getProperty("user.dir").contains("/src")) {
            splashImage = new JLabel(new ImageIcon("../resources/SplashScreen5.jpg"));
        } else {
            splashImage = new JLabel(new ImageIcon("resources/SplashScreen5.jpg"));
        }
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

        if (IDE.testing) {
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
