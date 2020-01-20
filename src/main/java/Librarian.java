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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableColumnModel;
import javax.swing.RowFilter;

class Borrow extends JDialog
{
	JTable table;
	JScrollPane scroll_pane;
	DefaultTableModel model;
	Panel main_panel, secondary_panel, input_panel;
	Button check_out, add_book, remove_book, exit;
	JTextField id_input;
	Label id_label, info;
	Borrow()
	{
		main_panel = new Panel();
		main_panel.setLayout(new BorderLayout());
		id_label = new Label("Podaj id egzemplarza");
		id_input = new JTextField();
		input_panel = new Panel();
		input_panel.setLayout(new BoxLayout(input_panel, BoxLayout.LINE_AXIS));
		input_panel.add(id_label);
		input_panel.add(id_input);
		
		secondary_panel = new Panel();
		secondary_panel.setLayout(new BoxLayout(secondary_panel, BoxLayout.PAGE_AXIS));
		check_out = new Button("Wypożycz");
	  	add_book = new Button("Dodaj książkę");
		remove_book = new Button("Usuń książkę");
		exit = new Button("Wyjdź");

		exit.addActionListener(
							   new ActionListener()
							   {
								   public void actionPerformed(ActionEvent event)
								   {
									   dispose();
								   }
							   });
		check_out.addActionListener(
									new ActionListener()
									{
										public void actionPerformed(ActionEvent event)
										{
											check_out();
										}
									});
		remove_book.addActionListener(
									  new ActionListener()
									  {
										  public void actionPerformed(ActionEvent event)
										  {
											  remove_book();
										  }
									  });
		add_book.addActionListener(
								   new ActionListener()
								   {
									   public void actionPerformed(ActionEvent event)
									   {
										   add_book();
									   }
								   });
				
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

	void check_out()
	{
		
	}

	void add_book()
	{
		
	}

	void remove_book()
	{
		
	}
}

class SearchLibrarian extends JDialog
{
	JTable table;
	Button search_button, exit_button;
	DefaultTableModel model;
	JTextField search_input;
	JScrollPane scroll_pane;
	Panel main_panel, secondary_panel;

	SearchLibrarian()
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
												search();
											}
										});

		
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Wyszukiwanie");
		setSize(800, 600);
		setResizable(false);
		load_books();
		setVisible(true);
	}

	void load_books()
	{
		model.setRowCount(0);
        try
			{
				PreparedStatement st = DBConnection.get().prepareStatement
					("SELECT call_number, books.isbn, title," + "authors.name, authors.last_name," + "if(books.isbn in (select books.isbn from copies join reservations on call_number = id UNION " + "SELECT books.isbn from copies join borrow on call_number = copy_id), 'Niedostępny', 'Dotępny')" + " FROM copies JOIN books ON copies.isbn = books.isbn " + "JOIN written_by ON books.isbn = written_by.isbn " + "JOIN authors ON written_by.author_id = authors.id;");
				ResultSet rs = st.executeQuery();
				if (rs.next())
					{
						model.addRow(new String[]{
								rs.getString(1), rs.getString(2), rs.getString(3),
								rs.getString(4) + " " + rs.getString(5), rs.getString(6)});
					}
			}
        catch(SQLException e)
			{
				System.out.println("Problemy z załadowaniem książek");
			}
	}

	void search()
	{
		try
			{
				TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
				table.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(search_input.getText()));
			}
		catch(Exception e)
			{
				System.out.println("Problemy z searchem");
			}
	}
}

class AddClient extends JDialog
{
	Panel button_panel, main_panel, login_panel, provide_panel, confirm_panel;
	Label infos, label_login, label_provide, label_confirm;
	JTextField login_input;
	JPasswordField provide, confirm;
	Button add, exit;
	AddClient()
	{
		main_panel = new Panel();
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.PAGE_AXIS));

		infos = new Label();
		
		label_login = new Label("Podaj login");
		
		login_panel = new Panel();
		login_panel.setLayout(new BoxLayout(login_panel, BoxLayout.LINE_AXIS));
		
		login_input = new JTextField();
		login_panel.add(label_login);
		login_panel.add(login_input);
		
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
									  add_client();
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
	
	void add_client()
	{
		String login, password, confirmed, name, surname;
		login = login_input.getText();
		password = provide.getText();
		confirmed = confirm.getText();
		try
			{
				if(
				   !(login.equals("") || password.equals("") ||
					 confirmed.equals(""))
				   )
					{
						if(password.equals(confirmed))
							{
								PreparedStatement st = DBConnection.get().
									prepareStatement("INSERT INTO client VALUES (?, ?)");
								st.setString(1, login);
								st.setString(2, password);
								ResultSet rs = st.executeQuery();
							}
						else
							{
								infos.setText("Hasła się nie zgadzają");
							}
					}
				else
					{
						infos.setText("Złe dane");
					}
			}
		catch(SQLException e)
			{
				infos.setText("Problem z dodaniem klienta");
			}
		login_input.setText("");
		provide.setText("");
		confirm.setText("");
	}
}

class LibrarianGUI extends JFrame
{
	Panel main_panel;
	Button search, add_client, check_in, exit;

	LibrarianGUI()
	{
		super("Librarian");

		main_panel = new Panel();
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.PAGE_AXIS));

		search = new Button("Wyszukaj");
		add_client = new Button("Dodaj użytkownika");
		check_in = new Button("Wypożyczanie");
		exit = new Button("Wyjdź");
		
		search.addActionListener(
								 new ActionListener()
								 {
									 public void actionPerformed(ActionEvent event)
									 {
										 JDialog search = new SearchLibrarian();
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
											 JDialog add_client = new AddClient();
										 }
									 });
		exit.addActionListener(
							   new ActionListener()
							   {
								   public void actionPerformed(ActionEvent event)
								   {
									   System.exit(0);
								   }
							   });

		
		main_panel.add(search);
		main_panel.add(add_client);
		main_panel.add(check_in);
		main_panel.add(exit);		

		add(main_panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
