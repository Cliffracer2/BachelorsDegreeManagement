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
	private String[] colName = {"학번", "이름", "학과", "주소"};
	
	private ArrayList<JLabel> labelText = new ArrayList<JLabel>();
	
	boolean printDebugConsole = false;
	
	public Student(int x, int y) {
		this.setLayout(null);
		this.setSize(x, y);
		this.initLayoutSetting();
		this.listenerManageStudentPanel();	
		this.setVisible(true);
	}
	
	// Label 레이아웃 설정
	private void setLayoutLabel() {
		if (printDebugConsole == true) {
			System.out.printf("setLayoutLabel\n");
		}
		int default_X = 20;
		int default_Y = 15;
		int default_Width = 50;
		int default_Height = 25;
		
		this.labelText.add(new JLabel("학번"));
		// setBounds(x, y, width, height);
		this.labelText.get(0).setBounds(default_X, default_Y, default_Width, default_Height);
		this.add(labelText.get(0));
		
		this.tfId = new JTextField(15);
		this.tfId.setBounds((default_X + 35), default_Y, 180, default_Height);
		this.add(this.tfId);
		
		this.labelText.add(new JLabel("이름"));
		this.labelText.get(1).setBounds(default_X, (default_Y + 30), default_Width, default_Height);
		this.add(labelText.get(1));
		
		this.tfName = new JTextField(20);
		this.tfName.setBounds((default_X + 35), (default_Y + 30), 180, default_Height);
		this.add(this.tfName);
		
		this.labelText.add(new JLabel("학과"));
		this.labelText.get(2).setBounds(default_X, (default_Y + 60), default_Width, default_Height);
		this.add(labelText.get(2));
		
		this.tfDepartment = new JTextField(20);
		this.tfDepartment.setBounds((default_X + 35), (default_Y + 60), 180, default_Height);
		this.add(this.tfDepartment);
		
		this.labelText.add(new JLabel("주소"));
		this.labelText.get(3).setBounds(default_X, (default_Y + 90), default_Width, default_Height);
		this.add(labelText.get(3));
		
		this.tfAddress = new JTextField(20);
		this.tfAddress.setBounds((default_X + 35), (default_Y + 90), 180, default_Height);
		this.add(this.tfAddress);
	}
	
	// Table 레이아웃 설정
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
	
	// Button 레이아웃 설정
	private void setLayoutButton() {
		if (printDebugConsole == true) {
			System.out.printf("setLayoutButton\n");
		}
		int default_X = 20;
		int default_Y = this.getHeight() - 165;
		int default_Width = 55;
		int default_Height = 90;
		
		this.btnInsert = new JButton("등록", iconInsert);
		this.btnInsert.setBounds(default_X, default_Y, default_Width, default_Height);
		this.btnInsert.setHorizontalTextPosition(JButton.CENTER);
		this.btnInsert.setVerticalTextPosition(JButton.BOTTOM);
		//this.btnInsert.setBorderPainted(false); // 버튼 테두리 삭제
		//this.btnInsert.setContentAreaFilled(false); // 버튼 배경 삭제
		this.btnInsert.setToolTipText("등록");
		this.add(this.btnInsert);
		
		this.btnSelect = new JButton("목록", iconSelect);
		this.btnSelect.setBounds((default_X + 70), default_Y, 55, default_Height);
		this.btnSelect.setHorizontalTextPosition(JButton.CENTER);
		this.btnSelect.setVerticalTextPosition(JButton.BOTTOM);
		//this.btnSelect.setContentAreaFilled(false); // 버튼 배경 삭제
		this.btnSelect.setToolTipText("목록");
		this.add(this.btnSelect);
		
		this.btnUpdate = new JButton("수정", iconUpdate);
		this.btnUpdate.setBounds((default_X + 140), default_Y, default_Width, default_Height);
		this.btnUpdate.setHorizontalTextPosition(JButton.CENTER);
		this.btnUpdate.setVerticalTextPosition(JButton.BOTTOM);
		//this.btnUpdate.setContentAreaFilled(false); // 버튼 배경 삭제
		this.btnUpdate.setToolTipText("수정");
		this.add(this.btnUpdate);
		
		this.btnDelete = new JButton("삭제", iconDelete);
		this.btnDelete.setBounds((default_X + 210), default_Y, default_Width, default_Height);
		this.btnDelete.setHorizontalTextPosition(JButton.CENTER);
		this.btnDelete.setVerticalTextPosition(JButton.BOTTOM);
		//this.btnDelete.setContentAreaFilled(false); // 버튼 배경 삭제
		this.btnDelete.setToolTipText("삭제");
		this.add(this.btnDelete);
		
		this.btnSearch = new JButton("검색", iconSearch);
		this.btnSearch.setHorizontalAlignment(JButton.LEFT);
		this.btnSearch.setBounds(245, 10, 88, 30);
		//this.btnSearch.setContentAreaFilled(false); // 버튼 배경 삭제
		this.btnSearch.setToolTipText("검색");
		this.add(this.btnSearch);
	}
	
	// 기본 레이아웃 설정
	private void initLayoutSetting() {
		
		if (printDebugConsole == true) {
			System.out.printf("getWidth %d\n", this.getWidth());
		}
		
		this.setLayoutLabel();
		this.setLayoutTable();
		this.setLayoutButton();
	}
	
	// 텍스트 필드 초기화
	private void clearTextField() {
		this.tfId.setText("");
		this.tfName.setText("");
		this.tfDepartment.setText("");
		this.tfAddress.setText("");
	}
	
	private void listenerManageStudentPanel() {
		
		// 검색 버튼 리스너 처리
		this.btnSearch.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				//readWriteDelete_DB(5);
				DBManage.readWriteDelete_DB(tfId, tfName, tfDepartment, tfAddress, model, 5); // 검색
			}
			
		});
		
		// 등록 버튼 리스너 처리
		this.btnInsert.addActionListener(new ActionListener () {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tfId.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "학번에 입력된 값이 없습니다.");
				}
				else if (tfName.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "이름에 입력된 값이 없습니다.");
				}
				else if (tfDepartment.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "학과에 입력된 값이 없습니다.");
				}
				else if (tfAddress.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "주소에 입력된 값이 없습니다.");
				}
				else {
					int resultConfirm;
					resultConfirm = JOptionPane.showConfirmDialog(null, "등록을 진행합니까?", "등록 확인", JOptionPane.YES_NO_OPTION);
					if (resultConfirm == 0) {
						boolean isErrorOccur;
						isErrorOccur = DBManage.readWriteDelete_DB(tfId, tfName, tfDepartment, tfAddress, model, 1); // 1을 입력하여 등록
						// 입력 결과에 에러가 없으면 등록 성공
						if (isErrorOccur == false) {
							JOptionPane.showMessageDialog(null, "등록 되었습니다.");
							// 등록을 마친 후 텍스트 필드에 있던 값 초기화
							clearTextField();
						}
						else {
							JOptionPane.showMessageDialog(null, "등록할 수 없습니다.\n입력 정보를 다시 확인해주세요.", "등록실패", JOptionPane.ERROR_MESSAGE);
						}

					}
				}
			}
			
		});
		
		// 수정 버튼 리스너 처리
		this.btnUpdate.addActionListener(new ActionListener () {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (tfId.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "학번에 입력된 값이 없습니다.");
					}
					else if (tfName.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "이름에 입력된 값이 없습니다.");
					}
					else if (tfDepartment.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "학과에 입력된 값이 없습니다.");
					}
					else if (tfAddress.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "주소에 입력된 값이 없습니다.");
					}
					else {
						int resultConfirm;
						resultConfirm = JOptionPane.showConfirmDialog(null, "수정 하시겠습니까?", "수정 확인", JOptionPane.YES_NO_OPTION);
						if (resultConfirm == 0) {
							DBManage.readWriteDelete_DB(tfId, tfName, tfDepartment, tfAddress, model, 2);  // 2를 입력하여 수정
							JOptionPane.showMessageDialog(null, "수정 되었습니다.");
							// 등록을 마친 후 텍스트 필드에 있던 값 초기화
							clearTextField();
						}
					}
				}
			}
		
		);
		
		// 목록버튼 리스너 처리
		this.btnSelect.addActionListener(new ActionListener () {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DBManage.readWriteDelete_DB(tfId, tfName, tfDepartment, tfAddress, model, 3); // 3를 입력하여 목록 보기
				// 텍스트 필드에 있던 값 초기화
				clearTextField();
			}
			
		});
		
		// 삭제 버튼 리스너 처리
		this.btnDelete.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "삭제 하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
				
				if (result == JOptionPane.YES_OPTION) {
					boolean isErrorOccur;
					
					isErrorOccur = DBManage.readWriteDelete_DB(tfId, tfName, tfDepartment, tfAddress, model, 4); // 4를 입력하여 삭제
					
					if (isErrorOccur == false) { // 결과에 오류가 없으면 삭제 완료.
						JOptionPane.showMessageDialog(null, "삭제했습니다.");
					}
					else {
						JOptionPane.showMessageDialog(null, "삭제할 수 없습니다.\n입력 정보를 다시 확인해주세요.", "삭제실패", JOptionPane.ERROR_MESSAGE);
					}
					
					if (printDebugConsole == true) {
						System.out.printf("삭제처리~\n", result);
					}
					result = 1;
					// 텍스트 필드에 있던 값 초기화
					clearTextField();
				}
			}
			
		});
		
		this.table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// 이벤트가 발생한 컴포넌트(table)을 구한다.
				table = (JTable)e.getComponent();
				// table의 모델을 구한다.
				model = (DefaultTableModel)table.getModel();
				// 현재 선택된 행의 컬럼 값을 구한다.
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