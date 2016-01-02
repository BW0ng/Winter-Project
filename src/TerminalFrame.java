/**
 * Brandon Wong
 * Winter-Project
 */

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.jediterm.pty.PtyMain;
import com.jediterm.terminal.TtyConnector;
import com.jediterm.terminal.ui.*;
import com.jediterm.terminal.ui.settings.DefaultTabbedSettingsProvider;
import com.pty4j.PtyProcess;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.Charset;
import java.util.Map;


public class TerminalFrame {
    public static final Logger LOG = Logger.getLogger(AbstractTerminalFrame.class);

    private JPanel myBufferFrame;

    private TerminalWidget myTerminal;

    private JPanel cmdPanel;

    private IDEWindow ideWindow;

    protected JPanel frame;

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

    protected TerminalFrame(JPanel cmdPanel, IDEWindow ideWindow) {
        myTerminal = new TabbedTerminalWidget(new DefaultTabbedSettingsProvider(), new Predicate<TerminalWidget>() {
            @Override
            public boolean apply(TerminalWidget terminalWidget) {
                openSession(terminalWidget);
                return true;
            }
        });

        frame = new JPanel();
        this.cmdPanel = cmdPanel;
        this.ideWindow = ideWindow;
        frame.setBackground(Color.BLACK);

        sizeFrameForTerm(frame);
        //frame.setPreferredSize();
        frame.add("Center", myTerminal.getComponent());

        openSession(myTerminal);
    }

    private void sizeFrameForTerm(final JPanel frame) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Dimension d = cmdPanel.getPreferredSize();

                d.width += frame.getWidth() - frame.getWidth();
                d.height += frame.getHeight() - frame.getHeight();
                frame.setPreferredSize(d);

                System.out.printf("Size Frame for Terminal Height: %d, Width:%d%n%n", frame.getHeight(), frame.getWidth());
            }
        });
    }
}