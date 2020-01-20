import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientGUI implements ActionListener {
    private final String login;
    private JFrame frame;
    private tableModel model;
    private JTable table;
    private JComboBox<String> view;
    private JButton res, filter;
    private JTextField isbn;

    public ClientGUI(String login){
        this.login = login;

        DBConnection.init("Client", "haslo1");

        frame = new JFrame();
        frame.setBounds(100, 100, 650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        model = new tableModel(new Object[] { "Sygnatura", "ISBN", "Tytuł", "Autor", "Status" });
        table = new JTable(model);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.EAST);

        String[] options = {"Zbiory", "Wypożyczone", "Rezerwacje"};

        view = new JComboBox<>(options);
        view.addActionListener(this);
        panel.setLayout(new GridLayout(14, 2));

        panel.add(new JLabel("   Jesteś zalogowany jako " + login + "   "));
        panel.add(new JLabel(""));
        panel.add(new JLabel("Przeglądaj: "));
        panel.add(view);

        //filter = new JButton("Filtruj");
        //filter.addActionListener(this);
        panel.add(new JLabel("Szukaj wyrażenia: "));
        isbn = new JTextField();
        panel.add(isbn);

        panel.add(new JLabel(""));
        res = new JButton("Rezerwuj");
        res.addActionListener(this);
        panel.add(res);

        isbn.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                filter();
            }
            public void removeUpdate(DocumentEvent e) {
                filter();
            }
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

        });

        for(int i = 0; i < 19; i++){
            panel.add(new JLabel(""));
        }

        loadBooks();

    }

    private void loadBooks(){
        model.setRowCount(0);
        try {
            PreparedStatement st = DBConnection.get().prepareStatement("SELECT call_number, books.isbn, title," +
                    "GROUP_CONCAT(CONCAT(authors.name, ' ', authors.last_name) SEPARATOR ', '), " +
                    "if(call_number in (select id from reservations UNION " +
                    "SELECT copy_id from borrow), 'Niedostępny', 'Dostępny') " +
                    "FROM copies JOIN books ON copies.isbn = books.isbn " +
                    "JOIN written_by ON books.isbn = written_by.isbn " +
                    "JOIN authors ON written_by.author_id = authors.id GROUP BY call_number;");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                model.addRow(new String[]{rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4),
                        rs.getString(5)});
            }
        }
        catch(SQLException e){
                e.printStackTrace();
        }
    }

    private void loadReservations(){
        model.setRowCount(0);
        try {
            PreparedStatement st = DBConnection.get().prepareStatement("SELECT reservations.id, books.isbn, title," +
                    "GROUP_CONCAT(CONCAT(authors.name, ' ', authors.last_name) SEPARATOR ', '), " +
                    "DATE_ADD(reservations.date, INTERVAL 3 DAY) " +
                    "FROM reservations JOIN copies ON reservations.id = copies.call_number " +
                    "JOIN books ON copies.isbn = books.isbn " +
                    "JOIN written_by ON books.isbn = written_by.isbn " +
                    "JOIN authors ON written_by.author_id = authors.id WHERE reservations.login = ? GROUP BY books.isbn;");
            st.setString(1, login);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                model.addRow(new String[]{rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4),
                        rs.getString(5)});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadBorrow(){
        model.setRowCount(0);
        try {
            PreparedStatement st = DBConnection.get().prepareStatement("SELECT borrow.id, books.isbn, title," +
                    "GROUP_CONCAT(CONCAT(authors.name, ' ', authors.last_name) SEPARATOR ', '), " +
                    "DATE_ADD(borrow.date, INTERVAL 1 MONTH) " +
                    "FROM borrow JOIN copies ON borrow.copy_id = copies.call_number " +
                    "JOIN books ON copies.isbn = books.isbn " +
                    "JOIN written_by ON books.isbn = written_by.isbn " +
                    "JOIN authors ON written_by.author_id = authors.id WHERE borrow.login = ? GROUP BY books.isbn;");
            st.setString(1, login);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                model.addRow(new String[]{rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4),
                        rs.getString(5)});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void filter(){
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        table.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("(?i)" + isbn.getText()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();

        if(name.equals("Rezerwuj")){
            if (table.getSelectedRow() < 0){
                JOptionPane.showMessageDialog(frame, "Nie wybrano egzemplarza");
                return;
            }
            if(table.getValueAt(table.getSelectedRow(), 4).toString().equals("Niedostępny")){
                JOptionPane.showMessageDialog(frame, "Wybrany egzemplarz nie jest dostępny");
            } else {
                try {
                    PreparedStatement st = DBConnection.get().prepareStatement("INSERT INTO reservations " +
                            "(id, date, login) VALUES (?, NOW(), ?);");
                    st.setString(1, table.getValueAt(table.getSelectedRow(), 0).toString());
                    st.setString(2, login);
                    int i = st.executeUpdate();
                    if(i > 0){
                        JOptionPane.showMessageDialog(frame, "Zarezerowano egzemplarz");
                    } else
                        JOptionPane.showMessageDialog(frame, "Operacja nie powiodła się");
                    loadBooks();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else if(name.equals("comboBoxChanged")) {
            name = (String) view.getSelectedItem();
            if (name.equals("Zbiory")) {
                res.setEnabled(true);
                table.getTableHeader().getColumnModel().getColumn(4).setHeaderValue("Status");
                loadBooks();
            } else if (name.equals("Wypożyczone")) {
                res.setEnabled(false);
                table.getTableHeader().getColumnModel().getColumn(4).setHeaderValue("Wypożyczone do");
                loadBorrow();
            } else if (name.equals("Rezerwacje")) {
                res.setEnabled(false);
                table.getTableHeader().getColumnModel().getColumn(4).setHeaderValue("Odbiór do");
                loadReservations();
            }
        }

    }

    private static class tableModel extends DefaultTableModel {
        //private final static Object[] colname = { "Sygnatura", "ISBN", "Tytuł", "Autor", "Status" };

        public tableModel(Object[] colname) {
            super(colname, 0);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

    }
}
