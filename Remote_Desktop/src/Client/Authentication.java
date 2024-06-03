package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Authentication extends JFrame implements ActionListener {
    private Socket cSocket = null;
    DataOutputStream password = null; // the password get from the server
    DataInputStream verification = null; // get from the server (is "valid" if the inputPass is correct)
    String verify = ""; // is verification in String

    // used for making GUI
    JButton submit;
    JPanel panel;
    JLabel label, label1;
    String width = "", height = "";
    JTextField text1;

    public Authentication(Socket cSocket) {
        this.cSocket = cSocket;

        // making GUI
        label1 = new JLabel("Enter Password");
        text1 = new JTextField(15);
        label = new JLabel("");
        this.setLayout(new BorderLayout());
        submit = new JButton("Submit");
        panel = new JPanel(new GridLayout(2, 1));
        panel.add(label1);
        panel.add(text1);
        panel.add(label);
        panel.add(submit);
        add(panel, BorderLayout.CENTER);
        submit.addActionListener(this);
        setSize(300, 80);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Client.Authentication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e) {
        String inputPass = text1.getText(); // get the input password from the client

        try{
            // reading the server output stream
            password = new DataOutputStream(cSocket.getOutputStream());
            verification = new DataInputStream(cSocket.getInputStream());
            password.writeUTF(inputPass);
            verify = verification.readUTF();

        }catch (IOException ex) {
            ex.printStackTrace();
        }

        //checking the given password
        if(verify.equals("valid")) {
            try {
                // get the resolution of the server screen
                width = verification.readUTF();
                height = verification.readUTF();
            }catch (IOException ex) {
                ex.printStackTrace();
            }

            // create the remote_desktop frame
            new CreateFrame(cSocket, width, height);
            dispose();
        }else {
            System.out.println("Please enter valid password");
            JOptionPane.showMessageDialog(this, "password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}
