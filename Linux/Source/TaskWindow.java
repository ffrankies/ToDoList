package pack1;

//import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.Component;
//import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;

public class TaskWindow extends JDialog implements ActionListener{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * The list of tasks to which the task being created/edited
	 * belongs
	 */
	private TaskList list;

	/*
	 * Textfield where user types in a short description of the task
	 */
	private JTextField description;

	/*
	 * TextArea where user types in details for the task
	 */
	private JTextArea details;

	/*
	 * Button that saves the task to the tasklist
	 */
	private JButton okButton;

	/*
	 * Button that cancels changes made to the task
	 */
	private JButton cancelButton;

	/*
	 * Tells whether or not the window should close
	 */
	private boolean closeStatus;

	/*
	 * Colors used in the taskwindow
	 */
	private final Color trans = new Color(1,1,1,0.55f);
	private final Color bckg = Color.WHITE;
	private final Color dark = Color.BLACK;
	private final Color select = Color.LIGHT_GRAY;
	//private final Color due = Color.RED;

	public static final boolean OK = true;
	public static final boolean CANCEL = false;

	/*
	 * The task being created/edited
	 */
	private Task task;

	/*
	 * Panels containing GUI components
	 */
	private JPanel chooserP, textBoxes, dLine, noneP, numdayP, weekdayP;

	/*
	 * X and Y positions of mouse pointer
	 */
	protected int pX;
	protected int pY;

	/*
	 * MyChooser objects that allow the user to select the type of
	 * repeat for the task, the due date for the task, and the
	 * number of days between a task is completed and when it's due
	 * again
	 */
	private MyChooser repeatType, dd, mm, yyyy, days;

	/*
	 * Radio buttons for selecting the days on which the task is due
	 */
	private JRadioButton[] buttons;

	/*
	 * Contains string versions of the repeat types available
	 */
	private ArrayList<String> options;

	/*
	 * Calendar object - obtains task's due date, used in instantiating
	 * other components
	 */
	private GregorianCalendar cal;

	/*
	 * The font used
	 */
	private final Font font = new Font("Cooper Black", Font.PLAIN, 13);
	//private final Font small = new Font("Arial", Font.PLAIN, 12);

	/*
	 * Icons for the cancel and ok buttons (cancel button used close
	 * icon)
	 */
	private ImageIcon closeI;
	private ImageIcon closeIPr;
	private ImageIcon okI;
	private ImageIcon okIPr;


	/*
	 * String containing directory of .exe file
	 */
	private String directory;

	/*
	 * Default constructor for TaskWindow
	 */
	public TaskWindow(JFrame paOccupy, Task task, TaskList list) {
		this.task = task;
		this.list = list;

		directory = list.getDir();
		loadIcons();

		//setTitle("Create a new task");
		closeStatus = CANCEL;
		setSize(300,500);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JSeparator separator = new JSeparator();
		separator.setForeground(dark);

		//Prevent user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		//Creates the transparent panel
		textBoxes = new JPanel();
		textBoxes.setBackground(bckg);
		textBoxes.setLayout(new BoxLayout(textBoxes, BoxLayout.Y_AXIS));

		//Adds title to taskWindow
		JPanel title = new JPanel();
		title.setOpaque(false);
		JLabel titleL;
		if(list.getTasks().contains(task))
			titleL = new JLabel("Edit Task", JLabel.CENTER);
		else
			titleL = new JLabel("Create New Task", JLabel.CENTER);
		titleL.setFont(font);
		title.add(titleL);
		textBoxes.add(title);

		textBoxes.add(separator);

		//Instantiate user input boxes
		//Instantiates the taskName textfield
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("Description:"));
		namePanel.getComponent(0).setFont(font);
		namePanel.setOpaque(false);
		description = new JTextField(task.getTaskName(),15);
		((AbstractDocument) description.getDocument()).setDocumentFilter(
				new DocumentSizeFilter(23));
		setupTextField(description);
		namePanel.add(description);
		textBoxes.add(namePanel);

		//Instantiates the description textfield
		JPanel descPanel = new JPanel();
		descPanel.add(new JLabel("Details:"));
		descPanel.getComponent(0).setFont(font);
		descPanel.setOpaque(false);
		details = new JTextArea(task.getDescription(),4,20);
		setupTextArea(details);
		descPanel.add(details);
		textBoxes.add(descPanel);

		textBoxes.add(separator);

		//Instantiates the repeat textfield
		options = new ArrayList<String>();
		options.add("None");
		options.add("At intervals");
		options.add("Specific days");
		repeatType = new MyChooser(options);
		repeatType.getLeft().addActionListener(this);
		repeatType.getRight().addActionListener(this);
		repeatType.addFont(font);
		JPanel reptPanel = new JPanel();
		reptPanel.add(new JLabel("Repeat:"));
		reptPanel.getComponent(0).setFont(font);
		reptPanel.add(repeatType);
		reptPanel.setOpaque(false);
		textBoxes.add(reptPanel);

		//Instantiates the date chooser
		cal = task.getDate();
		setupChooserP();
		setupNoneP();
		setupNumdayP();
		setupWeekdayP();
		//numdayP.add(chooserP);
		//dLine = noneP;
		if(task.getRepeat() == Repeat.NONE) {
			dLine = noneP;
			repeatType.setText(options.get(0));
		}
		if(task.getRepeat() == Repeat.NUMDAY) {
			numdayP.add(chooserP);
			dLine = numdayP;
			repeatType.setText(options.get(1));
		}
		if(task.getRepeat() == Repeat.SPDAY) {
			dLine = weekdayP;
			repeatType.setText(options.get(2));
		}
		textBoxes.add(dLine);

		//Adds spacing between textBoxes and edge of TaskWindow
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new EmptyBorder(10,-5,0,-5));
		panel.add(textBoxes);
		getContentPane().add(panel);

		JPanel buttons = new JPanel();
		buttons.setOpaque(false);

		okButton = new JButton();
		okButton.setIcon(okI);
		okButton.setPressedIcon(okIPr);
		setButton(okButton);
		cancelButton = new JButton();
		setButton(cancelButton);
		cancelButton.setIcon(closeI);
		cancelButton.setPressedIcon(closeIPr);

		buttons.add(okButton);
		buttons.add(cancelButton);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		//getContentPane().add(buttons, BorderLayout.SOUTH);
		getContentPane().add(buttons);

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

		//setFont();
		setUndecorated(true);
		setBackground(trans);
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == repeatType.getLeft())
			repeatType.prev();
		if(e.getSource() == repeatType.getRight())
			repeatType.next();
		if(e.getSource() == repeatType.getLeft() ||
				e.getSource() == repeatType.getRight()) {
			textBoxes.remove(dLine);
			String rType = repeatType.getText();
			if(rType.equals(options.get(0))) {
				noneP.add(chooserP);
				dLine = noneP;
			}
			if(rType.equals(options.get(1))) {
				numdayP.add(chooserP);
				dLine = numdayP;
			}
			if(rType.equals(options.get(2)))
				dLine = weekdayP;
			textBoxes.add(dLine);
			repaint();
			pack();
		}
		if(e.getSource() == days.getLeft())
			days.prev();
		if(e.getSource() == days.getRight())
			days.next();
		if(e.getSource() == dd.getLeft())
			dd.prev();
		if(e.getSource() == dd.getRight())
			dd.next();
		if(e.getSource() == mm.getLeft()) {
			mm.prev();
			maxDay();
		}
		if(e.getSource() == mm.getRight()) {
			mm.next();
			maxDay();
		}
		if(e.getSource() == yyyy.getLeft()) {
			yyyy.prev();
			maxDay();
		}
		if(e.getSource() == yyyy.getRight()) {
			yyyy.next();
			maxDay();
		}
		if(e.getSource() == okButton) {
			closeStatus = OK;

			//If task is already in list, then editing instead of adding
			//So remove task before adding updated version to list of
			//tasks
			if(list.getTasks().contains(task))
				list.remove(task);

			task.setTaskName(description.getText());
			task.setDescription(details.getText());
			String repeat = repeatType.getText();
			GregorianCalendar dueDate = new GregorianCalendar();
			if(repeat.equals("None")) {
				task.setRepeat(Repeat.NONE);
				dueDate.set(yyyy.getNum(), mm.getNum()-1,
						dd.getNum());
				task.setDate(dueDate);
			}
			else
				if(repeat.equals("At intervals")) {
					task.setRepeat(Repeat.NUMDAY);
					dueDate.set(yyyy.getNum(), mm.getNum()-1,
							dd.getNum());
					task.setDate(dueDate);
					task.setDaysBetween(days.getNum());
				}
				else
					if(repeat.equals("Specific days")) {
						task.setRepeat(Repeat.SPDAY);
						for(int i = 0; i < buttons.length; ++i) {
							if(buttons[i].isSelected())
								task.getWeekdays().add(i+1);
						}
						//task.setDate(new GregorianCalendar(
						//		TimeZone.getTimeZone("EST")));
						Task temp = new Task();
						dueDate = temp.getDate();
						do {
							dueDate.add(Calendar.DATE, 1);
						}
						while(!task.getWeekdays().contains(
								dueDate.get(Calendar.DAY_OF_WEEK)));
						task.setDate(dueDate);
					}
			list.add(task);
			dispose();
		}

		// Makes the dialog disappear, presumably if cancel
		// is pressed
		if(e.getSource() == cancelButton)
			dispose();
	}

	private void setupNoneP() {
		noneP = new JPanel();
		noneP.setOpaque(false);
		noneP.setLayout(new BoxLayout(noneP, BoxLayout.Y_AXIS));

		JPanel labelP = new JPanel();
		labelP.setOpaque(false);
		labelP.add(new JLabel("Due date:", JLabel.CENTER));
		labelP.getComponent(0).setFont(font);
		noneP.add(labelP);
		noneP.add(chooserP);
	}

	private void setupChooserP() {
		chooserP = new JPanel();
		chooserP.setOpaque(false);
		chooserP.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		Border space = new EmptyBorder(0,10,0,10);
		JLabel month = new JLabel("Month", JLabel.CENTER);
		month.setFont(font);
		month.setBorder(space);
		chooserP.add(month,c);
		c.gridx = 1;
		JLabel day = new JLabel("Day", JLabel.CENTER);
		day.setFont(font);
		day.setBorder(space);
		chooserP.add(day,c);
		c.gridx = 2;
		JLabel year = new JLabel("Year", JLabel.CENTER);
		year.setFont(font);
		year.setBorder(space);
		chooserP.add(year,c);
		c.gridx = 0;
		c.gridy = 1;

		mm = new MyChooser(1,12);
		mm.getLeft().addActionListener(this);
		mm.getRight().addActionListener(this);
		mm.setText(cal.get(Calendar.MONTH)+1);
		mm.addFont(font);
		dd = new MyChooser(1,31);
		dd.getLeft().addActionListener(this);
		dd.getRight().addActionListener(this);
		dd.setText(cal.get(Calendar.DAY_OF_MONTH));
		dd.addFont(font);
		yyyy = new MyChooser(2015,2045);
		yyyy.getLeft().addActionListener(this);
		yyyy.getRight().addActionListener(this);
		yyyy.setText(cal.get(Calendar.YEAR));
		yyyy.addFont(font);
		maxDay();

		chooserP.add(mm,c);
		c.gridx = 1;
		chooserP.add(dd,c);
		c.gridx = 2;
		chooserP.add(yyyy,c);
	}

	private void setupNumdayP() {
		numdayP = new JPanel();
		numdayP.setOpaque(false);
		numdayP.setLayout(new BoxLayout(numdayP, BoxLayout.Y_AXIS));

		JPanel dayP = new JPanel();
		dayP.setOpaque(false);
		dayP.add(new JLabel("Task repeats every "));
		days = new MyChooser(1,30);
		days.getLeft().addActionListener(this);
		days.getRight().addActionListener(this);
		days.setText(task.getDaysBetween());
		days.addFont(font);
		dayP.add(days);
		dayP.add(new JLabel(" day(s)."));

		numdayP.add(dayP);

		JPanel labelP = new JPanel();
		labelP.setOpaque(false);
		labelP.add(new JLabel("Starting from:", JLabel.CENTER));
		numdayP.add(labelP);
	}

	private void setupWeekdayP() {
		weekdayP = new JPanel();
		weekdayP.setOpaque(false);
		weekdayP.setLayout(new BoxLayout(weekdayP, BoxLayout.Y_AXIS));
		JPanel labelP = new JPanel();
		labelP.setOpaque(false);
		labelP.add(new JLabel("On what days is the task due?",
				JLabel.CENTER));
		weekdayP.add(labelP);

		JPanel buttonsP = new JPanel();
		buttonsP.setOpaque(false);
		buttons = new JRadioButton[7];
		String[] daysStrs = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri",
				"Sat"};
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new JRadioButton(daysStrs[i]);
			buttons[i].setBackground(trans);
			buttons[i].setFocusPainted(false);
			buttons[i].setContentAreaFilled(false);
			buttonsP.add(buttons[i]);
		}
		weekdayP.add(buttonsP);
		for(int i: task.getWeekdays())
			buttons[i-1].setSelected(true);
	}

	public boolean getCloseStatus() {
		return closeStatus;
	}

	private void setupTextField(JTextField field) {
		field.setOpaque(false);
		field.setSelectionColor(select);
	}

	private void setupTextArea(JTextArea area) {
		area.setOpaque(false);
		area.setSelectionColor(select);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		((AbstractDocument) area.getDocument()).setDocumentFilter(
				new DocumentSizeFilter(130));
		area.setBorder(new JTextField().getBorder());
	}

	private void maxDay() {
		int month = mm.getNum();
		if(month == 2) {
			if(cal.isLeapYear(yyyy.getNum()))
				dd.changeMax(29);
			else
				dd.changeMax(28);
		}
		else
			if(month == 9 || month == 4 || month == 6 || month == 11)
				dd.changeMax(30);
			else
				dd.changeMax(31);
	}

	/*
	 * Loads icons in use
	 */
	private void loadIcons() {
		closeI = loadImage("/Resources/Close.png");
		closeIPr = loadImage("/Resources/ClosePr.png");
		okI = loadImage("/Resources/Ok.png");
		okIPr = loadImage("/Resources/OkPr.png"); 
	}

	/*
	 * Converts png images to imageIcons to be used for buttons
	 */
	private ImageIcon loadImage(String imageName) {

		ImageIcon image = null;
		image = new ImageIcon(directory + imageName);
		return image;

	}

	/*
	 * Disables button opacity and borders, so the buttons correctly
	 * display images and nothing else.
	 */
	private void setButton(JButton button) {
		button.setOpaque(false);
		button.addActionListener(this);
		button.setFocusPainted(false);
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
	}

}
