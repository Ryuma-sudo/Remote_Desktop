package Client;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class CreateFrame extends Thread{
    String width = "", height = "";
    private JFrame frame = new JFrame();
    private JDesktopPane desktop = new JDesktopPane();
    private Socket cScoket = null;
    private JInternalFrame internalFrame = new JInternalFrame("Server", true, true, true);
    private JPanel cPanel = new JPanel();
    public CreateFrame(Socket cScoket, String width, String height) {
        this.cScoket = cScoket;
        this.width = width;
        this.height = height;
        start();
    }
    public void drawGUI() {
        frame.add(desktop, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        internalFrame.setLayout(new BorderLayout());
        internalFrame.getContentPane().add(cPanel, BorderLayout.CENTER);
        internalFrame.setSize(100, 100);
        desktop.add(internalFrame);

        try{
            internalFrame.setMaximum(true);
        }catch(PropertyVetoException ex) {
            ex.printStackTrace();
        }
        cPanel.setFocusable(true);
        internalFrame.setVisible(true);
    }
    public void run() {
        InputStream in = null;
        drawGUI();
        try {
            in = cScoket.getInputStream();
        }catch (IOException e) {
            e.printStackTrace();
        }
        new ReceivingScreen(in, cPanel);
        new SendEvents(cScoket, cPanel, width, height);
    }
}
