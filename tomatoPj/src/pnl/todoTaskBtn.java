package pnl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import frame.MainFrame;
import tomatoPj.Task;


public class todoTaskBtn extends JButton {
	private int task_no;
	
	public todoTaskBtn(int task_no, MainFrame mainFrame) {
		this.task_no = task_no;
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainFrame.setTask(null, null);
				mainFrame.showCard("task");
			}
		});
	}

	public int getTask_no() {
		return task_no;
	}

	public void setTask_no(int task_no) {
		this.task_no = task_no;
	}

}