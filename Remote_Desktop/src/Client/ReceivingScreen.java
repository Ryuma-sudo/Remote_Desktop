package Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ReceivingScreen extends Thread {
    private final DataInputStream dis; // to get the size of the screenshot image
    private JPanel cPanel = null;
    Image serverScreen = null;

    public ReceivingScreen(InputStream in, JPanel cPanel) {
        this.dis = new DataInputStream(new BufferedInputStream(in));
        this.cPanel = cPanel;
        start();
    }

    public void run() {
        try{
            while(true) {
                // compute the size of the image
                int length = dis.readInt();
                byte[] imageBytes = new byte[length];
                dis.readFully(imageBytes);

                // read the image sent from the server
                serverScreen = ImageIO.read(new ByteArrayInputStream(imageBytes));
                if (serverScreen != null) {
                    serverScreen = serverScreen.getScaledInstance(cPanel.getWidth(), cPanel.getHeight(), Image.SCALE_FAST);
                }

                // add the image to cPanel
                Graphics graphics = cPanel.getGraphics();
                graphics.drawImage(serverScreen, 0, 0, cPanel.getWidth(), cPanel.getHeight(), cPanel);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
