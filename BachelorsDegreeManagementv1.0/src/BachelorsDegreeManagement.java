/* 
  	�ڹ� �̴� ������Ʈ: �л���� ���α׷�
  	Oracle DB�� �����Ͽ� �л������ �ϴ� ���α׷��� �����Ѵ�.
*/
/*
-----------------------------------------------------------------------------------------------------------------------------------
	BachelorsDegreeManagement class.
	This is program main frame.
	In this class, manage panels using listener and also doing login process.
	When program start, connect to DB and disconnect at exit.
	
	2021.04.07 ymy - first write.
	2021.04.11 ymy - after login, show main image at main frame. and add some account dialog.
	2021.04.14 ymy - write some comment
	2021.04.15 ymy - add dialog and release 1.0
----------------------------------------------------------------------------------------------------------------------------------- 
*/

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class BachelorsDegreeManagement extends JFrame {

	public static String programVersion = "v1.0";
	public static JFrame frameMyself = null;
	
	private JTextField tf_IDField = null;
	private JPasswordField tf_Password = null;
	
	private Font textFont = null;
	
	private JLabel mainImageLabel = null;
	private JLabel loginImageLabel = null;
	
	private JButton btnLogin = null;
	
	private JPanel bookRentPanel = null;  // �޴��� ȭ���� ��µǴ� �г�
	private JPanel studentPanel = null;
	private JPanel currentStateBookRentPanel = null;
	private JPanel loginPanel = null;
	
	private JMenuBar menuBar = null;
	
	private JMenu m_student = null;
	private JMenu m_book = null;
	private JMenu m_account = null;
	private JMenu m_Program = null;
	
	private JMenuItem mi_list = null;
	private JMenuItem mi_bookRent = null;
	private JMenuItem mi_currentStateBookRent = null;
	private JMenuItem mi_accountInfo = null;
	private JMenuItem mi_adjustAccount = null;
	private JMenuItem mi_Logout = null;
	private JMenuItem mi_ProgramInfo = null;

	public static GatheringDialogs gatheringDialogs = null;
	
	public static String showMenuId = new String();
	
	private static ImageIcon windowIcon = new ImageIcon("images/windowIcon.png");
	
	public static Image windowIconImage = windowIcon.getImage();
	
	private int currentPanel = 0; // 0: ����ȭ��, 1: �л�����, 2: ��������, 3: ������Ȳ
	
	private static boolean printDebugConsole = false;
	private boolean skipLogin = false;	// ���߿� ���� �����ϼ��� ����
/*
-----------------------------------------------------------------------------------------------------------------------------------
	BachelorsDegreeManagement Constructor
	Function: Create main frame include login panel. when program start, that show login panel first.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	public BachelorsDegreeManagement() {
		this.setTitle("�л����");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(windowIconImage);

		DBManage.DB_Connect("jdbc:oracle:thin:@localhost:1521:xe", "ora_user", "hong");
		
		// gui look and feel �׸� ����
		/*
			���� PC�� �׸� ����
		 	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		*/
		try {

			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//this.createMainFrameImage();
		this.createMenuBar(false);
		if (skipLogin == true) {
			this.menuBar.setVisible(true);
		}
		else {
			this.createLoginPanel();
			this.add(this.loginPanel);
			this.menuBar.setVisible(false);
			this.setResizable(false);
		}
		
		this.studentPanel = new JPanel();	// studentPanel ����
		this.bookRentPanel = new JPanel();	// bookRentPanel ����
		this.currentStateBookRentPanel = new JPanel();

		this.listenerManageMainFrame(false); // ������ ó�� �޼ҵ�
		
		this.setSize(500, 520);
		this.setVisible(true);
		
        //���α׷� ����� ȭ�� ������� �����ϵ��� �Ѵ�.
        this.setLocationRelativeTo(null);
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: createMainFrameImage()
	Function: Create main frame image.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void createMainFrameImage() {
		// ���� ȭ�� �̹��� ������
		Image imgMain = Toolkit.getDefaultToolkit().getImage("images/windowIcon.png");
		Icon iconMain = new ImageIcon(imgMain);
		this.mainImageLabel = new JLabel(iconMain);
		this.mainImageLabel.setBounds(152, 80, 150, 150);
		this.add(this.mainImageLabel);
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: createMenuBar()
	Function: Create menu bar.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void createMenuBar(boolean isLogin) {
		
		if (isLogin == false) { // �α��� ��
			this.menuBar = new JMenuBar();
			
			this.m_student = new JMenu("�л�����");
			this.menuBar.add(this.m_student);
			this.m_book = new JMenu("��������");
			this.menuBar.add(m_book);
			
			this.mi_list = new JMenuItem("�л�����");
			this.m_student.add(this.mi_list);
			
			this.mi_bookRent = new JMenuItem("������");
			this.m_book.add(this.mi_bookRent);
			
			this.mi_currentStateBookRent = new JMenuItem("������Ȳ");
			this.m_book.add(this.mi_currentStateBookRent);
			
			this.setJMenuBar(this.menuBar);
		}
		else { // �α��� �� ���� ���� �޴��� �߰�
			if (printDebugConsole == true) {
				System.out.printf("createMenuBar isLogin %b\n", isLogin);
			}
			
			this.mi_accountInfo = new JMenuItem("��������");
			this.m_account.add(this.mi_accountInfo);
			this.mi_adjustAccount = new JMenuItem("�������� ����");
			this.m_account.add(this.mi_adjustAccount);
			this.mi_Logout = new JMenuItem("�α׾ƿ�");
			this.m_account.add(this.mi_Logout);
			
			//this.m_Program = new JMenu("���α׷�");
			this.mi_ProgramInfo = new JMenuItem("���α׷� ����");
			this.m_Program.add(this.mi_ProgramInfo);
		}
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: createLoginPanel()
	Function: Create login panel.	
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void createLoginPanel() {
		this.loginPanel = new JPanel();
		this.loginPanel.setLayout(null);
		//this.loginPanel.setBackground(new Color(250, 225, 0)); // ���� ����
		this.loginPanel.setBackground(new Color(15, 141, 191)); // ���� ����
		this.textFont = new Font("SanSerif", Font.BOLD, 16); // �ؽ�Ʈ ��Ʈ ����
		
		// �α��� ȭ�� �̹��� ������
		Image imgMain = Toolkit.getDefaultToolkit().getImage("images/graduation.png");
		Icon iconMain = new ImageIcon(imgMain);
		this.loginImageLabel = new JLabel(iconMain);
		this.loginImageLabel.setBounds(152, 80, 150, 150);
		this.loginPanel.add(this.loginImageLabel);
		
		// ID �Է� �ؽ�Ʈ �ʵ� ����
		this.tf_IDField = new JTextField();
		this.tf_IDField.setBounds(130, 280, 200, 35);
		this.tf_IDField.setFont(this.textFont);
		this.tf_IDField.setForeground(new Color(210, 210, 210));
		this.tf_IDField.setBackground(Color.WHITE);
		this.tf_IDField.setText("������ ID �Է�");
		this.loginPanel.add(this.tf_IDField);
		
		// PW �Է� �ؽ�Ʈ �ʵ� ����
		this.tf_Password = new JPasswordField();
		this.tf_Password.setBounds(130, 315, 200, 35);
		this.tf_Password.setEchoChar('��');
		this.textFont = new Font("SanSerif", Font.BOLD, 10); // �ؽ�Ʈ ��Ʈ ����
		this.tf_Password.setFont(this.textFont);
		this.tf_Password.setForeground(new Color(210, 210, 210));
		this.tf_Password.setBackground(Color.WHITE);
		this.tf_Password.setText("��й�ȣ�Է�");
		this.loginPanel.add(this.tf_Password);
		
		// �α��� ��ư ����
		this.btnLogin = new JButton("�α���");
		this.btnLogin.setForeground(Color.WHITE);
		//this.btnLogin.setBackground(new Color(82, 55, 56));
		this.btnLogin.setBackground(new Color(7, 26, 64));
		this.btnLogin.setBorderPainted(false);
		this.btnLogin.setBounds(130, 370, 200, 35);
		this.loginPanel.add(this.btnLogin);
	}
	
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Main frame listener management
	Method name: listenerManageMainFrame()
	Function: listener management before login, after login.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	private void listenerManageMainFrame(boolean isLogin) {
		
		// DB close �Ϻη� �޼ҵ�� �˾ƺ����� �����ʷ� ȣ����
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {

				DBManage.DB_Close();
			}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {
				if (printDebugConsole == true) {
					System.out.printf("windowActivated\n");
				}
				if (currentPanel == 2) { // ���� ���������г��� ���
					setResizable(true);
					bookRentPanel.removeAll();
					bookRentPanel.add(new BookRent(getWidth(), getHeight())); 	// ȭ�� ����
					getContentPane().removeAll();
					getContentPane().add(bookRentPanel);
				}
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				if (printDebugConsole == true) {
					System.out.printf("windowDeactivated\n");
				}
			}
			
		});
		
		// ������Ʈ ������. �Ϻη� �޼ҵ�� �˾ƺ����� �����ʷ� ȣ����
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				if (printDebugConsole == true) {
					System.out.printf("componentResized\n");
				}
				// ������ ���� �� ����� �°� �缳���ؼ� �ٽ� repaint�Ѵ�.
				if (currentPanel == 1) { // �л�����
					setResizable(true);
					studentPanel.removeAll();
					studentPanel.add(new Student(getWidth(), getHeight())); 	// ȭ�� ����
					getContentPane().removeAll();
					getContentPane().add(studentPanel);
				}
				else if (currentPanel == 2) { // ��������
					setResizable(true);
					bookRentPanel.removeAll();
					bookRentPanel.add(new BookRent(getWidth(), getHeight())); 	// ȭ�� ����
					getContentPane().removeAll();
					getContentPane().add(bookRentPanel);
					//frameMyself = (JFrame) getContentPane();
				}
				else if (currentPanel == 3) {
					setResizable(true);
					getContentPane().removeAll();
					currentStateBookRentPanel.removeAll();
					currentStateBookRentPanel.add(new BookRentPIChart(getWidth(), getHeight()));
					getContentPane().add(currentStateBookRentPanel);
				}
				else {
					// nothing. but may additional future.
				}

				revalidate();
				repaint();
			}

			@Override
			public void componentMoved(ComponentEvent e) {}

			@Override
			public void componentShown(ComponentEvent e) {}

			@Override
			public void componentHidden(ComponentEvent e) {}
			
		});
		
		// �л� ���� ������
		this.mi_list.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (printDebugConsole == true) {
					System.out.printf("enter mi_list actionPerformed\n");
				}
				currentPanel = 1;
				setResizable(true);
				studentPanel.removeAll();
				studentPanel.add(new Student(getWidth(), getHeight())); 	// ȭ�� ����
				studentPanel.setLayout(null);		// ���̾ƿ��������
				getContentPane().removeAll();
				getContentPane().add(studentPanel);
				revalidate();
				repaint();
			}
			
		});
		
		// å���� ������
		this.mi_bookRent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentPanel = 2;
				setResizable(true);
				bookRentPanel.removeAll();
				bookRentPanel.add(new BookRent(getWidth(), getHeight())); 	// ȭ�� ����
				bookRentPanel.setLayout(null);		// ���̾ƿ��������
				getContentPane().removeAll();
				getContentPane().add(bookRentPanel);
				revalidate();
				repaint();
			}
		});
		
		// ������Ȳ ������
		this.mi_currentStateBookRent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentPanel = 3;
				getContentPane().removeAll();
				setResizable(true);
				currentStateBookRentPanel.removeAll();
				currentStateBookRentPanel.add(new BookRentPIChart(getWidth(), getHeight()));
				currentStateBookRentPanel.setLayout(null);
				getContentPane().add(currentStateBookRentPanel);
				revalidate();
				repaint();
			}
			
		});
		
		// ID �Է�â ������
		this.tf_IDField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
        		if (printDebugConsole == true) {
        			System.out.printf("tfid focusGained\n");
        		}
        		if (tf_IDField.getText().equals("") || tf_IDField.getText().equals("������ ID �Է�")) {
    				textFont = new Font("SanSerif", Font.PLAIN, 16);
    				tf_IDField.setFont(textFont);
    				tf_IDField.setForeground(new Color(0, 0, 0));
    				tf_IDField.setBackground(Color.WHITE);
    				tf_IDField.setText("");
        		}
			}

			@Override
			public void focusLost(FocusEvent e) {
        		if (printDebugConsole == true) {
        			System.out.printf("tfid focusLost\n");
        		}
				if (tf_IDField.getText().equals("")) {
	        		textFont = new Font("SanSerif", Font.BOLD, 16); // �ؽ�Ʈ ��Ʈ ����
					tf_IDField.setFont(textFont);
					tf_IDField.setForeground(new Color(210, 210, 210));
					tf_IDField.setBackground(Color.WHITE);
					tf_IDField.setText("������ ID �Է�");
				}
			}
			
		});
		
		// Password �Է�â ������
		this.tf_Password.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				char[] getPassword = tf_Password.getPassword();
				String tempPassStore = String.valueOf(getPassword);
        		if (printDebugConsole == true) {
        			System.out.printf("tfpass focusGained\n");
        		}
        		if (tempPassStore.equals("") || tempPassStore.equals("��й�ȣ�Է�")) {
    				textFont = new Font("SanSerif", Font.PLAIN, 16);
    				tf_Password.setForeground(new Color(0, 0, 0));
    				tf_Password.setBackground(Color.WHITE);
    				tf_Password.setText("");
        		}
			}

			@Override
			public void focusLost(FocusEvent e) {
				char[] getPassword = tf_Password.getPassword();
				String tempPassStore = String.valueOf(getPassword);
        		if (printDebugConsole == true) {
        			System.out.printf("tfpass focusLost\n");
        		}
				if (tempPassStore.equals("")) {
					tf_Password.setFont(textFont);
					tf_Password.setForeground(new Color(210, 210, 210));
					tf_Password.setBackground(Color.WHITE);
					tf_Password.setText("��й�ȣ�Է�");
				}
			}
			
		});
		
		// �α��� ��ư ������
		this.btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean loginSuccess = false;
				
				// DB�� ����Ǿ��ִ� ������ ���� id, ��й�ȣ�� ��ġ�ϴ� ��쿡 �α��� ����ó��
				loginSuccess = DBManage.loginProcess(tf_IDField, tf_Password, true);
				if (loginSuccess == true) {
					if (printDebugConsole == true) {
						System.out.printf("addActionListener Login success :)\n");
					}
					if (m_account != null) { // ������ �� ������ ������ �����ϱ� ���� ���ǹ�
						m_account.removeAll();
						m_Program.removeAll();
						menuBar.remove(m_account);
						menuBar.remove(m_Program);
					}
					m_account = new JMenu(showMenuId + " ���� ��");
					menuBar.add(m_account);
					m_Program = new JMenu("���α׷�");
					menuBar.add(m_Program);
					createMenuBar(true); // �α��� ���� �� �޴��ٿ� ID���� �߰�
					setJMenuBar(menuBar);
					listenerManageMainFrame(true);
					setResizable(true);
					menuBar.setVisible(true);
					getContentPane().removeAll();
					createMainFrameImage();
					revalidate();
					repaint();
				}
				else {
					if (printDebugConsole == true) {
						System.out.printf("addActionListener Login fail :(\n");
					}
					gatheringDialogs = new GatheringDialogs(null, "�α��� ����", 0, true);
					gatheringDialogs.setLocationRelativeTo(null);
					gatheringDialogs.setVisible(true);
				}
			}
			
		});
		
		if (isLogin == true) {
			
			this.mi_accountInfo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// ���� ���� ǥ��.
					gatheringDialogs = new GatheringDialogs(null, "��������", 1, true);
					gatheringDialogs.setLocationRelativeTo(null);
					gatheringDialogs.setVisible(true);
				}
				
			});
			
			this.mi_adjustAccount.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					gatheringDialogs = new GatheringDialogs(BachelorsDegreeManagement.this, "�α��� Ȯ��", 4, true);
					gatheringDialogs.setLocationRelativeTo(null);
					gatheringDialogs.setVisible(true);
				}
				
			});
			
			// Logout �޴� ��ư Ŭ�� ������
			this.mi_Logout.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int resultConfirm;
					resultConfirm = JOptionPane.showConfirmDialog(null, "�α׾ƿ��� �����մϱ�?", "�α׾ƿ� Ȯ��", JOptionPane.YES_NO_OPTION);
	        		if (printDebugConsole == true) {
	        			System.out.printf("mouseClicked logout now resultConfirm %d\n", resultConfirm);
	        		}
	        		if (resultConfirm == 0) { // OK�� ���� ���
	        			// �α׾ƿ� �� �α��� �г� ���� �ʱ�ȭ(ó�� ������ ���� ������ �ϱ�����.)
		        		currentPanel = 0;
		        		setSize(500, 520);
		        		setResizable(false);
						tf_Password.setForeground(new Color(210, 210, 210));
						tf_Password.setBackground(Color.WHITE);
						tf_Password.setText("��й�ȣ�Է�");
		        		textFont = new Font("SanSerif", Font.BOLD, 16); // �ؽ�Ʈ ��Ʈ ����
						tf_IDField.setFont(textFont);
						tf_IDField.setForeground(new Color(210, 210, 210));
						tf_IDField.setBackground(Color.WHITE);
						tf_IDField.setText("������ ID �Է�");
		        		menuBar.setVisible(false);
						getContentPane().removeAll();
						getContentPane().add(loginPanel);
						revalidate();
						repaint();
	        		}
				}
				
			});
			
			this.mi_ProgramInfo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// ���� ���� ǥ��.
					gatheringDialogs = new GatheringDialogs(null, "�л���� ���α׷� " + programVersion, 6, true);
					gatheringDialogs.setLocationRelativeTo(null);
					gatheringDialogs.setVisible(true);
				}
				
			});
		}

	}
/*
-----------------------------------------------------------------------------------------------------------------------------------
	Method name: main()
	Function: Program main method.
----------------------------------------------------------------------------------------------------------------------------------- 
*/
	public static void main(String[] args) {
		/*
		if (printDebugConsole == true) {
		    UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		    for (UIManager.LookAndFeelInfo look : looks) {
		      System.out.println(look.getClassName());
		    }
		}
		*/
		new BachelorsDegreeManagement();
		
//		while(true) {
//        	try {
//        		if (printDebugConsole == true) {
//        			//System.out.printf("1 msg\n");
//        		}
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				
//				e.printStackTrace();
//			}
//		}
	}
}
