package pack1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

public class MyChooser<E> extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<E> data;
	private JLabel display;
	private JButton left, right;
	private Color trans = new Color(0,0,0,0);
	private Color bckg = new Color(1,1,1,0.55f);
	private int index;
	//private E result;
	
	public MyChooser(ArrayList<E> data) {
		super();
		this.data = data;
		this.setOpaque(false);
		
		left = new JButton("<");
		left.setContentAreaFilled(false);
		left.setBorderPainted(false);
		left.setSize(new Dimension(30,40));
		left.setMargin(new Insets(0,0,0,0));
		left.setFocusPainted(false);
		//left.setOpaque(false);
		//left.setFocusable(false);
		left.addActionListener(this);
		
		right = new JButton(">");
		right.setContentAreaFilled(false);
		//right.setFocusable(false);
		right.setBorderPainted(false);
		right.setSize(new Dimension(30,40));
		right.setMargin(new Insets(0,0,0,0));
		//button.setHorizontalTextPosition(SwingConstants.CENTER);
		//button.setFocusPainted(false);
		//button.setBorderPainted(false);
		//right.setBackground(trans);
		right.setFocusPainted(false);
		right.addActionListener(this);
		
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
		if(index < data.size()-1) {
			index++;
			setText(data.get(index));
		}
		else {
			index = 0;
			setText(data.get(index));
		}
	}
	
	public void prev() {
		if(index > 0) {
			index--;
			setText(data.get(index));
		}
		else {
			index = data.size()-1;
			setText(data.get(index));
		}
	}
	
//	public void setTransparent() {
//		this.setBorder(BorderFactory.createEmptyBorder());
//		this.setBackground(trans);
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		if(button == left)
			prev();
		if(button == right)
			next();
		repaint();
	}
	
	public void setText(E text) {
		this.display.setText((String)text);
		index = data.indexOf(text);
	}
	
	public E getText() {
		return data.get(index);
	}
}
