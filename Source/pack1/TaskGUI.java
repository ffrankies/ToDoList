package pack1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

public class TaskGUI extends JFrame implements ActionListener {

	/**
	 * Default serial value. Still don't get what it's needed for
	 */
	private static final long serialVersionUID = 1L;

	/* Organizes TaskList data into a table form for display */
	private JTable table;

	/* Contains the list of tasks */
	private TaskList model;

	/* Allows user to scroll through tasks */
	private JScrollPane scrollPane;

	/* Buttons to add, delete, edit tasks, as well as close the
	 * program */
	private JButton add, remove, edit, close;
	//private JPanel panel;

	/* Used to allow GUI to be dragged about screen */
	private int pX, pY;

	protected String[] columnToolTips = {"A description of the task.",
			"The due date for the task.",
	"Check this box once you have completed the task."};

	private final Color trans = new Color(1,1,1,0.55f);
	private final Color bckg = Color.WHITE;
	private final Color select = Color.LIGHT_GRAY;
	private final Color dark = Color.BLACK;
	private final Color due = Color.RED;

	public TaskGUI() {

		model = new TaskList();
		table = new JTable(model) {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			//Implements tooltips when a cell is moused over
			public String getToolTipText(MouseEvent e) {
				//String tip = null;
				java.awt.Point p = e.getPoint();
				//                int index = columnModel.getColumnIndexAtX(p.x);
				//                int realIndex =
				//                		columnModel.getColumn(index).getModelIndex();
				//                return columnToolTips[realIndex];
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				int realColIndex = columnModel.getColumn(colIndex).getModelIndex();
				if(realColIndex == 0)
					return model.getTask(rowIndex).getDescription();
				else
					return columnToolTips[realColIndex];
			}

			//Implement table header tool tips.
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					public String getToolTipText(MouseEvent e) {
						// String tip = null;
						java.awt.Point p = e.getPoint();
						int index = columnModel.getColumnIndexAtX(p.x);
						int realIndex =
								columnModel.getColumn(
										index).getModelIndex();
						return columnToolTips[realIndex];
					}

				};
			}
		};
		table.setShowGrid(false);
		table.setBorder(null);
		table.setOpaque(false);
		table.getTableHeader().setBackground(bckg);
		table.setBackground(bckg);
		table.setSelectionBackground(select);
		table.setFocusable(false);

		/* Prevents more than one task from being selected */
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		/* Prevents opacity from building up on each click */
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				repaint();
			}
		});

		scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(new Color(1,1,1,0f));
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setBackground(bckg);
		scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI()
		{   
			@Override 
			protected void configureScrollBarColors(){
				this.thumbColor = bckg;
				this.trackColor = dark;
			}

			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton button = new JButton("+");
				button.setFocusPainted(false);
				button.setBackground(bckg);
				button.setForeground(dark);
				button.setMargin(new Insets(0,0,0,0));
				return button;
			}

			@Override    
			protected JButton createIncreaseButton(int orientation) {
				JButton button = new JButton("-");
				button.setFocusPainted(false);
				button.setBackground(bckg);
				button.setForeground(dark);
				button.setMargin(new Insets(0,0,0,0));
				return button;
			}

		});

		//		scrollPane.getVerticalScrollBar().setBackground(bckg);
		//		scrollPane.getVerticalScrollBar().setForeground(bckg);
		//		scrollPane.getVerticalScrollBar().

		//scrollPane.setMaximumSize(new Dimension(250,250));
		//panel = new JPanel();

		/*GridBagLayout controls the size of elements in the frame
		 *It was chosen because the buttons need to be small, with
		 *a table covering most of the JFrame area */
		setLayout(new GridBagLayout());
		//Instantiating buttons
		add = new JButton("Add");
		add.addActionListener(this);
		//add.setMaximumSize(new Dimension(100,100));
		close = new JButton("Close");
		close.addActionListener(this);
		//add.setMaximumSize(new Dimension(100,100));
		remove = new JButton("Remove");
		remove.addActionListener(this);
		edit = new JButton("Edit");
		edit.addActionListener(this);

		//Constraints for close button, to appear at top right corner
		GridBagConstraints top = new GridBagConstraints();
		top.weightx = 1;
		top.weighty = 1;
		top.gridwidth = 1;
		top.gridx = 2;
		top.gridy = 0;
		top.anchor = GridBagConstraints.FIRST_LINE_END;
		add(close,top);

		//Constraints for the scrollpanel, containing the table
		GridBagConstraints scroll = new GridBagConstraints();
		scroll.weightx = 1;
		scroll.weighty = 1;
		scroll.gridwidth = 3;
		scroll.gridx = 0;
		scroll.gridy = 1;
		scroll.ipady = 100;
		scroll.ipadx = 420;
		add(scrollPane,scroll);

		//Constraints for the add, remove and edit buttons
		GridBagConstraints buttons = new GridBagConstraints();
		buttons.weightx = 1;
		buttons.weighty = 1;
		buttons.gridx = 2;
		buttons.gridy = 2;
		buttons.anchor = GridBagConstraints.LAST_LINE_END;
		add(add,buttons);
		buttons.gridx = 1;
		add(remove,buttons);
		buttons.gridx = 0;
		add(edit,buttons);

		scrollPane.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				table.clearSelection();
				repaint();
			}
		});
		// Add mouse listener for this frame
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me)
			{
				// Get x,y and store them
				pX=me.getX();
				pY=me.getY();
				//				table.clearSelection();
				//				repaint();
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


		setSize(450,170);
		setUndecorated(true); //No menuBar on frame
		setBackground(trans); //Makes background translucent-white
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if(button == add) {
			Task task = new Task();
			new TaskWindow(this,task,model);
			//model.add(task);
		}
		if(button == remove) {
			Task task = model.getTask(table.getSelectedRow());
			model.remove(task);
		}
		if(button == edit) {
			Task task = model.getTask(table.getSelectedRow());
			new TaskWindow(this,task,model);
			//model.remove(task);
		}
		model.save();
		repaint();
		if(button == close) {
			model.removeCompleted();
			model.save();
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new TaskGUI();
	}
}
