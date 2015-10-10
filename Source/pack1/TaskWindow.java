package pack1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		textBoxes.setLayout(new GridLayout(3,2));

		textBoxes.add(new JLabel("Description of Task:"));
		description = new JTextField("Do this",20);
		textBoxes.add(description);

		textBoxes.add(new JLabel("Deadline for the task:"));
		GregorianCalendar cal = new GregorianCalendar(
				TimeZone.getTimeZone("EST"));
		Date date = cal.getTime();
		deadline = new JTextField(fmt.format(date),30);
		textBoxes.add(deadline);

		textBoxes.add(new JLabel("Is the task urgent?"));
		important = new JTextField("Yes/No",30);
		textBoxes.add(important);

		getContentPane().add(textBoxes, BorderLayout.CENTER);

		JPanel buttons = new JPanel();

		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		buttons.add(okButton);
		buttons.add(cancelButton);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		getContentPane().add(buttons, BorderLayout.SOUTH);

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
			Date date;

			try {
				GregorianCalendar dueDate = new GregorianCalendar();
				date = fmt.parse(deadline.getText());
				dueDate.setTime(date);
				task.setDate(dueDate);
				//date = fmt.parse(deadline.getText());
				//task.setTaskName(description.getText());
				//task.setDate(deadline.getText());
				if(important.getText().toLowerCase().equals("yes"))
					task.setImportant(true);
				if(important.getText().toLowerCase().equals("no"))
					task.setImportant(false);
			}
			catch ( ParseException exception ) {
				exception.printStackTrace();
			}

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
