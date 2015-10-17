package pack1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.*;

public class ComboBoxRenderer extends JPanel 
implements ListCellRenderer {
	
	//private Font uhOhFont;
	private final Color trans = new Color(1,1,1,0f);
	private final Color select = new Color(1,1,1,0.3f);

	JPanel textPanel;
    JLabel text;

    public ComboBoxRenderer(JComboBox combo) {

    	setOpaque(false);
    	setBackground(trans);
        textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.add(this);
        textPanel.setBackground(trans);
        text = new JLabel();
        text.setOpaque(true);
        text.setFont(combo.getFont());
        textPanel.add(text);
    }

//    public void setColors(Color[] col)
//    {
//        colors = col;
//    }

//    public void setStrings(String[] str)
//    {
//        strings = str;
//    }
//
//    public Color[] getColors()
//    {
//        return colors;
//    }
//
//    public String[] getStrings()
//    {
//        return strings;
//    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

        if (isSelected)
        {
            text.setBackground(select);
        }
        else
        {
            text.setBackground(trans);
        }

//        if (colors.length != strings.length)
//        {
//            System.out.println("colors.length does not equal strings.length");
//            return this;
//        }
//        else if (colors == null)
//        {
//            System.out.println("use setColors first.");
//            return this;
//        }
//        else if (strings == null)
//        {
//            System.out.println("use setStrings first.");
//            return this;
//        }

        //text.setBackground(getBackground());

        text.setText(value.toString());
//        if (index>-1) {
//            text.setForeground(colors[index]);
//        }
        return text;
    }
}
