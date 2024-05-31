package Server;

import java.util.Scanner;

public class Start {
    public static void main(String[] args) {
        SetPassword frame1 = new SetPassword();
        frame1.setSize(300, 80);
        frame1.setLocation(500, 300);
        frame1.setVisible(true);

        System.out.printf("Type %Cquit%C to exit\n", 34, 34);
        Scanner scanner = new Scanner(System.in);

        while(true) {
            String input = scanner.nextLine();
            if (input.equals("quit")) {
                System.exit(0);
            }
        }
    }
}
