package pnl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import frame.MainFrame;
import tomatoPj.Member;
import tomatoPj.Member_Tag_Package_Repository;
import tomatoPj.Task;
import utility.CalendarData;
import utility.FontData;
import utility.IconData;
import utility.Utility;

public class TestTodoPnl extends JPanel{
	private Image image;
	
	private final static IconData IC = new IconData();
	private final static FontData FT = new FontData();
	private final static Utility UT = new Utility();
	private final static CalendarData CD = new CalendarData();
	private boolean toggleSwitch = true;
	// 로그인한 멤버의 모든 프로젝트의 task리스트
	private List<Task> pjAllList;
	private Member_Tag_Package_Repository mtpRepo;
	// 선택한 프로젝트에 포함된 Member 리스트
	private HashSet<Member> memberList;
	
	private void projectEachFunction(int project_no, HashSet<Member> memberList) {
		
	}
	private void projectAllFunction(List<Task> pjAllList) {
		this.pjAllList = pjAllList;
	}
	
	
	public TestTodoPnl(MainFrame mainFrame) {
		mtpRepo = new Member_Tag_Package_Repository();
		
		// 상단 배경 패널 ------------------------------------
				JPanel topBgPnl = new JPanel() {
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						g.drawImage(IC.getImageIcon("topLine").getImage(), 0, 0, null);
						setOpaque(false); // 이미지 불투명도 설정 : false = 불투명(이미지 표시) / true = 투명
					}
				};
				
				// 메인 영역 배경 패널 ------------------------------------
				JPanel calBgPnl = new JPanel() {
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						g.drawImage(IC.getImageIcon("calendarWeek").getImage(), 0, 0, null);
						setOpaque(false); 
					}
				};
				
				// 배경 패널 -----------------------------------------
				JPanel bgPnl = new JPanel() {
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						g.drawImage(IC.getImageIcon("Background").getImage(), 0, 0, null);
						setOpaque(false); 
					}
				};
				
				// 상단 패널 -----------------------------------------
				JPanel topPnl = new JPanel();
				
				// 메뉴 이동 버튼
				JButton logoBtn = UT.getBtnRoll(100, 45, "topLogo");
				topPnl.add(logoBtn);
				JButton kanbanMenuBtn = UT.getBtnRoll(753, 60, "navi_board2");
				topPnl.add(kanbanMenuBtn);
				JButton todoMenuBtn = UT.getBtnRoll(915, 60, "navi_todo2");
				topPnl.add(todoMenuBtn);
				JButton projectMenuBtn = UT.getBtnRoll(1064, 60, "navi_planner2");
				topPnl.add(projectMenuBtn);
				JButton logoutBtn = UT.getBtnRoll(1649, 40, "logout_btn");
				topPnl.add(logoutBtn);
				topPnl.setBounds(0, 0, 1920, 135);
				topPnl.setLayout(null);
				topPnl.setOpaque(false);
				
				topBgPnl.setBounds(0, 0, 1920, 135);
				topBgPnl.setLayout(null);
				topBgPnl.setOpaque(false);
				
				
				// 달력 패널 -----------------------------------------
				JPanel calPnl = new JPanel();
				calPnl.setBounds(164, 160, 1718, 870);
				calPnl.setLayout(null);
				calPnl.setOpaque(false);
				
				JLabel printCurrentMonth = new JLabel();
				String currentMonth = CD.getCurrentSelDate("monthofvalue") + " 월";
				printCurrentMonth.setText(currentMonth);
				printCurrentMonth.setFont(FT.nanumFontBold(25));
				printCurrentMonth.setForeground(Color.DARK_GRAY);
				
				printCurrentMonth.setBounds(120, 30, 50, 34);
				calPnl.add(printCurrentMonth);
				
				// 월선택 좌우버튼
				JButton selMonthbeforeBtn = UT.getBtn(50, 28, "before_btn");
				calPnl.add(selMonthbeforeBtn);
				JButton selMonthnextBtn = UT.getBtn(200, 28, "next_btn");
				calPnl.add(selMonthnextBtn);
				
				
				
				// 투두 리스트 패널 ------------------------------------
				JPanel todoListPnl = new JPanel();
				todoListPnl.setBounds(164, 160, 1718, 870);
				todoListPnl.setLayout(null);
				todoListPnl.setOpaque(false);
				
				// 뷰 설정 토글 버튼
				JButton toggleBtn = UT.getBtn(1210, 15, "prijectAll_toggle");
				todoListPnl.add(toggleBtn);
				ActionListener listener = new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                if(toggleSwitch){
			                    toggleBtn.setIcon(IC.getImageIcon("prijectEach_toggle"));
			                    toggleSwitch = false;
			                    /////////// 프로젝트를 특정해야함 ////////////
			                    ///////////  임시 프로젝트 넘버  ////////////
			                   // 프로젝트가 특정이된다면 바로가능
			                    try {
			                    	memberList = mtpRepo.returnMemberByPj_no(1);
									projectEachFunction(1, memberList);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
			                } else {
			                	toggleBtn.setIcon(IC.getImageIcon("prijectAll_toggle"));
			                	toggleSwitch = true;
			                	projectAllFunction(mainFrame.loginMember.getTakeTaskList());
			                }
			            }
			        };
			        toggleBtn.addActionListener(listener);
				
				// 선택 버튼
				JButton selBtn = UT.getBtn(1650, 30, "projectOpne");
				todoListPnl.add(selBtn);
				
				// 현재 날짜 출력 라벨
				JLabel currentDate = new JLabel();
				String todoDate = CD.getCurrentDate();
				currentDate.setText(todoDate);
				currentDate.setFont(FT.nanumFontBold(18));
				currentDate.setForeground(Color.DARK_GRAY);
				currentDate.setBounds(950, 140, 240, 30);
				
				todoListPnl.add(currentDate);
				
				
				calBgPnl.setBounds(164, 160, 1718, 870);
				calBgPnl.setLayout(null);
				calBgPnl.setOpaque(false);
				
				
				// 배경 패널에 각 패널 붙이기 ------------------------------
				bgPnl.add(topPnl); // 상단 패널
				bgPnl.add(topBgPnl); // 상단 배경 패널
				bgPnl.add(todoListPnl); // 투두 리스트 패널
				bgPnl.add(calPnl); // 달력 패널
				bgPnl.add(calBgPnl); // 메인 영역 배경 패널
				// -----------------------------------------------
				bgPnl.setBounds(0, 0, 1920, 1080);
				bgPnl.setLayout(null);
				setVisible(true);
				// -----------------------------------------------
				add(bgPnl);
		
		setLayout(null);
		setOpaque(false);
		
	} 
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}

