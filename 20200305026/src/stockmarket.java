import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class stockmarket extends JFrame {
    private JTable table1;
    private JButton searchbutton;
    private JButton deletebutton;
    private JTextField txtsearch;
    private JButton savebutton;
    private JButton updatebutton;
    private JTextField txtquantıty;
    private JTextField txtname;
    private JTextField txtid;
    private JPanel manangement;
    private JButton backbutton;
    private Connection connection;

    public stockmarket(){
        setContentPane(manangement);
        setTitle("Stock Manangement");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMaximumSize(new Dimension(400,600));
        setLocation(400,50);
        setVisible(true);

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
        savebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt(txtid.getText());
                String name = txtname.getText();
                int quantity = Integer.parseInt(txtquantıty.getText());

                try {
                    String query = "INSERT INTO product (ID, NAME, QUANTITY) VALUES (?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, id);
                    statement.setString(2, name);
                    statement.setInt(3, quantity);
                    statement.executeUpdate();
                    loadStockDataFromDatabase();
                    txtid.setText("");
                    txtname.setText("");
                    txtquantıty.setText("");
                    JOptionPane.showMessageDialog(null, "Data saved successfully.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while saving data.");
                }

                }



        });
        searchbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = txtsearch.getText();
                try {
                    String query = "SELECT * FROM product WHERE ID LIKE ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, "%" + searchQuery + "%");
                    ResultSet resultSet = statement.executeQuery();

                    String[] columnNames = {"ID", "NAME", "QUANTITY"};
                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                    while (resultSet.next()==true) {
                        String id = String.valueOf(resultSet.getInt("ID"));
                        String name = resultSet.getString("NAME");
                        String quantity = String.valueOf(resultSet.getInt("QUANTITY"));
                        Object[] rowData = {id, name, quantity};
                        model.addRow(rowData);
                        txtid.setText(id);
                        txtname.setText(name);
                        txtquantıty.setText(quantity);
                    }
                    table1.setModel(model);
                    resultSet.close();
                    statement.close();


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        updatebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to update.");
                    return;

                }
                {


                }
                int id = Integer.parseInt(txtid.getText());
                String name = txtname.getText();
                int quantity = Integer.parseInt(txtquantıty.getText());
                try {
                    String query = "UPDATE product SET NAME = ?, QUANTITY = ? WHERE ID = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setInt(2, quantity);
                    statement.setInt(3, id);
                    statement.executeUpdate();
                    loadStockDataFromDatabase();
                    txtid.setText("");
                    txtname.setText("");
                    txtquantıty.setText("");
                    JOptionPane.showMessageDialog(null, "Data updated successfully.");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        deletebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                    return;
            }
                int id = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
                try {
                    String query = "DELETE FROM product WHERE ID = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, id);
                    statement.executeUpdate();
                    loadStockDataFromDatabase();
                    txtid.setText("");
                    txtname.setText("");
                    txtquantıty.setText("");
                    JOptionPane.showMessageDialog(null, "Data deleted successfully.");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    private void loadStockDataFromDatabase() {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM product";
            PreparedStatement statement1 =connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);

            String[] columnNames ={"ID","NAME","QUANTITY"};
            DefaultTableModel model = new DefaultTableModel(columnNames,0);

            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int quantity = resultSet.getInt("QUANTITY");
                Object[] rowData = {id,name,quantity};
                model.addRow(rowData);
            }
            table1.setModel(model);




            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void connectToDatabase(){
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new stockmarket();

            }
        });
    }





}
