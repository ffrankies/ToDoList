package pack1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;

public class TaskGUI extends JFrame implements ActionListener {

	/**
	 * Default serial value. Still don't get what it's needed for
	 */
	private static final long serialVersionUID = 1L;

	/* Organizes TaskList data into a table form for display */
	private JTable table;

	/* Contains the list of tasks */
	private TaskList taskModel;

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

		private final SimpleDateFormat fmt = 
				new SimpleDateFormat("MM/dd/yyyy");
	//SimpleDateFormat format = SimpleDateFormat.SHORT;

	private final Color trans = new Color(1,1,1,0.55f);
	private final Color bckg = Color.WHITE;
	private final Color select = Color.LIGHT_GRAY;
	private final Color dark = Color.BLACK;
//	private final Color due = Color.RED;
	private final Color due = new Color(0.8f,0.3f,0.3f);
	private final Color selectDue = Color.PINK;

	private final Font font = new Font("Cooper Black", Font.PLAIN, 15);

	public TaskGUI() {

		taskModel = new TaskList();
		table = new JTable(taskModel) {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			//Implements tooltips when a cell is moused over
			public String getToolTipText(MouseEvent e) {
				//String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				int realColIndex = 
						columnModel.getColumn(colIndex).getModelIndex();
				if(realColIndex == 0)
					return taskModel.getTask(rowIndex).getDescription();
				else
					return columnToolTips[realColIndex];
			}

			//Implement table header tool tips.
			//			protected JTableHeader createDefaultTableHeader() {
			//				return new JTableHeader(columnModel) {
			//					/**
			//					 *
			//					 */
			//					private static final long serialVersionUID = 1L;
			//
			//					public String getToolTipText(MouseEvent e) {
			//						java.awt.Point p = e.getPoint();
			//						int index = columnModel.getColumnIndexAtX(p.x);
			//						int realIndex =
			//								columnModel.getColumn(
			//										index).getModelIndex();
			//						return columnToolTips[realIndex];
			//					}
			//
			//				};
			//			}

//			public Component prepareRenderer(
//					TableCellRenderer renderer, int row, int column)
//			{
//				Component c = 
//						super.prepareRenderer(renderer, row, column);
//
//				//Makes row red if task is overdue
//				if (!isRowSelected(row))
//				{
//					c.setBackground(getBackground());
//					int modelRow = convertRowIndexToModel(row);
//					Date date = new Date();
//					//System.out.println(fmt.format(date));
//					//					String str = 
//					//							(String)getModel().getValueAt(modelRow,1);
//					Date dueD = taskModel.getTask(modelRow).getDate().getTime();
//					if(date.after(dueD))
//						c.setForeground(due);
//					else
//						c.setForeground(dark);
//				}
//
//				return c;
//			}
		};
		table.setShowGrid(false);
		table.setBorder(null);
		table.setOpaque(false);
		//table.getTableHeader().setBackground(bckg);
		table.setSelectionBackground(select);
		table.setFocusable(false);
		table.setSize(296,700);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setMinWidth(195);
		//table.getColumnModel().getColumn(0).setMaxWidth(220);
		table.getColumnModel().getColumn(1).setMinWidth(50);
		table.getColumnModel().getColumn(2).setMinWidth(20);
		table.getColumnModel().getColumn(2).setMaxWidth(15);
		//table.getColumnModel().getColumn(0).setMinWidth(scrollPane.getWidth()-62);
		//table.getColumnModel().getColumn(2).get
		table.setTableHeader(null);
		//table.setFont(font);
		MyCellRenderer cell = new MyCellRenderer();
		table.getColumnModel().getColumn(0).setCellRenderer(cell);
		table.getColumnModel().getColumn(1).setCellRenderer(cell);
		CheckBoxRenderer ren = new CheckBoxRenderer();
		table.getColumnModel().getColumn(2).setCellRenderer(ren);
		//table.

		/* Prevents more than one task from being selected */
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		/* Prevents opacity from building up on each click */
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				repaint();
			}
		});

		scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(bckg);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setBackground(bckg);
		//scrollPane.setSize(296,700);
		scrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//Changes colors of scrollPane's scrollBar
		
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

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		//Instantiating buttons
		close = new JButton("Close");
		close.addActionListener(this);
		add = new JButton("Add");
		add.addActionListener(this);
		remove = new JButton("Remove");
		remove.addActionListener(this);
		edit = new JButton("Edit");
		edit.addActionListener(this);

		JPanel closeP = new JPanel();
		closeP.setLayout(new FlowLayout());
		closeP.setOpaque(false);
		closeP.add(close);
		closeP.setSize(300,close.getHeight());
		add(closeP);

		JPanel scrollP = new JPanel();
		scrollP.setLayout(new BorderLayout());
		scrollP.setOpaque(false);
		scrollP.setBorder(new EmptyBorder(0,0,0,0));
		scrollP.add(scrollPane, BorderLayout.CENTER);
		add(scrollP);
		//table.getColumnModel().getColumn(0).setMinWidth(scrollPane.getWidth()-62);

		JPanel buttonsP = new JPanel();
		buttonsP.setOpaque(false);
		buttonsP.add(add);
		buttonsP.add(remove);
		buttonsP.add(edit);
		buttonsP.setBorder(new EmptyBorder(0,0,29,0));
		add(buttonsP);

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

		//Makes GUI extend to the bottom of the screen
		GraphicsEnvironment ge = 
				GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = 
				defaultScreen.getDefaultConfiguration().getBounds();
		setSize(320,(int) rect.getMaxY());
//		if(scrollPane.getVerticalScrollBar().isVisible())
//			setSize(320,getHeight());
		int x = (int) rect.getMaxX() - this.getWidth();
		int y = 0;
		setLocation(x,y);
		setUndecorated(true); //No menuBar on frame
		setBackground(trans); //Makes background translucent-white
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if(button == add) {
			Task task = new Task();
			new TaskWindow(this,task,taskModel);
			if(scrollPane.getVerticalScrollBar().isVisible())
				setSize(320,getHeight());
			//model.add(task);
		}
		if(button == remove) {
			Task task = taskModel.getTask(table.getSelectedRow());
			taskModel.remove(task);
		}
		if(button == edit) {
			Task task = taskModel.getTask(table.getSelectedRow());
			new TaskWindow(this,task,taskModel);
			//model.remove(task);
		}
		taskModel.save();
		scrollPane.repaint();
		
		if(button == close) {
			taskModel.removeCompleted();
			taskModel.save();
			System.exit(0);
		}
	}

	/*
	 * Main function invokes the GUI.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				new TaskGUI();

			}

		});
	}

	/*
	 * Allows me to edit the checkBox in the third column of the 
	 * table
	 */
	private class CheckBoxRenderer extends JCheckBox implements 
	TableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		CheckBoxRenderer() {
			setHorizontalAlignment(JLabel.CENTER);
		}

		public Component getTableCellRendererComponent(JTable table, 
				Object value, boolean isSelected, boolean hasFocus, 
				int row, int column) {
//			if (isSelected) {
//				//setForeground(table.getSelectionForeground());
//				setForeground(table.getSelectionForeground());
//				//super.setBackground(table.getSelectionBackground());
//				setBackground(select);
//			} else {
//				setForeground(table.getForeground());
//				setBackground(bckg);
//			}
			
			Date date = new Date();
			Date dueD = taskModel.getTask(row).getDate().getTime();
			
			if(isSelected)
	        	if(date.after(dueD))
	        		this.setBackground(selectDue);
	        	else
	        		this.setBackground(select);
	        else
	        	if(date.after(dueD))
	        		this.setBackground(due);
	        	else
	        		this.setBackground(bckg);
			setSelected((Boolean)value);
			// model.getTasks().get(row).setCompleted((Boolean)value);
			//setSelected((value != null && ((Boolean) value).booleanValue()));
			return this;
		}
	}
	
	/*
	 * Allows me to edit how the text is displayed in the table
	 */
	public class MyCellRenderer  extends JTextPane 
	implements TableCellRenderer {

	    @Override
	    public Component getTableCellRendererComponent(
	            JTable table,
	            Object value,
	            boolean isSelected,
	            boolean hasFocus,
	            int row,
	            int column) {
	        this.setText((String)value);
	        //this.setWrapStyleWord(true);            
	     //   this.setLineWrap(true);  
	        //^^^^^could be useful if I choose to change the way I render this
	        this.setFont(font);
	        Insets i = this.getInsets();
	        int fontHeight = this.getFontMetrics(this.getFont()).getHeight();         
	        int height = fontHeight * 2;            
	        table.setRowHeight(row, height);
	        int vert = 6;
	        this.setMargin(new Insets(vert,i.left,vert,i.right));
	        
	        Date date = new Date();
			Date dueD = taskModel.getTask(row).getDate().getTime();
			
			if(isSelected)
	        	if(date.after(dueD))
	        		this.setBackground(selectDue);
	        	else
	        		this.setBackground(select);
	        else
	        	if(date.after(dueD))
	        		this.setBackground(due);
	        	else
	        		this.setBackground(bckg);
	        return this;
	    }

	}
}
