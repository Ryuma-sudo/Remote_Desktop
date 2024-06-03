package Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ReceivingScreen extends Thread {
    private final DataInputStream dis;
    private JPanel cPanel = null;
    Image image1 = null;

    public ReceivingScreen(InputStream in, JPanel cPanel) {
        this.dis = new DataInputStream(new BufferedInputStream(in));
        this.cPanel = cPanel;
        start();
    }

    public void run() {
        try{
            while(true) {
                int length = dis.readInt();
                byte[] imageBytes = new byte[length];
                dis.readFully(imageBytes);

                image1 = ImageIO.read(new ByteArrayInputStream(imageBytes));
                if (image1 != null) {
                    image1 = image1.getScaledInstance(cPanel.getWidth(), cPanel.getHeight(), Image.SCALE_FAST);
//                    SwingUtilities.invokeLater(() -> cPanel.repaint());
                }

                Graphics graphics = cPanel.getGraphics();
                graphics.drawImage(image1, 0, 0, cPanel.getWidth(), cPanel.getHeight(), cPanel);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
