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

	//		private final SimpleDateFormat fmt = 
	//				new SimpleDateFormat("MM/dd/yyyy");
	//SimpleDateFormat format = SimpleDateFormat.SHORT;

	//Colors used in the program
	private final Color trans = new Color(1,1,1,0.55f);
	private final Color bckg = Color.WHITE;
	private final Color select = Color.LIGHT_GRAY;
	private final Color dark = Color.BLACK;
	private final Color due = new Color(0.8f,0.3f,0.3f);
	private final Color selectDue = Color.PINK;

	private final Font font = new Font("Cooper Black", Font.PLAIN, 15);

	private ImageIcon closeI, closeIPr, addI, addIPr, editI, editIPr, 
	deleteI, deleteIPr, inc, dec;

	private ImageIcon check;

	private ImageIcon uncheck;
	
	private ImageIcon icon;

	public TaskGUI() {

		taskModel = new TaskList();
		loadIcons();
		
		setTitle("ToDoList");
		setIconImage(icon.getImage());
		
		table = new JTable(taskModel) {
			/**
			 *Default serial ID for the 
			 */
			private static final long serialVersionUID = 1L;

			//Implements tooltips when a cell is moused over
			public String getToolTipText(MouseEvent e) {
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				int realColIndex = 
						columnModel.getColumn(colIndex).getModelIndex();
				if(realColIndex == 0)
					return taskModel.getTask(rowIndex).getDescription();
				else
					return "";
			}

			@Override
			public JToolTip createToolTip() {
				JToolTip toolTip = super.createToolTip();
				toolTip.setBackground(select);
				toolTip.setForeground(dark);
				toolTip.setBorder(BorderFactory.createEmptyBorder());
				return toolTip;
			}

		};
		table.setShowGrid(false);
		table.setBorder(null);
		table.setOpaque(false);
		table.setSelectionBackground(select);
		table.setFocusable(false);
		table.setSize(296,700); // I think this is overidden somewhere
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setMinWidth(195);
		table.getColumnModel().getColumn(1).setMinWidth(50);
		table.getColumnModel().getColumn(2).setMinWidth(18);
		table.getColumnModel().getColumn(2).setMaxWidth(18);
		table.setTableHeader(null);
		MyCellRenderer cell = new MyCellRenderer();
		table.getColumnModel().getColumn(0).setCellRenderer(cell);
		table.getColumnModel().getColumn(1).setCellRenderer(cell);
		CheckBoxRenderer ren = new CheckBoxRenderer();
		CustomBooleanCellEditor ed = new CustomBooleanCellEditor();
		table.getColumnModel().getColumn(2).setCellRenderer(ren);
		table.getColumnModel().getColumn(2).setCellEditor(ed);

		/* Prevents more than one task from being selected */
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
				this.thumbColor = dark;
				this.trackColor = bckg;
				this.scrollBarWidth = 20;
			}

			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton button = new JButton();
				button.setFocusPainted(false);
				button.setBorderPainted(false);
				button.setFocusable(false);
				button.setRolloverEnabled(false);
				button.setIcon(dec);
				button.setContentAreaFilled(false);
				button.setMargin(new Insets(-3,0,-3,0));
				return button;
			}

			@Override    
			protected JButton createIncreaseButton(int orientation) {
				JButton button = new JButton();
				button.setFocusPainted(false);
				button.setBorderPainted(false);
				button.setFocusable(false);
				button.setRolloverEnabled(false);
				button.setIcon(inc);
				button.setContentAreaFilled(false);
				button.setMargin(new Insets(-3,0,-3,0));
				return button;
			}

		});

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		//Instantiating buttons
		close = new JButton();
		//close.addActionListener(this);
		close.setIcon(closeI);
		close.setPressedIcon(closeIPr);
		setButton(close);
		add = new JButton();
		//add.addActionListener(this);
		add.setIcon(addI);
		add.setPressedIcon(addIPr);
		setButton(add);
		remove = new JButton();
		//remove.addActionListener(this);
		remove.setIcon(deleteI);
		remove.setPressedIcon(deleteIPr);
		setButton(remove);
		edit = new JButton();
		//edit.addActionListener(this);
		edit.setIcon(editI);
		edit.setPressedIcon(editIPr);
		setButton(edit);

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
			table.clearSelection();
			taskModel.remove(task);
			table.repaint();
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
			if(isSelected())
				setIcon(check);
			else
				setIcon(uncheck);
			return this;
		}
	}
	
	public class CustomBooleanCellEditor extends AbstractCellEditor 
	implements TableCellEditor{

	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JCheckBox editor;

	    public CustomBooleanCellEditor() {
	        editor = new JCheckBox();
	        editor.setHorizontalAlignment(JLabel.CENTER);
	        editor.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
	        	
	        });
	        editor.setPressedIcon(check);
	    }

	    @Override
	    public Component getTableCellEditorComponent(JTable table, 
	    		Object value, boolean isSelected, int row, int column) {
	        if (value instanceof Boolean) {
	            boolean selected = (boolean) value;
	            editor.setSelected(selected);
	        }
	        
	        if(isSelected)
	        	editor.setIcon(check);
	        else
	        	editor.setIcon(uncheck);
	        //fireEditingStopped();
	        return editor;
	    }

	    @Override
	    public Object getCellEditorValue() {
	        return editor.isSelected();
	    }

	}

	/*
	 * Allows me to edit how the text is displayed in the table
	 */
	public class MyCellRenderer  extends JTextPane 
	implements TableCellRenderer {

		/**
		 * Default serial ID
		 */
		private static final long serialVersionUID = 1L;

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

	/*
	 * Loads icons in use
	 */
	private void loadIcons() {
		closeI = loadImage("\\Resources\\Close.png");
		closeIPr = loadImage("\\Resources\\ClosePr.png");
		addI = loadImage("\\Resources\\Add.png");
		addIPr = loadImage("\\Resources\\AddPr.png");
		editI = loadImage("\\Resources\\Edit.png");
		editIPr = loadImage("\\Resources\\EditPr.png");
		deleteI = loadImage("\\Resources\\Remove.png");
		deleteIPr = loadImage("\\Resources\\RemovePr.png"); 
		inc = loadImage("\\Resources\\Inc.png");
		dec = loadImage("\\Resources\\Dec.png");
		check = loadImage("\\Resources\\Check.png");
		uncheck = loadImage("\\Resources\\Uncheck.png");
		icon  = loadImage("\\Resources\\Icon.png");
	}

	/*
	 * Converts png images to imageIcons to be used for buttons
	 */
	private ImageIcon loadImage(String imageName) {

		ImageIcon image = null;
		image = new ImageIcon(System.getProperty(
				"user.dir") + imageName);
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
