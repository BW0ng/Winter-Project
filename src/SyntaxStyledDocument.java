import javax.swing.text.SimpleAttributeSet;

/**
 * Brandon Wong
 * Winter-Project
 */
public abstract class SyntaxStyledDocument {

    SimpleAttributeSet comments;

    public SyntaxStyledDocument() {
        initializeComments();
    }
    abstract void initializeComments();
    abstract void findComments(final TextEditorPanel panel);
}
