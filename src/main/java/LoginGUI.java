import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

/**
 * A Simple Login Dialog
 *
 * @author Oliver Watkins (c)
 */
public class LoginGUI extends JDialog {

    JLabel nameLabel = new JLabel("Name : ");
    JLabel passwordLabel = new JLabel("Password : ");

    JTextField nameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

    JButton okButton = new JButton("OK");
    JButton cancelButton = new JButton("Cancel");

    public LoginGUI() {
        setupUI();

        setUpListeners();

    }

    public void setupUI() {

        this.setTitle("Login");

        JPanel topPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(4, 4, 4, 4);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        topPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        topPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        topPanel.add(passwordLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        topPanel.add(passwordField, gbc);

        this.add(topPanel);

        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    private void setUpListeners() {

        passwordField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
            }
        }
        });

        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGUI.this.setVisible(false);
            }
        });
    }


    private void login() {
        String userName = nameField.getText();
        String password = passwordField.getText();
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");

            m.reset();
            m.update(password.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            String hashtext = bigInt.toString(16);
            while(hashtext.length() < 32 ){
                hashtext = "0"+hashtext;
            }
            PreparedStatement st = DBConnection.get().prepareStatement("SELECT login, permissions FROM users" +
                    " WHERE login = ? AND pass = ?;");
            st.setString(1, userName);
            st.setString(2, hashtext);
            ResultSet rs=st.executeQuery();

            if(rs.next()) {
                System.out.println("You logged in!");// + rs.getString(1));

                DBConnection.close();
                ClientGUI client = new ClientGUI(userName);
                this.setVisible(false);
            }
            else
                System.out.println("Incorrect input");

        } catch (NoSuchAlgorithmException | SQLException ex) {
            ex.printStackTrace();
        }

        //LoginGUI.this.setVisible(false);
    }

    public static void main(String[] args) {

        DBConnection.init("Client", "haslo1");
        LoginGUI ld = new LoginGUI();

        ld.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ld.setBounds(500, 300, 300, 150);
        ld.setVisible(true);

    }
}