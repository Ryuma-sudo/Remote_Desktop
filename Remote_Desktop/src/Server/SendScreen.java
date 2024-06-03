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
    private final int framePeriod = 10;
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
                // capture server's screen
                BufferedImage img = robot.createScreenCapture(rect);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(img, "png", baos);
                byte[] imageBytes = baos.toByteArray();

                // send the length of the image
                dos.writeInt(imageBytes.length);
                // send the image bytes
                dos.write(imageBytes);
                dos.flush();  // ensure the image is sent

                // sleep to control frame rate
                Thread.sleep(framePeriod); // theoretically (1000 / framePeriod) fps (does not count the transfer speed)
            }

        // error catching
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
