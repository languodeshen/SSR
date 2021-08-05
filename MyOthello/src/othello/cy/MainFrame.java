package othello.cy;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class MainFrame extends JFrame implements MouseListener, WindowListener {

	private final int chessboard_size = 8;
	private final int len = 75;
	private int level; // ����������1��������2���м���3���߼���
	private boolean firstMove; // �Ƿ�����
	private int[][] chessboard = new int[chessboard_size][chessboard_size];
	private int id; // ��ǰ��˭�£�1�����壬2�����壩
	private int count;
	private int count_black;
	private int count_white;
	private int skipcount;
	private Image images[] = new Image[3];
	private MyPoint pos = null;
	private boolean temp = true;

	public MainFrame(int level, boolean firstMove) throws HeadlessException {
		super("Othello V1.0");
		this.level = level;
		this.firstMove = firstMove;
		init();
		setBounds(200, 10, 800, 680);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addMouseListener(this);
		addWindowListener(this);
		if (!firstMove)
			computerOperate();
	}

	public void init() {
		setLayout(null);
		for (int i = 0; i < 3; i++)
			images[i] = Toolkit.getDefaultToolkit().getImage(
					"img/Level " + level + "/" + i + ".gif");
		for (int i = 0; i < chessboard_size; i++) {
			for (int j = 0; j < chessboard_size; j++)
				chessboard[i][j] = 0;
		}
		chessboard[chessboard_size / 2 - 1][chessboard_size / 2] = 1;
		chessboard[chessboard_size / 2][chessboard_size / 2 - 1] = 1;
		chessboard[chessboard_size / 2 - 1][chessboard_size / 2 - 1] = 2;
		chessboard[chessboard_size / 2][chessboard_size / 2] = 2;
		id = 1;
		count = 4;
		count_black = count_white = 2;
		skipcount = 0;
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 680);
		for (int i = 0; i < chessboard_size; i++) {
			for (int j = 0; j < chessboard_size; j++)
				g.drawImage(images[chessboard[i][j]], 50 + i * len, 50 + j
						* len, len, len, this);
		}
		g.setColor(Color.white);
		g.setFont(new Font("����", Font.BOLD, 12));
		if (!isEnd()) {
			if (id == 1)
				g.drawString("�\�η�", 680, 100);
			else
				g.drawString("�פη�", 680, 100);
		}
		g.drawString("���壺" + count_black, 680, 150);
		g.drawString("���壺" + count_white, 680, 170);
	}

	/**
	 * ��ͨ��һ�������ж��ܷ��ڸ�λ������
	 * 
	 * @param point
	 *            �õ��������е�λ��
	 * @param dx
	 *            x����ƫ����
	 * @param dy
	 *            y����ƫ����
	 * @param flag
	 *            ���Ҫ�жϵ�λ���ٽ�(x,y)ȡflag=0������ȡflag=1
	 * @return
	 */
	public boolean legalOnPosition(MyPoint point, int dx, int dy, int flag) {
		int self = id;
		int em = id % 2 + 1;
		point.setX(point.getX() + dx);
		point.setY(point.getY() + dy);
		if (point.getX() < 0 || point.getX() >= chessboard_size
				|| point.getY() < 0 || point.getY() >= chessboard_size)
			return false;
		// �����λ���Ǽ��������ӣ����ڸ�λ����Ҫ�µ�λ��֮�������һ���������ֵ������򷵻�true�����򷵻�false
		if (chessboard[point.getX()][point.getY()] == self) {
			if (flag == 1)
				return true;
			return false;
		}
		// �����λ���Ƕ��ֵ����ӣ���ݹ���ø÷���������
		if (chessboard[point.getX()][point.getY()] == em)
			return legalOnPosition(point, dx, dy, 1);
		return false;
	}

	/**
	 * �жϸ�λ���Ƿ�������
	 * 
	 * @param point
	 *            �õ��������е�λ��
	 * @return true:�������ӣ�false:��������
	 */
	public boolean illegal(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (chessboard[x][y] != 0)
			return true;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0)
					continue;
				if (legalOnPosition(new MyPoint(x, y), i, j, 0))
					return false;
			}
		}
		return true;
	}

	/**
	 * �ж��Ƿ��޴�����
	 * 
	 * @return
	 */
	public boolean skip() {
		for (int i = 0; i < chessboard_size; i++) {
			for (int j = 0; j < chessboard_size; j++) {
				if (chessboard[i][j] != 0)
					continue;
				if (!illegal(new MyPoint(i, j)))
					return false;
			}
		}
		return true;
	}

	public boolean isEnd() {
		if (count >= chessboard_size * chessboard_size || skipcount >= 2)
			return true;
		return false;
	}

	/**
	 * ���Ӳ���ת
	 * 
	 * @param point
	 *            ����λ��
	 */
	public void revergal(MyPoint point) {
		int self = id;
		int em = id % 2 + 1;
		int x = point.getX();
		int y = point.getY();
		int m, n;
		chessboard[x][y] = self;
		count++;
		if (self == 1)
			count_black++;
		else
			count_white++;
		skipcount = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0)
					continue;
				if (legalOnPosition(new MyPoint(x, y), i, j, 0)) {
					m = x + i;
					n = y + j;
					while (chessboard[m][n] == em) {
						chessboard[m][n] = self;
						if (self == 1) {
							count_black++;
							count_white--;
						} else {
							count_white++;
							count_black--;
						}
						m += i;
						n += j;
					}
				}
			}
		}
		repaint();
		id = id % 2 + 1;
		if (!isEnd() && skip()) {
			skipcount++;
			id = id % 2 + 1;
			if (skipcount == 1 && !skip()) {
				if (id == 1)
					JOptionPane.showMessageDialog(this, "�פϴ�ĤȤ����ʤ��Τǡ��\�A���Ƥ�������", "��å��`��",
							JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(this, "�\�ϴ�ĤȤ����ʤ��Τǡ��׾A���Ƥ�������", "��å��`��",
							JOptionPane.INFORMATION_MESSAGE);
				if (firstMove && id == 1 || !firstMove && id == 2)
					return;
			} else if (skipcount == 1 && skip())
				skipcount++;
		}
		if (!isEnd()) {
			if (firstMove && id == 2 || !firstMove && id == 1)
				computerOperate();
		}
		if (isEnd() && temp) {
			if (count_black > count_white) {
				count_black += chessboard_size * chessboard_size - count;
				repaint();
				JOptionPane.showMessageDialog(this, "��K�õ㣺�ڣ�" + count_black
						+ "���ף�" + count_white + "���ڤ΄٤��Ǥ���", "�Y��",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (count_black < count_white) {
				count_white += chessboard_size * chessboard_size - count;
				repaint();
				JOptionPane.showMessageDialog(this, "��K�õ㣺�ڣ�" + count_black
						+ "���ף�" + count_white + "���פ΄٤��Ǥ���", "�Y��",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				repaint();
				JOptionPane.showMessageDialog(this, "��K�õ㣺�ڣ�" + count_black
						+ "���ף�" + count_white + "�������֤���", "�Y��",
						JOptionPane.INFORMATION_MESSAGE);
			}
			int r = JOptionPane.showConfirmDialog(this, "�ꥹ���`�Ȥ��ޤ�����", "�ꥹ���`��",
					JOptionPane.YES_NO_OPTION);
			if (r == JOptionPane.YES_OPTION) {
				temp = false;
				dispose();
				new SettingFrame();
			} else
				System.exit(0);
		}
	}

	public void computerOperate() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(200); // �ӳ�0.2s
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MyPoint point = new MyPoint(0, 0);
				LinkedList<MyPoint> legalPoints = new LinkedList<MyPoint>();
				for (int i = 0; i < chessboard_size; i++) {
					for (int j = 0; j < chessboard_size; j++) {
						if (chessboard[i][j] != 0)
							continue;
						if (!illegal(new MyPoint(i, j)))
							legalPoints.add(new MyPoint(i, j));
					}
				}
				if (level == 1) {
					BeginnerAlgorithm alg1 = new BeginnerAlgorithm(id,
							chessboard, pos, legalPoints, count);
					point = alg1.getPositionToTurn();
				} else if (level == 2) {
					IntermediateAlgorithm alg2 = new IntermediateAlgorithm(id,
							chessboard, pos, legalPoints, count);
					point = alg2.getPositionToTurn();
				} else {
					ExpertAlgorithm alg3 = new ExpertAlgorithm(id, chessboard,
							pos, legalPoints, count);
					point = alg3.getPositionToTurn();
				}
				revergal(point);
			}
		}).start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = (e.getX() - 50) / len;
		int y = (e.getY() - 50) / len;
		if (isEnd() || x < 0 || x >= chessboard_size || y < 0
				|| y >= chessboard_size)
			return;
		if (illegal(new MyPoint(x, y)))
			JOptionPane.showMessageDialog(this, "�����ϴ�Ƥ��ޤ���", "�e�`",
					JOptionPane.ERROR_MESSAGE);
		else {
			pos = new MyPoint(x, y);
			revergal(pos);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		String message = "";
		if (firstMove)
			message = "��K���С����֤Ϥ����˽K��äơ���K�����ϡ����壺0�����壺64����ӛ�h���ޤ��������֤�K��äƤ����Ǥ���";
		else
			message = "��K���С����֤Ϥ����˽K��äơ���K�����ϡ����壺0�����壺64����ӛ�h���ޤ��������֤�K��äƤ����Ǥ���";
		int option = JOptionPane.showConfirmDialog(this, message, "�K���",
				JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
