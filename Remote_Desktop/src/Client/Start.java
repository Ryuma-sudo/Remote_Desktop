package Client;

import javax.swing.*;
import java.net.Socket;

public class Start {
    static String port = "2005";

    public void initialize(String ip, int port) {
        try{
            Socket socket = new Socket(ip, port);
            System.out.println("Connecting to the server...");
            Authentication frame1 = new Authentication(socket);
            frame1.setSize(300, 80);
            frame1.setLocation(500, 300);
            frame1.setVisible(true);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String ip = JOptionPane.showInputDialog("Please enter the IP address of the server: ");
        new Start().initialize(ip, Integer.parseInt(port));
    }
}
