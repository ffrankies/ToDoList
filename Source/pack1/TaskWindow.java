package pack1;

import java.awt.BorderLayout;
import java.awt.Color;
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

	private TaskList list;

	private JTextField name;

	private JTextArea details;

	private JButton okButton;

	private JButton cancelButton;

	private boolean closeStatus;

	private final Color trans = new Color(1,1,1,0.55f);
	private final Color bckg = Color.WHITE;
	private final Color dark = Color.BLACK;
	private final Color select = Color.LIGHT_GRAY;
	//private final Color due = Color.RED;

	public static final boolean OK = true;
	public static final boolean CANCEL = false;

	private Task task;

	private JPanel chooserP, textBoxes, dLine, noneP, numdayP, weekdayP;

	protected int pX;

	protected int pY;

	private MyChooser repeatType, dd, mm, yyyy, days;

	private JRadioButton[] buttons;

	private ArrayList<String> options;

	private GregorianCalendar cal;

	public TaskWindow(JFrame paOccupy, Task task, TaskList list) {
		this.task = task;
		this.list = list;

		setTitle("Create a new task");
		closeStatus = CANCEL;
		setSize(300,500);

		JSeparator separator = new JSeparator();
		separator.setForeground(dark);
		
		//Prevent user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		//Creates the transparent panel
		textBoxes = new JPanel();
		textBoxes.setBackground(bckg);
		textBoxes.setBorder(new JTextField().getBorder());
		textBoxes.setLayout(new BoxLayout(textBoxes, BoxLayout.Y_AXIS));

		//Adds title to taskWindow
		JPanel title = new JPanel();
		title.setOpaque(false);
		if(list.getTasks().contains(task))
			title.add(new JLabel("Edit Task", JLabel.CENTER));
		else
			title.add(new JLabel("Create New Task", JLabel.CENTER));
		textBoxes.add(title);

		textBoxes.add(separator);

		//Instantiate user input boxes
		//Instantiates the taskName textfield
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("Description:"));
		namePanel.setOpaque(false);
		name = new JTextField(task.getTaskName(),15);
		((AbstractDocument) name.getDocument()).setDocumentFilter(
				new DocumentSizeFilter(23));
		setupTextField(name);
		namePanel.add(name);
		textBoxes.add(namePanel);

		//Instantiates the description textfield
		JPanel descPanel = new JPanel();
		descPanel.add(new JLabel("Details:"));
		descPanel.setOpaque(false);
		details = new JTextArea(task.getDescription(),4,20);
		setupTextArea(details);
		descPanel.add(details);
		textBoxes.add(descPanel);

		textBoxes.add(separator);

		//Instantiates the repeat textfield
		options = new ArrayList<String>();
		//{"None", "Every x days", "Specific Days"};
		options.add("None");
		options.add("Every x days");
		options.add("Specific days");
		repeatType = new MyChooser(options);
		repeatType.getLeft().addActionListener(this);
		repeatType.getRight().addActionListener(this);
		JPanel reptPanel = new JPanel();
		reptPanel.add(new JLabel("Repeat:"));
		reptPanel.add(repeatType);
		reptPanel.setOpaque(false);
		textBoxes.add(reptPanel);

		//Instantiates the date chooser
		cal = task.getDate();
		setupChooserP();
		setupNoneP();
		setupNumdayP();
		setupWeekdayP();
		dLine = noneP;
		textBoxes.add(dLine);

		//Adds spacing between textBoxes and edge of TaskWindow
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new EmptyBorder(10,2,0,2));
		panel.add(textBoxes);
		getContentPane().add(panel, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setOpaque(false);

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
			
			task.setTaskName(name.getText());
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
				if(repeat.equals("Every x days")) {
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
						dueDate = task.getDate();
						do {
							dueDate.add(Calendar.DATE, 1);
						}
						while(!task.getWeekdays().contains(
								cal.get(Calendar.DAY_OF_WEEK)));
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
		noneP.add(labelP);

		//setupChooserP();
		noneP.add(chooserP);
		//noneP.setVisible(true);
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
		month.setBorder(space);
		chooserP.add(month,c);
		c.gridx = 1;
		JLabel day = new JLabel("Day", JLabel.CENTER);
		day.setBorder(space);
		chooserP.add(day,c);
		c.gridx = 2;
		JLabel year = new JLabel("Year", JLabel.CENTER);
		year.setBorder(space);
		chooserP.add(year,c);
		c.gridx = 0;
		c.gridy = 1;

		mm = new MyChooser(1,12);
		mm.getLeft().addActionListener(this);
		mm.getRight().addActionListener(this);
		//System.out.println(cal.get(Calendar.MONTH));
		mm.setText(cal.get(Calendar.MONTH)+1);
		dd = new MyChooser(1,31);
		dd.getLeft().addActionListener(this);
		dd.getRight().addActionListener(this);
		dd.setText(cal.get(Calendar.DAY_OF_MONTH));
		yyyy = new MyChooser(2015,2045);
		yyyy.getLeft().addActionListener(this);
		yyyy.getRight().addActionListener(this);
		yyyy.setText(cal.get(Calendar.YEAR));
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
		days = new MyChooser(0,30);
		days.getLeft().addActionListener(this);
		days.getRight().addActionListener(this);
		dayP.add(days);
		dayP.add(new JLabel(" days."));

		numdayP.add(dayP);

		JPanel labelP = new JPanel();
		labelP.setOpaque(false);
		labelP.add(new JLabel("Starting from:", JLabel.CENTER));
		numdayP.add(labelP);

		//		chooserP = new JPanel();
		//		chooserP.setOpaque(false);
		//		chooserP.setLayout(new GridBagLayout());
		//		GridBagConstraints c = new GridBagConstraints();
		//		c.gridx = 0;
		//		c.gridy = 0;
		//		chooserP.add(new JLabel("Month", JLabel.CENTER),c);
		//		c.gridx = 1;
		//		chooserP.add(new JLabel("Day", JLabel.CENTER),c);
		//		c.gridx = 2;
		//		chooserP.add(new JLabel("Year", JLabel.CENTER),c);
		//		c.gridx = 0;
		//		c.gridy = 1;
		//		MyChooser mm2 = mm;
		//		chooserP.add(mm2,c);
		//		c.gridx = 1;
		//		chooserP.add(dd,c);
		//		c.gridx = 2;
		//		chooserP.add(yyyy,c);
		//		numdayP.add(chooserP);
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
//		this.daysStrs = daysStrs;
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new JRadioButton(daysStrs[i]);
			buttons[i].setBackground(trans);
			buttons[i].setFocusPainted(false);
			buttons[i].setContentAreaFilled(false);
			buttonsP.add(buttons[i]);
		}
		weekdayP.add(buttonsP);
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
	
//	private String getDay(int day) {
//		String[] week = {"Sun", "Mon", "Tue", "Tue", "Wed", "Thu", 
//				"Fri", "Sat"};
//		return week[day-1];
//	}

}
