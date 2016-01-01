import javax.swing.*;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */
public class IDE {

    protected static boolean testing = true;
    protected static int counter = 0;

    public static void main(String[] args) {

        new SplashScreen();
    }

    public static void newIDEWindow() {

        try {
            System.getProperty("com.apple.laf.useScreenMenuBar", "true");
            System.getProperty("com.apple.mrj.application.apple.menu.about.name", "IDE");
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new IDEWindow("IDE");
    }

}
