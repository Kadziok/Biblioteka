import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    public ClientGUI(String login){
        this.login = login;

        DBConnection.init("Client", "haslo1");

        frame = new JFrame();
        frame.setBounds(100, 100, 650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        model = new tableModel();
        table = new JTable(model);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.EAST);

        String[] options = {"Zbiory", "Wypożyczone", "Rezerwacje"};

        view = new JComboBox<>(options);
        panel.setLayout(new GridLayout(10, 1));

        panel.add(new JLabel("   Jesteś zalogowany jako " + login + "   "));
        panel.add(view);

        JButton btnDodaj = new JButton("Dodaj");
        btnDodaj.addActionListener(this);
        panel.add(btnDodaj);

        JButton btnOtwrz = new JButton("Otwórz");
        btnOtwrz.addActionListener(this);
        panel.add(btnOtwrz);

        loadBooks();

    }

    private void loadBooks(){
        model.setRowCount(0);
        try {
            PreparedStatement st = DBConnection.get().prepareStatement("SELECT isbn, title, year FROM books;");
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                model.addRow(new String[]{rs.getString(1), rs.getString(2), "test", rs.getString(3)});
            }
        }
        catch(SQLException ex){

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private static class tableModel extends DefaultTableModel {
        private final static Object[] colname = { "ISBN", "Tytuł", "Autor", "Status" };

        public tableModel() {
            super(colname, 0);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

    }
}
