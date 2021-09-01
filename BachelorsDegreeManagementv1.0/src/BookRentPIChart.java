/*
-----------------------------------------------------------------------------------------------------------------------------------
	BookRentPIChart class.
	This class show PI chart about current state of book rent.
	
	2021.04.08 ymy - first write.
----------------------------------------------------------------------------------------------------------------------------------- 
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class BookRentPIChart extends JPanel {
	
	private DefaultTableModel model = null;
	
	private JTable currentStateTable = null; 
	
	private JScrollPane sp_CurrentState = null;
	
	private String query = null;
	
	private ArrayList<Integer> countDept = new ArrayList<Integer>();
	private ArrayList<Integer> deptRatio = new ArrayList<Integer>();
	public ArrayList<Color> deptColor = new ArrayList<Color>();
	private int totalCount = 0;
	//public static ArrayList<String> deptName = new ArrayList<String>();
	
	private boolean printDebugConsole = false;
/*
-----------------------------------------------------------------------------------------------------------------------------------
	BookRentPIChart Constructor
	Function: Draw PI chart about book rent.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	public BookRentPIChart(int x, int y) {
		
		this.setSize(x, y);
		this.generateColor();
		this.createCurrentStateTable();
	    this.readCurrentStateInfo();
		this.setVisible(true);
	}

/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: paintComponent()
	Function: Draw circle method.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Font myFont = new Font ("SanSerif", 1, 20);
		//g.setFont(myFont);
		//int skipCount = 0;
		int startAngle = 0;
		int endAngle = 0;
		
		for (int idx = 0; idx < this.countDept.size(); idx++) {
			//System.out.printf("skipCount %d\n", skipCount);
			if (idx == 0) {
				if (printDebugConsole == true) {
					System.out.printf("1 idx %d this.deptRatio.get %d\n",idx, this.deptRatio.get(idx));
				}
				g.setColor(deptColor.get(idx));
				// 원의 중앙 정렬을 위한 값 설정(완전한 중앙은 아님)
				g.fillArc((int)((double)this.getWidth() / 4.2), (int)((double)this.getHeight() / 3.5), 
						(this.getWidth() / 2), (this.getHeight() / 2), 0, this.deptRatio.get(idx));
				startAngle = this.deptRatio.get(idx);
			}
			else {
				if (printDebugConsole == true) {
					System.out.printf("2 idx %d this.deptRatio.get %d start %d\n",idx, this.deptRatio.get(idx), startAngle);
				}
				g.setColor(deptColor.get(idx));
				
				if (idx == this.countDept.size() - 1) { // 현재 마지막 idx인 경우 최종 angle 값 계산
					endAngle = 360 - startAngle;
					g.fillArc((int)((double)this.getWidth() / 4.2), (int)((double)this.getHeight() / 3.5), 
							(this.getWidth() / 2), (this.getHeight() / 2), startAngle, endAngle);
				}
				else {
					g.fillArc((int)((double)this.getWidth() / 4.2), (int)((double)this.getHeight() / 3.5), 
							(this.getWidth() / 2), (this.getHeight() / 2), startAngle, this.deptRatio.get(idx));
				}
				startAngle = startAngle + this.deptRatio.get(idx);
			}
		}
		this.countDept.removeAll(countDept);
		this.deptRatio.removeAll(deptRatio);
		deptColor.removeAll(deptColor);
		//g.fillArc((int)((double)this.getWidth() / 4.2), (int)((double)this.getHeight() / 3.5), (this.getWidth() / 2), (this.getHeight() / 2), 0, 360);
	}
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: createCurrentStateTable()
	Function: Create table of PI chart information. this table show each department name, ratio, color.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void createCurrentStateTable() {
		// 테이블 색깔 column에 색을 지정하기 위한 코드
		TableRenderer tableRenderer = new TableRenderer();
		String colName[] = {"학과", "비율", "색"};
		this.model = new DefaultTableModel(colName, 0);
		this.currentStateTable = new JTable(this.model);
		this.currentStateTable.setPreferredScrollableViewportSize(new Dimension(this.getWidth() - 80, 80));
		this.currentStateTable.setDefaultRenderer(Object.class, tableRenderer);
		this.add(this.currentStateTable);
		this.currentStateTable.setEnabled(false);
		this.sp_CurrentState = new JScrollPane(this.currentStateTable);
		this.sp_CurrentState.setBounds(0, 0, this.getWidth(), 80);
		this.add(this.sp_CurrentState);
	}
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: calCurrentStateRatio()
	Function: Calculation current state book ratio each departments.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void calCurrentStateRatio() {
		String tempQuery = null;
		for (int idx = 0; idx < DBManage.deptName.size(); idx++) {
			tempQuery = query + " where department like '" + DBManage.deptName.get(idx) + "'";
			
			if (this.printDebugConsole == true) {
				System.out.printf("%s\n", tempQuery);
			}
			
			try {
				DBManage.rs = DBManage.stmt.executeQuery(tempQuery);
				
			    while(DBManage.rs.next()) {
			    	if (this.printDebugConsole == true) {
			    		System.out.printf("%s count %d\n", DBManage.deptName.get(idx), DBManage.rs.getInt("count"));
			    	}
			    	this.countDept.add(DBManage.rs.getInt("count"));
			    }
			    DBManage.rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			double tempstore = 0;
			double tempratio = 0;
			tempratio = ((double)this.countDept.get(idx) / (double)this.totalCount) * 360;
			tempstore = ((double)this.countDept.get(idx) / (double)this.totalCount) * 100;
			if (this.printDebugConsole == true) {
				System.out.printf("%f\n", tempratio);
			}
			
			this.deptRatio.add((int)tempratio);
			
			//this.generateColor((byte)tempstore);
			//this.deptColor.add(new Color((byte)tempstore, (byte)(tempstore + 50), 0));
			tempstore = Double.parseDouble(String.format("%.2f",tempstore));
			String[] row = {DBManage.deptName.get(idx), Double.toString(tempstore) + "%", ""};
			this.model.addRow(row);
			tempQuery = "";
		}
	}
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: generateColor()
	Function: Make color each departments.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void generateColor() {
		deptColor.add(Color.RED);
		deptColor.add(Color.CYAN);
		deptColor.add(Color.GREEN);
		deptColor.add(Color.BLUE);
		deptColor.add(Color.YELLOW);
		deptColor.add(Color.MAGENTA);
		deptColor.add(Color.ORANGE);
	}
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: readCurrentStateInfo()
	Function: read current state book from DB.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void readCurrentStateInfo() {
		DBManage.getDepartmentList();
		try {
			// total count of current state book rent.
			query = "select count(department) as count from "
					+ "(select student.department, bookrent.no from student, bookrent where "
					+ "student.id = bookrent.id group by student.department, bookrent.no)";
			
			DBManage.rs = DBManage.stmt.executeQuery(query);
			
		    while(DBManage.rs.next()) {
		    	if (this.printDebugConsole == true) {
		    		System.out.printf("total count %d\n", DBManage.rs.getInt("count"));
		    	}
		    	this.totalCount = DBManage.rs.getInt("count");
		    }
		    DBManage.rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.calCurrentStateRatio();
	}
}
