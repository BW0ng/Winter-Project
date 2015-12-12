
/*
  Main class to create the first window
*/
import java.awt.*;
public class IDE {
  protected static long delayInSecs = 4;
    public static void main(String[] args) {
        new SplashScreen();
    }
    public static void newIDEWindow() {
          new IDEWindow("IDE");
   }
}
