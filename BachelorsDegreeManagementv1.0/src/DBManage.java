/*
-----------------------------------------------------------------------------------------------------------------------------------
	DBManage class.
	This class manage related DB. 
	
	2021.04.08 ymy - first write.
----------------------------------------------------------------------------------------------------------------------------------- 
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class DBManage {
	
	private static Connection conn; //Connection
	public static Statement stmt; //Statement��ü ����
	public static ResultSet rs;
	
	public static ArrayList<String> deptName = new ArrayList<String>();
	
	static boolean printDebugConsole = false;
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: DB_Connect()
	Function: Connect to DB using input address, id, password.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	public static void DB_Connect(String DB_Address, String userID, String password) {
		// DB connection
		try {
			//oracle jdbc����̹� �ε�
			Class.forName("oracle.jdbc.driver.OracleDriver"); // jdbc driver load
			conn = DriverManager.getConnection(DB_Address, userID, password); // ����
			stmt = conn.createStatement();
			if (printDebugConsole == true) {
				System.out.printf("����Ϸ�\n");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: DB_Connect()
	Function: Close connect to DB.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	public static void DB_Close() {
		
		try {
			//rs.close();
			stmt.close();
			conn.close();
			if (printDebugConsole == true) {
				System.out.printf("��������\n");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: loginProcess()
	Function: Handling login process.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	public static boolean loginProcess(JTextField tf_IDField, JPasswordField tf_Password, boolean isMainLogin) {
		boolean loginSuccess = false;
		char[] getPassword = tf_Password.getPassword();
		String tempPassStore = String.valueOf(getPassword);
		String adminID_DB = new String();		// DB�� ����� ������ ID 
		String adminPassword_DB = new String();	// DB�� ����� ������ ��й�ȣ
		
		if (printDebugConsole == true) {
			System.out.printf("id %s\n", tf_IDField.getText());
			System.out.printf("Pw char ");
			for (int idx = 0; idx < getPassword.length; idx++) {
				System.out.printf("%c", getPassword[idx]);
			}
			System.out.println();
			System.out.printf("Pw string %s\n", tempPassStore);
		}
		
		try {
			rs = stmt.executeQuery("select * from account_information");
			
			while(rs.next()) {
				if (printDebugConsole == true) {
					System.out.printf("\n%s\t|\t%s\t|\t%s\n", 
							rs.getString("account_type"), rs.getString("user_id"), rs.getString("user_password"));
				}
				
				// DB id Ÿ���� ������ �̸� �� ���������� id, ��й�ȣ�� ����
				if (rs.getString("account_type").equals("admin")) {
					if (printDebugConsole == true) {
						System.out.printf("������ ���� Ȯ��\nid: %s\npw: %s\n", rs.getString("user_id"), rs.getString("user_password"));
					}
					adminID_DB = rs.getString("user_id");
					adminPassword_DB = rs.getString("user_password");
				}
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		// DB�� ����Ǿ��ִ� ������ ���� id, ��й�ȣ�� ��ġ�ϴ� ��쿡 �α��� ����ó��
		if (tf_IDField.getText().equals(adminID_DB) && tempPassStore.equals(adminPassword_DB)) {
			if (printDebugConsole == true) {
				System.out.printf("loginProcess success :)\n");
			}
			// �α��ο� �����ϸ� �ش� ID�� ǥ���ϴ� �޴��� ���� �� �޴��ٴ� ���������� ���.
			if (isMainLogin == true) {
				BachelorsDegreeManagement.showMenuId = adminID_DB;
			}
			loginSuccess = true;
		}
		else {
			if (printDebugConsole == true) {
				System.out.printf("loginProcess fail :(\n");
			}
			loginSuccess = false;
		}
		
		return loginSuccess;
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: getDepartmentList()
	Function: Read department list from DB.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	public static void getDepartmentList() {
		DBManage.deptName.removeAll(DBManage.deptName); // ��ø add�� �������� ó�� �ҷ����� ����Ʈ �ʱ�ȭ.
		try {
			rs = stmt.executeQuery("select student.department from student group by student.department");
			while(DBManage.rs.next()) {
				if (printDebugConsole == true) {
					System.out.printf("\n%s\n", rs.getString("department"));
				}
				
				deptName.add(DBManage.rs.getString("department"));
			}
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: readWriteDelete_DB()
	Function: DB information read, write, update, delete.
	accessType
	1: Insert student information
	2: Update information
	3: Read student list
	4: Delete student information
	5: Id search
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	public static boolean readWriteDelete_DB(JTextField tfId, JTextField tfName, JTextField tfDepartment, JTextField tfAddress, DefaultTableModel model, int accessType) {
		boolean isErrorOccur = false;
		
		try {
			switch (accessType) {
				case 1: // ���
					stmt.executeUpdate("insert into student values('" + 
							tfId.getText() + "', '" + tfName.getText() + "', '" + 
							tfDepartment.getText() + "', '" + tfAddress.getText() + "')");
					rs = stmt.executeQuery("select * from student");
					break;
				case 2: // ����
					stmt.executeUpdate("update student set name = '" + 
							tfName.getText() + "', department = '" + tfDepartment.getText() + 
							"', address = '" + tfAddress.getText() + "' where id = '" + tfId.getText() + "'");
					rs = stmt.executeQuery("select * from student");
					break;
				case 3: // ���
					// do nothing
					rs = stmt.executeQuery("select * from student");
					break;
				case 4: // ����
					stmt.executeUpdate("delete from student where id = '" + tfId.getText() + "'");
					rs = stmt.executeQuery("select * from student");
					break;
				case 5: // �˻�
					if (tfId.getText().length() == 4) { // �˻��� �⵵�� �Է��� ���
						int yearVal = Integer.valueOf(tfId.getText());
						int tillYear = yearVal + 1;
						//System.out.printf("get text length %d year %d\n", tfId.getText().length(), yearVal);
						rs = stmt.executeQuery("select * from student where id > '" + 
								String.valueOf(yearVal) + "' and id < '" + String.valueOf(tillYear) + "'");
					}
					else {
						rs = stmt.executeQuery("select * from student where id = '" + tfId.getText() + "'");
					}
					break;
				default:
					rs = stmt.executeQuery("select * from student");
					break;
			}
			
			model.setNumRows(0);
			
			while(rs.next()) {
				if (printDebugConsole == true) {
					System.out.printf("\n%s\t|\t%s\t|\t%s\n", 
							rs.getString("id"), rs.getString("name"), rs.getString("department"));
				}
				if (accessType == 5) {
					tfName.setText(rs.getString("name"));
					tfDepartment.setText(rs.getString("department"));
					tfAddress.setText(rs.getString("address"));
				}
				
				String[] row = new String[4];
				row[0] = rs.getString("id");
				row[1] = rs.getString("name");
				row[2] = rs.getString("department");
				row[3] = rs.getString("address");
				
				model.addRow(row);
			}
			
			rs.close();
		}
		catch (Exception e1) {
			if (printDebugConsole == true) {
				System.out.println("Program fail :(");
			}
			e1.printStackTrace();
			isErrorOccur = true;
		}
		
		return isErrorOccur;
	}

}
