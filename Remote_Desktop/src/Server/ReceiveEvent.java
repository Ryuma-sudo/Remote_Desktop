package Server;

import Client.Commands;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ReceiveEvent extends Thread {
    Socket sSocket = null;
    Robot robot = null;

    public ReceiveEvent(Socket sSocket, Robot robot) {
        this.sSocket = sSocket;
        this.robot = robot;
        start();
    }

    public void run() {
        Scanner scanner = null;
        try{
            scanner =  new Scanner(sSocket.getInputStream());

            while(true){
                int command = scanner.nextInt();

                switch(command){
                    case -1://PRESS_MOUSE
                        robot.mousePress(scanner.nextInt());
                        break;
                    case -2://RELEASE_MOUSE
                        robot.mouseRelease(scanner.nextInt());
                        break;
                    case -3://PRESS_KEY
                        robot.keyPress(scanner.nextInt());
                        break;
                    case -4://RELEASE_KEY
                        robot.keyRelease(scanner.nextInt());
                        break;
                    case -5://MOVE_MOUSE
                        robot.mouseMove(scanner.nextInt(), scanner.nextInt());
                        break;
                    case -6://SCROLL_MOUSE
                        robot.mouseWheel(scanner.nextInt());
                        break;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
