/*
	Student class.
	This class doing manage student informations.
	It can see information, adjust name, student id, student major, address or 
	add student informations or delete.
	
	2021.04.07 ymy - first write.
*/

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
//import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Student extends JPanel {

	private JTextField tfId = null;
	private JTextField tfName = null;
	private JTextField tfDepartment = null;
	private JTextField tfAddress = null;

	private JScrollPane listScroll = null;
	
	private JButton btnInsert = null;
	private JButton btnSelect = null;
	private JButton btnUpdate = null;
	private JButton btnDelete = null;
	private JButton btnSearch = null;
	
	private ImageIcon iconInsert = new ImageIcon("images/registration.png");
	private ImageIcon iconSelect = new ImageIcon("images/list.png");
	private ImageIcon iconUpdate = new ImageIcon("images/edit.png");
	private ImageIcon iconDelete = new ImageIcon("images/remove.png");
	private ImageIcon iconSearch = new ImageIcon("images/search.png");
	
	private JTable table = null;
	private DefaultTableModel model = null;
	private String[] colName = {"�й�", "�̸�", "�а�", "�ּ�"};
	
	private ArrayList<JLabel> labelText = new ArrayList<JLabel>();
	
	boolean printDebugConsole = false;
	
	public Student(int x, int y) {
		this.setLayout(null);
		this.setSize(x, y);
		this.initLayoutSetting();
		this.listenerManageStudentPanel();	
		this.setVisible(true);
	}
	
	// Label ���̾ƿ� ����
	private void setLayoutLabel() {
		if (printDebugConsole == true) {
			System.out.printf("setLayoutLabel\n");
		}
		int default_X = 20;
		int default_Y = 15;
		int default_Width = 50;
		int default_Height = 25;
		
		this.labelText.add(new JLabel("�й�"));
		// setBounds(x, y, width, height);
		this.labelText.get(0).setBounds(default_X, default_Y, default_Width, default_Height);
		this.add(labelText.get(0));
		
		this.tfId = new JTextField(15);
		this.tfId.setBounds((default_X + 35), default_Y, 180, default_Height);
		this.add(this.tfId);
		
		this.labelText.add(new JLabel("�̸�"));
		this.labelText.get(1).setBounds(default_X, (default_Y + 30), default_Width, default_Height);
		this.add(labelText.get(1));
		
		this.tfName = new JTextField(20);
		this.tfName.setBounds((default_X + 35), (default_Y + 30), 180, default_Height);
		this.add(this.tfName);
		
		this.labelText.add(new JLabel("�а�"));
		this.labelText.get(2).setBounds(default_X, (default_Y + 60), default_Width, default_Height);
		this.add(labelText.get(2));
		
		this.tfDepartment = new JTextField(20);
		this.tfDepartment.setBounds((default_X + 35), (default_Y + 60), 180, default_Height);
		this.add(this.tfDepartment);
		
		this.labelText.add(new JLabel("�ּ�"));
		this.labelText.get(3).setBounds(default_X, (default_Y + 90), default_Width, default_Height);
		this.add(labelText.get(3));
		
		this.tfAddress = new JTextField(20);
		this.tfAddress.setBounds((default_X + 35), (default_Y + 90), 180, default_Height);
		this.add(this.tfAddress);
	}
	
	// Table ���̾ƿ� ����
	private void setLayoutTable() {
		if (printDebugConsole == true) {
			System.out.printf("setLayoutTable\n");
		}
		
		this.model = new DefaultTableModel(colName, 0);
		this.table = new JTable(this.model);
		this.table.setPreferredScrollableViewportSize(new Dimension(330, 300));
		this.listScroll = new JScrollPane(this.table);
		this.listScroll.setBounds(20, 140, (this.getWidth() - 50), (this.getHeight() - 315));
		this.add(this.listScroll);
	}
	
	// Button ���̾ƿ� ����
	private void setLayoutButton() {
		if (printDebugConsole == true) {
			System.out.printf("setLayoutButton\n");
		}
		int default_X = 20;
		int default_Y = this.getHeight() - 165;
		int default_Width = 55;
		int default_Height = 90;
		
		this.btnInsert = new JButton("���", iconInsert);
		this.btnInsert.setBounds(default_X, default_Y, default_Width, default_Height);
		this.btnInsert.setHorizontalTextPosition(JButton.CENTER);
		this.btnInsert.setVerticalTextPosition(JButton.BOTTOM);
		//this.btnInsert.setBorderPainted(false); // ��ư �׵θ� ����
		//this.btnInsert.setContentAreaFilled(false); // ��ư ��� ����
		this.btnInsert.setToolTipText("���");
		this.add(this.btnInsert);
		
		this.btnSelect = new JButton("���", iconSelect);
		this.btnSelect.setBounds((default_X + 70), default_Y, 55, default_Height);
		this.btnSelect.setHorizontalTextPosition(JButton.CENTER);
		this.btnSelect.setVerticalTextPosition(JButton.BOTTOM);
		//this.btnSelect.setContentAreaFilled(false); // ��ư ��� ����
		this.btnSelect.setToolTipText("���");
		this.add(this.btnSelect);
		
		this.btnUpdate = new JButton("����", iconUpdate);
		this.btnUpdate.setBounds((default_X + 140), default_Y, default_Width, default_Height);
		this.btnUpdate.setHorizontalTextPosition(JButton.CENTER);
		this.btnUpdate.setVerticalTextPosition(JButton.BOTTOM);
		//this.btnUpdate.setContentAreaFilled(false); // ��ư ��� ����
		this.btnUpdate.setToolTipText("����");
		this.add(this.btnUpdate);
		
		this.btnDelete = new JButton("����", iconDelete);
		this.btnDelete.setBounds((default_X + 210), default_Y, default_Width, default_Height);
		this.btnDelete.setHorizontalTextPosition(JButton.CENTER);
		this.btnDelete.setVerticalTextPosition(JButton.BOTTOM);
		//this.btnDelete.setContentAreaFilled(false); // ��ư ��� ����
		this.btnDelete.setToolTipText("����");
		this.add(this.btnDelete);
		
		this.btnSearch = new JButton("�˻�", iconSearch);
		this.btnSearch.setHorizontalAlignment(JButton.LEFT);
		this.btnSearch.setBounds(245, 10, 88, 30);
		//this.btnSearch.setContentAreaFilled(false); // ��ư ��� ����
		this.btnSearch.setToolTipText("�˻�");
		this.add(this.btnSearch);
	}
	
	// �⺻ ���̾ƿ� ����
	private void initLayoutSetting() {
		
		if (printDebugConsole == true) {
			System.out.printf("getWidth %d\n", this.getWidth());
		}
		
		this.setLayoutLabel();
		this.setLayoutTable();
		this.setLayoutButton();
	}
	
	// �ؽ�Ʈ �ʵ� �ʱ�ȭ
	private void clearTextField() {
		this.tfId.setText("");
		this.tfName.setText("");
		this.tfDepartment.setText("");
		this.tfAddress.setText("");
	}
	
	private void listenerManageStudentPanel() {
		
		// �˻� ��ư ������ ó��
		this.btnSearch.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				//readWriteDelete_DB(5);
				DBManage.readWriteDelete_DB(tfId, tfName, tfDepartment, tfAddress, model, 5); // �˻�
			}
			
		});
		
		// ��� ��ư ������ ó��
		this.btnInsert.addActionListener(new ActionListener () {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tfId.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "�й��� �Էµ� ���� �����ϴ�.");
				}
				else if (tfName.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "�̸��� �Էµ� ���� �����ϴ�.");
				}
				else if (tfDepartment.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "�а��� �Էµ� ���� �����ϴ�.");
				}
				else if (tfAddress.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "�ּҿ� �Էµ� ���� �����ϴ�.");
				}
				else {
					int resultConfirm;
					resultConfirm = JOptionPane.showConfirmDialog(null, "����� �����մϱ�?", "��� Ȯ��", JOptionPane.YES_NO_OPTION);
					if (resultConfirm == 0) {
						boolean isErrorOccur;
						isErrorOccur = DBManage.readWriteDelete_DB(tfId, tfName, tfDepartment, tfAddress, model, 1); // 1�� �Է��Ͽ� ���
						// �Է� ����� ������ ������ ��� ����
						if (isErrorOccur == false) {
							JOptionPane.showMessageDialog(null, "��� �Ǿ����ϴ�.");
							// ����� ��ģ �� �ؽ�Ʈ �ʵ忡 �ִ� �� �ʱ�ȭ
							clearTextField();
						}
						else {
							JOptionPane.showMessageDialog(null, "����� �� �����ϴ�.\n�Է� ������ �ٽ� Ȯ�����ּ���.", "��Ͻ���", JOptionPane.ERROR_MESSAGE);
						}

					}
				}
			}
			
		});
		
		// ���� ��ư ������ ó��
		this.btnUpdate.addActionListener(new ActionListener () {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (tfId.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "�й��� �Էµ� ���� �����ϴ�.");
					}
					else if (tfName.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "�̸��� �Էµ� ���� �����ϴ�.");
					}
					else if (tfDepartment.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "�а��� �Էµ� ���� �����ϴ�.");
					}
					else if (tfAddress.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "�ּҿ� �Էµ� ���� �����ϴ�.");
					}
					else {
						int resultConfirm;
						resultConfirm = JOptionPane.showConfirmDialog(null, "���� �Ͻðڽ��ϱ�?", "���� Ȯ��", JOptionPane.YES_NO_OPTION);
						if (resultConfirm == 0) {
							DBManage.readWriteDelete_DB(tfId, tfName, tfDepartment, tfAddress, model, 2);  // 2�� �Է��Ͽ� ����
							JOptionPane.showMessageDialog(null, "���� �Ǿ����ϴ�.");
							// ����� ��ģ �� �ؽ�Ʈ �ʵ忡 �ִ� �� �ʱ�ȭ
							clearTextField();
						}
					}
				}
			}
		
		);
		
		// ��Ϲ�ư ������ ó��
		this.btnSelect.addActionListener(new ActionListener () {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DBManage.readWriteDelete_DB(tfId, tfName, tfDepartment, tfAddress, model, 3); // 3�� �Է��Ͽ� ��� ����
				// �ؽ�Ʈ �ʵ忡 �ִ� �� �ʱ�ȭ
				clearTextField();
			}
			
		});
		
		// ���� ��ư ������ ó��
		this.btnDelete.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "���� �Ͻðڽ��ϱ�?", "���� Ȯ��", JOptionPane.YES_NO_OPTION);
				
				if (result == JOptionPane.YES_OPTION) {
					boolean isErrorOccur;
					
					isErrorOccur = DBManage.readWriteDelete_DB(tfId, tfName, tfDepartment, tfAddress, model, 4); // 4�� �Է��Ͽ� ����
					
					if (isErrorOccur == false) { // ����� ������ ������ ���� �Ϸ�.
						JOptionPane.showMessageDialog(null, "�����߽��ϴ�.");
					}
					else {
						JOptionPane.showMessageDialog(null, "������ �� �����ϴ�.\n�Է� ������ �ٽ� Ȯ�����ּ���.", "��������", JOptionPane.ERROR_MESSAGE);
					}
					
					if (printDebugConsole == true) {
						System.out.printf("����ó��~\n", result);
					}
					result = 1;
					// �ؽ�Ʈ �ʵ忡 �ִ� �� �ʱ�ȭ
					clearTextField();
				}
			}
			
		});
		
		this.table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// �̺�Ʈ�� �߻��� ������Ʈ(table)�� ���Ѵ�.
				table = (JTable)e.getComponent();
				// table�� ���� ���Ѵ�.
				model = (DefaultTableModel)table.getModel();
				// ���� ���õ� ���� �÷� ���� ���Ѵ�.
				tfId.setText((String)model.getValueAt(table.getSelectedRow(), 0));
				tfName.setText((String)model.getValueAt(table.getSelectedRow(), 1));
				tfDepartment.setText((String)model.getValueAt(table.getSelectedRow(), 2));
				tfAddress.setText((String)model.getValueAt(table.getSelectedRow(), 3));
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
	}
}