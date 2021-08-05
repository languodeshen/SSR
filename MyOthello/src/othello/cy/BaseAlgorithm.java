package othello.cy;

import java.util.*;

/**
 * �ڰ�������㷨
 * 
 * @author cy
 *
 */
public class BaseAlgorithm {

	protected final int chessboard_size = 8;
	protected int id;
	protected int[][] last_status = new int[chessboard_size][chessboard_size]; // ����֮ǰ���̵�״̬
	protected MyPoint last_step; // ��һ����������λ��
	protected LinkedList<MyPoint> legalPoints = new LinkedList<MyPoint>(); // ��ǰ��������������ӵ�λ��
	protected int[][] values = new int[chessboard_size][chessboard_size]; // ����Ȩֵ
	protected LinkedList<MyPoint> maxPoints = new LinkedList<MyPoint>(); // Ȩֵ��������λ��
	protected int count;

	public BaseAlgorithm(int id, int[][] last_status, MyPoint last_step,
			LinkedList<MyPoint> legalPoints, int count) {
		this.id = id;
		this.last_status = last_status;
		this.last_step = last_step;
		this.legalPoints = legalPoints;
		this.count = count;
		init();
	}

	public void init() {
		for (int i = 0; i < chessboard_size; i++) {
			for (int j = 0; j < chessboard_size; j++) {
				MyPoint point = new MyPoint(i, j);
				if (is11(point))
					values[i][j] = 500;
				else if (is12(point) || is22(point))
					values[i][j] = -200;
				else if (is13(point))
					values[i][j] = 90;
				else if (is33(point))
					values[i][j] = 2;
				else
					values[i][j] = 0;
				if (!illegal(last_status, point, id)) {
					if (eatAll(last_status, point, id)) // �Թ����
						values[i][j] = 1000;
					else {
						int[][] next_status = getNextStatus(last_status, point,
								id);
						boolean t = true;
						for (int m = 0; m < chessboard_size; m++) {
							for (int n = 0; n < chessboard_size; n++) {
								if (next_status[m][n] != 0
										|| illegal(next_status, new MyPoint(m,
												n), id % 2 + 1))
									continue;
								if (eatAll(next_status, new MyPoint(m, n),
										id % 2 + 1)) { // �����ֳԹ�
									values[i][j] = -1000;
									t = false;
									break;
								}
							}
						}
						if (t) {
							if (eat22(point, id))
								values[i][j] = -600;
							else
								values[i][j] += countEat(point);
						}
					}
				}
			}
		}
		changeValueBy11();
	}

	/**
	 * ����4��1,1λ�����ǵ�����ı�õ��Ȩֵ
	 */
	public void changeValueBy11() {
		int self = id;
		int em = id % 2 + 1;
		if (last_status[0][0] == self
				|| last_status[0][chessboard_size - 1] == self
				|| last_status[chessboard_size - 1][0] == self
				|| last_status[chessboard_size - 1][chessboard_size - 1] == self) { // �������ռ����
			if (last_status[0][0] == self
					&& last_status[0][chessboard_size - 1] == self
					|| last_status[0][0] == self
					&& last_status[chessboard_size - 1][0] == self
					|| last_status[0][chessboard_size - 1] == self
					&& last_status[chessboard_size - 1][chessboard_size - 1] == self
					|| last_status[chessboard_size - 1][0] == self
					&& last_status[chessboard_size - 1][chessboard_size - 1] == self) { // ��ռ��һ�����ϵ���������
				if (last_status[0][0] == self
						&& last_status[0][chessboard_size - 1] == self) {
					changeValues2(new MyPoint(0, 0), new MyPoint(0,
							chessboard_size - 1), 0);
					changeValues2(new MyPoint(0, 0), new MyPoint(0,
							chessboard_size - 1), 1);
				}
				if (last_status[0][0] == self
						&& last_status[chessboard_size - 1][0] == self) {
					changeValues2(new MyPoint(0, 0), new MyPoint(
							chessboard_size - 1, 0), 0);
					changeValues2(new MyPoint(0, 0), new MyPoint(
							chessboard_size - 1, 0), 1);
				}
				if (last_status[0][chessboard_size - 1] == self
						&& last_status[chessboard_size - 1][chessboard_size - 1] == self) {
					changeValues2(new MyPoint(0, chessboard_size - 1),
							new MyPoint(chessboard_size - 1,
									chessboard_size - 1), 0);
					changeValues2(new MyPoint(0, chessboard_size - 1),
							new MyPoint(chessboard_size - 1,
									chessboard_size - 1), -1);
				}
				if (last_status[chessboard_size - 1][0] == self
						&& last_status[chessboard_size - 1][chessboard_size - 1] == self) {
					changeValues2(new MyPoint(chessboard_size - 1, 0),
							new MyPoint(chessboard_size - 1,
									chessboard_size - 1), 0);
					changeValues2(new MyPoint(chessboard_size - 1, 0),
							new MyPoint(chessboard_size - 1,
									chessboard_size - 1), -1);
				}
			} else {
				if (last_status[0][0] == self) {
					changeValues1(new MyPoint(0, 0), 0, 0);
					changeValues1(new MyPoint(0, 0), 0, 1);
					changeValues1(new MyPoint(0, 0), 2, 0);
					changeValues1(new MyPoint(0, 0), 2, 1);
				}
				if (last_status[0][chessboard_size - 1] == self) {
					changeValues1(new MyPoint(0, chessboard_size - 1), 1, 0);
					changeValues1(new MyPoint(0, chessboard_size - 1), 1, 1);
					changeValues1(new MyPoint(0, chessboard_size - 1), 2, 0);
					changeValues1(new MyPoint(0, chessboard_size - 1), 2, -1);
				}
				if (last_status[chessboard_size - 1][0] == self) {
					changeValues1(new MyPoint(chessboard_size - 1, 0), 0, 0);
					changeValues1(new MyPoint(chessboard_size - 1, 0), 0, -1);
					changeValues1(new MyPoint(chessboard_size - 1, 0), 3, 0);
					changeValues1(new MyPoint(chessboard_size - 1, 0), 3, 1);
				}
				if (last_status[chessboard_size - 1][chessboard_size - 1] == self) {
					changeValues1(new MyPoint(chessboard_size - 1,
							chessboard_size - 1), 1, 0);
					changeValues1(new MyPoint(chessboard_size - 1,
							chessboard_size - 1), 1, -1);
					changeValues1(new MyPoint(chessboard_size - 1,
							chessboard_size - 1), 3, 0);
					changeValues1(new MyPoint(chessboard_size - 1,
							chessboard_size - 1), 3, -1);
				}
			}
		}
		if (last_status[0][0] == em
				|| last_status[0][chessboard_size - 1] == em
				|| last_status[chessboard_size - 1][0] == em
				|| last_status[chessboard_size - 1][chessboard_size - 1] == em) { // ������ռ����
			if (last_status[0][0] == em) {
				for (int i = 1; i < chessboard_size - 1; i++) {
					values[0][i] -= 100;
					values[i][0] -= 100;
				}
			}
			if (last_status[0][chessboard_size - 1] == em) {
				for (int i = 1; i < chessboard_size - 1; i++) {
					values[0][i] -= 100;
					values[i][chessboard_size - 1] -= 100;
				}
			}
			if (last_status[chessboard_size - 1][0] == em) {
				for (int i = 1; i < chessboard_size - 1; i++) {
					values[chessboard_size - 1][i] -= 100;
					values[i][0] -= 100;
				}
			}
			if (last_status[chessboard_size - 1][chessboard_size - 1] == em) {
				for (int i = 1; i < chessboard_size - 1; i++) {
					values[chessboard_size - 1][i] -= 100;
					values[i][chessboard_size - 1] -= 100;
				}
			}
		}
	}

	public void changeValues2(MyPoint p1, MyPoint p2, int line) {
		MyPoint[] points = new MyPoint[2];
		int i;
		if (p1.getX() == p2.getX()) {
			int x = p1.getX() + line;
			for (i = 1; i < chessboard_size - 1; i++) {
				if (last_status[x][i] == 0) {
					points[0] = new MyPoint(x, i);
					break;
				}
			}
			if (i == chessboard_size - 1)
				points[0] = null;
			if (points[0] != null) {
				for (i = chessboard_size - 2; i > 0; i--) {
					if (last_status[x][i] == 0) {
						points[1] = new MyPoint(x, i);
						break;
					}
				}
				if (points[0].getY() == points[1].getY()) {
					if (line == 0)
						values[x][points[0].getY()] = 200;
					else
						values[x][points[0].getY()] = 150;
				} else {
					if (line == 0) {
						values[x][points[0].getY()] = 200;
						values[x][points[1].getY()] = 200;
					} else {
						values[x][points[0].getY()] = 150;
						values[x][points[1].getY()] = 150;
					}
					for (int j = points[0].getY() + 1; j < points[1].getY(); j++)
						values[x][j] = 100;
				}
			}
		} else {
			int y = p1.getY() + line;
			for (i = 1; i < chessboard_size - 1; i++) {
				if (last_status[i][y] == 0) {
					points[0] = new MyPoint(i, y);
					break;
				}
			}
			if (i == chessboard_size - 1)
				points[0] = null;
			if (points[0] != null) {
				for (i = chessboard_size - 2; i > 0; i--) {
					if (last_status[i][y] == 0) {
						points[1] = new MyPoint(i, y);
						break;
					}
				}
				if (points[0].getX() == points[1].getX()) {
					if (line == 0)
						values[points[0].getX()][y] = 200;
					else
						values[points[0].getX()][y] = 150;
				} else {
					if (line == 0) {
						values[points[0].getX()][y] = 200;
						values[points[1].getX()][y] = 200;
					} else {
						values[points[0].getX()][y] = 150;
						values[points[1].getX()][y] = 150;
					}
					for (int j = points[0].getX() + 1; j < points[1].getX(); j++)
						values[j][y] = 100;
				}
			}
		}
	}

	public void changeValues1(MyPoint p1, int flag, int line) {
		MyPoint point = null;
		int i;
		if (flag == 0) { // y++
			int x = p1.getX() + line;
			for (i = 1; i < chessboard_size - 2; i++) {
				if (last_status[x][i] == 0) {
					point = new MyPoint(x, i);
					break;
				}
			}
			if (point != null) {
				if (line == 0)
					values[x][point.getY()] = 200;
				else
					values[x][point.getY()] = 150;
				for (int j = point.getY() + 1; j < chessboard_size - 2; j++)
					values[x][j] = 100;
			}
		} else if (flag == 1) { // y--
			int x = p1.getX() + line;
			for (i = chessboard_size - 2; i > 1; i--) {
				if (last_status[x][i] == 0) {
					point = new MyPoint(x, i);
					break;
				}
			}
			if (point != null) {
				if (line == 0)
					values[x][point.getY()] = 200;
				else
					values[x][point.getY()] = 150;
				for (int j = point.getY() - 1; j > 1; j--)
					values[x][j] = 100;
			}
		} else if (flag == 2) { // x++
			int y = p1.getY() + line;
			for (i = 1; i < chessboard_size - 2; i++) {
				if (last_status[i][y] == 0) {
					point = new MyPoint(i, y);
					break;
				}
			}
			if (point != null) {
				if (line == 0)
					values[point.getX()][y] = 200;
				else
					values[point.getX()][y] = 150;
				for (int j = point.getX() + 1; j < chessboard_size - 2; j++)
					values[j][y] = 100;
			}
		} else if (flag == 3) { // x--
			int y = p1.getY() + line;
			for (i = chessboard_size - 2; i > 1; i--) {
				if (last_status[i][y] == 0) {
					point = new MyPoint(i, y);
					break;
				}
			}
			if (point != null) {
				if (line == 0)
					values[point.getX()][y] = 200;
				else
					values[point.getX()][y] = 150;
				for (int j = point.getX() - 1; j > 1; j--)
					values[j][y] = 100;
			}
		}
	}

	/**
	 * ���ݸ���Ȩֵ�ĸߵͻ�ȡ����������λ��
	 * 
	 * @return
	 */
	public MyPoint getPositionToTurn() {
		if (legalPoints.size() == 1)
			return legalPoints.get(0);
		Iterator<MyPoint> iterator = legalPoints.iterator();
		while (iterator.hasNext()) {
			MyPoint point = iterator.next();
			if (maxPoints.isEmpty()
					|| values[point.getX()][point.getY()] == values[maxPoints
							.get(0).getX()][maxPoints.get(0).getY()])
				maxPoints.add(point);
			else if (values[point.getX()][point.getY()] > values[maxPoints.get(
					0).getX()][maxPoints.get(0).getY()]) {
				maxPoints.clear();
				maxPoints.add(point);
			}
		}
		if (maxPoints.size() == 1)
			return maxPoints.get(0);
		int r = (int) (maxPoints.size() * Math.random());
		return maxPoints.get(r);
	}

	/**
	 * �õ��ڸô�����֮�����̵�״̬
	 * 
	 * @param pre_status
	 * @param point
	 * @param now_id
	 * @return
	 */
	public int[][] getNextStatus(int[][] pre_status, MyPoint point, int now_id) {
		int[][] result = new int[chessboard_size][chessboard_size];
		int self = now_id;
		int em = now_id % 2 + 1;
		int x = point.getX();
		int y = point.getY();
		int m, n;
		for (int i = 0; i < chessboard_size; i++) {
			for (int j = 0; j < chessboard_size; j++)
				result[i][j] = pre_status[i][j];
		}
		result[x][y] = self;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0)
					continue;
				if (legalOnPosition(pre_status, new MyPoint(x, y), i, j, 0,
						now_id)) {
					m = x + i;
					n = y + j;
					while (result[m][n] == em) {
						result[m][n] = self;
						m += i;
						n += j;
					}
				}
			}
		}
		return result;
	}

	/**
	 * �ж��ڸô������ܷ񽫶��ֵ��ӳԹ�
	 * 
	 * @param pre_status
	 * @param point
	 * @param now_id
	 * @return
	 */
	public boolean eatAll(int[][] pre_status, MyPoint point, int now_id) {
		int[][] next_status = getNextStatus(pre_status, point, now_id);
		for (int i = 0; i < chessboard_size; i++) {
			for (int j = 0; j < chessboard_size; j++)
				if (next_status[i][j] == now_id % 2 + 1)
					return false;
		}
		return true;
	}

	/**
	 * �ж��Ƿ�ת��Ϊ�յĶ��Ƕ�Ӧ��2,2λ�϶��ֵ����ӣ���Ȼ������������п��ܾ�����
	 * 
	 * @param point
	 * @param now_id
	 * @return
	 */
	public boolean eat22(MyPoint point, int now_id) {
		int[][] next_status = getNextStatus(last_status, point, now_id);
		int self = now_id;
		int em = now_id % 2 + 1;
		if (last_status[0][0] == 0
				&& last_status[1][1] == em
				&& next_status[0][0] == 0
				&& next_status[1][1] == self
				|| last_status[0][chessboard_size - 1] == 0
				&& last_status[1][chessboard_size - 2] == em
				&& next_status[0][chessboard_size - 1] == 0
				&& next_status[1][chessboard_size - 2] == self
				|| last_status[chessboard_size - 1][0] == 0
				&& last_status[chessboard_size - 2][1] == em
				&& next_status[chessboard_size - 1][0] == 0
				&& next_status[chessboard_size - 2][1] == self
				|| last_status[chessboard_size - 1][chessboard_size - 1] == 0
				&& last_status[chessboard_size - 2][chessboard_size - 2] == em
				&& next_status[chessboard_size - 1][chessboard_size - 1] == 0
				&& next_status[chessboard_size - 2][chessboard_size - 2] == self)
			return true;
		return false;
	}

	/**
	 * �ж��Ƿ�ת��Ϊ�յ�1,3λ��Ӧ��2,4λ�϶��ֵ�����
	 * 
	 * @param point
	 * @param now_id
	 * @return
	 */
	public boolean eat24(MyPoint point, int now_id) {
		int[][] next_status = getNextStatus(last_status, point, now_id);
		int self = now_id;
		int em = now_id % 2 + 1;
		if (last_status[0][2] == 0
				&& last_status[1][3] == em
				&& next_status[0][2] == 0
				&& next_status[1][3] == self
				|| last_status[0][chessboard_size - 3] == 0
				&& last_status[1][chessboard_size - 4] == em
				&& next_status[0][chessboard_size - 3] == 0
				&& next_status[1][chessboard_size - 4] == self
				|| last_status[2][0] == 0
				&& last_status[3][1] == em
				&& next_status[2][0] == 0
				&& next_status[3][1] == self
				|| last_status[2][chessboard_size - 1] == 0
				&& last_status[3][chessboard_size - 2] == em
				&& next_status[2][chessboard_size - 1] == 0
				&& next_status[3][chessboard_size - 2] == self
				|| last_status[chessboard_size - 3][0] == 0
				&& last_status[chessboard_size - 4][1] == em
				&& next_status[chessboard_size - 3][0] == 0
				&& next_status[chessboard_size - 4][1] == self
				|| last_status[chessboard_size - 3][chessboard_size - 1] == 0
				&& last_status[chessboard_size - 4][chessboard_size - 2] == em
				&& next_status[chessboard_size - 3][chessboard_size - 1] == 0
				&& next_status[chessboard_size - 4][chessboard_size - 2] == self
				|| last_status[chessboard_size - 1][2] == 0
				&& last_status[chessboard_size - 2][3] == em
				&& next_status[chessboard_size - 1][2] == 0
				&& next_status[chessboard_size - 2][3] == self
				|| last_status[chessboard_size - 1][chessboard_size - 3] == 0
				&& last_status[chessboard_size - 2][chessboard_size - 4] == em
				&& next_status[chessboard_size - 1][chessboard_size - 3] == 0
				&& next_status[chessboard_size - 2][chessboard_size - 4] == self)
			return true;
		return false;
	}

	/**
	 * �����ڸ�λ������Ե��������ӵ�����
	 * 
	 * @param point
	 * @return
	 */
	public int countEat(MyPoint point) {
		int result = 0;
		int self = id;
		int em = id % 2 + 1;
		int x = point.getX();
		int y = point.getY();
		int m, n;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0)
					continue;
				if (legalOnPosition(last_status, new MyPoint(x, y), i, j, 0,
						self)) {
					m = x + i;
					n = y + j;
					while (last_status[m][n] == em) {
						result++;
						m += i;
						n += j;
					}
				}
			}
		}
		return result;
	}

	public boolean legalOnPosition(int[][] chessboard, MyPoint point, int dx,
			int dy, int flag, int now_id) {
		int self = now_id;
		int em = now_id % 2 + 1;
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
			return legalOnPosition(chessboard, point, dx, dy, 1, now_id);
		return false;
	}

	public boolean illegal(int[][] chessboard, MyPoint point, int now_id) {
		int x = point.getX();
		int y = point.getY();
		if (chessboard[x][y] != 0)
			return true;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0)
					continue;
				if (legalOnPosition(chessboard, new MyPoint(x, y), i, j, 0,
						now_id))
					return false;
			}
		}
		return true;
	}

	public boolean skip(int[][] chessboard, int now_id) {
		for (int i = 0; i < chessboard_size; i++) {
			for (int j = 0; j < chessboard_size; j++) {
				if (chessboard[i][j] != 0)
					continue;
				if (!illegal(chessboard, new MyPoint(i, j), now_id))
					return false;
			}
		}
		return true;
	}

	/**
	 * ��ȡ������ĳ�����ӻ��λ������
	 * 
	 * @param chessboard
	 * @param type
	 * @return
	 */
	public int getCountOfThisType(int[][] chessboard, int type) {
		int result = 0;
		for (int i = 0; i < chessboard_size; i++) {
			for (int j = 0; j < chessboard_size; j++) {
				if (chessboard[i][j] == type)
					result++;
			}
		}
		return result;
	}

	/**
	 * �жϸõ��Ƿ���1,1λ������
	 * 
	 * @param point
	 * @return
	 */
	public boolean is11(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0 && y == 0 || x == 0 && y == chessboard_size - 1
				|| x == chessboard_size - 1 && y == 0
				|| x == chessboard_size - 1 && y == chessboard_size - 1)
			return true;
		return false;
	}

	/**
	 * �жϸõ��Ƿ���1,2λ
	 * 
	 * @param point
	 * @return
	 */
	public boolean is12(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0 && y == 1 || x == 0 && y == chessboard_size - 2 || x == 1
				&& y == 0 || x == 1 && y == chessboard_size - 1
				|| x == chessboard_size - 2 && y == 0
				|| x == chessboard_size - 2 && y == chessboard_size - 1
				|| x == chessboard_size - 1 && y == 1
				|| x == chessboard_size - 1 && y == chessboard_size - 2)
			return true;
		return false;
	}

	/**
	 * �жϸõ��Ƿ���1,3λ
	 * 
	 * @param point
	 * @return
	 */
	public boolean is13(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0 && y == 2 || x == 0 && y == chessboard_size - 3 || x == 2
				&& y == 0 || x == 2 && y == chessboard_size - 1
				|| x == chessboard_size - 3 && y == 0
				|| x == chessboard_size - 3 && y == chessboard_size - 1
				|| x == chessboard_size - 1 && y == 2
				|| x == chessboard_size - 1 && y == chessboard_size - 3)
			return true;
		return false;
	}

	/**
	 * �жϸõ��Ƿ���1,4λ
	 * 
	 * @param point
	 * @return
	 */
	public boolean is14(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0 && y == 3 || x == 0 && y == chessboard_size - 4 || x == 3
				&& y == 0 || x == 3 && y == chessboard_size - 1
				|| x == chessboard_size - 4 && y == 0
				|| x == chessboard_size - 4 && y == chessboard_size - 1
				|| x == chessboard_size - 1 && y == 3
				|| x == chessboard_size - 1 && y == chessboard_size - 4)
			return true;
		return false;
	}

	/**
	 * �жϸõ��Ƿ���2,2λ
	 * 
	 * @param point
	 * @return
	 */
	public boolean is22(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 1 && y == 1 || x == 1 && y == chessboard_size - 2
				|| x == chessboard_size - 2 && y == 1
				|| x == chessboard_size - 2 && y == chessboard_size - 2)
			return true;
		return false;
	}

	/**
	 * �жϸõ��Ƿ���2,3λ
	 * 
	 * @param point
	 * @return
	 */
	public boolean is23(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 1 && y == 2 || x == 1 && y == chessboard_size - 3 || x == 2
				&& y == 1 || x == 2 && y == chessboard_size - 2
				|| x == chessboard_size - 3 && y == 1
				|| x == chessboard_size - 3 && y == chessboard_size - 2
				|| x == chessboard_size - 2 && y == 2
				|| x == chessboard_size - 2 && y == chessboard_size - 3)
			return true;
		return false;
	}

	/**
	 * �жϸõ��Ƿ���2,4λ
	 * 
	 * @param point
	 * @return
	 */
	public boolean is24(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 1 && y == 3 || x == 1 && y == chessboard_size - 4 || x == 3
				&& y == 1 || x == 3 && y == chessboard_size - 2
				|| x == chessboard_size - 4 && y == 1
				|| x == chessboard_size - 4 && y == chessboard_size - 2
				|| x == chessboard_size - 2 && y == 3
				|| x == chessboard_size - 2 && y == chessboard_size - 4)
			return true;
		return false;
	}

	/**
	 * �жϸõ��Ƿ���3,3λ
	 * 
	 * @param point
	 * @return
	 */
	public boolean is33(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 2 && y == 2 || x == 2 && y == chessboard_size - 3
				|| x == chessboard_size - 3 && y == 2
				|| x == chessboard_size - 3 && y == chessboard_size - 3)
			return true;
		return false;
	}

	/**
	 * �жϸõ��Ƿ���3,4λ
	 * 
	 * @param point
	 * @return
	 */
	public boolean is34(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 2 && y == 3 || x == 2 && y == chessboard_size - 4 || x == 3
				&& y == 2 || x == 3 && y == chessboard_size - 3
				|| x == chessboard_size - 4 && y == 2
				|| x == chessboard_size - 4 && y == chessboard_size - 3
				|| x == chessboard_size - 3 && y == 3
				|| x == chessboard_size - 3 && y == chessboard_size - 4)
			return true;
		return false;
	}

	/**
	 * ��ȡ�ٽ��õ��1,1λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint get11(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0 && y == 1 || x == 0 && y == 2 || x == 0 && y == 3 || x == 1
				&& y == 0 || x == 1 && y == 1 || x == 1 && y == 2 || x == 1
				&& y == 3 || x == 2 && y == 0 || x == 2 && y == 1 || x == 3
				&& y == 0 || x == 3 && y == 1)
			return new MyPoint(0, 0);
		else if (x == 0 && y == chessboard_size - 2 || x == 0
				&& y == chessboard_size - 3 || x == 0
				&& y == chessboard_size - 4 || x == 1
				&& y == chessboard_size - 1 || x == 1
				&& y == chessboard_size - 2 || x == 1
				&& y == chessboard_size - 3 || x == 1
				&& y == chessboard_size - 4 || x == 2
				&& y == chessboard_size - 1 || x == 2
				&& y == chessboard_size - 2 || x == 3
				&& y == chessboard_size - 1 || x == 3
				&& y == chessboard_size - 2)
			return new MyPoint(0, chessboard_size - 1);
		else if (x == chessboard_size - 1 && y == 1 || x == chessboard_size - 1
				&& y == 2 || x == chessboard_size - 1 && y == 3
				|| x == chessboard_size - 2 && y == 0
				|| x == chessboard_size - 2 && y == 1
				|| x == chessboard_size - 2 && y == 2
				|| x == chessboard_size - 2 && y == 3
				|| x == chessboard_size - 3 && y == 0
				|| x == chessboard_size - 3 && y == 1
				|| x == chessboard_size - 4 && y == 0
				|| x == chessboard_size - 4 && y == 1)
			return new MyPoint(chessboard_size - 1, 0);
		return new MyPoint(chessboard_size - 1, chessboard_size - 1);
	}

	/**
	 * ��ȡ�ٽ��õ��1,2λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint get12(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0 && y == 2 || x == 0 && y == 3 || x == 1 && y == 0 || x == 1
				&& y == 2 || x == 1 && y == 3)
			return new MyPoint(0, 1);
		else if (x == 0 && y == chessboard_size - 3 || x == 0
				&& y == chessboard_size - 4 || x == 1
				&& y == chessboard_size - 1 || x == 1
				&& y == chessboard_size - 3 || x == 1
				&& y == chessboard_size - 4)
			return new MyPoint(0, chessboard_size - 2);
		else if (x == 0 && y == 1 || x == 2 && y == 0 || x == 2 && y == 1
				|| x == 3 && y == 0 || x == 3 && y == 1)
			return new MyPoint(1, 0);
		else if (x == 0 && y == chessboard_size - 2 || x == 2
				&& y == chessboard_size - 1 || x == 2
				&& y == chessboard_size - 2 || x == 3
				&& y == chessboard_size - 1 || x == 3
				&& y == chessboard_size - 2)
			return new MyPoint(1, chessboard_size - 1);
		else if (x == chessboard_size - 1 && y == 1 || x == chessboard_size - 3
				&& y == 0 || x == chessboard_size - 3 && y == 1
				|| x == chessboard_size - 4 && y == 0
				|| x == chessboard_size - 4 && y == 1)
			return new MyPoint(chessboard_size - 2, 0);
		else if (x == chessboard_size - 1 && y == chessboard_size - 2
				|| x == chessboard_size - 3 && y == chessboard_size - 1
				|| x == chessboard_size - 3 && y == chessboard_size - 2
				|| x == chessboard_size - 4 && y == chessboard_size - 1
				|| x == chessboard_size - 4 && y == chessboard_size - 2)
			return new MyPoint(chessboard_size - 2, chessboard_size - 1);
		else if (x == chessboard_size - 1 && y == 2 || x == chessboard_size - 1
				&& y == 3 || x == chessboard_size - 2 && y == 0
				|| x == chessboard_size - 2 && y == 2
				|| x == chessboard_size - 2 && y == 3)
			return new MyPoint(chessboard_size - 1, 1);
		return new MyPoint(chessboard_size - 1, chessboard_size - 2);
	}

	/**
	 * ��ȡ�ٽ��õ��1,3λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint get13(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0 && y == 1 || x == 0 && y == 3 || x == 1 && y == 2 || x == 1
				&& y == 3 || x == 2 && y == 3)
			return new MyPoint(0, 2);
		else if (x == 0 && y == chessboard_size - 2 || x == 0
				&& y == chessboard_size - 4 || x == 1
				&& y == chessboard_size - 3 || x == 1
				&& y == chessboard_size - 4 || x == 2
				&& y == chessboard_size - 4)
			return new MyPoint(0, chessboard_size - 3);
		else if (x == 1 && y == 0 || x == 2 && y == 1 || x == 3 && y == 0
				|| x == 3 && y == 1 || x == 3 && y == 2)
			return new MyPoint(2, 0);
		else if (x == 1 && y == chessboard_size - 1 || x == 2
				&& y == chessboard_size - 2 || x == 3
				&& y == chessboard_size - 1 || x == 3
				&& y == chessboard_size - 2 || x == 3
				&& y == chessboard_size - 3)
			return new MyPoint(2, chessboard_size - 1);
		else if (x == chessboard_size - 2 && y == 0 || x == chessboard_size - 3
				&& y == 1 || x == chessboard_size - 4 && y == 0
				|| x == chessboard_size - 4 && y == 1
				|| x == chessboard_size - 4 && y == 2)
			return new MyPoint(chessboard_size - 3, 0);
		else if (x == chessboard_size - 2 && y == chessboard_size - 1
				|| x == chessboard_size - 3 && y == chessboard_size - 2
				|| x == chessboard_size - 4 && y == chessboard_size - 1
				|| x == chessboard_size - 4 && y == chessboard_size - 2
				|| x == chessboard_size - 4 && y == chessboard_size - 3)
			return new MyPoint(chessboard_size - 3, chessboard_size - 1);
		else if (x == chessboard_size - 1 && y == 1 || x == chessboard_size - 1
				&& y == 3 || x == chessboard_size - 2 && y == 2
				|| x == chessboard_size - 2 && y == 3
				|| x == chessboard_size - 3 && y == 3)
			return new MyPoint(chessboard_size - 1, 2);
		return new MyPoint(chessboard_size - 1, chessboard_size - 3);
	}

	/**
	 * ��ȡ�ٽ��õ��1,4λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint get14(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0 && y == 1 || x == 0 && y == 2 || x == 1 && y == 2 || x == 1
				&& y == 3 || x == 2 && y == 3)
			return new MyPoint(0, 3);
		else if (x == 0 && y == chessboard_size - 2 || x == 0
				&& y == chessboard_size - 3 || x == 1
				&& y == chessboard_size - 3 || x == 1
				&& y == chessboard_size - 4 || x == 2
				&& y == chessboard_size - 4)
			return new MyPoint(0, chessboard_size - 4);
		else if (x == 1 && y == 0 || x == 2 && y == 0 || x == 2 && y == 1
				|| x == 3 && y == 1 || x == 3 && y == 2)
			return new MyPoint(3, 0);
		else if (x == 1 && y == chessboard_size - 1 || x == 2
				&& y == chessboard_size - 1 || x == 2
				&& y == chessboard_size - 2 || x == 3
				&& y == chessboard_size - 2 || x == 3
				&& y == chessboard_size - 3)
			return new MyPoint(3, chessboard_size - 1);
		else if (x == chessboard_size - 2 && y == 0 || x == chessboard_size - 3
				&& y == 0 || x == chessboard_size - 3 && y == 1
				|| x == chessboard_size - 4 && y == 1
				|| x == chessboard_size - 4 && y == 2)
			return new MyPoint(chessboard_size - 4, 0);
		else if (x == chessboard_size - 2 && y == chessboard_size - 1
				|| x == chessboard_size - 3 && y == chessboard_size - 1
				|| x == chessboard_size - 3 && y == chessboard_size - 2
				|| x == chessboard_size - 4 && y == chessboard_size - 2
				|| x == chessboard_size - 4 && y == chessboard_size - 3)
			return new MyPoint(chessboard_size - 4, chessboard_size - 1);
		else if (x == chessboard_size - 1 && y == 1 || x == chessboard_size - 1
				&& y == 2 || x == chessboard_size - 2 && y == 2
				|| x == chessboard_size - 2 && y == 3
				|| x == chessboard_size - 3 && y == 3)
			return new MyPoint(chessboard_size - 1, 3);
		return new MyPoint(chessboard_size - 1, chessboard_size - 4);
	}

	/**
	 * ��ȡ�ٽ��õ��2,2λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint get22(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0 && y == 1 || x == 0 && y == 2 || x == 0 && y == 3 || x == 1
				&& y == 0 || x == 1 && y == 2 || x == 1 && y == 3 || x == 2
				&& y == 0 || x == 2 && y == 1 || x == 3 && y == 0 || x == 3
				&& y == 1)
			return new MyPoint(1, 1);
		else if (x == 0 && y == chessboard_size - 2 || x == 0
				&& y == chessboard_size - 3 || x == 0
				&& y == chessboard_size - 4 || x == 1
				&& y == chessboard_size - 1 || x == 1
				&& y == chessboard_size - 3 || x == 1
				&& y == chessboard_size - 4 || x == 2
				&& y == chessboard_size - 1 || x == 2
				&& y == chessboard_size - 2 || x == 3
				&& y == chessboard_size - 1 || x == 3
				&& y == chessboard_size - 2)
			return new MyPoint(1, chessboard_size - 2);
		else if (x == chessboard_size - 1 && y == 1 || x == chessboard_size - 1
				&& y == 2 || x == chessboard_size - 1 && y == 3
				|| x == chessboard_size - 2 && y == 0
				|| x == chessboard_size - 2 && y == 2
				|| x == chessboard_size - 2 && y == 3
				|| x == chessboard_size - 3 && y == 0
				|| x == chessboard_size - 3 && y == 1
				|| x == chessboard_size - 4 && y == 0
				|| x == chessboard_size - 4 && y == 1)
			return new MyPoint(chessboard_size - 2, 1);
		return new MyPoint(chessboard_size - 2, chessboard_size - 2);
	}

	/**
	 * ��ȡ�ٽ��õ��2,3λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint get23(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0 && y == 2 || x == 0 && y == 3 || x == 1 && y == 3 || x == 2
				&& y == 3)
			return new MyPoint(1, 2);
		else if (x == 0 && y == chessboard_size - 3 || x == 0
				&& y == chessboard_size - 4 || x == 1
				&& y == chessboard_size - 4 || x == 2
				&& y == chessboard_size - 4)
			return new MyPoint(1, chessboard_size - 3);
		else if (x == 2 && y == 0 || x == 3 && y == 0 || x == 3 && y == 1
				|| x == 3 && y == 2)
			return new MyPoint(2, 1);
		else if (x == 2 && y == chessboard_size - 1 || x == 3
				&& y == chessboard_size - 1 || x == 3
				&& y == chessboard_size - 2 || x == 3
				&& y == chessboard_size - 3)
			return new MyPoint(2, chessboard_size - 2);
		else if (x == chessboard_size - 3 && y == 0 || x == chessboard_size - 4
				&& y == 0 || x == chessboard_size - 4 && y == 1
				|| x == chessboard_size - 4 && y == 2)
			return new MyPoint(chessboard_size - 3, 1);
		else if (x == chessboard_size - 3 && y == chessboard_size - 1
				|| x == chessboard_size - 4 && y == chessboard_size - 1
				|| x == chessboard_size - 4 && y == chessboard_size - 2
				|| x == chessboard_size - 4 && y == chessboard_size - 3)
			return new MyPoint(chessboard_size - 3, chessboard_size - 2);
		else if (x == chessboard_size - 1 && y == 2 || x == chessboard_size - 1
				&& y == 3 || x == chessboard_size - 2 && y == 3
				|| x == chessboard_size - 3 && y == 3)
			return new MyPoint(chessboard_size - 2, 2);
		return new MyPoint(chessboard_size - 2, chessboard_size - 3);
	}

	public MyPoint get23_2(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 2 && y == 3)
			return new MyPoint(2, 1);
		else if (x == 2 && y == chessboard_size - 4)
			return new MyPoint(2, chessboard_size - 2);
		else if (x == 3 && y == 2)
			return new MyPoint(1, 2);
		else if (x == 3 && y == chessboard_size - 3)
			return new MyPoint(1, chessboard_size - 3);
		else if (x == chessboard_size - 4 && y == 2)
			return new MyPoint(chessboard_size - 2, 2);
		else if (x == chessboard_size - 4 && y == chessboard_size - 3)
			return new MyPoint(chessboard_size - 2, chessboard_size - 3);
		else if (x == chessboard_size - 3 && y == 3)
			return new MyPoint(chessboard_size - 3, 1);
		return new MyPoint(chessboard_size - 3, chessboard_size - 2);
	}

	/**
	 * ��ȡ�ٽ��õ��2,4λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint get24(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0 && y == 2 || x == 0 && y == 3 || x == 1 && y == 2 || x == 2
				&& y == 3)
			return new MyPoint(1, 3);
		else if (x == 0 && y == chessboard_size - 3 || x == 0
				&& y == chessboard_size - 4 || x == 1
				&& y == chessboard_size - 3 || x == 2
				&& y == chessboard_size - 4)
			return new MyPoint(1, chessboard_size - 4);
		else if (x == 2 && y == 0 || x == 2 && y == 1 || x == 3 && y == 0
				|| x == 3 && y == 2)
			return new MyPoint(3, 1);
		else if (x == 2 && y == chessboard_size - 1 || x == 2
				&& y == chessboard_size - 2 || x == 3
				&& y == chessboard_size - 1 || x == 3
				&& y == chessboard_size - 3)
			return new MyPoint(3, chessboard_size - 2);
		else if (x == chessboard_size - 3 && y == 0 || x == chessboard_size - 3
				&& y == 1 || x == chessboard_size - 4 && y == 0
				|| x == chessboard_size - 4 && y == 2)
			return new MyPoint(chessboard_size - 4, 1);
		else if (x == chessboard_size - 3 && y == chessboard_size - 1
				|| x == chessboard_size - 3 && y == chessboard_size - 2
				|| x == chessboard_size - 4 && y == chessboard_size - 1
				|| x == chessboard_size - 4 && y == chessboard_size - 3)
			return new MyPoint(chessboard_size - 4, chessboard_size - 2);
		else if (x == chessboard_size - 1 && y == 2 || x == chessboard_size - 1
				&& y == 3 || x == chessboard_size - 2 && y == 2
				|| x == chessboard_size - 3 && y == 3)
			return new MyPoint(chessboard_size - 2, 3);
		return new MyPoint(chessboard_size - 2, chessboard_size - 4);
	}

	/**
	 * ��ȡ�ٽ��õ��3,3λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint get33(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 1 && y == 2 || x == 1 && y == 3 || x == 2 && y == 1 || x == 2
				&& y == 3 || x == 3 && y == 1 || x == 3 && y == 2)
			return new MyPoint(2, 2);
		else if (x == 1 && y == chessboard_size - 3 || x == 1
				&& y == chessboard_size - 4 || x == 2
				&& y == chessboard_size - 2 || x == 2
				&& y == chessboard_size - 4 || x == 3
				&& y == chessboard_size - 2 || x == 3
				&& y == chessboard_size - 3)
			return new MyPoint(2, chessboard_size - 3);
		else if (x == chessboard_size - 2 && y == 2 || x == chessboard_size - 2
				&& y == 3 || x == chessboard_size - 3 && y == 1
				|| x == chessboard_size - 3 && y == 3
				|| x == chessboard_size - 4 && y == 1
				|| x == chessboard_size - 4 && y == 2)
			return new MyPoint(chessboard_size - 3, 2);
		return new MyPoint(chessboard_size - 3, chessboard_size - 3);
	}

	/**
	 * ��ȡ�ٽ��õ��3,4λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint get34(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 1 && y == 3 || x == 2 && y == 1)
			return new MyPoint(2, 3);
		else if (x == 1 && y == chessboard_size - 4 || x == 2
				&& y == chessboard_size - 2)
			return new MyPoint(2, chessboard_size - 4);
		else if (x == 3 && y == 1 || x == 1 && y == 2)
			return new MyPoint(3, 2);
		else if (x == 3 && y == chessboard_size - 2 || x == 1
				&& y == chessboard_size - 3)
			return new MyPoint(3, chessboard_size - 3);
		else if (x == chessboard_size - 4 && y == 1 || x == chessboard_size - 2
				&& y == 2)
			return new MyPoint(chessboard_size - 4, 2);
		else if (x == chessboard_size - 4 && y == chessboard_size - 2
				|| x == chessboard_size - 2 && y == chessboard_size - 3)
			return new MyPoint(chessboard_size - 4, chessboard_size - 3);
		else if (x == chessboard_size - 2 && y == 3 || x == chessboard_size - 3
				&& y == 1)
			return new MyPoint(chessboard_size - 3, 3);
		return new MyPoint(chessboard_size - 3, chessboard_size - 4);
	}

	/**
	 * ��ȡ��õ���Ե�1,1λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint getOppo11(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0
				&& (y == chessboard_size - 2 || y == chessboard_size - 3 || y == chessboard_size - 4)
				|| y == 0
				&& (x == chessboard_size - 2 || x == chessboard_size - 3 || x == chessboard_size - 4))
			return new MyPoint(0, 0);
		else if (x == 0
				&& (y == 1 || y == 2 || y == 3)
				|| y == chessboard_size - 1
				&& (x == chessboard_size - 2 || x == chessboard_size - 3 || x == chessboard_size - 4))
			return new MyPoint(0, chessboard_size - 1);
		else if (x == chessboard_size - 1
				&& (y == chessboard_size - 2 || y == chessboard_size - 3 || y == chessboard_size - 4)
				|| y == 0 && (x == 1 || x == 2 || x == 3))
			return new MyPoint(chessboard_size - 1, 0);
		return new MyPoint(chessboard_size - 1, chessboard_size - 1);
	}

	/**
	 * ��ȡ��õ���Ե�1,2λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint getOppo12(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0
				&& (y == chessboard_size - 2 || y == chessboard_size - 3 || y == chessboard_size - 4))
			return new MyPoint(0, 1);
		else if (x == 0 && (y == 1 || y == 2 || y == 3))
			return new MyPoint(0, chessboard_size - 2);
		else if (y == 0
				&& (x == chessboard_size - 2 || x == chessboard_size - 3 || x == chessboard_size - 4))
			return new MyPoint(1, 0);
		else if (y == chessboard_size - 1
				&& (x == chessboard_size - 2 || x == chessboard_size - 3 || x == chessboard_size - 4))
			return new MyPoint(1, chessboard_size - 1);
		else if (y == 0 && (x == 1 || x == 2 || x == 3))
			return new MyPoint(chessboard_size - 2, 0);
		else if (y == chessboard_size - 1 && (x == 1 || x == 2 || x == 3))
			return new MyPoint(chessboard_size - 2, chessboard_size - 1);
		else if (x == chessboard_size - 1
				&& (y == chessboard_size - 2 || y == chessboard_size - 3 || y == chessboard_size - 4))
			return new MyPoint(chessboard_size - 1, 1);
		return new MyPoint(chessboard_size - 1, chessboard_size - 2);
	}

	/**
	 * ��ȡ��õ���Ե�1,3λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint getOppo13(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0
				&& (y == chessboard_size - 2 || y == chessboard_size - 3 || y == chessboard_size - 4)
				|| x == 1
				&& (y == chessboard_size - 3 || y == chessboard_size - 4))
			return new MyPoint(0, 2);
		else if (x == 0 && (y == 1 || y == 2 || y == 3) || x == 1
				&& (y == 2 || y == 3))
			return new MyPoint(0, chessboard_size - 3);
		else if (y == 0
				&& (x == chessboard_size - 2 || x == chessboard_size - 3 || x == chessboard_size - 4)
				|| y == 1
				&& (x == chessboard_size - 3 || x == chessboard_size - 4))
			return new MyPoint(2, 0);
		else if (y == chessboard_size - 1
				&& (x == chessboard_size - 2 || x == chessboard_size - 3 || x == chessboard_size - 4)
				|| y == chessboard_size - 2
				&& (x == chessboard_size - 3 || x == chessboard_size - 4))
			return new MyPoint(2, chessboard_size - 1);
		else if (y == 0 && (x == 1 || x == 2 || x == 3) || y == 1
				&& (x == 2 || x == 3))
			return new MyPoint(chessboard_size - 3, 0);
		else if (y == chessboard_size - 1 && (x == 1 || x == 2 || x == 3)
				|| y == chessboard_size - 2 && (x == 2 || x == 3))
			return new MyPoint(chessboard_size - 3, chessboard_size - 1);
		else if (x == chessboard_size - 1
				&& (y == chessboard_size - 2 || y == chessboard_size - 3 || y == chessboard_size - 4)
				|| x == chessboard_size - 2
				&& (y == chessboard_size - 3 || y == chessboard_size - 4))
			return new MyPoint(chessboard_size - 1, 2);
		return new MyPoint(chessboard_size - 1, chessboard_size - 3);
	}

	/**
	 * ��ȡ��õ���Ե�1,4λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint getOppo14(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 0
				&& (y == chessboard_size - 2 || y == chessboard_size - 3 || y == chessboard_size - 4)
				|| x == 1
				&& (y == chessboard_size - 3 || y == chessboard_size - 4))
			return new MyPoint(0, 3);
		else if (x == 0 && (y == 1 || y == 2 || y == 3) || x == 1
				&& (y == 2 || y == 3))
			return new MyPoint(0, chessboard_size - 4);
		else if (y == 0
				&& (x == chessboard_size - 2 || x == chessboard_size - 3 || x == chessboard_size - 4)
				|| y == 1
				&& (x == chessboard_size - 3 || x == chessboard_size - 4))
			return new MyPoint(3, 0);
		else if (y == chessboard_size - 1
				&& (x == chessboard_size - 2 || x == chessboard_size - 3 || x == chessboard_size - 4)
				|| y == chessboard_size - 2
				&& (x == chessboard_size - 3 || x == chessboard_size - 4))
			return new MyPoint(3, chessboard_size - 1);
		else if (y == 0 && (x == 1 || x == 2 || x == 3) || y == 1
				&& (x == 2 || x == 3))
			return new MyPoint(chessboard_size - 4, 0);
		else if (y == chessboard_size - 1 && (x == 1 || x == 2 || x == 3)
				|| y == chessboard_size - 2 && (x == 2 || x == 3))
			return new MyPoint(chessboard_size - 4, chessboard_size - 1);
		else if (x == chessboard_size - 1
				&& (y == chessboard_size - 2 || y == chessboard_size - 3 || y == chessboard_size - 4)
				|| x == chessboard_size - 2
				&& (y == chessboard_size - 3 || y == chessboard_size - 4))
			return new MyPoint(chessboard_size - 1, 3);
		return new MyPoint(chessboard_size - 1, chessboard_size - 4);
	}

	/**
	 * ��ȡ��õ���Ե�2,3λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint getOppo23(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if ((x == 0 || x == 1)
				&& (y == chessboard_size - 3 || y == chessboard_size - 4))
			return new MyPoint(1, 2);
		else if ((x == 0 || x == 1) && (y == 2 || y == 3))
			return new MyPoint(1, chessboard_size - 3);
		else if ((y == 0 || y == 1)
				&& (x == chessboard_size - 3 || x == chessboard_size - 4))
			return new MyPoint(2, 1);
		else if ((y == chessboard_size - 1 || y == chessboard_size - 2)
				&& (x == chessboard_size - 3 || x == chessboard_size - 4))
			return new MyPoint(2, chessboard_size - 2);
		else if ((y == 0 || y == 1) && (x == 2 || x == 3))
			return new MyPoint(chessboard_size - 3, 1);
		else if ((y == chessboard_size - 1 || y == chessboard_size - 2)
				&& (x == 2 || x == 3))
			return new MyPoint(chessboard_size - 3, chessboard_size - 2);
		else if ((x == chessboard_size - 1 || x == chessboard_size - 2)
				&& (y == chessboard_size - 3 || y == chessboard_size - 4))
			return new MyPoint(chessboard_size - 2, 2);
		return new MyPoint(chessboard_size - 2, chessboard_size - 3);
	}

	/**
	 * ��ȡ��õ���Ե�2,4λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint getOppo24(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if ((x == 0 || x == 1)
				&& (y == chessboard_size - 3 || y == chessboard_size - 4))
			return new MyPoint(1, 3);
		else if ((x == 0 || x == 1) && (y == 2 || y == 3))
			return new MyPoint(1, chessboard_size - 4);
		else if ((y == 0 || y == 1)
				&& (x == chessboard_size - 3 || x == chessboard_size - 4))
			return new MyPoint(3, 1);
		else if ((y == chessboard_size - 1 || y == chessboard_size - 2)
				&& (x == chessboard_size - 3 || x == chessboard_size - 4))
			return new MyPoint(3, chessboard_size - 2);
		else if ((y == 0 || y == 1) && (x == 2 || x == 3))
			return new MyPoint(chessboard_size - 4, 1);
		else if ((y == chessboard_size - 1 || y == chessboard_size - 2)
				&& (x == 2 || x == 3))
			return new MyPoint(chessboard_size - 4, chessboard_size - 2);
		else if ((x == chessboard_size - 1 || x == chessboard_size - 2)
				&& (y == chessboard_size - 3 || y == chessboard_size - 4))
			return new MyPoint(chessboard_size - 2, 3);
		return new MyPoint(chessboard_size - 2, chessboard_size - 4);
	}

	/**
	 * ��ȡ��õ���Ե�3,3λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint getOppo33(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 2 && (y == chessboard_size - 2 || y == chessboard_size - 4)
				|| y == 2
				&& (x == chessboard_size - 2 || x == chessboard_size - 4))
			return new MyPoint(2, 2);
		else if (x == 2 && (y == 1 || y == 3) || y == chessboard_size - 3
				&& (x == chessboard_size - 2 || x == chessboard_size - 4))
			return new MyPoint(2, chessboard_size - 3);
		if (x == chessboard_size - 3
				&& (y == chessboard_size - 2 || y == chessboard_size - 4)
				|| y == 2 && (x == 1 || x == 3))
			return new MyPoint(chessboard_size - 3, 2);
		return new MyPoint(chessboard_size - 3, chessboard_size - 3);
	}

	/**
	 * ��ȡ��õ���Ե�3,4λ
	 * 
	 * @param point
	 * @return
	 */
	public MyPoint getOppo34(MyPoint point) {
		int x = point.getX();
		int y = point.getY();
		if (x == 1 && y == chessboard_size - 4 || x == 2
				&& (y == chessboard_size - 2 || y == chessboard_size - 4))
			return new MyPoint(2, 3);
		else if (x == 1 && y == 3 || x == 2 && (y == 1 || y == 3))
			return new MyPoint(2, chessboard_size - 4);
		else if (y == 1 && x == chessboard_size - 4 || y == 2
				&& (x == chessboard_size - 2 || x == chessboard_size - 4))
			return new MyPoint(3, 2);
		else if (y == chessboard_size - 2 && x == chessboard_size - 4
				|| y == chessboard_size - 3
				&& (x == chessboard_size - 2 || x == chessboard_size - 4))
			return new MyPoint(3, chessboard_size - 3);
		else if (y == 1 && x == chessboard_size - 4 || y == 2
				&& (x == 1 || x == 3))
			return new MyPoint(chessboard_size - 4, 2);
		else if (y == chessboard_size - 2 && x == 3 || y == chessboard_size - 3
				&& (x == 1 || x == 3))
			return new MyPoint(chessboard_size - 4, chessboard_size - 3);
		else if (x == chessboard_size - 2 && y == chessboard_size - 4
				|| x == chessboard_size - 3
				&& (y == chessboard_size - 2 || y == chessboard_size - 4))
			return new MyPoint(chessboard_size - 3, 3);
		return new MyPoint(chessboard_size - 3, chessboard_size - 4);
	}

	/**
	 * ��ȡ��ǰ�÷��ܵ���1,1λ�ĸ���
	 * 
	 * @param chessboard
	 * @param now_id
	 * @return
	 */
	public int canTurn11(int[][] chessboard, int now_id) {
		int result = 0;
		if (!illegal(chessboard, new MyPoint(0, 0), now_id))
			result++;
		if (!illegal(chessboard, new MyPoint(0, chessboard_size - 1), now_id))
			result++;
		if (!illegal(chessboard, new MyPoint(chessboard_size - 1, 0), now_id))
			result++;
		if (!illegal(chessboard, new MyPoint(chessboard_size - 1,
				chessboard_size - 1), now_id))
			result++;
		return result;
	}

	/**
	 * ��ȡ��ǰ�÷��ܵ���1,3λ�ĸ���
	 * 
	 * @param chessboard
	 * @param now_id
	 * @return
	 */
	public int canTurn13(int[][] chessboard, int now_id) {
		int result = 0;
		if (!illegal(chessboard, new MyPoint(0, 2), now_id))
			result++;
		if (!illegal(chessboard, new MyPoint(0, chessboard_size - 3), now_id))
			result++;
		if (!illegal(chessboard, new MyPoint(2, 0), now_id))
			result++;
		if (!illegal(chessboard, new MyPoint(2, chessboard_size - 1), now_id))
			result++;
		if (!illegal(chessboard, new MyPoint(chessboard_size - 3, 0), now_id))
			result++;
		if (!illegal(chessboard, new MyPoint(chessboard_size - 3,
				chessboard_size - 1), now_id))
			result++;
		if (!illegal(chessboard, new MyPoint(chessboard_size - 1, 2), now_id))
			result++;
		if (!illegal(chessboard, new MyPoint(chessboard_size - 1,
				chessboard_size - 3), now_id))
			result++;
		return result;
	}

	/**
	 * ��ȡ��ǰ�÷��ܵ���3,3λ�ĸ���
	 * 
	 * @param chessboard
	 * @param now_id
	 * @return
	 */
	public int canTurn33(int[][] chessboard, int now_id) {
		int result = 0;
		if (!illegal(chessboard, new MyPoint(2, 2), now_id))
			result++;
		if (!illegal(chessboard, new MyPoint(2, chessboard_size - 3), now_id))
			result++;
		if (!illegal(chessboard, new MyPoint(chessboard_size - 3, 2), now_id))
			result++;
		if (!illegal(chessboard, new MyPoint(chessboard_size - 3,
				chessboard_size - 3), now_id))
			result++;
		return result;
	}

	/**
	 * ���ǵ�һ����������� �������̵ĵ�1��Ϊ��02210100��0��ʾ��λ��1��ʾ���壬2��ʾ���壩�� �����ֵ��׷����壬
	 * ���������һ�����ܵ��ﶥ��A1����ô���۰�����ô����һ�����嶼�ܵ���A1�� �÷���ͨ��������ĳһ�л�ĳһ���ж��Ƿ�������������
	 * 
	 * @param line
	 * @param now_id
	 * @return
	 */
	public boolean lineToTurnPosition(int[] line, int now_id) {
		int self = now_id;
		int em = now_id % 2 + 1;
		int i;
		if (line[0] == 0 && line[1] == em) {
			i = 1;
			while (i < chessboard_size && line[i] == em)
				i++;
			if (i < chessboard_size && line[i] == self) {
				while (i < chessboard_size && line[i] == self)
					i++;
				if (i < chessboard_size && line[i] == 0) {
					i++;
					while (i < chessboard_size && line[i] == em)
						i++;
					if (i < chessboard_size && line[i] == self)
						return true;
				}
			}
		}
		return false;
	}

	public int toTurn11(int[][] chessboard, int now_id) {
		int result = 0;
		int self = now_id;
		int em = now_id % 2 + 1;
		int[] line = new int[chessboard_size];
		if (chessboard[0][0] == 0 && illegal(chessboard, new MyPoint(0, 0), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[0][i];
			if (lineToTurnPosition(line, self))
				result++;
			else {
				for (int i = 0; i < chessboard_size; i++)
					line[i] = chessboard[i][0];
				if (lineToTurnPosition(line, self))
					result++;
			}
		}
		if (chessboard[0][chessboard_size - 1] == 0
				&& illegal(chessboard, new MyPoint(0, chessboard_size - 1), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[0][chessboard_size - 1 - i];
			if (lineToTurnPosition(line, self))
				result++;
			else {
				for (int i = 0; i < chessboard_size; i++)
					line[i] = chessboard[i][chessboard_size - 1];
				if (lineToTurnPosition(line, self))
					result++;
			}
		}
		if (chessboard[chessboard_size - 1][0] == 0
				&& illegal(chessboard, new MyPoint(chessboard_size - 1, 0), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[chessboard_size - 1][i];
			if (lineToTurnPosition(line, self))
				result++;
			else {
				for (int i = 0; i < chessboard_size; i++)
					line[i] = chessboard[chessboard_size - 1 - i][0];
				if (lineToTurnPosition(line, self))
					result++;
			}
		}
		if (chessboard[chessboard_size - 1][chessboard_size - 1] == 0
				&& illegal(chessboard, new MyPoint(chessboard_size - 1,
						chessboard_size - 1), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[chessboard_size - 1][chessboard_size - 1
						- i];
			if (lineToTurnPosition(line, self))
				result++;
			else {
				for (int i = 0; i < chessboard_size; i++)
					line[i] = chessboard[chessboard_size - 1 - i][chessboard_size - 1];
				if (lineToTurnPosition(line, self))
					result++;
			}
		}
		return result;
	}

	public int toTurn13(int[][] chessboard, int now_id) {
		int result = 0;
		int self = now_id;
		int em = now_id % 2 + 1;
		int[] line = new int[chessboard_size];
		if (chessboard[0][2] == 0 && illegal(chessboard, new MyPoint(0, 2), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[i][2];
			if (lineToTurnPosition(line, self))
				result++;
		}
		if (chessboard[0][chessboard_size - 3] == 0
				&& illegal(chessboard, new MyPoint(0, chessboard_size - 3), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[i][chessboard_size - 3];
			if (lineToTurnPosition(line, self))
				result++;
		}
		if (chessboard[2][0] == 0 && illegal(chessboard, new MyPoint(2, 0), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[2][i];
			if (lineToTurnPosition(line, self))
				result++;
		}
		if (chessboard[2][chessboard_size - 1] == 0
				&& illegal(chessboard, new MyPoint(2, chessboard_size - 1), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[2][chessboard_size - 1 - i];
			if (lineToTurnPosition(line, self))
				result++;
		}
		if (chessboard[chessboard_size - 3][0] == 0
				&& illegal(chessboard, new MyPoint(chessboard_size - 3, 0), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[chessboard_size - 3][i];
			if (lineToTurnPosition(line, self))
				result++;
		}
		if (chessboard[chessboard_size - 3][chessboard_size - 1] == 0
				&& illegal(chessboard, new MyPoint(chessboard_size - 3,
						chessboard_size - 1), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[chessboard_size - 3][chessboard_size - 1
						- i];
			if (lineToTurnPosition(line, self))
				result++;
		}
		if (chessboard[chessboard_size - 1][2] == 0
				&& illegal(chessboard, new MyPoint(chessboard_size - 1, 2), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[chessboard_size - 1 - i][2];
			if (lineToTurnPosition(line, self))
				result++;
		}
		if (chessboard[chessboard_size - 1][chessboard_size - 3] == 0
				&& illegal(chessboard, new MyPoint(chessboard_size - 1,
						chessboard_size - 3), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[chessboard_size - 1 - i][chessboard_size - 3];
			if (lineToTurnPosition(line, self))
				result++;
		}
		return result;
	}

	/**
	 * ����һ����������� �������̵ĵ�1��Ϊ��02112220��0��ʾ��λ��1��ʾ���壬2��ʾ���壩�� �����ֵ��׷����壬
	 * ���������һ�����ܵ��ﶥ��A1��H1����ô���۰�����ô����һ�����嶼�ܵ���A1��H1�� �÷���ͨ��������ĳһ�л�ĳһ���ж��Ƿ�������������
	 * 
	 * @param line
	 * @param now_id
	 * @return
	 */
	public boolean lineInsertPosition(int[] line, int now_id) {
		int self = now_id;
		int em = now_id % 2 + 1;
		int i;
		if (line[0] == 0 && line[1] == em) {
			i = 1;
			while (i < chessboard_size && line[i] == em)
				i++;
			if (i < chessboard_size && line[i] == self) {
				while (i < chessboard_size && line[i] == self)
					i++;
				if (i < chessboard_size && line[i] == em)
					return true;
			}
		}
		return false;
	}

	public int insertTo11(int[][] chessboard, int now_id) {
		int result = 0;
		int self = now_id;
		int em = now_id % 2 + 1;
		int[] line = new int[chessboard_size];
		if (chessboard[0][0] == 0 && illegal(chessboard, new MyPoint(0, 0), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[0][i];
			if (lineInsertPosition(line, self))
				result++;
			else {
				for (int i = 0; i < chessboard_size; i++)
					line[i] = chessboard[i][0];
				if (lineInsertPosition(line, self))
					result++;
			}
		}
		if (chessboard[0][chessboard_size - 1] == 0
				&& illegal(chessboard, new MyPoint(0, chessboard_size - 1), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[0][chessboard_size - 1 - i];
			if (lineInsertPosition(line, self))
				result++;
			else {
				for (int i = 0; i < chessboard_size; i++)
					line[i] = chessboard[i][chessboard_size - 1];
				if (lineInsertPosition(line, self))
					result++;
			}
		}
		if (chessboard[chessboard_size - 1][0] == 0
				&& illegal(chessboard, new MyPoint(chessboard_size - 1, 0), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[chessboard_size - 1][i];
			if (lineInsertPosition(line, self))
				result++;
			else {
				for (int i = 0; i < chessboard_size; i++)
					line[i] = chessboard[chessboard_size - 1 - i][0];
				if (lineInsertPosition(line, self))
					result++;
			}
		}
		if (chessboard[chessboard_size - 1][chessboard_size - 1] == 0
				&& illegal(chessboard, new MyPoint(chessboard_size - 1,
						chessboard_size - 1), em)) {
			for (int i = 0; i < chessboard_size; i++)
				line[i] = chessboard[chessboard_size - 1][chessboard_size - 1
						- i];
			if (lineInsertPosition(line, self))
				result++;
			else {
				for (int i = 0; i < chessboard_size; i++)
					line[i] = chessboard[chessboard_size - 1 - i][chessboard_size - 1];
				if (lineInsertPosition(line, self))
					result++;
			}
		}
		return result;
	}

}
