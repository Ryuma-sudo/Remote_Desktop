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
    private JPanel cPanel = new JPanel();// contain the server's screen

    public CreateFrame(Socket cScoket, String width, String height) {
        this.cScoket = cScoket;
        this.width = width;
        this.height = height;
        start();
    }

    public void drawGUI() {
        // making a GUI to work with cPanel
        frame.add(desktop, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        internalFrame.setLayout(new BorderLayout());
        internalFrame.getContentPane().add(cPanel, BorderLayout.CENTER);
        desktop.add(internalFrame);
        frame.setTitle("Remote Desktop by NQTruc");

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

        // continuously receive the screen and send the mouse, keyboard event to server
        new ReceivingScreen(in, cPanel);
        new SendEvents(cScoket, cPanel, width, height);
    }
}
