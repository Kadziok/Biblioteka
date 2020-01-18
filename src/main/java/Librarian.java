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
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.ScrollPane;

class Borrow extends JDialog
{
	JTable table;
	JScrollPane scroll_pane;
	DefaultTableModel model;
	Panel main_panel, secondary_panel, input_panel;
	Button check_out, add_book, remove_book, exit;
	JTextField isbn_input;
	Label isbn_label, info;
	Borrow()
	{
		main_panel = new Panel();
		main_panel.setLayout(new BorderLayout());
		isbn_label = new Label("Podaj id egzemplarza");
		isbn_input = new JTextField();
		input_panel = new Panel();
		input_panel.setLayout(new BoxLayout(input_panel, BoxLayout.LINE_AXIS));
		input_panel.add(isbn_label);
		input_panel.add(isbn_input);
		
		secondary_panel = new Panel();
		secondary_panel.setLayout(new BoxLayout(secondary_panel, BoxLayout.PAGE_AXIS));
		check_out = new Button("Wypożycz");
	  	add_book = new Button("Dodaj książkę");
		remove_book = new Button("Usuń książkę");
		exit = new Button("Wyjdź");
		
		secondary_panel.add(input_panel);
		secondary_panel.add(check_out);
		secondary_panel.add(add_book);
		secondary_panel.add(remove_book);
		secondary_panel.add(exit);

		String[] columns = {"Id książki"};
		model = new DefaultTableModel(columns, 0);
		table = new JTable(model);
		
		scroll_pane = new JScrollPane();
		scroll_pane.setViewportView(table);
		main_panel.add(scroll_pane, BorderLayout.LINE_START);
		main_panel.add(secondary_panel, BorderLayout.CENTER);
		add(main_panel);

		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Wypożyczanie");
		setSize(800, 600);
		setResizable(false);
		setVisible(true);
	}
}

class Search extends JDialog
{
	JTable table;
	Button search_button, exit_button;
	DefaultTableModel model;
	JTextField search_input;
	JScrollPane scroll_pane;
	Panel main_panel, secondary_panel;

	Search()
	{
		exit_button = new Button("Wyjdź");
		scroll_pane = new JScrollPane();
		main_panel = new Panel();
		main_panel.setLayout(new BorderLayout());

		String[] columns = {"ISBN", "Tytuł", "Gatunek", "Wydawca", "Rok"};

		model = new DefaultTableModel(columns, 0);
		table = new JTable(model);
		scroll_pane.setViewportView(table);
		main_panel.add(scroll_pane, BorderLayout.CENTER);
		secondary_panel = new Panel();
		search_button = new Button("Wyszukaj");
		search_input = new JTextField(40);
		secondary_panel.setLayout(new BoxLayout(secondary_panel, BoxLayout.PAGE_AXIS));
		secondary_panel.add(search_input);
		secondary_panel.add(search_button);
		secondary_panel.add(exit_button);
		main_panel.add(secondary_panel, BorderLayout.PAGE_END);
		add(main_panel);

		exit_button.addActionListener(
								 new ActionListener()
								 {
									 public void actionPerformed(ActionEvent event)
									 {
										 dispose();
									 }
								 });
		search_button.addActionListener(
								 new ActionListener()
								 {
									 public void actionPerformed(ActionEvent event)
									 {
										 //search();
									 }
								 });

		
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Wyszukiwanie");
		setSize(800, 600);
		setResizable(false);
		setVisible(true);
	}

	void search()
	{
		System.out.println("Tu bedzie search");
	}
}

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
										 //LibrarianGUI.add_client();
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
		setSize(800, 600);
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
		check_in = new Button("Wypożyczanie");

		search.addActionListener(
								 new ActionListener()
								 {
									 public void actionPerformed(ActionEvent event)
									 {
										 JDialog search = new Search();
									 }
								 });
		check_in.addActionListener(
								 new ActionListener()
								 {
									 public void actionPerformed(ActionEvent event)
									 {
										 JDialog borrow = new Borrow();
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
		main_panel.add(check_in);
		
		add(main_panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setVisible(true);
	}
}

public class Librarian
{
	public static void main(String[] args)
	{
		JFrame librarian = new LibrarianGUI();
	}
}
