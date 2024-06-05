package Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ReceivingScreen extends Thread {
    private final DataInputStream dis; // to get the size of the screenshot image
    private JPanel cPanel = null;
    BufferedImage serverScreen = null;

    public ReceivingScreen(InputStream in, JPanel cPanel) {
        this.dis = new DataInputStream(new BufferedInputStream(in));
        this.cPanel = cPanel;
        start();
    }

    public void run() {
        try{
            while(true) {
                Image scaledImg = null;
                // compute the size of the image
                int length = dis.readInt();
                byte[] imageBytes = new byte[length];
                dis.readFully(imageBytes);

                // read the image sent from the server
                serverScreen = ImageIO.read(new ByteArrayInputStream(imageBytes));

                if (serverScreen != null) {
                    scaledImg = serverScreen.getScaledInstance(cPanel.getWidth(), cPanel.getHeight(), Image.SCALE_SMOOTH);
                }

                // add the image to cPanel
                Graphics graphics = cPanel.getGraphics();
                graphics.drawImage(scaledImg, 0, 0, cPanel.getWidth(), cPanel.getHeight(), cPanel);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
