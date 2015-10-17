package pack1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class TaskWindow extends JDialog implements ActionListener{

	private TaskList list;
	
	//private DateComboBox dateChooser;

	private JTextField name;

	private JTextField deadline;

	private JTextField important;
	
	private JTextField description;

	private JButton okButton;

	private JComboBox<String> repeatType;
	
	private JButton cancelButton;

	private boolean closeStatus;
	
	private final Color bckg = new Color(1,1,1,0.55f);
	private final Color trans = new Color(1,1,1,0f);
	private final Color select = new Color(1,1,1,0.3f);

	public static final boolean OK = true;
	public static final boolean CANCEL = false;

	private Task task;
	
	private JPanel noneP, numdayP, weekdayP;

	private SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");

	protected int pX;

	protected int pY;


	public TaskWindow(JFrame paOccupy, Task task, TaskList list) {
		this.task = task;
		this.list = list;

		fmt.setLenient(false);
//		bckg = new Color(1,1,1,0.55f);
//		trans = new Color(1,1,1,0f);

		setTitle("Create a new task");
		closeStatus = CANCEL;
		setSize(300,500);

		//Prevent user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		//Creates the transparent panel
		JPanel textBoxes = new JPanel();
		textBoxes.setBackground(bckg);
		textBoxes.setLayout(new GridLayout(6,1));
		textBoxes.setOpaque(false);
		
		//Instantiate text input boxes
		//Instantiates the taskName textfield
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("Description:"));
		namePanel.setOpaque(false);
		name = new JTextField(task.getTaskName(),15);
		setupTextField(name);
		namePanel.add(name);
		textBoxes.add(namePanel);
		
		//Instantiates the description textfield
		JPanel descPanel = new JPanel();
		descPanel.add(new JLabel("Details:"));
		descPanel.setOpaque(false);
		description = new JTextField(task.getDescription(),20);
		setupTextField(description);
		descPanel.add(description);
		textBoxes.add(descPanel);

		//Instantiates the repeat textfield
		//textBoxes.add(new JLabel("Repeat:"));
		ArrayList<String> options = new ArrayList();
		options.add("None");
		options.add("Every x days");
		options.add("Specific days");//{"None", "Every x days", "Specific Days"};
		MyChooser<String> repeatType = new MyChooser<String>(options);
		JPanel reptPanel = new JPanel();
		reptPanel.add(new JLabel("Repeat:"));
		reptPanel.add(repeatType);
		//reptPanel.setAlignmentY(CENTER_ALIGNMENT);
		reptPanel.setOpaque(false);
//		Box box = new Box(BoxLayout.Y_AXIS);
//		box.add(repeatType);
		textBoxes.add(reptPanel);
		//Instantiates the deadline textfield
		textBoxes.add(new JLabel("Deadline for the task:"));
		//		GregorianCalendar cal = new GregorianCalendar(
		//				TimeZone.getTimeZone("EST"));
		//		Date date = cal.getTime();
		Date date = task.getDate().getTime();
		//choose = new JButton(fmt.format(date));
		//dateChooser = new DateComboBox(fmt);
		//choose.addActionListener(this);
		deadline = new JTextField(fmt.format(date),30);
		deadline.setBackground(trans);
		deadline.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				repaint();
			}
		});
		textBoxes.add(deadline);

		JPanel impPanel = new JPanel();
		impPanel.add(new JLabel("Is the task urgent?"));
		impPanel.setOpaque(false);
		//important = new JTextField("Yes/No",30);
		String imp;
		if(task.isImportant())
			imp = "Yes";
		else
			imp = "No";
		important = new JTextField(imp,10);
		setupTextField(important);
		impPanel.add(important);
		textBoxes.add(impPanel);

		getContentPane().add(textBoxes, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setBackground(bckg);

		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		buttons.add(okButton);
		buttons.add(cancelButton);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		getContentPane().add(buttons, BorderLayout.SOUTH);

		// Add mouse listener for this frame
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me)
			{
				// Get x,y and store them
				pX=me.getX();
				pY=me.getY();
				repaint();
			}
		});

		// Add MouseMotionListener for detecting drag
		addMouseMotionListener(new MouseAdapter(){
			public void mouseDragged(MouseEvent me)
			{
				// Set the location
				// get the current location x-co-ordinate and then get
				// the current drag x co-ordinate, add them and subtract 
				// most recent mouse pressed x co-ordinate
				// do same for y co-ordinate
				setLocation(getLocation().x+me.getX()-
						pX,getLocation().y+me.getY()-pY);
			}
		});

		setUndecorated(true);
		setBackground(bckg);
		setSize(300,200);
		//pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		//JButton button = (JButton) e.getSource();
		//Fills the Site if OK is clicked
//		if(e.getSource() == choose) {
//			dateChooser = new DateComboBox(fmt);
//			dateChooser.
//		}
		if(e.getSource() == okButton) {
			closeStatus = OK;

			//If task is already in list, then editing instead of adding
			//So remove task before adding updated version to list of 
			//tasks
			if(list.getTasks().contains(task))
				list.remove(task);

			Date date;

			try {
				GregorianCalendar dueDate = new GregorianCalendar();
				date = fmt.parse(deadline.getText());
				dueDate.setTime(date);
				task.setDate(dueDate);
				//date = fmt.parse(deadline.getText());
				task.setTaskName(name.getText());
				task.setDescription(description.getText());
				//task.setDate(deadline.getText());
				if(important.getText().toLowerCase().equals("yes"))
					task.setImportant(true);
				if(important.getText().toLowerCase().equals("no"))
					task.setImportant(false);
			}
			catch ( ParseException exception ) {
				exception.printStackTrace();
			}

			//Adds edited/newly created task to list
			list.add(task);
		}

		// Makes the dialog disappear, presumably if cancel
		// is pressed
		if(e.getSource() == cancelButton)
			dispose();
	}

	public boolean getCloseStatus() {
		return closeStatus;
	}
	
	public void setupTextField(JTextField field) {
		field.setBackground(trans);
		field.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				repaint();
			}
		});
		field.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				repaint();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				repaint();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				repaint();
			}
			
		});
		field.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				repaint();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				repaint();
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				repaint();
			}
			
		});
	}
}
