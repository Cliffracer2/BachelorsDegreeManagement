/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog class
	This class gathering dialog panels and manage listener of dialog.
----------------------------------------------------------------------------------------------------------------------------------- 
*/

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GatheringDialogs extends JDialog {
	private JPanel panel = null;
	
	private Font textFont = null;
	
	private JTextField tfemail = null;

	private JPasswordField pw_Field = null;
	private JPasswordField pw_FieldConfrim = null;
	
	private ArrayList<JLabel> labelText = new ArrayList<JLabel>();
	private JLabel pwMatchLabel = null;
	
	private JCheckBox checkBox = null;
	
	private JButton btnOK = null;
	private JButton btnCancel = null;
	private JButton btnLogin = null;

	private JLabel ImageLabel = null;

	public static JTextField tfNumber = null;
	public static JTextField tfId = null;
	private JTextField tfBookno = null;
	private JTextField tfRentDate = null;
	
	private JLabel accountInfoLabel = null;
	private JLabel idLabel = null;
	private JLabel nameLabel = null;
	private JLabel emailLabel = null;
	private JLabel joinDateLabel = null;
	private JLabel name1 = null;
	private JLabel name2 = null;
	private JLabel phone = null;
	
	private Calendar today = Calendar.getInstance();
	
	boolean printDebugConsole = false;
/*
-----------------------------------------------------------------------------------------------------------------------------------
	GatheringDialogs Constructor
	Function: Create dialog in accordance with Dialog type.
	Dialog type
	0: Login fail
	1: Account information
	2: Borrow book
	3: Return book
	4: Re-login
	5: Adjust account information
	6: Program information
----------------------------------------------------------------------------------------------------------------------------------- 
*/	
	public GatheringDialogs(JFrame frame, String title, int dialogType, boolean isModal) {
		super(frame, title, isModal);
		
		this.panel = new JPanel();
		this.panel.setLayout(null);
		this.setIconImage(BachelorsDegreeManagement.windowIconImage);
		
		switch (dialogType) {
			case 0: // Login fail
				this.setLoginFailDialog();
				this.listenerManageLoginFail();
				break;
			case 1: // Account information
				this.setAccountInfoDialog();
				this.listenerManageAccountInfo();
				break;
			case 2: // Borrow book
				this.setBorrowBookDialog();
				this.listenerManageBorrowBook();
				break;
			case 3: // Return book
				this.setReturnBookDialog();
				this.listenerManageReturnBook();
				break;
			case 4: // ReLogin
				this.setReLoginDialog();
				this.listenerManageReLogin();
				break;
			case 5: // Adjust account information
				this.setAdjustAccountInfoDialog();
				this.listenerManageAdjustAccount();
				break;
			case 6: // Program information
				this.setProgramInfo();
				this.listenerManageProgramInfo();
			default:
				break;
		}
		
		this.add(this.panel);
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Create dialog panels
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 0 
	Method name: setLoginFailDialog()
	Function: Create login fail dialog panel.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void setLoginFailDialog() {
		this.setSize(300, 200);
		this.panel.setBackground(new Color(0, 120, 215));
		
		
		if (printDebugConsole == true) {
			System.out.printf("setLayoutLabel\n");
		}
		// ȭ�� �̹��� ������
		Image img = Toolkit.getDefaultToolkit().getImage("images/sadFace.png");
		Icon icon = new ImageIcon(img);
		this.ImageLabel = new JLabel(icon);
		this.ImageLabel.setBounds(0, 0, 150, 150);
		this.panel.add(this.ImageLabel);
		
		this.btnOK = new JButton("Ȯ��");
		if (printDebugConsole == true) {
			System.out.printf("panel.getHeight() %d\n", this.getHeight());
		}
		this.btnOK.setBounds(170, 80, 70, 50);
		this.panel.add(this.btnOK);
		
		this.setResizable(false);
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 1
	Method name: setAccountInfoDialog()
	Function: Create account information dialog panel.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void setAccountInfoDialog() {
		this.setSize(300, 200);
		
		if (printDebugConsole == true) {
			System.out.printf("AccountInfoDialog setLayoutLabel\n");
		}
		
		// ���̾�α׿� ǥ�õǴ� �̹���
		Image imgMain = Toolkit.getDefaultToolkit().getImage("images/school.png");
		Icon iconMain = new ImageIcon(imgMain);
		this.accountInfoLabel = new JLabel(iconMain);
		this.accountInfoLabel.setBounds(0, 0, 120, 120);
		this.panel.add(this.accountInfoLabel);
		
		// DB�κ��� ���� ������ �о �ش� ������ Label�� �߰�
		try {	
			DBManage.rs = DBManage.stmt.executeQuery("select account_idx, account_type, user_id, user_password, user_name, email, to_char(join_date, 'YYYY-MM-DD') as joindate from account_information");
			
			while(DBManage.rs.next()) {
				if (printDebugConsole == true) {
					System.out.printf("\n%s\t|\t%s\t|\t%s\t|\t%s\n", 
							DBManage.rs.getString("joindate"), DBManage.rs.getString("user_id"), DBManage.rs.getString("user_name"), DBManage.rs.getString("email"));
				}
				this.idLabel = new JLabel("ID:	" + DBManage.rs.getString("user_id"));
				this.nameLabel = new JLabel("�̸�:	" + DBManage.rs.getString("user_name"));
				this.joinDateLabel = new JLabel("������: " + DBManage.rs.getString("joindate"));
				this.emailLabel = new JLabel(DBManage.rs.getString("email"));
			}
			this.idLabel.setBounds(110, 10, 120, 30);
			this.panel.add(this.idLabel);
			this.nameLabel.setBounds(110, 30, 120, 30);
			this.panel.add(this.nameLabel);
			this.joinDateLabel.setBounds(110, 50, 150, 30);
			this.panel.add(this.joinDateLabel);
			this.emailLabel.setBounds(110, 70, 150, 30);
			this.panel.add(this.emailLabel);
			
			DBManage.rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		this.btnOK = new JButton("Ȯ��");
		if (printDebugConsole == true) {
			System.out.printf("panel.getHeight() %d\n", this.getHeight());
		}
		this.btnOK.setBounds(110, (this.getHeight() - 90), 60, 40);
		this.panel.add(this.btnOK);
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 2
	Method name: setBorrowBookDialog()
	Function: Create borrow book dialog panel.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	// TODO: å��ȣ�� üũ�ڽ��� ����: (������ Ȯ���ϱ� ����), �й��� �Է��ϸ� �ٸ� �������� �ڵ����� ä������ �ٲٱ�
	private void setBorrowBookDialog() {
		this.setSize(300, 300);
		
		if (printDebugConsole == true) {
			System.out.printf("setLayoutLabel\n");
		}
		int default_X = 20;
		int default_Y = 15;
		int default_Width = 50;
		int default_Height = 25;
		
		// �ڵ����� ���� ��¥ �Է�		
		int year = this.today.get(Calendar.YEAR);
		int month = this.today.get(Calendar.MONTH) + 1;
		String strMonth = String.format("%02d", month);
		int day = this.today.get(Calendar.DAY_OF_MONTH);
		String strDay = String.format("%02d", day);
		
		this.labelText.add(new JLabel("�����ȣ"));
		// setBounds(x, y, width, height);
		this.labelText.get(0).setBounds(default_X, default_Y, default_Width, default_Height);
		this.panel.add(this.labelText.get(0));
		
		tfNumber = new JTextField(15);
		tfNumber.setBounds((default_X + 60), default_Y, 180, default_Height);
		tfNumber.setText(Integer.toString(year) + strMonth + strDay);
		this.panel.add(tfNumber);
		
		this.labelText.add(new JLabel("�й�"));
		this.labelText.get(1).setBounds(default_X, (default_Y + 30), default_Width, default_Height);
		this.panel.add(this.labelText.get(1));
		
		tfId = new JTextField(20);
		tfId.setBounds((default_X + 60), (default_Y + 30), 180, default_Height);
		this.panel.add(tfId);
		
		this.labelText.add(new JLabel("å��ȣ"));
		this.labelText.get(2).setBounds(default_X, (default_Y + 60), default_Width, default_Height);
		this.panel.add(this.labelText.get(2));
		
		this.tfBookno = new JTextField(20);
		this.tfBookno.setBounds((default_X + 60), (default_Y + 60), 180, default_Height);
		this.panel.add(this.tfBookno);
		
		this.labelText.add(new JLabel("���⳯¥"));
		this.labelText.get(3).setBounds(default_X, (default_Y + 90), default_Width, default_Height);
		this.panel.add(this.labelText.get(3));
		
		this.tfRentDate = new JTextField(20);
		this.tfRentDate.setBounds((default_X + 60), (default_Y + 90), 180, default_Height);
		this.tfRentDate.setText(Integer.toString(year) + strMonth + strDay);
		
		this.panel.add(this.tfRentDate);
		
		this.btnOK = new JButton("Ȯ��");
		if (printDebugConsole == true) {
			System.out.printf("panel.getHeight() %d\n", this.getHeight());
		}
		this.btnOK.setBounds(20, (this.getHeight() - 120), 70, 50);
		this.panel.add(this.btnOK);
		
		this.btnCancel = new JButton("���");
		this.btnCancel.setBounds(110, (this.getHeight() - 120), 70, 50);
		this.panel.add(this.btnCancel);
		
		this.setResizable(false);
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 3
	Method name: setReturnBookDialog()
	Function: Create return book dialog panel.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void setReturnBookDialog() {
		this.setSize(300, 200);
		this.setResizable(false);
		
		if (printDebugConsole == true) {
			System.out.printf("setLayoutLabel\n");
		}
		int default_X = 20;
		int default_Y = 15;
		int default_Width = 50;
		int default_Height = 25;
		
		// �ڵ����� ���� ��¥ �Է�		
//		int year = this.today.get(Calendar.YEAR);
//		int month = this.today.get(Calendar.MONTH) + 1;
//		String strMonth = String.format("%02d", month);
//		int day = this.today.get(Calendar.DAY_OF_MONTH);
//		String strDay = String.format("%02d", day);
		
		this.labelText.add(new JLabel("�����ȣ"));
		// setBounds(x, y, width, height);
		this.labelText.get(0).setBounds(default_X, default_Y, default_Width, default_Height);
		this.panel.add(this.labelText.get(0));
		
		tfNumber = new JTextField(15);
		tfNumber.setBounds((default_X + 60), default_Y, 180, default_Height);
		//tfNumber.setText(Integer.toString(year) + strMonth + strDay);
		panel.add(tfNumber);
		
		this.labelText.add(new JLabel("�й�"));
		this.labelText.get(1).setBounds(default_X, (default_Y + 30), default_Width, default_Height);
		this.panel.add(this.labelText.get(1));
		
		tfId = new JTextField(20);
		tfId.setBounds((default_X + 60), (default_Y + 30), 180, default_Height);
		this.panel.add(tfId);
		
		this.btnOK = new JButton("Ȯ��");
		if (printDebugConsole == true) {
			System.out.printf("panel.getHeight() %d\n", this.getHeight());
		}
		this.btnOK.setBounds(80, (this.getHeight() - 120), 70, 50);
		this.panel.add(this.btnOK);
		
		this.btnCancel = new JButton("���");
		this.btnCancel.setBounds(160, (this.getHeight() - 120), 70, 50);
		this.panel.add(this.btnCancel);
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 4
	Method name: setReLoginDialog()
	Function: Create re-login dialog panel.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void setReLoginDialog() {
		this.setSize(300, 220);
		this.setResizable(false);
		
		if (printDebugConsole == true) {
			System.out.printf("setLayoutLabel\n");
		}
		int default_X = 20;
		int default_Y = 50;
		int default_Width = 50;
		int default_Height = 25;
		
		this.labelText.add(new JLabel("���������� ���� �ٽ� �α����� ���ּ���."));
		this.labelText.get(0).setBounds(default_X, default_Y - 40, 260, default_Height);
		this.panel.add(this.labelText.get(0));
		
		// �ؽ�Ʈ �ʵ� ����
		this.labelText.add(new JLabel("ID"));
		// setBounds(x, y, width, height);
		this.labelText.get(1).setBounds(default_X, default_Y, default_Width, default_Height);
		this.panel.add(this.labelText.get(1));
		
		tfId = new JTextField(15);
		tfId.setBounds((default_X + 65), default_Y, 180, default_Height);
		this.panel.add(tfId);
		
		this.labelText.add(new JLabel("��й�ȣ"));
		this.labelText.get(2).setBounds(default_X, (default_Y + 30), default_Width, default_Height);
		this.panel.add(this.labelText.get(2));

		this.pw_Field = new JPasswordField();
		this.pw_Field.setEchoChar('��');
		this.pw_Field.setBounds((default_X + 65), (default_Y + 30), 180, default_Height);
		this.panel.add(this.pw_Field);
		
		this.btnLogin = new JButton("�α���");
		if (printDebugConsole == true) {
			System.out.printf("setReLoginDialog panel.getHeight() %d\n", this.getHeight());
		}
		this.btnLogin.setBounds(60, (this.getHeight() - 105), 70, 50);
		this.panel.add(this.btnLogin);
		
		this.btnCancel = new JButton("���");
		this.btnCancel.setBounds(160, (this.getHeight() - 105), 70, 50);
		this.panel.add(this.btnCancel);
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 5
	Method name: setAdjustAccountInfoDialog()
	Function: Create adjust account dialog panel.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void setAdjustAccountInfoDialog() {
		this.setSize(260, 385);
		
		if (printDebugConsole == true) {
			System.out.printf("setLayoutLabel\n");
		}
		int default_X = 20;
		int default_Y = 40;
		int default_Width = 200;
		int default_Height = 25;
		
		this.checkBox = new JCheckBox();
		this.checkBox.setText("��й�ȣ ����");
		this.checkBox.setBounds(20, 15, default_Width, default_Height);
		this.panel.add(this.checkBox);
		
		this.labelText.add(new JLabel("������ ��й�ȣ �Է�"));
		// setBounds(x, y, width, height);
		this.labelText.get(0).setBounds(default_X, default_Y, default_Width, default_Height);
		this.panel.add(this.labelText.get(0));
		
		this.pw_Field = new JPasswordField();
		this.pw_Field.setEchoChar('��');
		this.pw_Field.setBounds(default_X, (default_Y + 30), default_Width, default_Height);
		this.panel.add(this.pw_Field);
		
		this.labelText.add(new JLabel("��й�ȣ ���Է�"));
		// setBounds(x, y, width, height);
		this.labelText.get(1).setBounds(default_X, (default_Y + 60), default_Width, default_Height);
		this.panel.add(this.labelText.get(1));
		
		this.pw_FieldConfrim = new JPasswordField();
		this.pw_FieldConfrim.setEchoChar('��');
		this.pw_FieldConfrim.setBounds(default_X, (default_Y + 90), default_Width, default_Height);
		this.panel.add(this.pw_FieldConfrim);
		
		this.pwMatchLabel = new JLabel();
		this.pwMatchLabel.setBounds(default_X, (default_Y + 120), default_Width, default_Height);
		this.panel.add(this.pwMatchLabel);
		
		this.labelText.add(new JLabel("�̸��� ����"));
		this.labelText.get(2).setBounds(default_X, (default_Y + 150), default_Width, default_Height);
		this.panel.add(this.labelText.get(2));
		
		this.tfemail = new JTextField(20);
		this.tfemail.setBounds(default_X, (default_Y + 180), default_Width, default_Height);
		this.panel.add(this.tfemail);
		
		this.pw_Field.setEnabled(false);
		this.pw_FieldConfrim.setEnabled(false);
		
		this.btnOK = new JButton("Ȯ��");
		if (printDebugConsole == true) {
			System.out.printf("panel.getHeight() %d\n", this.getHeight());
		}
		this.btnOK.setBounds(40, (this.getHeight() - 110), 70, 50);
		this.panel.add(this.btnOK);
		
		this.btnCancel = new JButton("���");
		this.btnCancel.setBounds(130, (this.getHeight() - 110), 70, 50);
		this.panel.add(this.btnCancel);
		
		this.setResizable(false);
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 6
	Method name: setProgramInfo()
	Function: Create program information dialog panel.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void setProgramInfo() {
		this.setSize(330, 200);
		
		if (printDebugConsole == true) {
			System.out.printf("AccountInfoDialog setLayoutLabel\n");
		}
		
		// ���̾�α׿� ǥ�õǴ� �̹���
		Image imgName1 = Toolkit.getDefaultToolkit().getImage("images/M.png");
		Icon iconName1 = new ImageIcon(imgName1);
		this.name1 = new JLabel(iconName1);
		this.name1.setBounds(20, 30, 64, 64);
		this.panel.add(this.name1);
		Image imgName2 = Toolkit.getDefaultToolkit().getImage("images/Y.png");
		Icon iconName2 = new ImageIcon(imgName2);
		this.name2 = new JLabel(iconName2);
		this.name2.setBounds(70, 30, 64, 64);
		this.panel.add(this.name2);

		this.nameLabel = new JLabel("Creator: Yoon Min Yong");
		this.emailLabel = new JLabel("nicegiup@naver.com");
		this.phone = new JLabel("TEL: 010-7332-0258");
		
		this.nameLabel.setBounds(140, 30, 200, 30);
		this.panel.add(this.nameLabel);
		this.emailLabel.setBounds(140, 50, 200, 30);
		this.panel.add(this.emailLabel);
		this.phone.setBounds(140, 70, 200, 30);
		this.panel.add(this.phone);
		
		this.btnOK = new JButton("Ȯ��");
		if (printDebugConsole == true) {
			System.out.printf("panel.getHeight() %d\n", this.getHeight());
		}
		this.btnOK.setBounds(126, (this.getHeight() - 90), 60, 40);
		panel.add(this.btnOK);
	}
	
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Create dialog panels end
----------------------------------------------------------------------------------------------------------------------------------- 
*/

/*
-----------------------------------------------------------------------------------------------------------------------------------
	Management Listener
----------------------------------------------------------------------------------------------------------------------------------- 
*/

/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 0 
	Method name: listenerManageLoginFail()
	Function: Management login fail dialog listener.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void listenerManageLoginFail() {
		this.btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 1
	Method name: listenerManageAccountInfo()
	Function: Management account information dialog listener.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void listenerManageAccountInfo() {
		// Ȯ�ι�ư ������ ó��
		this.btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
	}

/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 2
	Method name: listenerManageBorrowBook()
	Function: Management borrow book dialog listener.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void listenerManageBorrowBook() {
		// Ȯ�ι�ư ������ ó��
		this.btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// bookrent table�� ������
				if (tfNumber.getText().length() < 10) {
					JOptionPane.showMessageDialog(null, "�����ȣ�� �Էµ� ���� ���ų� �߸��Ǿ����ϴ�.");
				}
				else if (tfId.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "�й��� �Էµ� ���� �����ϴ�.");
				}
				else if (tfBookno.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "å��ȣ�� �Էµ� ���� �����ϴ�.");
				}
				else if (tfRentDate.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "��¥�� �Էµ� ���� �����ϴ�.");
				}
				else {
					String BooknoString = String.format("%06d", Integer.valueOf(tfBookno.getText()));
					//tfBookno.getText().format("%06s", tfBookno.getText());
					if (printDebugConsole == true) {
						System.out.println("insert into bookrent values('" + 
								tfNumber.getText() + "', '" + tfId.getText() + "', '" + 
								BooknoString + "', '" + tfRentDate.getText() + "')");
					}
					
					try {
						DBManage.stmt.executeUpdate("insert into bookrent values('" + 
								tfNumber.getText() + "', '" + tfId.getText() + "', '" + 
								BooknoString + "', '" + tfRentDate.getText() + "')");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "������ �Ǿ����ϴ�.", "������ Ȯ��", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					// ����� ��ģ �� �ؽ�Ʈ �ʵ忡 �ִ� �� �ʱ�ȭ
					tfNumber.setText("");
					tfId.setText("");
					tfBookno.setText("");
					tfRentDate.setText("");
				}
			}
			
		});
		
		// ��ҹ�ư ������ ó��
		this.btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// �ؽ�Ʈ �ʵ� �ʱ�ȭ
				tfNumber.setText("");
				tfId.setText("");
				tfBookno.setText("");
				tfRentDate.setText("");
				setVisible(false);
			}
			
		});
	}

/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 3
	Method name: listenerManageReturnBook()
	Function: Management return book dialog listener.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void listenerManageReturnBook() {
		// Ȯ�ι�ư ������ ó��
		this.btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (tfNumber.getText().length() < 10) {
					JOptionPane.showMessageDialog(null, "�����ȣ�� �Էµ� ���� ���ų� �߸��Ǿ����ϴ�.");
				}
				else if (tfId.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "�й��� �Էµ� ���� �����ϴ�.");
				}
				else {
					//tfBookno.getText().format("%06s", tfBookno.getText());
					if (printDebugConsole == true) {
						System.out.println("delete from bookrent where id = '" + tfId.getText() + "'" + " and " + "no = '" + tfNumber.getText() + "'");
					}
					// bookrent table�� �ִ� ������ ����
					try {
						DBManage.stmt.executeUpdate("delete from bookrent where id = '" + tfId.getText() + "'" + " and " + "no = '" + tfNumber.getText() + "'");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "�ݳ�ó�� �Ǿ����ϴ�.", "�ݳ�ó�� Ȯ��", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					// ����� ��ģ �� �ؽ�Ʈ �ʵ忡 �ִ� �� �ʱ�ȭ
					tfNumber.setText("");
					tfId.setText("");
				}
			}
			
		});
		
		// ��ҹ�ư ������ ó��
		this.btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// �ؽ�Ʈ �ʵ� �ʱ�ȭ
				tfNumber.setText("");
				tfId.setText("");
				setVisible(false);
			}
			
		});
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 4
	Method name: listenerManageReLogin()
	Function: Management re-login dialog listener.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void listenerManageReLogin() {
		// Ȯ�ι�ư ������ ó��
		this.btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean loginSuccess = false;
				
				loginSuccess = DBManage.loginProcess(tfId, pw_Field, false);
				if (loginSuccess == true) {
					// �α����� �����ϸ� ������ �����ϴ� â�� ����.
					if (printDebugConsole == true) {
						System.out.printf("\n\nlistenerManageReLogin loginSuccess :)\n\n");
					}
					setVisible(false);
					BachelorsDegreeManagement.gatheringDialogs = new GatheringDialogs(null, "�������� ����", 5, true);
					BachelorsDegreeManagement.gatheringDialogs.setLocationRelativeTo(null);
					BachelorsDegreeManagement.gatheringDialogs.setVisible(true);
				}
				else {
					BachelorsDegreeManagement.gatheringDialogs = new GatheringDialogs(null, "�α��� ����", 0, true);
					BachelorsDegreeManagement.gatheringDialogs.setLocationRelativeTo(null);
					BachelorsDegreeManagement.gatheringDialogs.setVisible(true);
				}
			}
			
		});
		
		// ��ҹ�ư ������ ó��
		this.btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 5
	Method name: listenerManageAdjustAccount()
	Function: Management adjust account dialog listener.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void listenerManageAdjustAccount() {
		
		this.pw_Field.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (printDebugConsole == true) {
					System.out.printf("pw_Field focusGained\n");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
			}
			
		});
		
		this.pw_FieldConfrim.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				char[] getPassword = pw_Field.getPassword();
				String tempPassStore = String.valueOf(getPassword);
				char[] getPasswordConfirm = pw_FieldConfrim.getPassword();
				String tempPassStoreConfirm = String.valueOf(getPasswordConfirm);
				
				System.out.printf("pw_FieldConfrim focusGained\n");

				if (printDebugConsole == true) {
					System.out.printf("pw %s vs pwc %s\n", tempPassStore, tempPassStoreConfirm);
				}
				
				// ��й�ȣ Ȯ�� �ʵ尡 ��Ŀ���Ǹ� �Է��� ��й�ȣ�� ������ Ȯ���ϰ� �޽����� ����.
				if (tempPassStoreConfirm != null) {
					textFont = new Font("SanSerif", Font.BOLD, 13); // �ؽ�Ʈ ��Ʈ ����
					pwMatchLabel.setFont(textFont);
					if (tempPassStoreConfirm.equals(tempPassStore)) {
						pwMatchLabel.setForeground(Color.GREEN);
						pwMatchLabel.setText("��й�ȣ�� ��ġ�մϴ�.");
					}
					else {
						pwMatchLabel.setForeground(Color.RED);
						pwMatchLabel.setText("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
					}
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				char[] getPassword = pw_Field.getPassword();
				String tempPassStore = String.valueOf(getPassword);
				char[] getPasswordConfirm = pw_FieldConfrim.getPassword();
				String tempPassStoreConfirm = String.valueOf(getPasswordConfirm);
				
				System.out.printf("pw_FieldConfrim focusLost\n");

				if (printDebugConsole == true) {
					System.out.printf("pw %s vs pwc %s\n", tempPassStore, tempPassStoreConfirm);
				}
				
				// ��й�ȣ Ȯ�� �ʵ� ��Ŀ���� ����� ���� �Է��� ��й�ȣ�� ������ Ȯ���ϰ� �޽����� ����.
				if (tempPassStoreConfirm != null) {
					textFont = new Font("SanSerif", Font.BOLD, 13); // �ؽ�Ʈ ��Ʈ ����
					pwMatchLabel.setFont(textFont);
					if (tempPassStoreConfirm.equals(tempPassStore)) {
						pwMatchLabel.setForeground(Color.GREEN);
						pwMatchLabel.setText("��й�ȣ�� ��ġ�մϴ�.");
					}
					else {
						pwMatchLabel.setForeground(Color.RED);
						pwMatchLabel.setText("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
					}
				}
			}
			
		});
		
		// Ȯ�ι�ư ������ ó��
		this.btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		
		// ��ҹ�ư ������ ó��
		this.btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
		
		this.checkBox.addItemListener(new ItemListener() {

			@SuppressWarnings("static-access")
			@Override
			public void itemStateChanged(ItemEvent e) {
				int stateChange = e.getStateChange();
				// üũ �ڽ� ������ ������ �Է��� ����� �ʱ�ȭ�ϰ� ��Ȱ��ȭ, üũ�� �ʵ� Ȱ��ȭ
				if (stateChange == e.DESELECTED) {
					pw_Field.setText(null);
					pw_FieldConfrim.setText(null);
					pw_Field.setEnabled(false);
					pw_FieldConfrim.setEnabled(false);
					pwMatchLabel.setText(null);
				}
				else {
					pw_Field.setEnabled(true);
					pw_FieldConfrim.setEnabled(true);
				}
			}
			
		});
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Dialog type: 6
	Method name: listenerManageProgramInfo()
	Function: Management program information dialog listener.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void listenerManageProgramInfo() {
		// Ȯ�ι�ư ������ ó��
		this.btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Management Listener end
----------------------------------------------------------------------------------------------------------------------------------- 
*/

}
