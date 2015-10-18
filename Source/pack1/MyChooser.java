package pack1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class MyChooser extends JPanel { //implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> data;
	private ArrayList<Integer> nums;
	private JLabel display;
	private JButton left, right;
	//private Color trans = new Color(0,0,0,0);
	//private Color bckg = new Color(1,1,1,0.55f);
	private int index;
	//private E result;

	public MyChooser(int min, int max) {
		super();
		this.nums = new ArrayList<Integer>();
		setOpaque(false);
		for(int i = min; i <= max; i++) {
			nums.add(i);
		}
		left = new JButton("<");
		setUpButton(left);

		right = new JButton(">");
		setUpButton(right);

		display = new JLabel(""+min, JLabel.CENTER);
		index = 0;
		
		setLayout(new BorderLayout());
		add(BorderLayout.WEST, left);
		add(BorderLayout.CENTER, display);
		add(BorderLayout.EAST, right);
		//		add(left);
		//		add(display);
		//		add(right);
		setAlignmentY(SwingConstants.CENTER);
		//setSize(new Dimension(200,40));
		//setMargin(new Insets(0,0,0,0));

		setVisible(true);
	}

	public MyChooser(ArrayList<String> data) {
		super();
		this.data = data;
		this.setOpaque(false);

		left = new JButton("<");
		setUpButton(left);

		right = new JButton(">");
		setUpButton(right);

		display = new JLabel((String)data.get(0), JLabel.CENTER);
		index = 0;

		setLayout(new BorderLayout());
		add(BorderLayout.WEST, left);
		add(BorderLayout.CENTER, display);
		add(BorderLayout.EAST, right);
		//		add(left);
		//		add(display);
		//		add(right);
		setAlignmentY(SwingConstants.CENTER);
		//setSize(new Dimension(200,40));
		//setMargin(new Insets(0,0,0,0));

		setVisible(true);
	}

	public void next() {
		if(data != null)
			if(index < data.size()-1) {
				index++;
				setText(data.get(index));
			}
			else {
				index = 0;
				setText(data.get(index));
			}
		else
			if(index < nums.size()-1) {
				index++;
				setText(nums.get(index));
			}
			else {
				index = 0;
				setText(nums.get(index));
			}
	}

	public void prev() {
		if(data != null)
		if(index > 0) {
			index--;
			setText(data.get(index));
		}
		else {
			index = data.size()-1;
			setText(data.get(index));
		}
		else
			if(index > 0) {
				index--;
				setText(nums.get(index));
			}
			else {
				index = nums.size()-1;
				setText(nums.get(index));
			}
	}

	//	public void setTransparent() {
	//		this.setBorder(BorderFactory.createEmptyBorder());
	//		this.setBackground(trans);
	//	}

	//@Override
//	public void actionPerformed(ActionEvent e) {
//		JButton button = (JButton)e.getSource();
//		if(button == left)
//			prev();
//		if(button == right)
//			next();
//		repaint();
//	}

	public void setText(String text) {
		this.display.setText(text);
		index = data.indexOf(text);
	}
	
	public void setText(int text) {
		this.display.setText(""+text);
		index = nums.indexOf(text);
	}

	public String getText() {
		return data.get(index);
	}
	
	public int getNum() {
		return nums.get(index);
	}

	public void setUpButton(JButton button) {
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setSize(new Dimension(30,40));
		button.setMargin(new Insets(0,0,0,0));
		button.setFocusPainted(false);
		//button.addActionListener(this);
	}
	
	public JButton getLeft() {
		return left;
	}
	
	public JButton getRight() {
		return right;
	}
}
