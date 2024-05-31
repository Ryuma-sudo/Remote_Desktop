package Server;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ReceiveEvent extends Thread {
    Socket sSocket = null;
    Robot robot = null;
    boolean continueLoop = true;

    public ReceiveEvent(Socket sSocket, Robot robot) {
        this.sSocket = sSocket;
        this.robot = robot;
        start();
    }

    public void run() {
        Scanner scanner = null;
        try{
            scanner =  new Scanner(sSocket.getInputStream());

            while(continueLoop){
                int command = scanner.nextInt();

                switch(command){
                    case -1:
                        robot.mousePress(scanner.nextInt());
                        break;
                    case -2:
                        robot.mouseRelease(scanner.nextInt());
                        break;
                    case -3:
                        robot.keyPress(scanner.nextInt());
                        break;
                    case -4:
                        robot.keyRelease(scanner.nextInt());
                        break;
                    case -5:
                        robot.mouseMove(scanner.nextInt(), scanner.nextInt());
                        break;
                    case -6:
                        robot.mouseWheel(scanner.nextInt());
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
