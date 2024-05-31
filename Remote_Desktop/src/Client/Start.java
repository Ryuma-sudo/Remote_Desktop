package Client;

import javax.swing.*;
import java.net.Socket;
import java.util.Scanner;

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

        System.out.printf("Type %Cquit%C to exit\n", 34, 34);
        Scanner scanner = new Scanner(System.in);

        while(true) {
            String input = scanner.nextLine();
            if (input.equals("quit")) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        String ip = JOptionPane.showInputDialog("Please enter the IP address of the server: ");
        new Start().initialize(ip, Integer.parseInt(port));
    }
}
