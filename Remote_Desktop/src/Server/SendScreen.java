package Server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class SendScreen extends Thread {
    private final Socket socket;
    private final Robot robot;
    private final Rectangle rect;
    private DataOutputStream dos;

    public SendScreen(Socket socket, Robot robot, Rectangle rect) {
        this.socket = socket;
        this.robot = robot;
        this.rect = rect;
        start();
    }

    @Override
    public void run() {
        try {
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            while (true) {
                BufferedImage img = robot.createScreenCapture(rect);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(img, "png", baos);
                byte[] imageBytes = baos.toByteArray();

                // Send the length of the image
                dos.writeInt(imageBytes.length);
                // Send the image bytes
                dos.write(imageBytes);
                dos.flush();  // Ensure the image is sent

                // Sleep to control frame rate
                Thread.sleep(10);  // Adjust as needed for desired frame rate
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
