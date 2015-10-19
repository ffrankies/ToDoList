package pack1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.text.AbstractDocument;

public class TaskWindow extends JDialog implements ActionListener{

	private TaskList list;
	
	//private DateComboBox dateChooser;

	private JTextField name;

	private JTextField deadline;

	private JTextField important;
	
	private JTextArea details;

	private JButton okButton;
	
	private JButton cancelButton;

	private boolean closeStatus;
	
	private final Color bckg = new Color(1,1,1,0.55f);
	private final Color trans = new Color(1,1,1,0f);
	private final Color select = new Color(0,0,0,0.4f);

	public static final boolean OK = true;
	public static final boolean CANCEL = false;

	private Task task;
	
	private JPanel chooserP, textBoxes, dLine, noneP, numdayP, weekdayP;

	private SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");

	protected int pX;

	protected int pY;
	
	private MyChooser repeatType, dd, mm, yyyy, days;
	
	private JRadioButton[] buttons;
	
	private ArrayList<String> options;
	
	private Date date;
	
	private String[] daysStrs;
	
	private Calendar cal;


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
		textBoxes = new JPanel();
		textBoxes.setBackground(bckg);
		textBoxes.setBorder(BorderFactory.createCompoundBorder(
				new EmptyBorder(5, 5, 5, 5), 
				new JTextField().getBorder()));
		//textBoxes.set
		
		//textBoxes.setLayout(new GridLayout(6,1));
		textBoxes.setLayout(new BoxLayout(textBoxes, BoxLayout.Y_AXIS));
		textBoxes.setOpaque(false);
		
		//Adds title to taskWindow
		JPanel title = new JPanel();
		title.setOpaque(false);
		if(list.getTasks().contains(task))
			title.add(new JLabel("Edit Task", JLabel.CENTER));
		else
			title.add(new JLabel("Create New Task", JLabel.CENTER));
		textBoxes.add(title);
		
		textBoxes.add(new JSeparator());
		
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

		textBoxes.add(new JSeparator());
		
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
		
		//Instantiates the deadline textfield
//		textBoxes.add(new JLabel("Deadline for the task:"));
//		//		GregorianCalendar cal = new GregorianCalendar(
//		//				TimeZone.getTimeZone("EST"));
//		//		Date date = cal.getTime();
//		Date date = task.getDate().getTime();
//		//choose = new JButton(fmt.format(date));
//		//dateChooser = new DateComboBox(fmt);
//		//choose.addActionListener(this);
//		deadline = new JTextField(fmt.format(date),30);
//		deadline.setBackground(trans);
//		deadline.addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent me) {
//				repaint();
//			}
//		});
//		textBoxes.add(deadline);
		date = task.getDate().getTime();
		cal = task.getDate();
		setupChooserP();
		setupNoneP();
		setupNumdayP();
		setupWeekdayP();
		dLine = noneP;
		textBoxes.add(dLine);

		

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
		//setSize(300,240);
		pack();
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
		//Changes panel for choosing deadline depending on repeatType
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
		if(e.getSource() == mm.getLeft())
			mm.prev();
		if(e.getSource() == mm.getRight())
			mm.next();
		if(e.getSource() == yyyy.getLeft())
			yyyy.prev();
		if(e.getSource() == yyyy.getRight())
			yyyy.next();
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
				task.setDescription(details.getText());
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
		chooserP.add(new JLabel("Month", JLabel.CENTER),c);
		c.gridx = 1;
		chooserP.add(new JLabel("Day", JLabel.CENTER),c);
		c.gridx = 2; 
		chooserP.add(new JLabel("Year", JLabel.CENTER),c);
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
		String[] daysStrs = {"Mon", "Tue", "Wed", "Thu", "Fri", 
				"Sat", "Sun"};
		this.daysStrs = daysStrs;
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
		field.setBackground(trans);
		field.setSelectionColor(Color.WHITE);
		field.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				repaint();
			}
			public void mouseReleased(MouseEvent me) {
				repaint();
			}
			public void mouseDragged(MouseEvent me) {
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
	
	private void setupTextArea(JTextArea area) {
		area.setBackground(trans);
		area.setSelectionColor(Color.WHITE);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		((AbstractDocument) area.getDocument()).setDocumentFilter(
				new DocumentSizeFilter(130));
		area.setBorder(new JTextField().getBorder());
		//Is this actually necessary?
		area.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent arg0) {
				repaint();
			}
			
		});
		area.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				repaint();
			}
			public void mouseReleased(MouseEvent me) {
				repaint();
			}
			public void mouseDragged(MouseEvent me) {
				repaint();
			}
		});
		area.getDocument().addDocumentListener(new DocumentListener() {

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
		area.addKeyListener(new KeyListener() {

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
