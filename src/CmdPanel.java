import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.jediterm.pty.PtyMain;
import com.jediterm.terminal.TtyConnector;
import com.jediterm.terminal.ui.TabbedTerminalWidget;
import com.jediterm.terminal.ui.TerminalSession;
import com.jediterm.terminal.ui.TerminalWidget;
import com.jediterm.terminal.ui.UIUtil;
import com.jediterm.terminal.ui.settings.DefaultTabbedSettingsProvider;
import com.pty4j.PtyProcess;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Brandon Wong and Topher Thomas
 * Winter-Project
 */

// TODO Implement a JTextPane. Used for more advanced and multiple fonts

public class CmdPanel extends JPanel {

    JPanel panel;

    public CmdPanel(JPanel panel, IDEWindow ideWindow) {
        this.panel = panel;

        TabbedTerminalWidget myTerminal = new TabbedTerminalWidget(new DefaultTabbedSettingsProvider(), new Predicate<TerminalWidget>() {
            @Override
            public boolean apply(TerminalWidget terminalWidget) {
                openSession(terminalWidget);
                return true;
            }
        });

        add(myTerminal.getComponent());

        openSession(myTerminal);
    }

    public Dimension getMinimumSize() {

        return new Dimension(15, 30);
    }

    public Dimension getPreferredSize() {

        int height = panel.getSize().height;
        int width = panel.getSize().width;
        return new Dimension(width, height);
    }

    private void openSession(TerminalWidget terminal) {
        if (terminal.canOpenSession()) {
            openSession(terminal, createTtyConnector());
        }
    }

    public void openSession(TerminalWidget terminal, TtyConnector ttyConnector) {
        TerminalSession session = terminal.createTerminalSession(ttyConnector);
        session.start();
    }

    public TtyConnector createTtyConnector() {
        try {
            Map<String, String> envs = Maps.newHashMap(System.getenv());
            envs.put("TERM", "xterm");
            String[] command = new String[]{"/bin/bash", "--login"};

            if (UIUtil.isWindows) {
                command = new String[]{"cmd.exe"};
            }

            PtyProcess process = PtyProcess.exec(command, envs, null);

            return new PtyMain.LoggingPtyProcessTtyConnector(process, Charset.forName("UTF-8"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}