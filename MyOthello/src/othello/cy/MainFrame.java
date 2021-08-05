package othello.cy;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class MainFrame extends JFrame implements MouseListener, WindowListener {

	private final int chessboard_size = 8;
	private final int len = 75;
	private int level; // 对手棋力（1：初级，2：中级，3：高级）
	private boolean firstMove; // 是否先行
	private int[][] chessboard = new int[chessboard_size][chessboard_size];
	private int id; // 当前该谁下（1：黑棋，2：白棋）
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
		g.setFont(new Font("宋体", Font.BOLD, 12));
		if (!isEnd()) {
			if (id == 1)
				g.drawString("黒の番", 680, 100);
			else
				g.drawString("白の番", 680, 100);
		}
		g.drawString("黑棋：" + count_black, 680, 150);
		g.drawString("白棋：" + count_white, 680, 170);
	}

	/**
	 * 仅通过一个方向判断能否在该位置落子
	 * 
	 * @param point
	 *            该点在棋盘中的位置
	 * @param dx
	 *            x方向偏移量
	 * @param dy
	 *            y方向偏移量
	 * @param flag
	 *            如果要判断的位置临近(x,y)取flag=0，否则取flag=1
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
		// 如果该位置是己方的棋子，则在该位置与要下的位置之间如果有一个或多个对手的棋子则返回true，否则返回false
		if (chessboard[point.getX()][point.getY()] == self) {
			if (flag == 1)
				return true;
			return false;
		}
		// 如果该位置是对手的棋子，则递归调用该方法求出结果
		if (chessboard[point.getX()][point.getY()] == em)
			return legalOnPosition(point, dx, dy, 1);
		return false;
	}

	/**
	 * 判断该位置是否不能落子
	 * 
	 * @param point
	 *            该点在棋盘中的位置
	 * @return true:不能落子，false:可以落子
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
	 * 判断是否无处落子
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
	 * 落子并翻转
	 * 
	 * @param point
	 *            落子位置
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
					JOptionPane.showMessageDialog(this, "白は打つところがないので、黒続けてください", "メッセージ",
							JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(this, "黒は打つところがないので、白続けてください", "メッセージ",
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
				JOptionPane.showMessageDialog(this, "最終得点：黑：" + count_black
						+ "，白：" + count_white + "，黑の勝ちです！", "結果",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (count_black < count_white) {
				count_white += chessboard_size * chessboard_size - count;
				repaint();
				JOptionPane.showMessageDialog(this, "最終得点：黑：" + count_black
						+ "，白：" + count_white + "，白の勝ちです！", "結果",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				repaint();
				JOptionPane.showMessageDialog(this, "最終得点：黑：" + count_black
						+ "，白：" + count_white + "，引き分け！", "結果",
						JOptionPane.INFORMATION_MESSAGE);
			}
			int r = JOptionPane.showConfirmDialog(this, "リスタートしますか？", "リスタート",
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
					Thread.sleep(200); // 延迟0.2s
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
			JOptionPane.showMessageDialog(this, "ここは打てられません！", "錯誤",
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
			message = "今終われば、対局はすぐに終わって、最終点数は「黑棋：0，白棋：64」と記録します！！対局を終わっていいですか";
		else
			message = "今終われば、対局はすぐに終わって、最終点数は「白棋：0，黑棋：64」と記録します！！対局を終わっていいですか";
		int option = JOptionPane.showConfirmDialog(this, message, "終わる",
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
