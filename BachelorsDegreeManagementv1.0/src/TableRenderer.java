
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;
 
@SuppressWarnings("serial")
public class TableRenderer extends DefaultTableCellRenderer 
{
	int idx = 0;
	
	public ArrayList<Color> deptColor = new ArrayList<Color>();
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column)
    {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
		deptColor.add(Color.RED);
		deptColor.add(Color.CYAN);
		deptColor.add(Color.GREEN);
		deptColor.add(Color.BLUE);
		deptColor.add(Color.YELLOW);
		deptColor.add(Color.MAGENTA);
		deptColor.add(Color.ORANGE);
		deptColor.add(Color.PINK);
		deptColor.add(Color.GRAY);
		deptColor.add(Color.WHITE);
		deptColor.add(Color.BLACK);
		
		
		
		System.out.printf("TableRenderer test %b\n", hasFocus);
		
		//idx++;
		
    	if ((column == 2) && (row == 0)) {
    		c.setBackground(deptColor.get(0));
    	}
    	else if ((column == 2) && (row == 1)) {
    		c.setBackground(deptColor.get(1));
    	}
    	else if ((column == 2) && (row == 2)) {
    		c.setBackground(deptColor.get(2));
    	}
    	else if ((column == 2) && (row == 3)) {
    		c.setBackground(deptColor.get(3));
    	}
    	else if ((column == 2) && (row == 4)) {
    		c.setBackground(deptColor.get(4));
    	}
    	else if ((column == 2) && (row == 5)) {
    		c.setBackground(deptColor.get(5));
    	}
    	else if ((column == 2) && (row == 6)) {
    		c.setBackground(deptColor.get(6));
    	}
    	else if ((column == 2) && (row == 7)) {
    		c.setBackground(deptColor.get(7));
    	}
    	else if ((column == 2) && (row == 8)) {
    		c.setBackground(deptColor.get(8));
    	}
    	else if ((column == 2) && (row == 9)) {
    		c.setBackground(deptColor.get(9));
    	}
    	else if ((column == 2) && (row == 10)) {
    		c.setBackground(deptColor.get(10));
    	}
    	else {
    		c.setBackground(table.getBackground());
    	}
        
        return c;
    }
}