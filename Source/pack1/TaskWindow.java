package pack1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.*;

public class TaskWindow extends JDialog implements ActionListener{

	private TaskList list;

	private JTextField description;

	private JTextField deadline;

	private JTextField important;

	private JButton okButton;

	private JButton cancelButton;

	private boolean closeStatus;

	public static final boolean OK = true;
	public static final boolean CANCEL = false;

	private Task task;

	private SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");

	protected int pX;

	protected int pY;


	public TaskWindow(JFrame paOccupy, Task task, TaskList list) {
		this.task = task;
		this.list = list;

		fmt.setLenient(false);

		setTitle("Create a new task");
		closeStatus = CANCEL;
		setSize(300,500);

		//Prevent user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		//Instantiate and display text input boxes
		JPanel textBoxes = new JPanel();
		textBoxes.setBackground(new Color(1,1,1,0.55f));
		textBoxes.setLayout(new GridLayout(3,2));

		textBoxes.add(new JLabel("Description of Task:"));
		description = new JTextField(task.getTaskName(),20);
		textBoxes.add(description);

		textBoxes.add(new JLabel("Deadline for the task:"));
		//		GregorianCalendar cal = new GregorianCalendar(
		//				TimeZone.getTimeZone("EST"));
		//		Date date = cal.getTime();
		Date date = task.getDate().getTime();
		deadline = new JTextField(fmt.format(date),30);
		textBoxes.add(deadline);

		textBoxes.add(new JLabel("Is the task urgent?"));
		//important = new JTextField("Yes/No",30);
		String imp;
		if(task.isImportant())
			imp = "Yes";
		else
			imp = "No";
		important = new JTextField(imp);
		textBoxes.add(important);

		getContentPane().add(textBoxes, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setBackground(new Color(1,1,1,0.55f));

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
		setBackground(new Color(1,1,1,0.55f));
		setSize(300,200);
		//pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		//JButton button = (JButton) e.getSource();
		//Fills the Site if OK is clicked
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
				task.setTaskName(description.getText());
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
		dispose();
	}

	public boolean getCloseStatus() {
		return closeStatus;
	}
}
