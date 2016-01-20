import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

/**
 * Brandon Wong
 * Winter-Project
 */
public class JavaStyledDocument extends SyntaxStyledDocument {
    SimpleAttributeSet comments;

    public JavaStyledDocument() {
        initializeComments();
    }
    public void initializeComments() {
        comments = new SimpleAttributeSet();
        StyleConstants.setForeground(comments, new Color(82, 220, 124));
    }
    public void findComments(final TextEditorPanel panel) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < panel.pane.getText().length(); i++) {
                    if(panel.pane.getText().charAt(i) == '/' &&
                            panel.pane.getText().charAt(i+1) == '*') {
                        System.out.println("Found Multi-Line Comment");
                    } else if (panel.pane.getText().charAt(i) == '/' &&
                            panel.pane.getText().charAt(i+1) == '/') {
                        System.out.println("Found one Line Comment");
                    }
                }
            }
        });
    }
}
