package pack1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
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
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				int realColIndex = 
						columnModel.getColumn(colIndex).getModelIndex();
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
		table.setSelectionBackground(select);
		table.setFocusable(false);
		table.setSize(296,700);

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
		scrollPane.setSize(296,700);
		
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
		scrollP.setBorder(new EmptyBorder(0,5,0,5));
		scrollP.add(scrollPane, BorderLayout.CENTER);
		add(scrollP);

		JPanel buttonsP = new JPanel();
		buttonsP.setOpaque(false);
		buttonsP.add(add);
		buttonsP.add(remove);
		buttonsP.add(edit);
		buttonsP.setBorder(new EmptyBorder(0,0,25,0));
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
		
		
		GraphicsEnvironment ge = 
				GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = 
        		defaultScreen.getDefaultConfiguration().getBounds();
        setSize(300,(int) rect.getMaxY());
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
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				new TaskGUI();
				
			}
			
		});
	}
}
