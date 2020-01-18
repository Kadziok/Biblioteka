import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Panel;
import java.awt.Button;
import java.awt.event.*;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Label;

class Add_client extends JDialog
{
	Panel button_panel, main_panel, login_panel, provide_panel, confirm_panel;
	Label infos, label_login, label_provide, label_confirm;
	JTextField login;
	JPasswordField provide, confirm;
	Button add, exit;
	Add_client()
	{
		main_panel = new Panel();
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.PAGE_AXIS));

		infos = new Label();
		
		label_login = new Label("Podaj login");
		
		login_panel = new Panel();
		login_panel.setLayout(new BoxLayout(login_panel, BoxLayout.LINE_AXIS));
		
		login = new JTextField();
		login_panel.add(label_login);
		login_panel.add(login);
		
		label_provide = new Label("Podaj hasło");
		provide = new JPasswordField();
		
		provide_panel = new Panel();
		provide_panel.setLayout(new BoxLayout(provide_panel, BoxLayout.LINE_AXIS));
		provide_panel.add(label_provide);
		provide_panel.add(provide);
		
		label_confirm = new Label("Potwierdź"); 
		confirm = new JPasswordField();

		confirm_panel = new Panel();
		confirm_panel.setLayout(new BoxLayout(confirm_panel, BoxLayout.LINE_AXIS));
		confirm_panel.add(label_confirm);
		confirm_panel.add(confirm);
		
		main_panel.add(infos);
		main_panel.add(login_panel);
		main_panel.add(provide_panel);
		main_panel.add(confirm_panel);
		
		add = new Button("Dodaj");
		exit = new Button("Wyjdź");
		
		exit.addActionListener(
								 new ActionListener()
								 {
									 public void actionPerformed(ActionEvent event)
									 {
										 dispose();
									 }
								 });
		add.addActionListener(
								 new ActionListener()
								 {
									 public void actionPerformed(ActionEvent event)
									 {
										 LibrarianGUI.add_client();
									 }
								 });

		
		button_panel = new Panel();

		button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.LINE_AXIS));
		
		button_panel.add(add);
		button_panel.add(exit);

		main_panel.add(button_panel);

		add(main_panel);

		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Dodawanie klienta");
		setSize(800,600);
		setVisible(true);
		setResizable(false);
	}
}

class LibrarianGUI extends JFrame
{
	Panel main_panel;
	Button search, add_client, edit_state, check_in, check_out;

	LibrarianGUI()
	{
		super("Librarian");

		main_panel = new Panel();
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.PAGE_AXIS));

		search = new Button("Wyszukaj");
		add_client = new Button("Dodaj użytkownika");
		edit_state = new Button("Jakas nazwa");
		check_in = new Button("Jakas nazwa");
		check_out = new Button("Jakas nazwa");

		search.addActionListener(
								 new ActionListener()
								 {
									 public void actionPerformed(ActionEvent event)
									 {
										 System.out.println("Derpy");
									 }
								 });
		edit_state.addActionListener(
								 new ActionListener()
								 {
									 public void actionPerformed(ActionEvent event)
									 {
										 System.out.println("Derpy");
									 }
								 });
		check_out.addActionListener(
								 new ActionListener()
								 {
									 public void actionPerformed(ActionEvent event)
									 {
										 System.out.println("Derpy");
									 }
								 });
		check_in.addActionListener(
								 new ActionListener()
								 {
									 public void actionPerformed(ActionEvent event)
									 {
										 System.out.println("Derpy");
									 }
								 });
		add_client.addActionListener(
								 new ActionListener()
								 {
									 public void actionPerformed(ActionEvent event)
									 {
										 JDialog add_client = new Add_client();
									 }
								 });

		
		main_panel.add(search);
		main_panel.add(add_client);
		main_panel.add(edit_state);
		main_panel.add(check_in);
		main_panel.add(check_out);

		add(main_panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setVisible(true);
	}

	static void add_client()
	{
		System.out.println("Tutaj dodasz klienta");
	}
}

public class Librarian
{
	public static void main(String[] args)
	{
		JFrame librarian = new LibrarianGUI();
	}
}
