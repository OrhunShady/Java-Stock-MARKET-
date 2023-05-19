import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class arasayfa extends JFrame {
    private JButton buttonstock;
    private JButton buttoncurrent;
    private JButton cancelButton;
    private JPanel arayüz;
    private JLabel txtwelcome;
    private JLabel txtstock;
    private JLabel txtcurrent;
    private JLabel txtexit;


    public arasayfa() {
        setTitle("Connection Page");
        setContentPane(arayüz);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        buttonstock.setPreferredSize(new Dimension(150,50));
        buttoncurrent.setPreferredSize(new Dimension(150,50));
        cancelButton.setPreferredSize(new Dimension(150,50));
        setLocation(400, 50);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        


        buttonstock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string =txtstock.getText();
                stockmarket stockmarket = new stockmarket();
                stockmarket.setVisible(true);
                setVisible(false);
            }
        });

        buttoncurrent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentstock currentstock = new currentstock();
                currentstock.setVisible(true);
                setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });



        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                arasayfa arasayfa = new arasayfa();
            }
        });
    }
}


