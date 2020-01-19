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
import javax.swing.JComboBox;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.NoSuchAlgorithmException;


class SetAuthor extends JDialog
{
	JTable table;
	String isbn;
	JScrollPane scroll_pane;
	DefaultTableModel model;
	Panel main_panel, secondary_panel, input_panel;
	Button check_out, add_author, remove_author, exit, add_new;
	JComboBox id_input;
	Label id_label, info;
	SetAuthor(String isbn)
	{
		this.isbn = isbn;
		main_panel = new Panel();
		main_panel.setLayout(new BorderLayout());
		id_label = new Label("Podaj id autora");
		id_input = new JComboBox();
		input_panel = new Panel();
		input_panel.setLayout(new BoxLayout(input_panel, BoxLayout.LINE_AXIS));
		input_panel.add(id_label);
		input_panel.add(id_input);
		
		secondary_panel = new Panel();
		secondary_panel.setLayout(new BoxLayout(secondary_panel, BoxLayout.PAGE_AXIS));
		check_out = new Button("Dodaj do bazy danych");
		add_new = new Button("Dodaj nowego autora");
	  	add_author = new Button("Dodaj autora");
		remove_author = new Button("Usuń autora");
		exit = new Button("Wyjdź");

		exit.addActionListener(
							   new ActionListener()
							   {
								   public void actionPerformed(ActionEvent event)
								   {
									   dispose();
								   }
							   });
		remove_author.addActionListener(
										new ActionListener()
										{
											public void actionPerformed(ActionEvent event)
											{
												remove_author();
											}
										});
		add_author.addActionListener(
									 new ActionListener()
									 {
										 public void actionPerformed(ActionEvent event)
										 {
											 add_author();
										 }
									 });
		add_new.addActionListener(
								  new ActionListener()
								  {
									  public void actionPerformed(ActionEvent event)
									  {
										  add_new_author();
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

		secondary_panel.add(input_panel);
		secondary_panel.add(check_out);
		secondary_panel.add(add_author);
		secondary_panel.add(add_new);
		secondary_panel.add(remove_author);
		secondary_panel.add(exit);

		String[] columns = {"Id autora"};
		model = new DefaultTableModel(columns, 0);
		table = new JTable(model);
		
		scroll_pane = new JScrollPane();
		scroll_pane.setViewportView(table);
		main_panel.add(scroll_pane, BorderLayout.LINE_START);
		main_panel.add(secondary_panel, BorderLayout.CENTER);
		add(main_panel);

		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Ustawianie autorów");
		setSize(800, 600);
		setResizable(false);

		load_authors();
		setVisible(true);
	}

	void load_authors()
	{
		
	}

	void check_out()
	{
		
	}

	void add_new_author()
	{
		JDialog add_author = new AddAuthor();
	}

	void add_author()
	{
		
	}

	void remove_author()
	{
		
	}
}

class AddAuthor extends JDialog
{
	Panel button_panel, main_panel, name_panel, surname_panel;
	Label infos, name_label, surname_label;
	JTextField name_input, surname_input;
	Button add, exit;
	AddAuthor()
	{
		main_panel = new Panel();
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.PAGE_AXIS));

		infos = new Label();
		
		name_panel = new Panel();
		name_label = new Label("Podaj imię");
		name_input = new JTextField();
		name_panel.setLayout(new BoxLayout(name_panel, BoxLayout.LINE_AXIS));
		name_panel.add(name_label);
		name_panel.add(name_input);
		
		surname_panel = new Panel();
		surname_label = new Label("Podaj nazwisko");
		surname_input = new JTextField();
		surname_panel.setLayout(new BoxLayout(surname_panel, BoxLayout.LINE_AXIS));
		surname_panel.add(surname_label);
		surname_panel.add(surname_input);
		
		main_panel.add(infos);
		main_panel.add(name_panel);
		main_panel.add(surname_panel);
		
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
									  add_author();
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
		setTitle("Dodawanie autora");
		setSize(800, 600);
		setVisible(true);
		setResizable(false);
	}

	void add_author()
	{
		
	}
}
	
class AddLibrarian extends JDialog
{
	Panel button_panel, main_panel, login_panel, provide_panel, confirm_panel,
		name_panel, surname_panel;
	Label infos, label_login, label_provide, label_confirm,
		name_label, surname_label;
	JTextField login_input, name_input, surname_input;
	JPasswordField provide, confirm;
	Button add, exit;
	AddLibrarian()
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

		name_panel = new Panel();
		name_label = new Label("Podaj imię");
		name_input = new JTextField();
		name_panel.setLayout(new BoxLayout(name_panel, BoxLayout.LINE_AXIS));
		name_panel.add(name_label);
		name_panel.add(name_input);

		
		surname_panel = new Panel();
		surname_label = new Label("Podaj nazwisko");
		surname_input = new JTextField();
		surname_panel.setLayout(new BoxLayout(surname_panel, BoxLayout.LINE_AXIS));
		surname_panel.add(surname_label);
		surname_panel.add(surname_input);
		
		main_panel.add(infos);
		main_panel.add(login_panel);
		main_panel.add(provide_panel);
		main_panel.add(name_panel);
		main_panel.add(surname_panel);
		
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
									  add_librarian();
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
		setTitle("Dodawanie autorów do pozycji");
		setSize(800, 600);
		setVisible(true);
		setResizable(false);
	}

	void add_librarian()
	{
		try
			{
				String login, password, confirmed, name, surname;
				login = login_input.getText();
				password = provide.getText();
				confirmed = confirm.getText();
				name = name_input.getText();
				surname = surname_input.getText();

				if(
				   !(login.equals("") || password.equals("") ||
					 confirmed.equals("") || name.equals("") || surname.equals(""))
				   )
					{
						if(password.equals(confirmed))
							{
								PreparedStatement st = DBConnection.get().
									prepareStatement("INSERT INTO librarian VALUES (?, ?, ?, ?)");
								st.setString(1, login);
								st.setString(2, password);
								st.setString(3, name);
								st.setString(4, surname);
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
				infos.setText("Problem z dodaniem bibliotekarza");
			}
		login_input.setText("");
		password.setText("");
		confirm.setText("");
		name_input.setText("");
		surname_input.setText("");
	}
}

class AddBook extends JDialog
{
	Panel button_panel, main_panel, isbn_panel, title_panel, genre_panel,
		editor_panel, year_panel;
	Label infos, label_isbn, label_title, label_genre,
		editor_label, year_label;
	JTextField isbn_input, editor_input, year_input, title_input, genre_input;
	Button add, exit;
	AddBook()
	{
		main_panel = new Panel();
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.PAGE_AXIS));

		infos = new Label();
		
		label_isbn = new Label("Podaj isbn");
		
		isbn_panel = new Panel();
		isbn_panel.setLayout(new BoxLayout(isbn_panel, BoxLayout.LINE_AXIS));
		
		isbn_input = new JTextField();
		isbn_panel.add(label_isbn);
		isbn_panel.add(isbn_input);
		
		label_title = new Label("Podaj tytuł");
		title_input = new JTextField();
		
		title_panel = new Panel();
		title_panel.setLayout(new BoxLayout(title_panel, BoxLayout.LINE_AXIS));
		title_panel.add(label_title);
		title_panel.add(title_input);
		
		label_genre = new Label("Podaj gatunek"); 
		genre_input = new JTextField();

		genre_panel = new Panel();
		genre_panel.setLayout(new BoxLayout(genre_panel, BoxLayout.LINE_AXIS));
		genre_panel.add(label_genre);
		genre_panel.add(genre_input);

		editor_panel = new Panel();
		editor_label = new Label("Podaj wydawcę");
		editor_input = new JTextField();
		editor_panel.setLayout(new BoxLayout(editor_panel, BoxLayout.LINE_AXIS));
		editor_panel.add(editor_label);
		editor_panel.add(editor_input);

		
		year_panel = new Panel();
		year_label = new Label("Podaj rok wydania");
		year_input = new JTextField();
		year_panel.setLayout(new BoxLayout(year_panel, BoxLayout.LINE_AXIS));
		year_panel.add(year_label);
		year_panel.add(year_input);
		
		main_panel.add(infos);
		main_panel.add(isbn_panel);
		main_panel.add(title_panel);
		main_panel.add(genre_panel);
		main_panel.add(editor_panel);
		main_panel.add(year_panel);
		
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
									  add_book();
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
		setTitle("Dodawanie książki");
		setSize(800, 600);
		setVisible(true);
		setResizable(false);
	}

	void add_book()
	{
		String isbn = isbn_input.getText();
		String editor = editor_input.getText();
		String year = year_input.getText();
		String title = title_input.getText();
		String genre = genre_input.getText();

		try
			{
				if(!(isbn.equals("") || editor.equals("") || year.equals("") || title.equals("") || genre.equals("")))
					{
						PreparedStatement st = DBConnection.get().
							prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?)");
						st.setString(1, isbn);
						st.setString(2, editor);
						st.setString(3, year);
						st.setString(4, title);
						st.setString(5, genre);
						ResultSet rs = st.executeQuery();
					}
			}
		catch(SQLException e)
			{
				infos.setText("Problem z dodaniem książki do bazy danych");
			}
		isbn_input.setText("");
		editor_input.setText("");
		year_input.setText("");
		title_input.setText("");
		genre_input.setText("");
		JDialog set_author = new SetAuthor(isbn); 
	}
}


class SearchAdmin extends JDialog
{
	JTable table;
	Button search_button, exit_button;
	DefaultTableModel model;
	JTextField search_input;
	JScrollPane scroll_pane;
	Panel main_panel, secondary_panel;

	SearchAdmin()
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
		setVisible(true);
	}

	void search()
	{
		System.out.println("Tu bedzie search");
	}
}

class AdminGUI extends JFrame
{
	Panel main_panel;
	Button search, add_librarian, add_book, update_reservations, exit;


	AdminGUI()
	{
		super("Admin");

		main_panel = new Panel();
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.PAGE_AXIS));

		search = new Button("Wyszukaj");
		add_librarian = new Button("Dodaj bibliotekarza");
		add_book = new Button("Dodaj książkę");
		update_reservations = new Button("Zauktualizuj rezerwacje");
		exit = new Button("Wyjdź");
		
		search.addActionListener(
								 new ActionListener()
								 {
									 public void actionPerformed(ActionEvent event)
									 {
										 JDialog search = new SearchAdmin();
									 }
								 });
		add_librarian.addActionListener(
										new ActionListener()
										{
											public void actionPerformed(ActionEvent event)
											{
												JDialog add_librarian = new AddLibrarian();
											}
										});
		add_book.addActionListener(
								   new ActionListener()
								   {
									   public void actionPerformed(ActionEvent event)
									   {
										   JDialog add_book = new AddBook();
									   }
								   });
		update_reservations.addActionListener(
											  new ActionListener()
											  {
												  public void actionPerformed(ActionEvent event)
												  {
													  updateReservations();
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
		main_panel.add(add_book);
		main_panel.add(add_librarian);
		main_panel.add(update_reservations);
		main_panel.add(exit);
		
		add(main_panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setVisible(true);
	}

	void updateReservations()
	{
		try
			{
				PreparedStatement st = DBConnection.get().
					prepareStatement(
									 "DELETE FROM reservations WHERE DATEDIFF(CURDATE(), date) >= 3"
									 );
				ResultSet rs = st.executeQuery();
			}
		catch(SQLException ex)
			{
				System.out.println("Errory here");
			}
	}
}

public class Admin
{
	public static void main(String[] args)
	{
		JFrame admin_gui = new AdminGUI();
	}
}
