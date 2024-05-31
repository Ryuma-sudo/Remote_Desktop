package Server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SendScreen extends Thread {
    Socket socket = null;
    Robot robot = null;
    Rectangle rect = null;
    OutputStream oos = null;

    public SendScreen(Socket sSocket, Robot robot, Rectangle rect) {
        this.socket = sSocket;
        this.robot = robot;
        this.rect = rect;
        start();
    }

    public void run() {
        try {
            oos = socket.getOutputStream();

        }catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            BufferedImage img = robot.createScreenCapture(rect);
            try {
                ImageIO.write(img, "jpeg", oos);
            }catch (IOException e) {
                e.printStackTrace();
            }
            try{
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
