import javax.swing.*;

/*
  Main class to create the first window
*/
public class IDE {

    protected static boolean testing = true;

    public static void main(String[] args) {
        new SplashScreen();
    }
    public static void newIDEWindow() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new IDEWindow("IDE");
   }

}
