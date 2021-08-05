package othello.cy;

import java.util.*;

/**
 * 黑白棋初级算法（基本只判断这一步棋盘的情况，AI很低）
 * 
 * @author cy
 *
 */
public class BeginnerAlgorithm extends BaseAlgorithm {

	public BeginnerAlgorithm(int id, int[][] last_status, MyPoint last_step,
			LinkedList<MyPoint> legalPoints, int count) {
		super(id, last_status, last_step, legalPoints, count);
		// TODO Auto-generated constructor stub
		setValues();
	}

	public void setValues() {
		int self = id;
		int em = id % 2 + 1;
		// 首先考虑上一步对手落子位置的几种特殊情况：
		// 1. 下在1,4位；
		// 2. 下在1,3位
		if (last_step != null) {
			if (super.is14(last_step)) {
				MyPoint n = super.getOppo24(last_step);
				int nx = n.getX();
				int ny = n.getY();
				if (last_status[nx][ny] == 0
						&& !super.illegal(last_status, n, self)) {
					int[][] next_status = super.getNextStatus(last_status, n,
							self);
					if (next_status[super.get11(last_step).getX()][super.get11(
							last_step).getY()] == 0
							&& super.illegal(next_status,
									new MyPoint(super.get11(last_step).getX(),
											super.get11(last_step).getY()), em)
							&& next_status[super.getOppo11(last_step).getX()][super
									.getOppo11(last_step).getY()] == 0
							&& super.illegal(next_status, new MyPoint(super
									.getOppo11(last_step).getX(), super
									.getOppo11(last_step).getY()), em)
							&& next_status[super.get12(last_step).getX()][super
									.get12(last_step).getY()] == 0
							&& next_status[super.getOppo12(last_step).getX()][super
									.getOppo12(last_step).getY()] == 0
							&& next_status[super.get13(last_step).getX()][super
									.get13(last_step).getY()] == 0
							&& super.illegal(next_status,
									new MyPoint(super.get13(last_step).getX(),
											super.get13(last_step).getY()), em)
							&& next_status[super.getOppo13(last_step).getX()][super
									.getOppo13(last_step).getY()] == 0
							&& super.illegal(next_status, new MyPoint(super
									.getOppo13(last_step).getX(), super
									.getOppo13(last_step).getY()), em)
							&& !super.illegal(next_status, new MyPoint(super
									.getOppo14(last_step).getX(), super
									.getOppo14(last_step).getY()), em))
						values[nx][ny] += 110;
				}
			} else if (super.is13(last_step)) {
				MyPoint n = super.get14(last_step);
				int nx = n.getX();
				int ny = n.getY();
				if (last_status[nx][ny] == 0
						&& !super.illegal(last_status, n, self)) {
					int[][] next_status = super.getNextStatus(last_status, n,
							self);
					if (next_status[super.get11(last_step).getX()][super.get11(
							last_step).getY()] == 0
							&& super.illegal(next_status,
									new MyPoint(super.get11(last_step).getX(),
											super.get11(last_step).getY()), em)
							&& next_status[super.getOppo11(last_step).getX()][super
									.getOppo11(last_step).getY()] == 0
							&& super.illegal(next_status, new MyPoint(super
									.getOppo11(last_step).getX(), super
									.getOppo11(last_step).getY()), em)
							&& next_status[super.get12(last_step).getX()][super
									.get12(last_step).getY()] == 0
							&& next_status[super.getOppo12(last_step).getX()][super
									.getOppo12(last_step).getY()] == 0
							&& last_status[super.getOppo13(last_step).getX()][super
									.getOppo13(last_step).getY()] == em
							&& last_status[super.getOppo14(last_step).getX()][super
									.getOppo14(last_step).getY()] != 0)
						values[nx][ny] += 100;
				} else {
					n = super.getOppo14(last_step);
					nx = n.getX();
					ny = n.getY();
					if (last_status[nx][ny] == 0
							&& !super.illegal(last_status, n, self)) {
						int[][] next_status = super.getNextStatus(last_status,
								n, self);
						if (next_status[super.get11(last_step).getX()][super
								.get11(last_step).getY()] == 0
								&& super.illegal(next_status,
										new MyPoint(super.get11(last_step)
												.getX(), super.get11(last_step)
												.getY()), em)
								&& next_status[super.getOppo11(last_step)
										.getX()][super.getOppo11(last_step)
										.getY()] == 0
								&& super.illegal(next_status, new MyPoint(super
										.getOppo11(last_step).getX(), super
										.getOppo11(last_step).getY()), em)
								&& next_status[super.get12(last_step).getX()][super
										.get12(last_step).getY()] == 0
								&& next_status[super.getOppo12(last_step)
										.getX()][super.getOppo12(last_step)
										.getY()] == 0
								&& last_status[super.getOppo13(last_step)
										.getX()][super.getOppo13(last_step)
										.getY()] == em
								&& last_status[super.get14(last_step).getX()][super
										.get14(last_step).getY()] != 0)
							values[nx][ny] += 100;
					}
				}
			}
		}
		// 下面依次遍历计算机所有能下的位置并进行分类改变权值
		Iterator<MyPoint> iterator = legalPoints.iterator();
		while (iterator.hasNext()) {
			MyPoint point = iterator.next();
			int x = point.getX();
			int y = point.getY();
			int[][] next_status = super.getNextStatus(last_status, point, self);
			if (super.is11(point)) {
				if (!super.illegal(last_status, point, em)) // 如果对手可以下在该位置则需要继续提高该位置的权值进行抢占
					values[x][y] += 200;
			} else if (super.is12(point)) {
				if (!super.illegal(next_status, super.get11(point), em)) {
					values[x][y] -= 300;
					if (super.canTurn11(last_status, em) == super.canTurn11(
							next_status, em))
						values[x][y] += 200;
				} else if (last_status[super.get11(point).getX()][super.get11(
						point).getY()] != 0
						&& last_status[super.getOppo11(point).getX()][super
								.getOppo11(point).getY()] == 0
						&& last_status[super.getOppo12(point).getX()][super
								.getOppo12(point).getY()] == em
						&& last_status[super.getOppo13(point).getX()][super
								.getOppo13(point).getY()] != 0
						&& last_status[super.getOppo14(point).getX()][super
								.getOppo14(point).getY()] != 0
						&& last_status[super.get14(point).getX()][super.get14(
								point).getY()] != 0
						&& last_status[super.get13(point).getX()][super.get13(
								point).getY()] != 0
						&& super.illegal(next_status, super.getOppo11(point),
								em))
					values[x][y] += 200;
				else if (last_status[super.get11(point).getX()][super.get11(
						point).getY()] == 0
						&& (last_status[super.get13(point).getX()][super.get13(
								point).getY()] == 0
								|| last_status[super.get14(point).getX()][super
										.get14(point).getY()] == 0
								|| last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] == 0 || last_status[super
								.getOppo13(point).getX()][super
								.getOppo13(point).getY()] == 0))
					values[x][y] -= 50;
				if (last_status[1][1] == em
						&& last_status[0][0] == 0
						|| last_status[1][chessboard_size - 2] == em
						&& last_status[0][chessboard_size - 1] == 0
						|| last_status[chessboard_size - 2][1] == em
						&& last_status[chessboard_size - 1][0] == 0
						|| last_status[chessboard_size - 2][chessboard_size - 2] == em
						&& last_status[chessboard_size - 1][chessboard_size - 1] == 0) {
					if (last_status[1][1] == em
							&& last_status[0][0] == 0
							&& super.illegal(last_status, new MyPoint(0, 0),
									self)
							&& super.illegal(last_status, new MyPoint(0, 0), em)
							&& !super.illegal(next_status, new MyPoint(0, 0),
									self)
							&& super.illegal(next_status, new MyPoint(0, 0), em))
						values[x][y] += 50;
					else if (last_status[1][chessboard_size - 2] == em
							&& last_status[0][chessboard_size - 1] == 0
							&& super.illegal(last_status, new MyPoint(0,
									chessboard_size - 1), self)
							&& super.illegal(last_status, new MyPoint(0,
									chessboard_size - 1), em)
							&& !super.illegal(next_status, new MyPoint(0,
									chessboard_size - 1), self)
							&& super.illegal(next_status, new MyPoint(0,
									chessboard_size - 1), em))
						values[x][y] += 50;
					else if (last_status[chessboard_size - 2][1] == em
							&& last_status[chessboard_size - 1][0] == 0
							&& super.illegal(last_status, new MyPoint(
									chessboard_size - 1, 0), self)
							&& super.illegal(last_status, new MyPoint(
									chessboard_size - 1, 0), em)
							&& !super.illegal(next_status, new MyPoint(
									chessboard_size - 1, 0), self)
							&& super.illegal(next_status, new MyPoint(
									chessboard_size - 1, 0), em))
						values[x][y] += 50;
					else if (last_status[chessboard_size - 2][chessboard_size - 2] == em
							&& last_status[chessboard_size - 1][chessboard_size - 1] == 0
							&& super.illegal(last_status, new MyPoint(
									chessboard_size - 1, chessboard_size - 1),
									self)
							&& super.illegal(last_status, new MyPoint(
									chessboard_size - 1, chessboard_size - 1),
									em)
							&& !super.illegal(next_status, new MyPoint(
									chessboard_size - 1, chessboard_size - 1),
									self)
							&& super.illegal(next_status, new MyPoint(
									chessboard_size - 1, chessboard_size - 1),
									em))
						values[x][y] += 50;
				}
			} else if (super.is22(point)) {
				if (!super.illegal(next_status, super.get11(point), em)) {
					values[x][y] -= 300;
					if (super.canTurn11(last_status, em) == super.canTurn11(
							next_status, em))
						values[x][y] += 200;
					int[][] status2 = super.getNextStatus(next_status,
							super.get11(point), em);
					int[][] status3;
					if (super.get11(point).getX() == 0
							&& super.get11(point).getY() == 0) {
						if (status2[0][chessboard_size - 2] == em
								&& !super.illegal(status2, new MyPoint(0, 1),
										self)
								&& status2[0][chessboard_size - 1] == 0
								&& super.illegal(status2, new MyPoint(0,
										chessboard_size - 1), self)
								&& super.illegal(status2, new MyPoint(0,
										chessboard_size - 1), em)
								&& status2[0][2] != 0 && status2[0][3] != 0
								&& status2[0][chessboard_size - 4] != 0
								&& status2[0][chessboard_size - 3] != 0) {
							status3 = super.getNextStatus(status2, new MyPoint(
									0, 1), self);
							if (super.illegal(status3, new MyPoint(0,
									chessboard_size - 1), em))
								values[x][y] += 30;
						} else if (status2[chessboard_size - 2][0] == em
								&& !super.illegal(status2, new MyPoint(1, 0),
										self)
								&& status2[chessboard_size - 1][0] == 0
								&& super.illegal(status2, new MyPoint(
										chessboard_size - 1, 0), self)
								&& super.illegal(status2, new MyPoint(
										chessboard_size - 1, 0), em)
								&& status2[2][0] != 0 && status2[3][0] != 0
								&& status2[chessboard_size - 4][0] != 0
								&& status2[chessboard_size - 3][0] != 0) {
							status3 = super.getNextStatus(status2, new MyPoint(
									1, 0), self);
							if (super.illegal(status3, new MyPoint(
									chessboard_size - 1, 0), em))
								values[x][y] += 30;
						}
					} else if (super.get11(point).getX() == 0
							&& super.get11(point).getY() == chessboard_size - 1) {
						if (status2[0][1] == em
								&& !super.illegal(status2, new MyPoint(0,
										chessboard_size - 2), self)
								&& status2[0][0] == 0
								&& super.illegal(status2, new MyPoint(0, 0),
										self)
								&& super.illegal(status2, new MyPoint(0, 0), em)
								&& status2[0][chessboard_size - 3] != 0
								&& status2[0][chessboard_size - 4] != 0
								&& status2[0][3] != 0 && status2[0][2] != 0) {
							status3 = super.getNextStatus(status2, new MyPoint(
									0, chessboard_size - 2), self);
							if (super.illegal(status3, new MyPoint(0, 0), em))
								values[x][y] += 30;
						} else if (status2[chessboard_size - 2][chessboard_size - 1] == em
								&& !super.illegal(status2, new MyPoint(1,
										chessboard_size - 1), self)
								&& status2[chessboard_size - 1][chessboard_size - 1] == 0
								&& super.illegal(status2, new MyPoint(
										chessboard_size - 1,
										chessboard_size - 1), self)
								&& super.illegal(status2, new MyPoint(
										chessboard_size - 1,
										chessboard_size - 1), em)
								&& status2[2][chessboard_size - 1] != 0
								&& status2[3][chessboard_size - 1] != 0
								&& status2[chessboard_size - 4][chessboard_size - 1] != 0
								&& status2[chessboard_size - 3][chessboard_size - 1] != 0) {
							status3 = super.getNextStatus(status2, new MyPoint(
									1, chessboard_size - 1), self);
							if (super.illegal(status3, new MyPoint(
									chessboard_size - 1, chessboard_size - 1),
									em))
								values[x][y] += 30;
						}
					} else if (super.get11(point).getX() == chessboard_size - 1
							&& super.get11(point).getY() == 0) {
						if (status2[chessboard_size - 1][chessboard_size - 2] == em
								&& !super.illegal(status2, new MyPoint(
										chessboard_size - 1, 1), self)
								&& status2[chessboard_size - 1][chessboard_size - 1] == 0
								&& super.illegal(status2, new MyPoint(
										chessboard_size - 1,
										chessboard_size - 1), self)
								&& super.illegal(status2, new MyPoint(
										chessboard_size - 1,
										chessboard_size - 1), em)
								&& status2[chessboard_size - 1][2] != 0
								&& status2[chessboard_size - 1][3] != 0
								&& status2[chessboard_size - 1][chessboard_size - 4] != 0
								&& status2[chessboard_size - 1][chessboard_size - 3] != 0) {
							status3 = super.getNextStatus(status2, new MyPoint(
									chessboard_size - 1, 1), self);
							if (super.illegal(status3, new MyPoint(
									chessboard_size - 1, chessboard_size - 1),
									em))
								values[x][y] += 30;
						} else if (status2[1][0] == em
								&& !super.illegal(status2, new MyPoint(
										chessboard_size - 2, 0), self)
								&& status2[0][0] == 0
								&& super.illegal(status2, new MyPoint(0, 0),
										self)
								&& super.illegal(status2, new MyPoint(0, 0), em)
								&& status2[chessboard_size - 3][0] != 0
								&& status2[chessboard_size - 4][0] != 0
								&& status2[3][0] != 0 && status2[2][0] != 0) {
							status3 = super.getNextStatus(status2, new MyPoint(
									chessboard_size - 2, 0), self);
							if (super.illegal(status3, new MyPoint(0, 0), em))
								values[x][y] += 30;
						}
					} else if (super.get11(point).getX() == chessboard_size - 1
							&& super.get11(point).getY() == chessboard_size - 1) {
						if (status2[chessboard_size - 1][1] == em
								&& !super.illegal(status2, new MyPoint(
										chessboard_size - 1,
										chessboard_size - 2), self)
								&& status2[chessboard_size - 1][0] == 0
								&& super.illegal(status2, new MyPoint(
										chessboard_size - 1, 0), self)
								&& super.illegal(status2, new MyPoint(
										chessboard_size - 1, 0), em)
								&& status2[chessboard_size - 1][chessboard_size - 3] != 0
								&& status2[chessboard_size - 1][chessboard_size - 4] != 0
								&& status2[chessboard_size - 1][3] != 0
								&& status2[chessboard_size - 1][2] != 0) {
							status3 = super.getNextStatus(status2, new MyPoint(
									chessboard_size - 1, chessboard_size - 2),
									self);
							if (super.illegal(status3, new MyPoint(
									chessboard_size - 1, 0), em))
								values[x][y] += 30;
						} else if (status2[1][chessboard_size - 1] == em
								&& !super.illegal(status2, new MyPoint(
										chessboard_size - 2,
										chessboard_size - 1), self)
								&& status2[0][chessboard_size - 1] == 0
								&& super.illegal(status2, new MyPoint(0,
										chessboard_size - 1), self)
								&& super.illegal(status2, new MyPoint(0,
										chessboard_size - 1), em)
								&& status2[chessboard_size - 3][chessboard_size - 1] != 0
								&& status2[chessboard_size - 4][chessboard_size - 1] != 0
								&& status2[3][chessboard_size - 1] != 0
								&& status2[2][chessboard_size - 1] != 0) {
							status3 = super.getNextStatus(status2, new MyPoint(
									chessboard_size - 2, chessboard_size - 1),
									self);
							if (super.illegal(status3, new MyPoint(0,
									chessboard_size - 1), em))
								values[x][y] += 30;
						}
					}
				} else if (last_status[super.get11(point).getX()][super.get11(
						point).getY()] == 0)
					values[x][y] -= 20;
			} else if (super.is13(point)) {
				if (super.toTurn11(next_status, self)
						+ super.insertTo11(next_status, self) > super.toTurn11(
						last_status, self)
						+ super.insertTo11(last_status, self)
						|| super.canTurn11(next_status, em) < super.canTurn11(
								last_status, em))
					values[x][y] += 300;
				else if (last_status[super.getOppo11(point).getX()][super
						.getOppo11(point).getY()] == 0
						&& (last_status[super.getOppo12(point).getX()][super
								.getOppo12(point).getY()] == em || last_status[super
								.getOppo12(point).getX()][super
								.getOppo12(point).getY()] == self
								&& last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == self
								&& last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] == self
								&& last_status[super.get14(point).getX()][super
										.get14(point).getY()] == self)
						&& last_status[super.get11(point).getX()][super.get11(
								point).getY()] != self)
					values[x][y] += 30;
				else if (last_status[super.get11(point).getX()][super.get11(
						point).getY()] == 0
						&& last_status[super.get12(point).getX()][super.get12(
								point).getY()] == 0
						&& last_status[super.getOppo11(point).getX()][super
								.getOppo11(point).getY()] == 0
						&& last_status[super.getOppo12(point).getX()][super
								.getOppo12(point).getY()] == 0) {
					if (last_status[super.getOppo13(point).getX()][super
							.getOppo13(point).getY()] == 0
							&& super.illegal(next_status,
									super.getOppo13(point), em)
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] == em
							&& last_status[super.get14(point).getX()][super
									.get14(point).getY()] == em
							|| last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == self
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] == em
							&& last_status[super.get14(point).getX()][super
									.get14(point).getY()] == em
							|| last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == self
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] == self
							&& last_status[super.get14(point).getX()][super
									.get14(point).getY()] != 0
							|| last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == self
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] == 0
							&& super.illegal(next_status,
									super.getOppo14(point), em)
							&& last_status[super.get14(point).getX()][super
									.get14(point).getY()] == em
							|| last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == self
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] == em
							&& last_status[super.get14(point).getX()][super
									.get14(point).getY()] == 0
							&& super.illegal(next_status, super.get14(point),
									em))
						values[x][y] += 20;
					else if (last_status[super.get14(point).getX()][super
							.get14(point).getY()] == em
							&& last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == 0
							|| !super.illegal(last_status, point, em))
						values[x][y] += 10;
				}
			} else if (super.is14(point)) {
				if (super.toTurn11(next_status, self)
						+ super.insertTo11(next_status, self) > super.toTurn11(
						last_status, self)
						+ super.insertTo11(last_status, self)
						|| super.canTurn11(next_status, em) < super.canTurn11(
								last_status, em))
					values[x][y] += 300;
				else if (last_status[super.getOppo11(point).getX()][super
						.getOppo11(point).getY()] == 0
						&& last_status[super.getOppo12(point).getX()][super
								.getOppo12(point).getY()] == em
						&& last_status[super.getOppo13(point).getX()][super
								.getOppo13(point).getY()] == 0)
					values[x][y] += 30;
				else if (last_status[super.getOppo11(point).getX()][super
						.getOppo11(point).getY()] == 0
						&& last_status[super.getOppo12(point).getX()][super
								.getOppo12(point).getY()] == em
						&& last_status[super.getOppo13(point).getX()][super
								.getOppo13(point).getY()] == em
						&& last_status[super.getOppo14(point).getX()][super
								.getOppo14(point).getY()] == em
						&& last_status[super.get13(point).getX()][super.get13(
								point).getY()] == self)
					values[x][y] -= 100;
				else if (last_status[super.get11(point).getX()][super.get11(
						point).getY()] == 0
						&& last_status[super.get12(point).getX()][super.get12(
								point).getY()] == 0
						&& last_status[super.getOppo11(point).getX()][super
								.getOppo11(point).getY()] == 0
						&& last_status[super.getOppo12(point).getX()][super
								.getOppo12(point).getY()] == 0) {
					if (last_status[super.get13(point).getX()][super.get13(
							point).getY()] == self
							&& last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == self
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] != 0)
						values[x][y] += 110;
					else if (last_status[super.getOppo13(point).getX()][super
							.getOppo13(point).getY()] == self
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] == em)
						values[x][y] += 30;
					else if (last_status[super.get13(point).getX()][super
							.get13(point).getY()] == self)
						values[x][y] += 20;
					else if (last_status[super.get13(point).getX()][super
							.get13(point).getY()] == 0
							&& last_status[super.get23(point).getX()][super
									.get23(point).getY()] == em
							&& next_status[super.get23(point).getX()][super
									.get23(point).getY()] == self)
						values[x][y] -= 10;
					else if (last_status[super.get13(point).getX()][super
							.get13(point).getY()] == em
							|| last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] == em)
						values[x][y] -= 2;
					else if (last_status[super.get13(point).getX()][super
							.get13(point).getY()] == 0
							&& last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == 0
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] == 0) {
						if (last_status[super.get24(point).getX()][super.get24(
								point).getY()] == em)
							values[x][y] += 2;
						else
							values[x][y] += 1;
					}
				}
			} else if (super.is23(point)) {
				if (super.canTurn11(next_status, em) < super.canTurn11(
						last_status, em))
					values[x][y] += 300;
				else if ((last_status[super.get13(point).getX()][super.get13(
						point).getY()] == 0 || last_status[super.getOppo13(
						point).getX()][super.getOppo13(point).getY()] == 0)
						&& (last_status[super.getOppo24(point).getX()][super
								.getOppo24(point).getY()] == self
								&& last_status[super.get24(point).getX()][super
										.get24(point).getY()] == em || last_status[super
								.getOppo23(point).getX()][super
								.getOppo23(point).getY()] == self
								&& last_status[super.getOppo24(point).getX()][super
										.getOppo24(point).getY()] == em
								&& last_status[super.get24(point).getX()][super
										.get24(point).getY()] == em))
					values[x][y] -= 10;
				else if (!super.illegal(next_status, super.get13(point), em))
					values[x][y] -= 3;
				else if (last_status[super.get33(point).getX()][super.get33(
						point).getY()] == 0)
					values[x][y] -= 2;
				else if (last_status[super.get33(point).getX()][super.get33(
						point).getY()] == self)
					values[x][y] -= 1;
				else if (super.toTurn13(next_status, self) > super.toTurn13(
						last_status, self)
						|| super.canTurn13(next_status, em) < super.canTurn13(
								last_status, em))
					values[x][y] += 40;
				else if (!(last_status[super.getOppo13(point).getX()][super
						.getOppo13(point).getY()] == em || last_status[super
						.getOppo13(point).getX()][super.getOppo13(point).getY()] == 0
						&& (last_status[super.getOppo23(point).getX()][super
								.getOppo23(point).getY()] == self || last_status[super
								.getOppo24(point).getX()][super
								.getOppo24(point).getY()] == self)))
					values[x][y] += 1;
			} else if (super.is24(point)) {
				if (super.canTurn11(next_status, em) < super.canTurn11(
						last_status, em))
					values[x][y] += 300;
				else if ((last_status[super.get13(point).getX()][super.get13(
						point).getY()] == 0 || last_status[super.getOppo13(
						point).getX()][super.getOppo13(point).getY()] == 0)
						&& last_status[super.getOppo23(point).getX()][super
								.getOppo23(point).getY()] == self
						&& last_status[super.getOppo24(point).getX()][super
								.getOppo24(point).getY()] == em)
					values[x][y] -= 10;
				else if (!super.illegal(next_status, super.get13(point), em))
					values[x][y] -= 2;
				else if (last_status[super.get34(point).getX()][super.get34(
						point).getY()] == 0)
					values[x][y] -= 1;
				else if (!super.illegal(next_status, super.get14(point), em)) {
					int[][] status2 = super.getNextStatus(next_status,
							super.get14(point), em);
					if (super.illegal(status2, super.get13(point), self))
						values[x][y] -= 1;
				} else if (super.toTurn13(next_status, self) > super.toTurn13(
						last_status, self)
						|| super.canTurn13(next_status, em) < super.canTurn13(
								last_status, em))
					values[x][y] += 40;
				else if (last_status[super.get33(point).getX()][super.get33(
						point).getY()] == em
						&& last_status[super.getOppo34(point).getX()][super
								.getOppo34(point).getY()] == 0
						|| last_status[super.get33(point).getX()][super.get33(
								point).getY()] == 0
						&& last_status[super.getOppo34(point).getX()][super
								.getOppo34(point).getY()] == em)
					values[x][y] -= 2;
			} else if (super.is33(point)) {
				if (super.canTurn11(next_status, em) < super.canTurn11(
						last_status, em))
					values[x][y] += 300;
				else if (super.toTurn13(next_status, self) > super.toTurn13(
						last_status, self)
						|| super.canTurn13(next_status, em) < super.canTurn13(
								last_status, em))
					values[x][y] += 70;
				else {
					if (x == 2 && y == 2) {
						if (last_status[2][chessboard_size - 3] == self
								&& last_status[2][chessboard_size - 4] != 0
								&& last_status[2][3] != 0
								&& last_status[chessboard_size - 3][2] == self
								&& last_status[chessboard_size - 4][2] != 0
								&& last_status[3][2] != 0)
							values[x][y] += 3;
						else if (last_status[2][chessboard_size - 3] == self
								&& last_status[2][chessboard_size - 4] != 0
								&& last_status[2][3] != 0
								&& last_status[3][2] == 0
								|| last_status[chessboard_size - 3][2] == self
								&& last_status[chessboard_size - 4][2] != 0
								&& last_status[3][2] != 0
								&& last_status[2][3] == 0)
							values[x][y] += 2;
						else if (last_status[2][3] == 0
								&& last_status[3][2] == 0)
							values[x][y] += 1;
					} else if (x == 2 && y == chessboard_size - 3) {
						if (last_status[2][2] == self
								&& last_status[2][3] != 0
								&& last_status[2][chessboard_size - 4] != 0
								&& last_status[chessboard_size - 3][chessboard_size - 3] == self
								&& last_status[chessboard_size - 4][chessboard_size - 3] != 0
								&& last_status[3][chessboard_size - 3] != 0)
							values[x][y] += 3;
						else if (last_status[2][2] == self
								&& last_status[2][3] != 0
								&& last_status[2][chessboard_size - 4] != 0
								&& last_status[3][chessboard_size - 3] == 0
								|| last_status[chessboard_size - 3][chessboard_size - 3] == self
								&& last_status[chessboard_size - 4][chessboard_size - 3] != 0
								&& last_status[3][chessboard_size - 3] != 0
								&& last_status[2][chessboard_size - 4] == 0)
							values[x][y] += 2;
						else if (last_status[2][chessboard_size - 4] == 0
								&& last_status[3][chessboard_size - 3] == 0)
							values[x][y] += 1;
					} else if (x == chessboard_size - 3 && y == 2) {
						if (last_status[chessboard_size - 3][chessboard_size - 3] == self
								&& last_status[chessboard_size - 3][chessboard_size - 4] != 0
								&& last_status[chessboard_size - 3][3] != 0
								&& last_status[2][2] == self
								&& last_status[3][2] != 0
								&& last_status[chessboard_size - 4][2] != 0)
							values[x][y] += 3;
						else if (last_status[chessboard_size - 3][chessboard_size - 3] == self
								&& last_status[chessboard_size - 3][chessboard_size - 4] != 0
								&& last_status[chessboard_size - 3][3] != 0
								&& last_status[chessboard_size - 4][2] == 0
								|| last_status[2][2] == self
								&& last_status[3][2] != 0
								&& last_status[chessboard_size - 4][2] != 0
								&& last_status[chessboard_size - 3][3] == 0)
							values[x][y] += 2;
						else if (last_status[chessboard_size - 3][3] == 0
								&& last_status[chessboard_size - 4][2] == 0)
							values[x][y] += 1;
					} else if (x == chessboard_size - 3
							&& y == chessboard_size - 3) {
						if (last_status[chessboard_size - 3][2] == self
								&& last_status[chessboard_size - 3][3] != 0
								&& last_status[chessboard_size - 3][chessboard_size - 4] != 0
								&& last_status[2][chessboard_size - 3] == self
								&& last_status[3][chessboard_size - 3] != 0
								&& last_status[chessboard_size - 4][chessboard_size - 3] != 0)
							values[x][y] += 3;
						else if (last_status[chessboard_size - 3][2] == self
								&& last_status[chessboard_size - 3][3] != 0
								&& last_status[chessboard_size - 3][chessboard_size - 4] != 0
								&& last_status[chessboard_size - 4][chessboard_size - 3] == 0
								|| last_status[2][chessboard_size - 3] == self
								&& last_status[3][chessboard_size - 3] != 0
								&& last_status[chessboard_size - 4][chessboard_size - 3] != 0
								&& last_status[chessboard_size - 3][chessboard_size - 4] == 0)
							values[x][y] += 2;
						else if (last_status[chessboard_size - 3][chessboard_size - 4] == 0
								&& last_status[chessboard_size - 4][chessboard_size - 3] == 0)
							values[x][y] += 1;
					}
				}
			} else { // 3,4位
				if (super.canTurn11(next_status, em) < super.canTurn11(
						last_status, em))
					values[x][y] += 300;
				else if (super.canTurn13(next_status, em) > super.canTurn13(
						last_status, em))
					values[x][y] -= 3;
				else if (super.toTurn13(next_status, self) > super.toTurn13(
						last_status, self)
						|| super.canTurn13(next_status, em) < super.canTurn13(
								last_status, em))
					values[x][y] += 70;
				else if (last_status[super.getOppo33(point).getX()][super
						.getOppo33(point).getY()] == self
						&& last_status[super.getOppo34(point).getX()][super
								.getOppo34(point).getY()] == em)
					values[x][y] += 1;
				else if (last_status[super.getOppo33(point).getX()][super
						.getOppo33(point).getY()] == em
						&& last_status[super.getOppo34(point).getX()][super
								.getOppo34(point).getY()] != 0
						&& !super.illegal(next_status, super.get33(point), em))
					values[x][y] -= 1;
			}
		}
	}

}
