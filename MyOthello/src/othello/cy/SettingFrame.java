package othello.cy;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class SettingFrame extends JFrame implements ActionListener {

	private JComboBox levelSelector, firstMoveSelector;
	private JButton start, exit;

	public SettingFrame() {
		super("���ä�Q���");
		init();
		setBounds(490, 300, 220, 150);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init() {
		setLayout(new FlowLayout());
		add(new JLabel("������"));
		levelSelector = new JComboBox();
		levelSelector.addItem("3. �߼�             ");
		levelSelector.addItem("2. �м�             ");
		levelSelector.addItem("1. ����             ");
		add(levelSelector);
		add(new JLabel("���֤��x�k��"));
		firstMoveSelector = new JComboBox();
		firstMoveSelector.addItem("���֣��ڣ�");
		firstMoveSelector.addItem("���֣��ף�");
		add(firstMoveSelector);
		start = new JButton("�����`��");
		start.addActionListener(this);
		add(start);
		exit = new JButton("�K���");
		exit.addActionListener(this);
		add(exit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == start) {
			if (levelSelector.getSelectedIndex() == 2) { // ����
				if (firstMoveSelector.getSelectedIndex() == 0) // ����
					new MainFrame(1, true);
				else
					// ����
					new MainFrame(1, false);
			} else if (levelSelector.getSelectedIndex() == 1) { // �м�
				if (firstMoveSelector.getSelectedIndex() == 0) // ����
					new MainFrame(2, true);
				else
					// ����
					new MainFrame(2, false);
			} else { // �߼�
				if (firstMoveSelector.getSelectedIndex() == 0) // ����
					new MainFrame(3, true);
				else
					// ����
					new MainFrame(3, false);
			}
			dispose();
		} else
			System.exit(0);
	}

}
