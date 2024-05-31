package Client;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SendEvents implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private Socket cSocket = null;
    private JPanel cPanel = null;
    private PrintWriter writer = null;
    String width = "", height = "";
    double w;
    double h;

    public SendEvents(Socket cSocket, JPanel cPanel, String width, String height) {
        this.cSocket = cSocket;
        this.cPanel = cPanel;
        this.width = width;
        this.height = height;

        this.cPanel.addKeyListener(this);
        this.cPanel.addMouseListener(this);
        this.cPanel.addMouseMotionListener(this);

        w = Double.valueOf(width.trim()).doubleValue();
        h = Double.valueOf(height.trim()).doubleValue();

        try{
            writer = new PrintWriter(cSocket.getOutputStream());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        double xScale = (double) w/cPanel.getWidth();
        double yScale = (double) h/cPanel.getHeight();
        writer.println(Commands.MOVE_MOUSE.getAbbrev());
        writer.println((int) (e.getX() * xScale));
        writer.println((int) (e.getY() * yScale));
        writer.flush();
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        writer.println(Commands.PRESS_MOUSE.getAbbrev());
        int button = e.getButton();
        int xButton = 16;
        if(button == 3) {
            xButton = 4;
        }
        writer.println(xButton);
        writer.flush();
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        writer.println(Commands.RELEASE_MOUSE.getAbbrev());
        int button = e.getButton();
        int xButton = 16;
        if(button == 3) {
            xButton = 4;
        }
        writer.println(xButton);
        writer.flush();
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        writer.println(Commands.PRESS_KEY.getAbbrev());
        writer.println(e.getKeyCode());
        writer.flush();
    }

    public void keyReleased(KeyEvent e) {
        writer.println(Commands.RELEASE_KEY.getAbbrev());
        writer.println(e.getKeyCode());
        writer.flush();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        writer.println(Commands.SCROLL_MOUSE.getAbbrev());
        writer.println(e.getWheelRotation());
        writer.flush();
    }
}
