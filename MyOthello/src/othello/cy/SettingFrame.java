package othello.cy;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class SettingFrame extends JFrame implements ActionListener {

	private JComboBox levelSelector, firstMoveSelector;
	private JButton start, exit;

	public SettingFrame() {
		super("配置を決める");
		init();
		setBounds(490, 300, 220, 150);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init() {
		setLayout(new FlowLayout());
		add(new JLabel("棋力："));
		levelSelector = new JComboBox();
		levelSelector.addItem("3. 高級             ");
		levelSelector.addItem("2. 中級             ");
		levelSelector.addItem("1. 初級             ");
		add(levelSelector);
		add(new JLabel("先手を選択："));
		firstMoveSelector = new JComboBox();
		firstMoveSelector.addItem("先手（黑）");
		firstMoveSelector.addItem("後手（白）");
		add(firstMoveSelector);
		start = new JButton("スタート");
		start.addActionListener(this);
		add(start);
		exit = new JButton("終わる");
		exit.addActionListener(this);
		add(exit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == start) {
			if (levelSelector.getSelectedIndex() == 2) { // 初级
				if (firstMoveSelector.getSelectedIndex() == 0) // 先手
					new MainFrame(1, true);
				else
					// 后手
					new MainFrame(1, false);
			} else if (levelSelector.getSelectedIndex() == 1) { // 中级
				if (firstMoveSelector.getSelectedIndex() == 0) // 先手
					new MainFrame(2, true);
				else
					// 后手
					new MainFrame(2, false);
			} else { // 高级
				if (firstMoveSelector.getSelectedIndex() == 0) // 先手
					new MainFrame(3, true);
				else
					// 后手
					new MainFrame(3, false);
			}
			dispose();
		} else
			System.exit(0);
	}

}
