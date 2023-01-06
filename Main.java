/*
 * Title: Blackjack
 * Date Started: 1/5/23
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Main{
    // BACKEND
    private static Player user = new Player(); // for testing, will create multiple users later
    
    // FRONTEND
    JFrame frame;
    JPanel pane;

    JLabel tempLabel;
    JButton tempButton;



    public Main(){
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pane = new JPanel();

        tempLabel = new JLabel("TEST");
        tempButton = new JButton("Hello World");

        pane.add(tempLabel);
        pane.add(tempButton);

        frame.setContentPane(pane);

        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    private static void runGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        Main gui = new Main(); //ignore this error
    }
  
    public static void main(String[] args) {
        System.out.println(user.getChipsAmount());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runGUI();
            }
        });
    }
}
