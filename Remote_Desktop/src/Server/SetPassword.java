package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetPassword extends JFrame implements ActionListener {
    static String port = "";

    // use to make GUI
    JButton submit;
    JPanel panel;
    JTextField textField;
    String password;
    JLabel label ,label1;

    public SetPassword(String port) {
        this.port = port;

        // making GUI
        label1 = new JLabel("Set Password");
        textField = new JTextField(15);
        label = new JLabel();
        this.setLayout(new BorderLayout());
        submit = new JButton("Submit");
        panel = new JPanel(new GridLayout(2, 1));
        panel.add(label1);
        panel.add(textField);
        panel.add(label);
        panel.add(submit);
        add(panel, BorderLayout.CENTER);
        submit.addActionListener(this);
        setTitle("Set Password");
        setSize(300, 80);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e) {
        password = textField.getText();
        dispose();
        new InitConnection(Integer.parseInt(port), password);
    }
}
