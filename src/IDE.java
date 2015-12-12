/*
  Main class to create the first window
*/
public class IDE {

    protected static boolean testing = true;

    public static void main(String[] args) {
        new SplashScreen();
    }
    public static void newIDEWindow() {
          new IDEWindow("IDE");
   }
}
