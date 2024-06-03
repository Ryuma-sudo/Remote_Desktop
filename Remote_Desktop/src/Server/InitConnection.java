package Server;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class InitConnection {
    ServerSocket sSocket = null;
    DataOutputStream verify = null;
    DataInputStream password = null;

    public InitConnection(int port, String value1) {
        Robot robot = null; // to control the server desktop
        Rectangle rect = null; // to get the server screen
        try {
            System.out.println("Waiting for initial connection...");
            sSocket = new ServerSocket(port);

            // set up robot
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
            robot = new Robot(gDev);

            // set up rect
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            rect = new Rectangle(dim);

            String width = "" + dim.getWidth();
            String height = "" + dim.getHeight();

            while (true) {
                // connect to the client
                Socket sc = sSocket.accept();
                password = new DataInputStream(sc.getInputStream());
                verify = new DataOutputStream(sc.getOutputStream());

                String pass = password.readUTF();

                if (pass.equals(value1)) {
                    verify.writeUTF("valid");
                    verify.writeUTF(width);
                    verify.writeUTF(height);

                    System.out.println("Connection successful");

                    // continuously send screen and receive event to/from client
                    new SendScreen(sc, robot, rect);
                    new ReceiveEvent(sc, robot);
                }else {
                    verify.writeUTF("invalid");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
