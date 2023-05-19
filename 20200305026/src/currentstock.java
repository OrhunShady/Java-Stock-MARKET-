import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class currentstock extends JFrame {
    private JTable table1;
    private JButton backbutton;
    private JPanel panel1;
    private JScrollPane currentstockpane;

    private Connection connection;
    private ArrayList<String> myArrayList;

    public currentstock() {
        setContentPane(panel1);
        setTitle("Current Stock");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMaximumSize(new Dimension(400, 300));
        setVisible(true);
        setLocation(400, 50);

        myArrayList = new ArrayList<>();

        connectToDatabase();
        loadStockDataFromDatabase();
        backbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arasayfa arasayfa = new arasayfa();
                arasayfa.setVisible(true);
                setVisible(false);
            }
        });
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost/20200305026";
            String username = "root";
            String password = "";

            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void loadStockDataFromDatabase() {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM product";
            ResultSet resultSet = statement.executeQuery(query);

            String[] columnNames = {"ID", "NAME", "QUANTITY"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int quantity = resultSet.getInt("QUANTITY");
                Object[] rowData = {id, name, quantity};
                model.addRow(rowData);


                myArrayList.add(name);
            }
            table1.setModel(model);

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new currentstock();
            }
        });
    }
}
