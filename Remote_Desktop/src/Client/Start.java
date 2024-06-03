package Client;

import javax.swing.*;
import java.net.Socket;
import java.util.Scanner;

public class Start {
    static String port = "2055";

    public void initialize(String ip, int port) {
        // connect to the server socket
        try{
            Socket socket = new Socket(ip, port);
            System.out.println("Connecting to the server...");
            // start authenticating
            new Authentication(socket);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.printf("Type %Cquit%C to exit\n", 34, 34);
        Scanner scanner = new Scanner(System.in);

        String ip = JOptionPane.showInputDialog("Please enter the IP address of the server: ");
        new Start().initialize(ip, Integer.parseInt(port));

        // a way forced the program to exit
        while(true) {
            String input = scanner.nextLine();
            if (input.equals("quit")) {
                System.exit(0);
            }
        }
    }
}
