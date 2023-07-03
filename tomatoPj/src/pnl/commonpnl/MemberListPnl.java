package pnl.commonpnl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import button.PlusBtn;
import dbutil.SelectProjectInfo;
import frame.MainFrame;
import popup.MemberAddPopup;
import tomatoPj.Member;
import tomatoPj.MemberRepository;
import utility.FontData;
import utility.IconData;

public class MemberListPnl extends JPanel {
	private IconData iconData;
	private Image image;
	private FontData fontData;
	private JPanel memberAddPnl;
	private PlusBtn plusBtn;
	private final static int MAX_MEMBER_SIZE = 4;
	private int count;
	private List<MemberPnl> memberPnls = new ArrayList<>();
	private List<Member> members = new ArrayList<>();
	private MemberPnl memberPnl;
	private MainFrame mainFrame;
	private MemberRepository memberRepository;
	private MemberAddPopup memberAddPopup;

	/**
	 * Create the panel.
	 */
	public MemberListPnl(MainFrame mainFrame) {
		memberRepository = new MemberRepository();
		iconData = new IconData();
		fontData = new FontData();

		this.mainFrame = mainFrame;
		this.image = iconData.getImageIcon("long_bar").getImage();
		setOpaque(false);

		setLayout(null);

		addPnl();

		plusBtn = new PlusBtn();
		plusBtn.setBounds(0, 10, 40, 40); // Set initial bounds
		memberAddPnl.add(plusBtn);

		plusBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (count < MAX_MEMBER_SIZE) {
					count++;
					memberAddPopup = new MemberAddPopup();
					memberAddPopup.addWindowListener(new WindowAdapter() {

						@Override
						public void windowClosed(WindowEvent e) {
							System.out.println("창닫힘");
							System.out.println("되긴 하니?");
							mainFrame.tempInfo = mainFrame.pjInfo;
							System.out.println(mainFrame.tempInfo);
							mainFrame.projectPnl.projectPnl.insertPjInfo(mainFrame, mainFrame.tempInfo.getProject_no(), mainFrame.tempInfo.getTitle());
							
							addPnl();
							members = mainFrame.pjInfo.getMemberList();
							System.out.println(members);
							// Clear the memberPnls list
							memberPnls.clear();
							
							if (members != null) {
								for (Member member : members) {
									if (MemberListPnl.this.mainFrame.loginMember.getMember().equals(member)) {
										continue;
									}
									MemberPnl memberPnl = new MemberPnl(member, mainFrame);
									memberPnls.add(memberPnl);
								}
								for (MemberPnl memberPnl : memberPnls) {
									memberPnl.setPreferredSize(new Dimension(90, 90));
									memberAddPnl.add(memberPnl);
								}
								memberAddPnl.remove(plusBtn);
								
								// Make the plusBtn visible if member count is less than MAX_MEMBER_SIZE
								plusBtn.setVisible(members.size() <= MAX_MEMBER_SIZE);
								
								memberAddPnl.revalidate();
								memberAddPnl.repaint();
								memberAddPnl.add(plusBtn);
								count = memberPnls.size();
								
							}
						}

					});
					memberAddPopup.setAlwaysOnTop(true);
					memberAddPopup.setVisible(true);
					memberAddPopup.setLocation(plusBtn.getX() + 100, plusBtn.getY() + 500);
					memberAddPopup.addMemberBtn.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							memberRepository.addProjectMember(mainFrame.pjInfo.getProject_no(),
									memberAddPopup.idInsertTextField.getText());
							memberAddPopup.dispose();
						}
					});

//					memberAddPopup.dispose();
				} else {
					plusBtn.setVisible(false);
				}
				

			}
		});

		mainFrame.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				addPnl();
				members = mainFrame.pjInfo.getMemberList();

				// Clear the memberPnls list
				memberPnls.clear();
				if (members != null) {
					for (Member member : members) {
						if (MemberListPnl.this.mainFrame.loginMember.getMember().equals(member)) {
							continue;
						}
						MemberPnl memberPnl = new MemberPnl(member, mainFrame);
						memberPnls.add(memberPnl);
					}
					for (MemberPnl memberPnl : memberPnls) {
						memberPnl.setPreferredSize(new Dimension(90, 90));
						memberAddPnl.add(memberPnl);
					}
					memberAddPnl.remove(plusBtn);

					// Make the plusBtn visible if member count is less than MAX_MEMBER_SIZE
					plusBtn.setVisible(members.size() <= MAX_MEMBER_SIZE);

					memberAddPnl.revalidate();
					memberAddPnl.repaint();
					memberAddPnl.add(plusBtn);
					count = memberPnls.size();
				}
			}
		});

	}

	private void addPanel() {
		memberPnl = new MemberPnl();
		memberPnl.setPreferredSize(new Dimension(80, 80));

		memberAddPnl.remove(plusBtn);
		memberAddPnl.add(memberPnl);
		memberAddPnl.add(plusBtn);

		memberAddPnl.revalidate();
		memberAddPnl.repaint();

		if (count >= MAX_MEMBER_SIZE) {
			plusBtn.setVisible(false);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	private void addPnl() {
		this.removeAll();
		JPanel memberTitleLbl = new JPanel();
		memberTitleLbl.setBounds(0, 0, 130, 70);
		memberTitleLbl.setOpaque(false);
		add(memberTitleLbl);
		memberTitleLbl.setLayout(new BorderLayout(0, 0));

		JLabel memberLbl = new JLabel("Member", SwingConstants.CENTER);
		memberLbl.setBackground(new Color(0, 0, 0, 0));
		memberLbl.setFont(fontData.nanumFontBold(20));
		memberLbl.setForeground(new Color(112, 112, 112));
		memberTitleLbl.add(memberLbl, BorderLayout.CENTER);

		memberAddPnl = new JPanel();
		memberAddPnl.setBounds(0, 70, 130, 458);
		memberAddPnl.setOpaque(false);
		memberAddPnl.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Use FlowLayout
		add(memberAddPnl);
	}
	 public void insertPjInfo(MainFrame mainFrame, int project_no, String title) {
	    	mainFrame.pjInfo = new SelectProjectInfo(project_no, title, null, null);
	    	mainFrame.showCard("columSelect");
			mainFrame.pjInfo.setProject_no(project_no);
			mainFrame.pjInfo.setTitle(title);
			
	    }
}