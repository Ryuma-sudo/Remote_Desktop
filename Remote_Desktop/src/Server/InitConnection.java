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
    String width = "", height = "";

    public InitConnection(int port, String value1) {
        Robot robot = null;
        Rectangle rect = null;
        try {
            System.out.println("Waiting for initial connection...");
            sSocket = new ServerSocket(port);
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            String width = "" + dim.getWidth();
            String height = "" + dim.getHeight();
            rect = new Rectangle(dim);
            robot = new Robot(gDev);

            while (true) {
                Socket sc = sSocket.accept();
                password = new DataInputStream(sc.getInputStream());
                verify = new DataOutputStream(sc.getOutputStream());

                String pass = password.readUTF();

                if (pass.equals(value1)) {
                    verify.writeUTF("valid");
                    verify.writeUTF(width);
                    verify.writeUTF(height);

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
