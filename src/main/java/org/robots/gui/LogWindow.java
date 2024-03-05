package org.robots.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.robots.log.LogChangeListener;
import org.robots.log.LogEntry;
import org.robots.log.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener {
    private LogWindowSource logSource;
    private TextArea logContent;

    public LogWindow(LogWindowSource logSource) {
        super("Протокол работы", true, true, true, true);

        this.logSource = logSource;
        this.logSource.registerListener(this);
        this.logContent = new TextArea("");
        this.logContent.setSize(200, 500);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();

        for (LogEntry entry : logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }

        logContent.setText(content.toString());
        logContent.invalidate();
    }
    
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }
}
