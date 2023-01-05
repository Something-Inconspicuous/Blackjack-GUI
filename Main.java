
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;



class Main{
    JFrame frame;
    JPanel pane;

    JLabel tempLabel;


    public Main(){
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pane = new JPanel();

        tempLabel = new JLabel("TEST");


        pane.add(tempLabel);

        frame.setContentPane(pane);

        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    private static void runGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        Main gui = new Main(); //ignore this error
    }
  
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runGUI();
            }
        });
    }
}
