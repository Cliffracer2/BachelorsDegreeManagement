// original source from https://moozi.tistory.com/
// 2021.04.07 revision by ymy.

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
//import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class BookRent extends JPanel{
	
	private DefaultTableModel model = null;
	
	private JTable tableBookRent = null; 
	
	private JLabel l_dept = null;
	
	@SuppressWarnings("rawtypes")
	private JComboBox cb_dept = null;
	
	private JScrollPane sp_BookRent = null;
	
	private JButton btnBorrow = null;
	private JButton btnReturn = null;
	
	private ImageIcon iconBorrow = new ImageIcon("images/borrow_Book.png");
	private ImageIcon iconReturn = new ImageIcon("images/return_Book.png");
	
	private String query;
	private String[] dept = null;

	boolean printDebugConsole = false;
	
	public BookRent(int x, int y) {
	
		this.query = "select student.id, student.name, books.title, bookRent.rDate, bookRent.no" + 
				" from  bookRent, student,books" + 
				" where bookRent.id=student.id" + 
				" and bookRent.bookNo=books.no" + 
				" order by bookRent.no";								
	    
	    this.setLayout(null); // 레이아웃설정. 레이아웃 사용 안함.
	    
	    this.setSize(x, y); // 화면크기
	    this.createBookRentPanel();
	    this.listenerManageBookRenttPanel();
	    this.setVisible(true);
	    
	    // 전체목록
	    this.list();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createBookRentPanel() {
		
		int default_X = 20;
		int default_Y = 15;
		int default_Width = 30;
		int default_Height = 25;
		
		this.l_dept = new JLabel("학과");
	    this.l_dept.setBounds(default_X, default_Y, default_Width, default_Height);
	    this.add(this.l_dept);

	    DBManage.getDepartmentList(); // DB에 있는 학과리스트를 불러온다.
	    this.dept = new String[DBManage.deptName.size() + 1];
	    this.dept[0] = "전체";
		if (printDebugConsole == true) {
    		System.out.println(this.dept[0]);
    	}
	    for (int idx = 0; idx < DBManage.deptName.size(); idx++) {
	    	this.dept[idx + 1] = DBManage.deptName.get(idx);
			if (printDebugConsole == true) {
	    		System.out.println(this.dept[idx + 1]);
	    	}
	    }
	    this.cb_dept = new JComboBox(this.dept); // 학과 콤보박스
	    this.cb_dept.setBounds((default_X + 35), default_Y, (default_Width * 5), default_Height);
	    this.add(this.cb_dept);
    
	    // 대출 목록 테이블 생성
	    String colName[] = {"학번", "이름", "도서명", "대출일", "대출번호"};
	    this.model = new DefaultTableModel(colName, 0);
	    this.tableBookRent = new JTable(this.model);
	    this.tableBookRent.setPreferredScrollableViewportSize(new Dimension(330, this.getHeight() - 180));
	    this.add(this.tableBookRent);
	    this.sp_BookRent = new JScrollPane(this.tableBookRent);
	    this.sp_BookRent.setBounds(default_X, (default_Y + 30), (this.getWidth() - 50), (this.getHeight() - 220));
	    this.add(this.sp_BookRent);

		default_X = 20;
		default_Y = this.getHeight() - 165;
		default_Width = 55;
		default_Height = 90;
		
		this.btnBorrow = new JButton("대출", this.iconBorrow);
		this.btnBorrow.setBounds(default_X, default_Y, default_Width, default_Height);
		this.btnBorrow.setHorizontalTextPosition(JButton.CENTER);
		this.btnBorrow.setVerticalTextPosition(JButton.BOTTOM);
		this.btnBorrow.setToolTipText("책 대출");
		this.add(this.btnBorrow);
		
		this.btnReturn = new JButton("반납", this.iconReturn);
		this.btnReturn.setBounds((default_X + 70), default_Y, 55, default_Height);
		this.btnReturn.setHorizontalTextPosition(JButton.CENTER);
		this.btnReturn.setVerticalTextPosition(JButton.BOTTOM);
		this.btnReturn.setToolTipText("반납");
		this.add(this.btnReturn);
	}
	
	private void listenerManageBookRenttPanel() {
		
	    this.cb_dept.addActionListener(new ActionListener() {
	    	
			@Override
			public void actionPerformed(ActionEvent e) {
				//기본 쿼리
				query = "select s.id, s.name, b.title, br.rDate, br.no" + 
						" from student s, books b, bookRent br" + 
						" where br.id=s.id and br.bookNo=b.no";
		
				@SuppressWarnings("rawtypes")
				JComboBox cb = (JComboBox)e.getSource();
				int selectedIndex = cb.getSelectedIndex(); // 선택한 아이템의 index
				
				// 동적쿼리. 컴보박스 아이템에 따라서 쿼리가 달라짐
				if(selectedIndex == 0) {
					query += " order by s.id";
				}
				else {
					query += " and s.department='" + DBManage.deptName.get(selectedIndex - 1) + "'" + " order by b.no";
					if (printDebugConsole == true) {
			    		System.out.println(query);
			    	}
				}

				list(); // 목록출력
			}
		});
	    
	    this.btnBorrow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BachelorsDegreeManagement.gatheringDialogs = new GatheringDialogs(null, "대출", 2, true);
				BachelorsDegreeManagement.gatheringDialogs.setLocationRelativeTo(null);
				BachelorsDegreeManagement.gatheringDialogs.setVisible(true);
				if (printDebugConsole == true) {
					System.out.println("button borrow");
				}
			}
	    	
	    });
	    
	    this.btnReturn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BachelorsDegreeManagement.gatheringDialogs = new GatheringDialogs(null, "반납", 3, false);
				BachelorsDegreeManagement.gatheringDialogs.setLocationRelativeTo(null);
				BachelorsDegreeManagement.gatheringDialogs.setVisible(true);
				if (printDebugConsole == true) {
					System.out.println("button return");
				}
			}
	    	
	    });
	    
		this.tableBookRent.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// 이벤트가 발생한 컴포넌트(table)을 구한다.
				tableBookRent = (JTable)e.getComponent();
				// table의 모델을 구한다.
				model = (DefaultTableModel)tableBookRent.getModel();
				// 현재 선택된 행의 컬럼 값을 구한다.
				GatheringDialogs.tfId.setText((String)model.getValueAt(tableBookRent.getSelectedRow(), 0));
				GatheringDialogs.tfNumber.setText((String)model.getValueAt(tableBookRent.getSelectedRow(), 4));
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
	
	public void list(){
	
		try {
			if (printDebugConsole == true) {
	    		System.out.println("연결되었습니다.....");
	    		System.out.println(query);
	    	}
		     
			// Select문 실행     
			DBManage.rs = DBManage.stmt.executeQuery(query);
		    // JTable 초기화
		    model.setNumRows(0);
		    while(DBManage.rs.next()) {
		    	
		    	String[] row = new String[5];//컬럼의 갯수가 4
			    row[0] = DBManage.rs.getString("id");
			    row[1] = DBManage.rs.getString("name");
			    row[2] = DBManage.rs.getString("title");
			    row[3] = DBManage.rs.getString("rdate");
			    row[4] =  DBManage.rs.getString("no");
			    
			    model.addRow(row);
		    }
		    DBManage.rs.close();
	    }
	    catch(Exception e1){
	    	//e.getStackTrace();
	    	System.out.println(e1.getMessage());
	    }							
	}

}