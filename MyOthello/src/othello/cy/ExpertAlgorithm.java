package othello.cy;

import java.util.*;

/**
 * �ڰ���߼��㷨�����������14����AI�ϸߣ�
 * 
 * @author cy
 *
 */
public class ExpertAlgorithm extends BaseAlgorithm {

	private final int max_steps = 7;

	public ExpertAlgorithm(int id, int[][] last_status, MyPoint last_step,
			LinkedList<MyPoint> legalPoints, int count) {
		super(id, last_status, last_step, legalPoints, count);
		// TODO Auto-generated constructor stub
		setValues();
	}

	public void setValues() {
		int self = id;
		int em = id % 2 + 1;
		// ����ֽ�ʣ���2����λ����2����λ��������ʱ��ֱ���Ƶ��վ��жϸ����ĸ�λ��
		if (count == chessboard_size * chessboard_size - 2
				&& legalPoints.size() == 2) {
			setValuesForLast2Steps();
			return;
		}
		// �������α���������������µ�λ�ò����з���ı�Ȩֵ
		Iterator<MyPoint> iterator = legalPoints.iterator();
		while (iterator.hasNext()) {
			MyPoint point = iterator.next();
			int x = point.getX();
			int y = point.getY();
			int[][] next_status = super.getNextStatus(last_status, point, self);
			if (values[x][y] < 1000 && eatAllIn4Steps(next_status, 2 - 1)) {
				values[x][y] = 999;
				return;
			}
			boolean temp = true;
			if (super.is11(point)) {
				if (!super.illegal(last_status, point, em)) // ������ֿ������ڸ�λ������Ҫ������߸�λ�õ�Ȩֵ������ռ
					values[x][y] += 400;
				temp = false;
			} else if ((super.is12(point) || super.is22(point))
					&& !super.illegal(next_status, super.get11(point), em)) {
				values[x][y] -= 300;
				if (super.canTurn11(last_status, em) == super.canTurn11(
						next_status, em))
					values[x][y] += 200;
				else {
					int[][] status2 = super.getNextStatus(next_status,
							super.get11(point), em);
					int[][] status3;
					if (super.is12(point)) {
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
									&& super.illegal(last_status, new MyPoint(
											0, 0), self)
									&& super.illegal(last_status, new MyPoint(
											0, 0), em)
									&& !super.illegal(next_status, new MyPoint(
											0, 0), self)
									&& super.illegal(next_status, new MyPoint(
											0, 0), em))
								values[x][y] += 30;
							else if (last_status[1][chessboard_size - 2] == em
									&& last_status[0][chessboard_size - 1] == 0
									&& super.illegal(last_status, new MyPoint(
											0, chessboard_size - 1), self)
									&& super.illegal(last_status, new MyPoint(
											0, chessboard_size - 1), em)
									&& !super.illegal(next_status, new MyPoint(
											0, chessboard_size - 1), self)
									&& super.illegal(next_status, new MyPoint(
											0, chessboard_size - 1), em))
								values[x][y] += 30;
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
								values[x][y] += 30;
							else if (last_status[chessboard_size - 2][chessboard_size - 2] == em
									&& last_status[chessboard_size - 1][chessboard_size - 1] == 0
									&& super.illegal(last_status, new MyPoint(
											chessboard_size - 1,
											chessboard_size - 1), self)
									&& super.illegal(last_status, new MyPoint(
											chessboard_size - 1,
											chessboard_size - 1), em)
									&& !super.illegal(next_status, new MyPoint(
											chessboard_size - 1,
											chessboard_size - 1), self)
									&& super.illegal(next_status, new MyPoint(
											chessboard_size - 1,
											chessboard_size - 1), em))
								values[x][y] += 30;
						}
						if (super.get11(point).getX() == 0
								&& super.get11(point).getY() == 0) {
							if (x == 1
									&& y == 0
									&& status2[0][chessboard_size - 2] == em
									&& !super.illegal(status2,
											new MyPoint(0, 1), self)
									&& status2[0][chessboard_size - 1] == 0
									&& super.illegal(status2, new MyPoint(0,
											chessboard_size - 1), self)
									&& super.illegal(status2, new MyPoint(0,
											chessboard_size - 1), em)
									&& status2[0][2] != 0 && status2[0][3] != 0
									&& status2[0][chessboard_size - 4] != 0
									&& status2[0][chessboard_size - 3] != 0) {
								status3 = super.getNextStatus(status2,
										new MyPoint(0, 1), self);
								if (super.illegal(status3, new MyPoint(0,
										chessboard_size - 1), em)) {
									values[x][y] += 20;
									if (status3[chessboard_size - 2][chessboard_size - 1] == em
											&& super.illegal(
													status3,
													new MyPoint(
															chessboard_size - 1,
															chessboard_size - 1),
													em))
										values[x][y] += 40;
								}
							} else if (x == 0
									&& y == 1
									&& status2[chessboard_size - 2][0] == em
									&& !super.illegal(status2,
											new MyPoint(1, 0), self)
									&& status2[chessboard_size - 1][0] == 0
									&& super.illegal(status2, new MyPoint(
											chessboard_size - 1, 0), self)
									&& super.illegal(status2, new MyPoint(
											chessboard_size - 1, 0), em)
									&& status2[2][0] != 0 && status2[3][0] != 0
									&& status2[chessboard_size - 4][0] != 0
									&& status2[chessboard_size - 3][0] != 0) {
								status3 = super.getNextStatus(status2,
										new MyPoint(1, 0), self);
								if (super.illegal(status3, new MyPoint(
										chessboard_size - 1, 0), em)) {
									values[x][y] += 20;
									if (status3[chessboard_size - 1][chessboard_size - 2] == em
											&& super.illegal(
													status3,
													new MyPoint(
															chessboard_size - 1,
															chessboard_size - 1),
													em))
										values[x][y] += 40;
								}
							}
						} else if (super.get11(point).getX() == 0
								&& super.get11(point).getY() == chessboard_size - 1) {
							if (x == 1
									&& y == chessboard_size - 1
									&& status2[0][1] == em
									&& !super.illegal(status2, new MyPoint(0,
											chessboard_size - 2), self)
									&& status2[0][0] == 0
									&& super.illegal(status2,
											new MyPoint(0, 0), self)
									&& super.illegal(status2,
											new MyPoint(0, 0), em)
									&& status2[0][chessboard_size - 3] != 0
									&& status2[0][chessboard_size - 4] != 0
									&& status2[0][3] != 0 && status2[0][2] != 0) {
								status3 = super.getNextStatus(status2,
										new MyPoint(0, chessboard_size - 2),
										self);
								if (super.illegal(status3, new MyPoint(0, 0),
										em)) {
									values[x][y] += 20;
									if (status3[chessboard_size - 2][0] == em
											&& super.illegal(
													status3,
													new MyPoint(
															chessboard_size - 1,
															0), em))
										values[x][y] += 40;
								}
							} else if (x == 0
									&& y == chessboard_size - 2
									&& status2[chessboard_size - 2][chessboard_size - 1] == em
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
								status3 = super.getNextStatus(status2,
										new MyPoint(1, chessboard_size - 1),
										self);
								if (super.illegal(status3, new MyPoint(
										chessboard_size - 1,
										chessboard_size - 1), em)) {
									values[x][y] += 20;
									if (status3[chessboard_size - 1][1] == em
											&& super.illegal(
													status3,
													new MyPoint(
															chessboard_size - 1,
															0), em))
										values[x][y] += 40;
								}
							}
						} else if (super.get11(point).getX() == chessboard_size - 1
								&& super.get11(point).getY() == 0) {
							if (x == chessboard_size - 2
									&& y == 0
									&& status2[chessboard_size - 1][chessboard_size - 2] == em
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
								status3 = super.getNextStatus(status2,
										new MyPoint(chessboard_size - 1, 1),
										self);
								if (super.illegal(status3, new MyPoint(
										chessboard_size - 1,
										chessboard_size - 1), em)) {
									values[x][y] += 20;
									if (status3[1][chessboard_size - 1] == em
											&& super.illegal(
													status3,
													new MyPoint(0,
															chessboard_size - 1),
													em))
										values[x][y] += 40;
								}
							} else if (x == chessboard_size - 1
									&& y == 1
									&& status2[1][0] == em
									&& !super.illegal(status2, new MyPoint(
											chessboard_size - 2, 0), self)
									&& status2[0][0] == 0
									&& super.illegal(status2,
											new MyPoint(0, 0), self)
									&& super.illegal(status2,
											new MyPoint(0, 0), em)
									&& status2[chessboard_size - 3][0] != 0
									&& status2[chessboard_size - 4][0] != 0
									&& status2[3][0] != 0 && status2[2][0] != 0) {
								status3 = super.getNextStatus(status2,
										new MyPoint(chessboard_size - 2, 0),
										self);
								if (super.illegal(status3, new MyPoint(0, 0),
										em)) {
									values[x][y] += 20;
									if (status3[0][chessboard_size - 2] == em
											&& super.illegal(
													status3,
													new MyPoint(0,
															chessboard_size - 1),
													em))
										values[x][y] += 40;
								}
							}
						} else if (super.get11(point).getX() == chessboard_size - 1
								&& super.get11(point).getY() == chessboard_size - 1) {
							if (x == chessboard_size - 2
									&& y == chessboard_size - 1
									&& status2[chessboard_size - 1][1] == em
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
								status3 = super.getNextStatus(status2,
										new MyPoint(chessboard_size - 1,
												chessboard_size - 2), self);
								if (super.illegal(status3, new MyPoint(
										chessboard_size - 1, 0), em)) {
									values[x][y] += 20;
									if (status3[1][0] == em
											&& super.illegal(status3,
													new MyPoint(0, 0), em))
										values[x][y] += 40;
								}
							} else if (x == chessboard_size - 1
									&& y == chessboard_size - 2
									&& status2[1][chessboard_size - 1] == em
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
								status3 = super.getNextStatus(status2,
										new MyPoint(chessboard_size - 2,
												chessboard_size - 1), self);
								if (super.illegal(status3, new MyPoint(0,
										chessboard_size - 1), em)) {
									values[x][y] += 20;
									if (status3[0][1] == em
											&& super.illegal(status3,
													new MyPoint(0, 0), em))
										values[x][y] += 40;
								}
							}
						}
					} else {
						if (super.get11(point).getX() == 0
								&& super.get11(point).getY() == 0) {
							if (status2[0][chessboard_size - 2] == em
									&& !super.illegal(status2,
											new MyPoint(0, 1), self)
									&& status2[0][chessboard_size - 1] == 0
									&& super.illegal(status2, new MyPoint(0,
											chessboard_size - 1), self)
									&& super.illegal(status2, new MyPoint(0,
											chessboard_size - 1), em)
									&& status2[0][2] != 0 && status2[0][3] != 0
									&& status2[0][chessboard_size - 4] != 0
									&& status2[0][chessboard_size - 3] != 0) {
								status3 = super.getNextStatus(status2,
										new MyPoint(0, 1), self);
								if (super.illegal(status3, new MyPoint(0,
										chessboard_size - 1), em)) {
									values[x][y] += 20;
									if (status3[chessboard_size - 2][chessboard_size - 1] == em
											&& super.illegal(
													status3,
													new MyPoint(
															chessboard_size - 1,
															chessboard_size - 1),
													em))
										values[x][y] += 40;
								}
							} else if (status2[chessboard_size - 2][0] == em
									&& !super.illegal(status2,
											new MyPoint(1, 0), self)
									&& status2[chessboard_size - 1][0] == 0
									&& super.illegal(status2, new MyPoint(
											chessboard_size - 1, 0), self)
									&& super.illegal(status2, new MyPoint(
											chessboard_size - 1, 0), em)
									&& status2[2][0] != 0 && status2[3][0] != 0
									&& status2[chessboard_size - 4][0] != 0
									&& status2[chessboard_size - 3][0] != 0) {
								status3 = super.getNextStatus(status2,
										new MyPoint(1, 0), self);
								if (super.illegal(status3, new MyPoint(
										chessboard_size - 1, 0), em)) {
									values[x][y] += 20;
									if (status3[chessboard_size - 1][chessboard_size - 2] == em
											&& super.illegal(
													status3,
													new MyPoint(
															chessboard_size - 1,
															chessboard_size - 1),
													em))
										values[x][y] += 40;
								}
							}
						} else if (super.get11(point).getX() == 0
								&& super.get11(point).getY() == chessboard_size - 1) {
							if (status2[0][1] == em
									&& !super.illegal(status2, new MyPoint(0,
											chessboard_size - 2), self)
									&& status2[0][0] == 0
									&& super.illegal(status2,
											new MyPoint(0, 0), self)
									&& super.illegal(status2,
											new MyPoint(0, 0), em)
									&& status2[0][chessboard_size - 3] != 0
									&& status2[0][chessboard_size - 4] != 0
									&& status2[0][3] != 0 && status2[0][2] != 0) {
								status3 = super.getNextStatus(status2,
										new MyPoint(0, chessboard_size - 2),
										self);
								if (super.illegal(status3, new MyPoint(0, 0),
										em)) {
									values[x][y] += 20;
									if (status3[chessboard_size - 2][0] == em
											&& super.illegal(
													status3,
													new MyPoint(
															chessboard_size - 1,
															0), em))
										values[x][y] += 40;
								}
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
								status3 = super.getNextStatus(status2,
										new MyPoint(1, chessboard_size - 1),
										self);
								if (super.illegal(status3, new MyPoint(
										chessboard_size - 1,
										chessboard_size - 1), em)) {
									values[x][y] += 20;
									if (status3[chessboard_size - 1][1] == em
											&& super.illegal(
													status3,
													new MyPoint(
															chessboard_size - 1,
															0), em))
										values[x][y] += 40;
								}
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
								status3 = super.getNextStatus(status2,
										new MyPoint(chessboard_size - 1, 1),
										self);
								if (super.illegal(status3, new MyPoint(
										chessboard_size - 1,
										chessboard_size - 1), em)) {
									values[x][y] += 20;
									if (status3[1][chessboard_size - 1] == em
											&& super.illegal(
													status3,
													new MyPoint(0,
															chessboard_size - 1),
													em))
										values[x][y] += 40;
								}
							} else if (status2[1][0] == em
									&& !super.illegal(status2, new MyPoint(
											chessboard_size - 2, 0), self)
									&& status2[0][0] == 0
									&& super.illegal(status2,
											new MyPoint(0, 0), self)
									&& super.illegal(status2,
											new MyPoint(0, 0), em)
									&& status2[chessboard_size - 3][0] != 0
									&& status2[chessboard_size - 4][0] != 0
									&& status2[3][0] != 0 && status2[2][0] != 0) {
								status3 = super.getNextStatus(status2,
										new MyPoint(chessboard_size - 2, 0),
										self);
								if (super.illegal(status3, new MyPoint(0, 0),
										em)) {
									values[x][y] += 20;
									if (status3[0][chessboard_size - 2] == em
											&& super.illegal(
													status3,
													new MyPoint(0,
															chessboard_size - 1),
													em))
										values[x][y] += 40;
								}
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
								status3 = super.getNextStatus(status2,
										new MyPoint(chessboard_size - 1,
												chessboard_size - 2), self);
								if (super.illegal(status3, new MyPoint(
										chessboard_size - 1, 0), em)) {
									values[x][y] += 20;
									if (status3[1][0] == em
											&& super.illegal(status3,
													new MyPoint(0, 0), em))
										values[x][y] += 40;
								}
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
								status3 = super.getNextStatus(status2,
										new MyPoint(chessboard_size - 2,
												chessboard_size - 1), self);
								if (super.illegal(status3, new MyPoint(0,
										chessboard_size - 1), em)) {
									values[x][y] += 20;
									if (status3[0][1] == em
											&& super.illegal(status3,
													new MyPoint(0, 0), em))
										values[x][y] += 40;
								}
							}
						}
					}
				}
				temp = false;
			}
			// ��4�����Ǿ�Ϊ���Ҳ��ڶ��Ǻ��ٽ����ǵ�λ�õĿ�λ�ĸ���������1ʱ����Ҫ�ȶԶ��ǵ���������жϲ��������ø����Ȩֵ
			else if (last_status[0][0] == 0
					&& last_status[0][chessboard_size - 1] == 0
					&& last_status[chessboard_size - 1][0] == 0
					&& last_status[chessboard_size - 1][0] == 0
					&& getCountOfLastPositionExceptNear11(last_status) <= 1) {
				if (judgeForTurn11(next_status, max_steps - 1) == 7) {
					if (super.is12(point) || super.is22(point))
						values[x][y] = 300;
					else
						values[x][y] = 330;
					temp = false;
				} else if (judgeForTurn11(next_status, max_steps - 1) == 6) {
					if (super.is12(point) || super.is22(point))
						values[x][y] = 240;
					else
						values[x][y] = 260;
					temp = false;
				} else if (judgeForTurn11(next_status, max_steps - 1) > 0) {
					if (super.is12(point) || super.is22(point))
						values[x][y] = 200;
					else
						values[x][y] = 220;
					temp = false;
				} else if (judgeForTurn11(next_status, max_steps - 1) <= -6
						&& (super.is12(point) || super.is22(point))) {
					values[x][y] -= 200;
					temp = false;
				} else if (judgeForTurn11(next_status, max_steps - 1) < 0
						&& (super.is12(point) || super.is22(point))) {
					values[x][y] -= 100;
					temp = false;
				}
			}
			if (!super.eat22(point, self) && temp) {
				if (super.is12(point)) {
					if (last_status[super.get11(point).getX()][super.get11(
							point).getY()] != 0
							&& last_status[super.getOppo11(point).getX()][super
									.getOppo11(point).getY()] == 0
							&& last_status[super.getOppo12(point).getX()][super
									.getOppo12(point).getY()] == em
							&& last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] != 0
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] != 0
							&& last_status[super.get14(point).getX()][super
									.get14(point).getY()] != 0
							&& last_status[super.get13(point).getX()][super
									.get13(point).getY()] != 0
							&& super.illegal(next_status,
									super.getOppo11(point), em))
						values[x][y] += 400;
					else if (last_status[super.get11(point).getX()][super
							.get11(point).getY()] == 0
							&& (last_status[super.get13(point).getX()][super
									.get13(point).getY()] == 0
									|| last_status[super.get14(point).getX()][super
											.get14(point).getY()] == 0
									|| last_status[super.getOppo14(point)
											.getX()][super.getOppo14(point)
											.getY()] == 0 || last_status[super
									.getOppo13(point).getX()][super.getOppo13(
									point).getY()] == 0))
						values[x][y] -= 50;
					else if (super.illegal(next_status, super.get11(point), em)
							&& last_status[super.getOppo11(point).getX()][super
									.getOppo11(point).getY()] == self
							&& last_status[super.getOppo12(point).getX()][super
									.getOppo12(point).getY()] != 0
							&& last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] != 0
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] != 0
							&& last_status[super.get14(point).getX()][super
									.get14(point).getY()] != 0
							&& last_status[super.get13(point).getX()][super
									.get13(point).getY()] != 0)
						values[x][y] += 400;
					else if (!super.illegal(last_status,
							super.getOppo11(point), em)
							&& super.illegal(next_status,
									super.getOppo11(point), em)
							&& super.illegal(next_status, super.get11(point),
									em))
						values[x][y] += 400;
					else if (last_status[super.getOppo11(point).getX()][super
							.getOppo11(point).getY()] == 0
							&& last_status[super.getOppo12(point).getX()][super
									.getOppo12(point).getY()] == 0
							&& last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == self
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] == em
							&& last_status[super.get14(point).getX()][super
									.get14(point).getY()] == em
							&& last_status[super.get13(point).getX()][super
									.get13(point).getY()] == em
							&& last_status[super.get11(point).getX()][super
									.get11(point).getY()] == 0)
						values[x][y] += 203;
					else if (last_status[super.getOppo11(point).getX()][super
							.getOppo11(point).getY()] == 0
							&& last_status[super.getOppo12(point).getX()][super
									.getOppo12(point).getY()] == 0
							&& last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == self
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] == self
							&& last_status[super.get14(point).getX()][super
									.get14(point).getY()] == em
							&& last_status[super.get13(point).getX()][super
									.get13(point).getY()] == em
							&& last_status[super.get11(point).getX()][super
									.get11(point).getY()] == 0)
						values[x][y] += 200;
					if (last_status[super.get11(point).getX()][super.get11(
							point).getY()] != 0
							&& last_status[super.get13(point).getX()][super
									.get13(point).getY()] != 0
							&& last_status[super.get14(point).getX()][super
									.get14(point).getY()] != 0
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] != 0
							&& last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] != 0
							&& last_status[super.getOppo12(point).getX()][super
									.getOppo12(point).getY()] != 0
							&& last_status[super.getOppo11(point).getX()][super
									.getOppo11(point).getY()] != 0) {
						if (values[x][y] < 100)
							values[x][y] = 100;
					}
				} else if (super.is22(point)) {
					if (last_status[super.get11(point).getX()][super.get11(
							point).getY()] == 0)
						values[x][y] -= 40;
				} else if (super.is13(point)) {
					boolean t = true;
					if (!super.illegal(last_status, super.getOppo11(point),
							self)) {
						int[][] status2 = super.getNextStatus(next_status,
								super.get12(point), em);
						if (!super.illegal(status2, super.getOppo11(point),
								self)
								|| !super.illegal(status2, super.get11(point),
										self)) {
							values[x][y] += 770;
							t = false;
						}
					}
					if (t) {
						if (super.toTurn11(next_status, self)
								+ super.insertTo11(next_status, self) > super
								.toTurn11(last_status, self)
								+ super.insertTo11(last_status, self)
								|| super.canTurn11(next_status, em) < super
										.canTurn11(last_status, em))
							values[x][y] += 700;
						else if ((last_status[0][0] == 0
								&& last_status[1][1] == em
								|| last_status[0][chessboard_size - 1] == 0
								&& last_status[1][chessboard_size - 2] == em
								|| last_status[chessboard_size - 1][0] == 0
								&& last_status[chessboard_size - 2][1] == em || last_status[chessboard_size - 1][chessboard_size - 1] == 0
								&& last_status[chessboard_size - 2][chessboard_size - 2] == em)
								&& judgeForTurn11(next_status, max_steps - 1) == 7)
							values[x][y] += 300;
						else if (super.getCountOfThisType(next_status, self) >= super
								.getCountOfThisType(next_status, em)
								&& cannotTurnExcept12And22(next_status))
							values[x][y] += 200;
						else if (last_status[super.getOppo11(point).getX()][super
								.getOppo11(point).getY()] == 0
								&& (last_status[super.getOppo12(point).getX()][super
										.getOppo12(point).getY()] == em || last_status[super
										.getOppo12(point).getX()][super
										.getOppo12(point).getY()] == self
										&& last_status[super.getOppo13(point)
												.getX()][super.getOppo13(point)
												.getY()] == self
										&& last_status[super.getOppo14(point)
												.getX()][super.getOppo14(point)
												.getY()] == self
										&& last_status[super.get14(point)
												.getX()][super.get14(point)
												.getY()] == self)
								&& last_status[super.get11(point).getX()][super
										.get11(point).getY()] != self)
							values[x][y] += 50;
						else if (last_status[super.get11(point).getX()][super
								.get11(point).getY()] == 0
								&& last_status[super.get12(point).getX()][super
										.get12(point).getY()] == 0
								&& last_status[super.getOppo11(point).getX()][super
										.getOppo11(point).getY()] == 0
								&& last_status[super.getOppo12(point).getX()][super
										.getOppo12(point).getY()] == 0) {
							if (!super.illegal(last_status, point, em)
									|| super.canTurn13(last_status, em) < super
											.canTurn13(next_status, em))
								values[x][y] += 20;
							if (last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == self
									&& last_status[super.getOppo14(point)
											.getX()][super.getOppo14(point)
											.getY()] == em
									&& last_status[super.get14(point).getX()][super
											.get14(point).getY()] == em
									|| last_status[super.getOppo13(point)
											.getX()][super.getOppo13(point)
											.getY()] == self
									&& last_status[super.getOppo14(point)
											.getX()][super.getOppo14(point)
											.getY()] == self
									&& last_status[super.get14(point).getX()][super
											.get14(point).getY()] != 0)
								values[x][y] += 30;
							else if (last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == 0
									&& super.illegal(next_status,
											super.getOppo13(point), em)
									&& last_status[super.getOppo14(point)
											.getX()][super.getOppo14(point)
											.getY()] == em
									&& last_status[super.get14(point).getX()][super
											.get14(point).getY()] == em
									|| last_status[super.getOppo13(point)
											.getX()][super.getOppo13(point)
											.getY()] == self
									&& last_status[super.getOppo14(point)
											.getX()][super.getOppo14(point)
											.getY()] == 0
									&& super.illegal(next_status,
											super.getOppo14(point), em)
									&& last_status[super.get14(point).getX()][super
											.get14(point).getY()] == em
									|| last_status[super.getOppo13(point)
											.getX()][super.getOppo13(point)
											.getY()] == self
									&& last_status[super.getOppo14(point)
											.getX()][super.getOppo14(point)
											.getY()] == em
									&& last_status[super.get14(point).getX()][super
											.get14(point).getY()] == 0
									&& super.illegal(next_status,
											super.get14(point), em))
								values[x][y] += 20;
							else if (last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == self
									&& last_status[super.getOppo14(point)
											.getX()][super.getOppo14(point)
											.getY()] == 0
									&& !super.illegal(next_status,
											super.getOppo14(point), em)
									&& last_status[super.get14(point).getX()][super
											.get14(point).getY()] == em
									|| last_status[super.getOppo13(point)
											.getX()][super.getOppo13(point)
											.getY()] == self
									&& last_status[super.getOppo14(point)
											.getX()][super.getOppo14(point)
											.getY()] == em
									&& last_status[super.get14(point).getX()][super
											.get14(point).getY()] == 0
									&& !super.illegal(next_status,
											super.get14(point), em))
								values[x][y] -= 90;
							else if (last_status[super.get14(point).getX()][super
									.get14(point).getY()] == em
									&& last_status[super.getOppo13(point)
											.getX()][super.getOppo13(point)
											.getY()] == 0
									&& !turn3Or4(next_status, point, em, true))
								values[x][y] += 10;
							if (last_status[super.get11(point).getX()][super
									.get11(point).getY()] != 0
									&& last_status[super.get12(point).getX()][super
											.get12(point).getY()] != 0
									&& last_status[super.get14(point).getX()][super
											.get14(point).getY()] != 0
									&& last_status[super.getOppo14(point)
											.getX()][super.getOppo14(point)
											.getY()] != 0
									&& last_status[super.getOppo13(point)
											.getX()][super.getOppo13(point)
											.getY()] != 0
									&& last_status[super.getOppo12(point)
											.getX()][super.getOppo12(point)
											.getY()] != 0
									&& last_status[super.getOppo11(point)
											.getX()][super.getOppo11(point)
											.getY()] != 0) {
								if (values[x][y] < 100)
									values[x][y] = 100;
							}
						}
					}
				} else if (super.is14(point)) {
					if (super.toTurn11(next_status, self)
							+ super.insertTo11(next_status, self) > super
							.toTurn11(last_status, self)
							+ super.insertTo11(last_status, self)
							|| super.canTurn11(next_status, em) < super
									.canTurn11(last_status, em))
						values[x][y] += 700;
					else if ((last_status[0][0] == 0 && last_status[1][1] == em
							|| last_status[0][chessboard_size - 1] == 0
							&& last_status[1][chessboard_size - 2] == em
							|| last_status[chessboard_size - 1][0] == 0
							&& last_status[chessboard_size - 2][1] == em || last_status[chessboard_size - 1][chessboard_size - 1] == 0
							&& last_status[chessboard_size - 2][chessboard_size - 2] == em)
							&& judgeForTurn11(next_status, max_steps - 1) == 7)
						values[x][y] += 300;
					else if (super.getCountOfThisType(next_status, self) >= super
							.getCountOfThisType(next_status, em)
							&& cannotTurnExcept12And22(next_status))
						values[x][y] += 200;
					else if (last_status[super.getOppo11(point).getX()][super
							.getOppo11(point).getY()] == 0
							&& last_status[super.getOppo12(point).getX()][super
									.getOppo12(point).getY()] == em
							&& last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == 0)
						values[x][y] += 40;
					else if (last_status[super.getOppo11(point).getX()][super
							.getOppo11(point).getY()] == 0
							&& last_status[super.getOppo12(point).getX()][super
									.getOppo12(point).getY()] == em
							&& last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == em
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] == em
							&& last_status[super.get13(point).getX()][super
									.get13(point).getY()] == self)
						values[x][y] -= 200;
					else if (last_status[super.get11(point).getX()][super
							.get11(point).getY()] == 0
							&& last_status[super.get12(point).getX()][super
									.get12(point).getY()] == 0
							&& last_status[super.getOppo11(point).getX()][super
									.getOppo11(point).getY()] == 0
							&& last_status[super.getOppo12(point).getX()][super
									.getOppo12(point).getY()] == 0) {
						if (super.canTurn13(next_status, em) < super.canTurn13(
								last_status, em))
							values[x][y] += 20;
						else if (emToTurn13(next_status) < emToTurn13(last_status))
							values[x][y] += 10;
						if (last_status[super.get13(point).getX()][super.get13(
								point).getY()] == self
								&& last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == self
								&& last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] != 0)
							values[x][y] += 120;
						else if (last_status[super.getOppo13(point).getX()][super
								.getOppo13(point).getY()] == self
								&& last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] == em) {
							if (super.illegal(next_status, super.get13(point),
									em)
									|| !super.illegal(last_status,
											super.get13(point), em)
									&& !super.illegal(next_status,
											super.get13(point), em))
								values[x][y] += 30;
							else
								values[x][y] += 1;
						} else if (last_status[super.get13(point).getX()][super
								.get13(point).getY()] == self
								&& last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == self
								&& last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] == 0) {
							if (nextStepCanTurnPosition(next_status,
									super.getOppo14(point)))
								values[x][y] += 100;
							else
								values[x][y] -= 10;
						} else if (last_status[super.get13(point).getX()][super
								.get13(point).getY()] == self
								|| last_status[super.get13(point).getX()][super
										.get13(point).getY()] == em
								&& last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == self
								&& last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] == self)
							values[x][y] += 20;
						else if (last_status[super.get13(point).getX()][super
								.get13(point).getY()] == em
								&& last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] == 0
								&& last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == em
								|| last_status[super.get13(point).getX()][super
										.get13(point).getY()] == em
								&& last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] == self
								&& last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == 0
								|| last_status[super.get13(point).getX()][super
										.get13(point).getY()] == 0
								&& last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] != 0
								&& last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == em)
							values[x][y] -= 90;
						else if (last_status[super.get13(point).getX()][super
								.get13(point).getY()] == em
								&& last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] != 0
								&& last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == em)
							values[x][y] += 110;
						else if (last_status[super.get13(point).getX()][super
								.get13(point).getY()] == em
								&& last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] == 0
								&& !turn3Or4(next_status, super.get13(point),
										self, false)
								|| last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] == em
								&& last_status[super.get13(point).getX()][super
										.get13(point).getY()] == 0
								|| last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == em
								&& last_status[super.get13(point).getX()][super
										.get13(point).getY()] == 0)
							values[x][y] -= 30;
						else if (last_status[super.get13(point).getX()][super
								.get13(point).getY()] == 0
								&& last_status[super.get23(point).getX()][super
										.get23(point).getY()] == em
								&& next_status[super.get23(point).getX()][super
										.get23(point).getY()] == self)
							values[x][y] -= 20;
						else if (last_status[super.get13(point).getX()][super
								.get13(point).getY()] == 0
								&& last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == 0
								&& last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] == 0) {
							if (super.illegal(next_status, super.get13(point),
									em)
									&& super.illegal(next_status,
											super.getOppo13(point), em))
								values[x][y] += 1;
							else if (!super.illegal(next_status,
									super.getOppo13(point), em))
								values[x][y] -= 5;
						}
					}
					if (last_status[super.get11(point).getX()][super.get11(
							point).getY()] != 0
							&& last_status[super.get12(point).getX()][super
									.get12(point).getY()] != 0
							&& last_status[super.get13(point).getX()][super
									.get13(point).getY()] != 0
							&& last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] != 0
							&& last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] != 0
							&& last_status[super.getOppo12(point).getX()][super
									.getOppo12(point).getY()] != 0
							&& last_status[super.getOppo11(point).getX()][super
									.getOppo11(point).getY()] != 0) {
						if (values[x][y] < 100)
							values[x][y] = 100;
					}
				} else if (super.is23(point)) {
					if (super.canTurn11(next_status, em) < super.canTurn11(
							last_status, em))
						values[x][y] += 400;
					else if ((last_status[0][0] == 0 && last_status[1][1] == em
							|| last_status[0][chessboard_size - 1] == 0
							&& last_status[1][chessboard_size - 2] == em
							|| last_status[chessboard_size - 1][0] == 0
							&& last_status[chessboard_size - 2][1] == em || last_status[chessboard_size - 1][chessboard_size - 1] == 0
							&& last_status[chessboard_size - 2][chessboard_size - 2] == em)
							&& judgeForTurn11(next_status, max_steps - 1) == 7)
						values[x][y] += 300;
					else if (super.getCountOfThisType(next_status, self) >= super
							.getCountOfThisType(next_status, em)
							&& cannotTurnExcept12And22(next_status))
						values[x][y] += 200;
					else if (last_status[super.get13(point).getX()][super
							.get13(point).getY()] == 0
							&& last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == 0
							&& last_status[super.getOppo23(point).getX()][super
									.getOppo23(point).getY()] == self
							&& last_status[super.getOppo24(point).getX()][super
									.getOppo24(point).getY()] == em
							&& last_status[super.get24(point).getX()][super
									.get24(point).getY()] == em) {
						if (!super.illegal(next_status, super.get13(point), em)
								|| !super.illegal(next_status,
										super.getOppo13(point), em))
							values[x][y] -= 70;
						else
							values[x][y] -= 30;
					} else if (last_status[super.get13(point).getX()][super
							.get13(point).getY()] == 0
							&& (last_status[super.getOppo24(point).getX()][super
									.getOppo24(point).getY()] == self
									&& last_status[super.get24(point).getX()][super
											.get24(point).getY()] == em || last_status[super
									.getOppo23(point).getX()][super.getOppo23(
									point).getY()] == self
									&& last_status[super.getOppo24(point)
											.getX()][super.getOppo24(point)
											.getY()] == em
									&& last_status[super.get24(point).getX()][super
											.get24(point).getY()] == em)) {
						if (!super.illegal(next_status, super.get13(point), em))
							values[x][y] -= 60;
						else {
							values[x][y] -= 2;
							if (judgeForTurn13(next_status, 2 - 1) < 0)
								values[x][y] -= 3;
							else if (judgeForTurn13(next_status, 2 - 1) == 2
									|| super.toTurn13(next_status, self) > super
											.toTurn13(last_status, self)
									|| super.canTurn13(next_status, em) < super
											.canTurn13(last_status, em))
								values[x][y] += 20;
							else if (emToTurn13(next_status) < emToTurn13(last_status))
								values[x][y] += 10;
							if (emToTurn13(next_status) > super.toTurn13(
									last_status, em))
								values[x][y] -= 3;
						}
					} else if (last_status[super.getOppo13(point).getX()][super
							.getOppo13(point).getY()] == 0
							&& last_status[super.getOppo23(point).getX()][super
									.getOppo23(point).getY()] == self
							&& last_status[super.getOppo24(point).getX()][super
									.getOppo24(point).getY()] == em
							&& last_status[super.get24(point).getX()][super
									.get24(point).getY()] == em) {
						if (!super.illegal(next_status, super.getOppo13(point),
								em))
							values[x][y] -= 60;
						else {
							values[x][y] -= 2;
							if (judgeForTurn13(next_status, 2 - 1) < 0)
								values[x][y] -= 3;
							else if (judgeForTurn13(next_status, 2 - 1) == 2
									|| super.toTurn13(next_status, self) > super
											.toTurn13(last_status, self)
									|| super.canTurn13(next_status, em) < super
											.canTurn13(last_status, em))
								values[x][y] += 20;
							else if (emToTurn13(next_status) < emToTurn13(last_status))
								values[x][y] += 10;
							if (emToTurn13(next_status) > super.toTurn13(
									last_status, em))
								values[x][y] -= 3;
						}
					} else if (super.eat24(point, self)) {
						values[x][y] -= 30;
						if (judgeForTurn13(next_status, 2 - 1) < 0)
							values[x][y] -= 3;
						else if (judgeForTurn13(next_status, 2 - 1) == 2
								|| super.toTurn13(next_status, self) > super
										.toTurn13(last_status, self)
								|| super.canTurn13(next_status, em) < super
										.canTurn13(last_status, em))
							values[x][y] += 20;
						else if (emToTurn13(next_status) < emToTurn13(last_status))
							values[x][y] += 10;
						if (emToTurn13(next_status) > super.toTurn13(
								last_status, em))
							values[x][y] -= 3;
					} else if (!super.illegal(next_status, super.get13(point),
							em)) {
						if (!turn3Or4(next_status, super.getOppo13(point),
								self, false)) {
							values[x][y] -= 50;
							if (last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == em)
								values[x][y] -= 3;
							else if (last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == 0)
								values[x][y] -= 1;
						} else
							values[x][y] -= 2;
						if (super.canTurn13(last_status, self) == 0
								&& minTurn13(next_status) > 0
								|| super.toTurn13(next_status, self) > super
										.toTurn13(last_status, self))
							values[x][y] += 40;
					} else if (last_status[super.get33(point).getX()][super
							.get33(point).getY()] == 0) {
						values[x][y] -= 4;
						if (judgeForTurn13(next_status, 2 - 1) < 0)
							values[x][y] -= 3;
						else if (judgeForTurn13(next_status, 2 - 1) == 2
								|| super.toTurn13(next_status, self) > super
										.toTurn13(last_status, self)
								|| super.canTurn13(next_status, em) < super
										.canTurn13(last_status, em))
							values[x][y] += 20;
						else if (emToTurn13(next_status) < emToTurn13(last_status))
							values[x][y] += 10;
						if (emToTurn13(next_status) > super.toTurn13(
								last_status, em))
							values[x][y] -= 3;
						if (last_status[super.getOppo13(point).getX()][super
								.getOppo13(point).getY()] == em
								&& last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] == em
								&& last_status[super.get14(point).getX()][super
										.get14(point).getY()] == em
								&& last_status[super.get13(point).getX()][super
										.get13(point).getY()] == 0)
							values[x][y] -= 2;
					} else {
						boolean t = true;
						if (turn3Or4(next_status, super.getOppo13(point), self,
								false))
							values[x][y] += 2;
						else if (judgeForTurn13(next_status, 2 - 1) < 0
								|| last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == em
								|| last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == 0
								&& (last_status[super.getOppo23(point).getX()][super
										.getOppo23(point).getY()] == self || last_status[super
										.getOppo24(point).getX()][super
										.getOppo24(point).getY()] == self)) {
							values[x][y] -= 2;
							t = false;
						} else {
							int[] line = new int[chessboard_size];
							if (x == 1)
								for (int i = 0; i < chessboard_size; i++)
									line[i] = next_status[i][y];
							if (x == chessboard_size - 2)
								for (int i = 0; i < chessboard_size; i++)
									line[i] = next_status[chessboard_size - 1
											- i][y];
							if (y == 1)
								for (int i = 0; i < chessboard_size; i++)
									line[i] = next_status[x][i];
							if (y == chessboard_size - 2)
								for (int i = 0; i < chessboard_size; i++)
									line[i] = next_status[x][chessboard_size
											- 1 - i];
							if (line[2] == self && line[3] == 0
									&& line[chessboard_size - 4] == self
									&& line[chessboard_size - 3] == self
									|| line[2] == self && line[3] == self
									&& line[chessboard_size - 4] == 0
									&& line[chessboard_size - 3] == self) {
								values[x][y] -= 2;
								t = false;
							}
						}
						if (t) {
							if (super.canTurn13(last_status, self) > 0
									&& minTurn13(next_status) >= super
											.canTurn13(last_status, self))
								values[x][y] += 90;
						}
						if (judgeForTurn13(next_status, 2 - 1) == 2
								|| super.toTurn13(next_status, self) > super
										.toTurn13(last_status, self)
								|| super.canTurn13(next_status, em) < super
										.canTurn13(last_status, em))
							values[x][y] += 20;
						else if (emToTurn13(next_status) < emToTurn13(last_status))
							values[x][y] += 10;
						if (last_status[super.get13(point).getX()][super.get13(
								point).getY()] == self)
							values[x][y] += 1;
						else if (last_status[super.get13(point).getX()][super
								.get13(point).getY()] == em
								&& last_status[super.get33(point).getX()][super
										.get33(point).getY()] == em
								&& super.illegal(next_status,
										super.get14(point), em))
							values[x][y] += 2;
						if (last_status[super.get33(point).getX()][super.get33(
								point).getY()] == em)
							values[x][y] += 3;
						if (emToTurn13(next_status) > super.toTurn13(
								last_status, em))
							values[x][y] -= 3;
						if (last_status[super.getOppo13(point).getX()][super
								.getOppo13(point).getY()] == em
								&& last_status[super.getOppo14(point).getX()][super
										.getOppo14(point).getY()] == em
								&& last_status[super.get14(point).getX()][super
										.get14(point).getY()] == em
								&& last_status[super.get13(point).getX()][super
										.get13(point).getY()] == 0)
							values[x][y] -= 2;
					}
				} else if (super.is24(point)) {
					if (super.canTurn11(next_status, em) < super.canTurn11(
							last_status, em))
						values[x][y] += 400;
					else if ((last_status[0][0] == 0 && last_status[1][1] == em
							|| last_status[0][chessboard_size - 1] == 0
							&& last_status[1][chessboard_size - 2] == em
							|| last_status[chessboard_size - 1][0] == 0
							&& last_status[chessboard_size - 2][1] == em || last_status[chessboard_size - 1][chessboard_size - 1] == 0
							&& last_status[chessboard_size - 2][chessboard_size - 2] == em)
							&& judgeForTurn11(next_status, max_steps - 1) == 7)
						values[x][y] += 300;
					else if (super.getCountOfThisType(next_status, self) >= super
							.getCountOfThisType(next_status, em)
							&& cannotTurnExcept12And22(next_status))
						values[x][y] += 200;
					else {
						if (last_status[super.get13(point).getX()][super.get13(
								point).getY()] == 0)
							values[x][y] -= 1;
						if (last_status[super.getOppo13(point).getX()][super
								.getOppo13(point).getY()] == 0
								&& last_status[super.getOppo23(point).getX()][super
										.getOppo23(point).getY()] == self
								&& last_status[super.getOppo24(point).getX()][super
										.getOppo24(point).getY()] == em) {
							if (!super.illegal(next_status, super.get13(point),
									em)
									|| !super.illegal(next_status,
											super.getOppo13(point), em))
								values[x][y] -= 70;
							else {
								values[x][y] -= 2;
								if (judgeForTurn13(next_status, 2 - 1) < 0)
									values[x][y] -= 3;
								else if (judgeForTurn13(next_status, 2 - 1) == 2
										|| super.toTurn13(next_status, self) > super
												.toTurn13(last_status, self)
										|| super.canTurn13(next_status, em) < super
												.canTurn13(last_status, em))
									values[x][y] += 20;
								else if (emToTurn13(next_status) < emToTurn13(last_status))
									values[x][y] += 10;
								if (emToTurn13(next_status) > super.toTurn13(
										last_status, em))
									values[x][y] -= 3;
							}
						} else if (super.eat24(point, self)) {
							values[x][y] -= 30;
							if (judgeForTurn13(next_status, 2 - 1) < 0)
								values[x][y] -= 3;
							else if (judgeForTurn13(next_status, 2 - 1) == 2
									|| super.toTurn13(next_status, self) > super
											.toTurn13(last_status, self)
									|| super.canTurn13(next_status, em) < super
											.canTurn13(last_status, em))
								values[x][y] += 20;
							else if (emToTurn13(next_status) < emToTurn13(last_status))
								values[x][y] += 10;
							if (emToTurn13(next_status) > super.toTurn13(
									last_status, em))
								values[x][y] -= 3;
						} else if (!super.illegal(next_status,
								super.get13(point), em)) {
							if (!turn3Or4(next_status, super.getOppo13(point),
									self, false)) {
								values[x][y] -= 40;
								if (last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == em)
									values[x][y] -= 3;
								else if (last_status[super.getOppo13(point)
										.getX()][super.getOppo13(point).getY()] == 0)
									values[x][y] -= 1;
							} else
								values[x][y] -= 1;
							if (super.canTurn13(last_status, self) == 0
									&& minTurn13(next_status) > 0)
								values[x][y] += 39;
						} else if (last_status[super.get13(point).getX()][super
								.get13(point).getY()] == self
								&& !(last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == em && last_status[super
										.getOppo14(point).getX()][super
										.getOppo14(point).getY()] == em)) {
							values[x][y] += 2;
							if (last_status[super.getOppo14(point).getX()][super
									.getOppo14(point).getY()] == em
									&& last_status[super.getOppo13(point)
											.getX()][super.getOppo13(point)
											.getY()] == 0
									&& super.illegal(next_status,
											super.getOppo13(point), em)
									|| last_status[super.getOppo13(point)
											.getX()][super.getOppo13(point)
											.getY()] == self
									&& last_status[super.getOppo24(point)
											.getX()][super.getOppo24(point)
											.getY()] != 0)
								values[x][y] += 10;
							if (last_status[super.get33(point).getX()][super
									.get33(point).getY()] == em
									&& next_status[super.get33(point).getX()][super
											.get33(point).getY()] == self)
								values[x][y] += 3;
							if (judgeForTurn13(next_status, 2 - 1) < 0
									|| emToTurn13(next_status) > super
											.toTurn13(last_status, em))
								values[x][y] -= 3;
							if (super.canTurn13(last_status, self) > 0
									&& minTurn13(next_status) >= super
											.canTurn13(last_status, self))
								values[x][y] += 90;
							if (judgeForTurn13(next_status, 2 - 1) == 2
									|| super.toTurn13(next_status, self) > super
											.toTurn13(last_status, self)
									|| super.canTurn13(next_status, em) < super
											.canTurn13(last_status, em))
								values[x][y] += 20;
							else if (emToTurn13(next_status) < emToTurn13(last_status))
								values[x][y] += 10;
						} else if (!super.illegal(next_status,
								super.get14(point), em)) {
							if (judgeForTurn13(next_status, 2 - 1) < 0)
								values[x][y] -= 3;
							else if (next_status[super.get33(point).getX()][super
									.get33(point).getY()] == em
									&& next_status[super.getOppo34(point)
											.getX()][super.getOppo34(point)
											.getY()] == 0
									|| next_status[super.getOppo34(point)
											.getX()][super.getOppo34(point)
											.getY()] == em
									&& next_status[super.get33(point).getX()][super
											.get33(point).getY()] == 0)
								values[x][y] -= 2;
							else if (turn3Or4(next_status,
									super.getOppo13(point), self, false))
								values[x][y] += 1;
							int[][] status2 = super.getNextStatus(next_status,
									super.get14(point), em);
							if (super
									.illegal(status2, super.get13(point), self)
									&& super.illegal(status2,
											super.getOppo13(point), self))
								values[x][y] -= 10;
							else {
								int[][] status3 = super.getNextStatus(status2,
										super.get13(point), em);
								if (turn3Or4(status3, super.get13(point), em,
										true))
									values[x][y] -= 1;
								else {
									if (last_status[super.get33(point).getX()][super
											.get33(point).getY()] == em
											&& next_status[super.get33(point)
													.getX()][super.get33(point)
													.getY()] == self)
										values[x][y] += 2;
									if (super.canTurn13(last_status, self) > 0
											&& minTurn13(next_status) >= super
													.canTurn13(last_status,
															self))
										values[x][y] += 90;
									if (judgeForTurn13(next_status, 2 - 1) == 2
											|| super.toTurn13(next_status, self) > super
													.toTurn13(last_status, self)
											|| super.canTurn13(next_status, em) < super
													.canTurn13(last_status, em))
										values[x][y] += 20;
									else if (emToTurn13(next_status) < emToTurn13(last_status))
										values[x][y] += 10;
								}
							}
							if (last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == em
									|| last_status[super.getOppo13(point)
											.getX()][super.getOppo13(point)
											.getY()] == 0
									&& last_status[super.getOppo23(point)
											.getX()][super.getOppo23(point)
											.getY()] == self)
								values[x][y] -= 2;
							if (emToTurn13(next_status) > super.toTurn13(
									last_status, em))
								values[x][y] -= 3;
						} else if (last_status[super.get33(point).getX()][super
								.get33(point).getY()] == em
								&& next_status[super.get33(point).getX()][super
										.get33(point).getY()] == self) {
							if (judgeForTurn13(next_status, 2 - 1) < 0)
								values[x][y] -= 3;
							else if (super.illegal(next_status,
									super.getOppo14(point), em)) {
								values[x][y] += 5;
								if (turn3Or4(next_status,
										super.getOppo13(point), self, false))
									values[x][y] += 1;
								if (super.canTurn13(last_status, self) > 0
										&& minTurn13(next_status) >= super
												.canTurn13(last_status, self))
									values[x][y] += 90;
								if (judgeForTurn13(next_status, 2 - 1) == 2
										|| super.toTurn13(next_status, self) > super
												.toTurn13(last_status, self)
										|| super.canTurn13(next_status, em) < super
												.canTurn13(last_status, em))
									values[x][y] += 20;
								else if (emToTurn13(next_status) < emToTurn13(last_status))
									values[x][y] += 10;
							} else {
								int[][] status2 = super
										.getNextStatus(next_status,
												super.getOppo14(point), em);
								if (super.illegal(status2, super.get13(point),
										self))
									values[x][y] -= 3;
								else {
									values[x][y] += 5;
									if (super.canTurn13(last_status, self) > 0
											&& minTurn13(next_status) >= super
													.canTurn13(last_status,
															self))
										values[x][y] += 90;
									if (judgeForTurn13(next_status, 2 - 1) == 2
											|| super.toTurn13(next_status, self) > super
													.toTurn13(last_status, self)
											|| super.canTurn13(next_status, em) < super
													.canTurn13(last_status, em))
										values[x][y] += 20;
									else if (emToTurn13(next_status) < emToTurn13(last_status))
										values[x][y] += 10;
								}
							}
							if (emToTurn13(next_status) > super.toTurn13(
									last_status, em))
								values[x][y] -= 3;
							if (last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == em
									&& last_status[super.getOppo14(point)
											.getX()][super.getOppo14(point)
											.getY()] == em
									&& last_status[super.get14(point).getX()][super
											.get14(point).getY()] == em
									&& last_status[super.get13(point).getX()][super
											.get13(point).getY()] == 0)
								values[x][y] -= 2;
						} else if (last_status[super.get34(point).getX()][super
								.get34(point).getY()] == 0) {
							values[x][y] -= 1;
							if (judgeForTurn13(next_status, 2 - 1) < 0)
								values[x][y] -= 3;
							boolean t = true;
							if (!super.illegal(next_status,
									super.getOppo24(point), em)) {
								int[][] status2 = super
										.getNextStatus(next_status,
												super.getOppo24(point), em);
								if (super.illegal(status2,
										super.getOppo13(point), self)
										&& super.illegal(status2,
												super.getOppo14(point), self)) {
									values[x][y] -= 3;
									t = false;
								}
							}
							if (t) {
								if (judgeForTurn13(next_status, 2 - 1) == 2
										|| super.toTurn13(next_status, self) > super
												.toTurn13(last_status, self)
										|| super.canTurn13(next_status, em) < super
												.canTurn13(last_status, em))
									values[x][y] += 20;
								else if (emToTurn13(next_status) < emToTurn13(last_status))
									values[x][y] += 10;
							}
							if (emToTurn13(next_status) > super.toTurn13(
									last_status, em))
								values[x][y] -= 3;
							if (last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == em
									&& last_status[super.getOppo14(point)
											.getX()][super.getOppo14(point)
											.getY()] == em
									&& last_status[super.get14(point).getX()][super
											.get14(point).getY()] == em
									&& last_status[super.get13(point).getX()][super
											.get13(point).getY()] == 0)
								values[x][y] -= 2;
						} else if (super.toTurn13(next_status, self) <= super
								.toTurn13(last_status, self)
								&& (next_status[super.get33(point).getX()][super
										.get33(point).getY()] == em
										&& next_status[super.getOppo34(point)
												.getX()][super.getOppo34(point)
												.getY()] == 0 || next_status[super
										.get33(point).getX()][super
										.get33(point).getY()] == 0
										&& next_status[super.getOppo34(point)
												.getX()][super.getOppo34(point)
												.getY()] == em)) {
							values[x][y] -= 2;
							if (emToTurn13(next_status) > super.toTurn13(
									last_status, em))
								values[x][y] -= 3;
							if (last_status[super.getOppo13(point).getX()][super
									.getOppo13(point).getY()] == em
									&& last_status[super.getOppo14(point)
											.getX()][super.getOppo14(point)
											.getY()] == em
									&& last_status[super.get14(point).getX()][super
											.get14(point).getY()] == em
									&& last_status[super.get13(point).getX()][super
											.get13(point).getY()] == 0)
								values[x][y] -= 2;
						} else {
							boolean t = true;
							if (turn3Or4(next_status, super.getOppo13(point),
									self, false))
								values[x][y] += 2;
							else if (judgeForTurn13(next_status, 2 - 1) < 0) {
								values[x][y] -= 3;
								t = false;
							} else if (last_status[super.getOppo13(point)
									.getX()][super.getOppo13(point).getY()] == em
									|| last_status[super.getOppo13(point)
											.getX()][super.getOppo13(point)
											.getY()] == 0
									&& last_status[super.getOppo23(point)
											.getX()][super.getOppo23(point)
											.getY()] == self) {
								values[x][y] -= 1;
								if (last_status[super.getOppo13(point).getX()][super
										.getOppo13(point).getY()] == em
										&& last_status[super.getOppo14(point)
												.getX()][super.getOppo14(point)
												.getY()] == em
										&& last_status[super.get14(point)
												.getX()][super.get14(point)
												.getY()] == em
										&& last_status[super.get13(point)
												.getX()][super.get13(point)
												.getY()] == 0)
									values[x][y] -= 2;
								t = false;
							} else {
								int[][] status2 = super
										.getNextStatus(next_status,
												super.getOppo24(point), em);
								if (super.illegal(status2,
										super.getOppo13(point), self)
										&& super.illegal(status2,
												super.getOppo14(point), self)) {
									values[x][y] -= 3;
									t = false;
								}
							}
							if (t) {
								if (super.canTurn13(last_status, self) > 0
										&& minTurn13(next_status) >= super
												.canTurn13(last_status, self))
									values[x][y] += 90;
							}
							if (judgeForTurn13(next_status, 2 - 1) == 2
									|| super.toTurn13(next_status, self) > super
											.toTurn13(last_status, self)
									|| super.canTurn13(next_status, em) < super
											.canTurn13(last_status, em))
								values[x][y] += 20;
							else if (emToTurn13(next_status) < emToTurn13(last_status))
								values[x][y] += 10;
							if (emToTurn13(next_status) > super.toTurn13(
									last_status, em))
								values[x][y] -= 3;
						}
					}
				} else if (super.is33(point)) {
					if (super.canTurn11(next_status, em) < super.canTurn11(
							last_status, em))
						values[x][y] += 400;
					else if ((last_status[0][0] == 0 && last_status[1][1] == em
							|| last_status[0][chessboard_size - 1] == 0
							&& last_status[1][chessboard_size - 2] == em
							|| last_status[chessboard_size - 1][0] == 0
							&& last_status[chessboard_size - 2][1] == em || last_status[chessboard_size - 1][chessboard_size - 1] == 0
							&& last_status[chessboard_size - 2][chessboard_size - 2] == em)
							&& judgeForTurn11(next_status, max_steps - 1) == 7)
						values[x][y] += 300;
					else if (super.getCountOfThisType(next_status, self) >= super
							.getCountOfThisType(next_status, em)
							&& cannotTurnExcept12And22(next_status))
						values[x][y] += 200;
					else if (super.eat24(point, self)
							|| super.canTurn13(next_status, em) > super
									.canTurn13(last_status, em))
						values[x][y] -= 30;
					else if (judgeForTurn13(next_status, 2 - 1) == 2
							|| super.toTurn13(next_status, self) > super
									.toTurn13(last_status, self)
							|| super.canTurn13(next_status, em) < super
									.canTurn13(last_status, em)) {
						values[x][y] += 40;
						if (super.canTurn13(last_status, self) > 0
								&& minTurn13(next_status) >= super.canTurn13(
										last_status, self))
							values[x][y] += 70;
					} else if (emToTurn13(next_status) < emToTurn13(last_status)) {
						values[x][y] += 20;
						if (super.canTurn13(last_status, self) > 0
								&& minTurn13(next_status) >= super.canTurn13(
										last_status, self))
							values[x][y] += 90;
					} else {
						if (judgeForTurn13(next_status, 2 - 1) < 0)
							values[x][y] -= 3;
						else if (super.canTurn13(last_status, self) > 0
								&& minTurn13(next_status) >= super.canTurn13(
										last_status, self))
							values[x][y] += 90;
						if (emToTurn13(next_status) > super.toTurn13(
								last_status, em))
							values[x][y] -= 3;
						if (x == 2 && y == 2) {
							if (last_status[2][chessboard_size - 3] == self
									&& last_status[2][chessboard_size - 4] != 0
									&& last_status[2][3] != 0
									&& last_status[chessboard_size - 3][2] == self
									&& last_status[chessboard_size - 4][2] != 0
									&& last_status[3][2] != 0
									|| last_status[2][chessboard_size - 3] == self
									&& last_status[2][chessboard_size - 4] != 0
									&& last_status[2][3] != 0
									&& last_status[3][2] == 0
									|| last_status[chessboard_size - 3][2] == self
									&& last_status[chessboard_size - 4][2] != 0
									&& last_status[3][2] != 0
									&& last_status[2][3] == 0)
								values[x][y] += 3;
							else if (last_status[2][3] == 0
									&& last_status[3][2] == 0)
								values[x][y] += 1;
						} else if (x == 2 && y == chessboard_size - 3) {
							if (last_status[2][2] == self
									&& last_status[2][3] != 0
									&& last_status[2][chessboard_size - 4] != 0
									&& last_status[chessboard_size - 3][chessboard_size - 3] == self
									&& last_status[chessboard_size - 4][chessboard_size - 3] != 0
									&& last_status[3][chessboard_size - 3] != 0
									|| last_status[2][2] == self
									&& last_status[2][3] != 0
									&& last_status[2][chessboard_size - 4] != 0
									&& last_status[3][chessboard_size - 3] == 0
									|| last_status[chessboard_size - 3][chessboard_size - 3] == self
									&& last_status[chessboard_size - 4][chessboard_size - 3] != 0
									&& last_status[3][chessboard_size - 3] != 0
									&& last_status[2][chessboard_size - 4] == 0)
								values[x][y] += 3;
							else if (last_status[2][chessboard_size - 4] == 0
									&& last_status[3][chessboard_size - 3] == 0)
								values[x][y] += 1;
						} else if (x == chessboard_size - 3 && y == 2) {
							if (last_status[chessboard_size - 3][chessboard_size - 3] == self
									&& last_status[chessboard_size - 3][chessboard_size - 4] != 0
									&& last_status[chessboard_size - 3][3] != 0
									&& last_status[2][2] == self
									&& last_status[3][2] != 0
									&& last_status[chessboard_size - 4][2] != 0
									|| last_status[chessboard_size - 3][chessboard_size - 3] == self
									&& last_status[chessboard_size - 3][chessboard_size - 4] != 0
									&& last_status[chessboard_size - 3][3] != 0
									&& last_status[chessboard_size - 4][2] == 0
									|| last_status[2][2] == self
									&& last_status[3][2] != 0
									&& last_status[chessboard_size - 4][2] != 0
									&& last_status[chessboard_size - 3][3] == 0)
								values[x][y] += 3;
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
									&& last_status[chessboard_size - 4][chessboard_size - 3] != 0
									|| last_status[chessboard_size - 3][2] == self
									&& last_status[chessboard_size - 3][3] != 0
									&& last_status[chessboard_size - 3][chessboard_size - 4] != 0
									&& last_status[chessboard_size - 4][chessboard_size - 3] == 0
									|| last_status[2][chessboard_size - 3] == self
									&& last_status[3][chessboard_size - 3] != 0
									&& last_status[chessboard_size - 4][chessboard_size - 3] != 0
									&& last_status[chessboard_size - 3][chessboard_size - 4] == 0)
								values[x][y] += 3;
							else if (last_status[chessboard_size - 3][chessboard_size - 4] == 0
									&& last_status[chessboard_size - 4][chessboard_size - 3] == 0)
								values[x][y] += 1;
						}
					}
				} else { // 3,4λ
					if (super.canTurn11(next_status, em) < super.canTurn11(
							last_status, em))
						values[x][y] += 400;
					else if ((last_status[0][0] == 0 && last_status[1][1] == em
							|| last_status[0][chessboard_size - 1] == 0
							&& last_status[1][chessboard_size - 2] == em
							|| last_status[chessboard_size - 1][0] == 0
							&& last_status[chessboard_size - 2][1] == em || last_status[chessboard_size - 1][chessboard_size - 1] == 0
							&& last_status[chessboard_size - 2][chessboard_size - 2] == em)
							&& judgeForTurn11(next_status, max_steps - 1) == 7)
						values[x][y] += 300;
					else if (super.getCountOfThisType(next_status, self) >= super
							.getCountOfThisType(next_status, em)
							&& cannotTurnExcept12And22(next_status))
						values[x][y] += 200;
					else if (super.eat24(point, self)
							|| super.canTurn13(next_status, em) > super
									.canTurn13(last_status, em))
						values[x][y] -= 30;
					else if (judgeForTurn13(next_status, 2 - 1) == 2
							|| super.toTurn13(next_status, self) > super
									.toTurn13(last_status, self)
							|| super.canTurn13(next_status, em) < super
									.canTurn13(last_status, em)) {
						values[x][y] += 20;
						if (super.canTurn13(last_status, self) > 0
								&& minTurn13(next_status) >= super.canTurn13(
										last_status, self))
							values[x][y] += 90;
					} else if (emToTurn13(next_status) < emToTurn13(last_status)) {
						values[x][y] += 10;
						if (super.canTurn13(last_status, self) > 0
								&& minTurn13(next_status) >= super.canTurn13(
										last_status, self))
							values[x][y] += 90;
					}
					boolean t = true, t1 = true, t2 = true;
					if (judgeForTurn13(next_status, 2 - 1) < 0
							|| emToTurn13(next_status) > super.toTurn13(
									last_status, em)) {
						values[x][y] -= 3;
						t1 = false;
					}
					int[] line = new int[chessboard_size];
					if (x == 3)
						for (int i = 0; i < chessboard_size; i++)
							line[i] = last_status[i][y];
					if (x == chessboard_size - 4)
						for (int i = 0; i < chessboard_size; i++)
							line[i] = last_status[chessboard_size - 1 - i][y];
					if (y == 3)
						for (int i = 0; i < chessboard_size; i++)
							line[i] = last_status[x][i];
					if (y == chessboard_size - 4)
						for (int i = 0; i < chessboard_size; i++)
							line[i] = last_status[x][chessboard_size - 1 - i];
					if (line[0] == self && line[1] != 0 && line[2] != 0
							|| line[chessboard_size - 1] == self
							&& line[chessboard_size - 2] != 0
							&& line[chessboard_size - 3] != 0
							&& line[chessboard_size - 4] != 0) {
						values[x][y] += 5;
						t = false;
					} else {
						if (!super.illegal(next_status, super.get24(point), em)) {
							int[][] status2 = super.getNextStatus(next_status,
									super.get24(point), em);
							if (super
									.illegal(status2, super.get13(point), self)
									&& super.illegal(status2,
											super.get14(point), self)) {
								values[x][y] -= 7;
								if (line[0] == em
										&& line[1] == self
										&& line[2] != 0
										&& line[chessboard_size - 4] == 0
										|| line[chessboard_size - 1] == em
										&& line[chessboard_size - 2] == self
										&& line[chessboard_size - 3] != 0
										&& line[chessboard_size - 4] != 0
										&& line[2] == 0
										|| line[chessboard_size - 3] == em
										&& line[chessboard_size - 4] != 0
										&& !super.illegal(next_status,
												super.get33(point), em))
									values[x][y] -= 2;
								else if (line[2] == em
										&& line[chessboard_size - 4] == 0
										&& line[chessboard_size - 3] != self
										|| line[1] == 0 && line[2] == self
										&& line[chessboard_size - 4] == em
										&& line[chessboard_size - 3] != self
										|| line[chessboard_size - 2] == 0
										&& line[chessboard_size - 3] == self
										&& line[chessboard_size - 4] == em
										&& line[2] == em && line[1] != self)
									values[x][y] -= 1;
								t = false;
								t1 = false;
								t2 = false;
							}
						}
						if (t2) {
							if (line[chessboard_size - 1] == 0
									&& line[chessboard_size - 2] != em
									&& line[chessboard_size - 3] == self
									&& line[chessboard_size - 4] == em
									&& line[2] != em || line[0] == 0
									&& line[1] != em && line[2] == self
									&& line[chessboard_size - 4] == 0
									&& line[chessboard_size - 3] == em
									&& line[chessboard_size - 2] != self) {
								values[x][y] += 3;
								if (line[2] == self
										&& line[chessboard_size - 3] == self
										&& line[chessboard_size - 4] == em
										|| last_status[super.get13(point)
												.getX()][super.get13(point)
												.getY()] == self
										&& last_status[super.getOppo13(point)
												.getX()][super.getOppo13(point)
												.getY()] == self)
									values[x][y] += 1;
								t = false;
							} else if (!(last_status[super.getOppo13(
									super.get23_2(point)).getX()][super
									.getOppo13(super.get23_2(point)).getY()] == em || last_status[super
									.getOppo13(super.get23_2(point)).getX()][super
									.getOppo13(super.get23_2(point)).getY()] == 0
									&& (last_status[super.getOppo23(
											super.get23_2(point)).getX()][super
											.getOppo23(super.get23_2(point))
											.getY()] == self || last_status[super
											.getOppo24(super.get23_2(point))
											.getX()][super.getOppo24(
											super.get23_2(point)).getY()] == self))
									&& line[chessboard_size - 1] == 0
									&& line[chessboard_size - 2] != em
									&& line[chessboard_size - 3] == self
									&& line[chessboard_size - 4] == 0
									&& line[2] == em) {
								values[x][y] += 2;
								t = false;
							}
						}
					}
					if (t) {
						if (line[0] == em
								&& line[1] == self
								&& line[2] != 0
								&& line[chessboard_size - 4] == 0
								|| line[chessboard_size - 1] == em
								&& line[chessboard_size - 2] == self
								&& line[chessboard_size - 3] != 0
								&& line[chessboard_size - 4] != 0
								&& line[2] == 0
								|| line[chessboard_size - 3] == em
								&& line[chessboard_size - 4] != 0
								&& !super.illegal(next_status,
										super.get33(point), em)) {
							values[x][y] -= 9;
							t1 = false;
						} else if (line[2] == em
								&& line[chessboard_size - 4] == 0
								&& line[chessboard_size - 3] != self
								|| line[1] == 0 && line[2] == self
								&& line[chessboard_size - 4] == em
								&& line[chessboard_size - 3] != self
								|| line[chessboard_size - 2] == 0
								&& line[chessboard_size - 3] == self
								&& line[chessboard_size - 4] == em
								&& line[2] == em && line[1] != self) {
							values[x][y] -= 8;
							t1 = false;
						}
					}
					if (t1) {
						if (super.canTurn13(last_status, self) > 0
								&& minTurn13(next_status) >= super.canTurn13(
										last_status, self))
							values[x][y] += 90;
					}

				}
			}
		}
	}

	/**
	 * ����ֽ�ʣ���2��ʱ���ж������ĸ�λ����������ĵ����϶࣬�������϶��λ�õ�Ȩֵ��Ϊ1���������ٵ�λ�õ�Ȩֵ��Ϊ0
	 */
	public void setValuesForLast2Steps() {
		int self = id;
		int em = id % 2 + 1;
		int[][] next_status;
		int[][] status2;
		MyPoint p1, p2;
		int x1, x2;
		Iterator<MyPoint> iterator = legalPoints.iterator();
		p1 = iterator.next();
		p2 = iterator.next();
		next_status = super.getNextStatus(last_status, p1, self);
		if (!super.skip(next_status, em))
			status2 = super.getNextStatus(next_status, p2, em);
		else if (!super.skip(next_status, self))
			status2 = super.getNextStatus(next_status, p2, self);
		else
			status2 = next_status;
		x1 = super.getCountOfThisType(status2, self);
		if (x1 > super.getCountOfThisType(status2, em))
			x1 += super.getCountOfThisType(status2, 0);
		next_status = super.getNextStatus(last_status, p2, self);
		if (!super.skip(next_status, em))
			status2 = super.getNextStatus(next_status, p1, em);
		else if (!super.skip(next_status, self))
			status2 = super.getNextStatus(next_status, p1, self);
		else
			status2 = next_status;
		x2 = super.getCountOfThisType(status2, self);
		if (x2 > super.getCountOfThisType(status2, em))
			x2 += super.getCountOfThisType(status2, 0);
		if (x1 > x2) {
			values[p1.getX()][p1.getY()] = 1;
			values[p2.getX()][p2.getY()] = 0;
		} else if (x1 < x2) {
			values[p2.getX()][p2.getY()] = 1;
			values[p1.getX()][p1.getY()] = 0;
		}
	}

	/**
	 * �ж��ܷ��ں���4���ڽ����ֵ���ȫ���Թ�
	 * 
	 * @param chessboard
	 * @param flag
	 *            �ݹ��������1�ε���ʱȡflag=1�������жϺ���1-2������2�ε���ʱȡflag=0�������жϺ���3-4��
	 * @return
	 */
	public boolean eatAllIn4Steps(int[][] chessboard, int flag) {
		int self = id;
		int em = id % 2 + 1;
		int[][] next_status;
		boolean t, t1;
		// ��������һ���д�����ʱ����һ�������ֿ����µ�����λ�ý����ж�
		if (!super.skip(chessboard, em)) {
			for (int i = 0; i < chessboard_size; i++) {
				for (int j = 0; j < chessboard_size; j++) {
					if (super.illegal(chessboard, new MyPoint(i, j), em))
						continue;
					next_status = super.getNextStatus(chessboard, new MyPoint(
							i, j), em);
					// ����Ϊ�˼򻯳��򣬵�������޴�����ʱֱ�ӷ���false
					if (super.skip(next_status, self))
						return false;
					t = false;
					// �ҵ���������֮���������µ�����λ�ý��б������ж��Ƿ��ܽ����ֵ��ӳԹ�
					for (int m = 0; m < chessboard_size; m++) {
						for (int n = 0; n < chessboard_size; n++) {
							if (super.illegal(next_status, new MyPoint(m, n),
									self))
								continue;
							if (super.eatAll(next_status, new MyPoint(m, n),
									self)) {
								t = true;
								break;
							}
						}
						if (t)
							break;
					}
					// �����2���ڲ��ܳԹ���ֵ��������flag��ֵ�����жϣ�
					// ���flagΪ0�򷵻�false��
					// ���������������֮���������µ�����λ�õݹ���ø÷���������
					if (!t) {
						if (flag == 0)
							return false;
						t1 = false;
						for (int m = 0; m < chessboard_size; m++) {
							for (int n = 0; n < chessboard_size; n++) {
								if (super.illegal(next_status,
										new MyPoint(m, n), self))
									continue;
								if (eatAllIn4Steps(super.getNextStatus(
										next_status, new MyPoint(m, n), self),
										flag - 1)) {
									t1 = true;
									break;
								}
							}
							if (t1)
								break;
						}
						if (!t1)
							return false;
					}
				}
			}
		}
		// ��������һ���޴�����ʱ��ֱ�Ӹ��ݵ�ǰ���̵���������ж�
		else {
			if (super.skip(chessboard, self))
				return false;
			for (int m = 0; m < chessboard_size; m++) {
				for (int n = 0; n < chessboard_size; n++) {
					if (super.illegal(chessboard, new MyPoint(m, n), self))
						continue;
					if (super.eatAll(chessboard, new MyPoint(m, n), self))
						return true;
				}
			}
			if (flag == 0)
				return false;
			t1 = false;
			for (int m = 0; m < chessboard_size; m++) {
				for (int n = 0; n < chessboard_size; n++) {
					if (super.illegal(chessboard, new MyPoint(m, n), self))
						continue;
					if (eatAllIn4Steps(super.getNextStatus(chessboard,
							new MyPoint(m, n), self), flag - 1)) {
						t1 = true;
						break;
					}
				}
				if (t1)
					break;
			}
			if (!t1)
				return false;
		}
		return true;
	}

	/**
	 * �жϺ���14����˫���Ƿ��ܵ���4������
	 * 
	 * @param chessboard
	 * @param flag
	 *            �ݹ��������1�ε���ʱȡflag=6�������жϺ���1-2������2�ε���ʱȡflag=5�������жϺ���3-4�� ...
	 *            ��7�ε���ʱȡflag=0�������жϺ���13-14��
	 * @return +7��������ں���2�����ܵ��ﶥ�� +6��������ں���4�����ܵ��ﶥ�� ... +1��������ں���14�����ܵ��ﶥ��
	 *         0��˫���ں���14���ڶ����ܵ��ﶥ�� -1�������ں���13�����ܵ��ﶥ�� ... -6�������ں���3�����ܵ��ﶥ��
	 *         -7�������ں���1�����ܵ��ﶥ��
	 */
	public int judgeForTurn11(int[][] chessboard, int flag) {
		int self = id;
		int em = id % 2 + 1;
		int[][] next_status;
		boolean t, t1;
		int n0, n1;
		// ��������һ���д�����ʱ����һ�������ֿ����µ�����λ�ý����ж�
		if (!super.skip(chessboard, em)) {
			// ���ж϶�����һ���ܷ񵽴ﶥ�ǣ�������򷵻�-flag-1
			for (int i = 0; i < chessboard_size; i++) {
				for (int j = 0; j < chessboard_size; j++) {
					if (super.illegal(chessboard, new MyPoint(i, j), em))
						continue;
					if (super.is11(new MyPoint(i, j)))
						return -flag - 1;
				}
			}
			// ���������һ�����ܵ��ﶥ�ǣ����жϼ�����ں���2�����ܷ񵽴ﶥ�ǣ�������򷵻�flag+1
			t1 = true;
			for (int i = 0; i < chessboard_size; i++) {
				for (int j = 0; j < chessboard_size; j++) {
					if (super.illegal(chessboard, new MyPoint(i, j), em))
						continue;
					next_status = super.getNextStatus(chessboard, new MyPoint(
							i, j), em);
					if (super.skip(next_status, self)) {
						t1 = false;
						break;
					}
					t = false;
					for (int m = 0; m < chessboard_size; m++) {
						for (int n = 0; n < chessboard_size; n++) {
							if (super.illegal(next_status, new MyPoint(m, n),
									self))
								continue;
							if (super.is11(new MyPoint(m, n))) {
								t = true;
								break;
							}
						}
						if (t)
							break;
					}
					if (!t) {
						t1 = false;
						break;
					}
				}
				if (!t1)
					break;
			}
			if (t1)
				return flag + 1;
			// �����2����˫�������ܵ��ﶥ�������flag��ֵ�����жϣ�
			// ���flagΪ0�򷵻�0��
			// ����ݹ���ø÷���������
			if (flag == 0)
				return 0;
			n1 = flag;
			for (int i = 0; i < chessboard_size; i++) {
				for (int j = 0; j < chessboard_size; j++) {
					if (super.illegal(chessboard, new MyPoint(i, j), em))
						continue;
					next_status = super.getNextStatus(chessboard, new MyPoint(
							i, j), em);
					if (super.skip(next_status, self)) {
						if (judgeForTurn11(next_status, flag - 1) < n1)
							n1 = judgeForTurn11(next_status, flag - 1);
					} else {
						n0 = -flag;
						for (int m = 0; m < chessboard_size; m++) {
							for (int n = 0; n < chessboard_size; n++) {
								if (super.illegal(next_status,
										new MyPoint(m, n), self))
									continue;
								if (judgeForTurn11(super.getNextStatus(
										next_status, new MyPoint(m, n), self),
										flag - 1) > n0)
									n0 = judgeForTurn11(super.getNextStatus(
											next_status, new MyPoint(m, n),
											self), flag - 1);
							}
						}
						if (n0 < n1)
							n1 = n0;
					}
				}
			}
			return n1;
		}
		// ��������һ���޴�����ʱ��ֱ�Ӹ��ݵ�ǰ���̵���������ж�
		else {
			if (super.skip(chessboard, self))
				return 0;
			// ֱ���жϼ�����ں���2�����ܷ񵽴ﶥ�ǣ�������򷵻�flag+1
			for (int m = 0; m < chessboard_size; m++) {
				for (int n = 0; n < chessboard_size; n++) {
					if (super.illegal(chessboard, new MyPoint(m, n), self))
						continue;
					if (super.is11(new MyPoint(m, n)))
						return flag + 1;
				}
			}
			// �����2����˫�������ܵ��ﶥ�������flag��ֵ�����жϣ�
			// ���flagΪ0�򷵻�0��
			// ����ݹ���ø÷���������
			if (flag == 0)
				return 0;
			n0 = -flag;
			for (int m = 0; m < chessboard_size; m++) {
				for (int n = 0; n < chessboard_size; n++) {
					if (super.illegal(chessboard, new MyPoint(m, n), self))
						continue;
					if (judgeForTurn11(super.getNextStatus(chessboard,
							new MyPoint(m, n), self), flag - 1) > n0)
						n0 = judgeForTurn11(super.getNextStatus(chessboard,
								new MyPoint(m, n), self), flag - 1);
				}
			}
			return n0;
		}
	}

	/**
	 * �жϺ���4����˫���Ƿ��ܵ���8��1,3λ
	 * 
	 * @param chessboard
	 * @param flag
	 *            �ݹ��������1�ε���ʱȡflag=1�������жϺ���1-2������2�ε���ʱȡflag=0�������жϺ���3-4��
	 * @return +2��������ں���2�����ܵ���1,3λ +1��������ں���4�����ܵ���1,3λ 0��˫���ں���4���ڶ����ܵ���1,3λ
	 *         -1�������ں���3�����ܵ���1,3λ -2�������ں���1�����ܵ���1,3λ
	 */
	public int judgeForTurn13(int[][] chessboard, int flag) {
		int self = id;
		int em = id % 2 + 1;
		int[][] next_status;
		boolean t, t1;
		int n0, n1;
		// ��������һ���д�����ʱ����һ�������ֿ����µ�����λ�ý����ж�
		if (!super.skip(chessboard, em)) {
			// ���ж϶�����һ���ܷ񵽴�1,3λ��������򷵻�-flag-1
			for (int i = 0; i < chessboard_size; i++) {
				for (int j = 0; j < chessboard_size; j++) {
					if (super.illegal(chessboard, new MyPoint(i, j), em))
						continue;
					if (super.is13(new MyPoint(i, j)))
						return -flag - 1;
				}
			}
			// ���������һ�����ܵ���1,3λ�����жϼ�����ں���2�����ܷ񵽴�1,3λ��������򷵻�flag+1
			t1 = true;
			for (int i = 0; i < chessboard_size; i++) {
				for (int j = 0; j < chessboard_size; j++) {
					if (super.illegal(chessboard, new MyPoint(i, j), em)
							|| super.is22(new MyPoint(i, j))) // �˴���2,2λ���˵�
						continue;
					next_status = super.getNextStatus(chessboard, new MyPoint(
							i, j), em);
					if (super.is12(new MyPoint(i, j))
							&& !super.illegal(next_status,
									super.get11(new MyPoint(i, j)), self)) // �����λ����1,2λ����һ��������������ٽ���λ�õĶ���Ҳ������˵�
						continue;
					if (super.skip(next_status, self)) // ����������һ���޴�����ֱ�ӷ���-flag-1
						return -flag - 1;
					t = false;
					for (int m = 0; m < chessboard_size; m++) {
						for (int n = 0; n < chessboard_size; n++) {
							if (super.illegal(next_status, new MyPoint(m, n),
									self))
								continue;
							if (super.is13(new MyPoint(m, n))) {
								t = true;
								break;
							}
						}
						if (t)
							break;
					}
					if (!t) {
						t1 = false;
						break;
					}
				}
				if (!t1)
					break;
			}
			if (t1)
				return flag + 1;
			// �����2����˫�������ܵ���1,3λ�����flag��ֵ�����жϣ�
			// ���flagΪ0�򷵻�0��
			// ����ݹ���ø÷���������
			if (flag == 0)
				return 0;
			n1 = flag;
			for (int i = 0; i < chessboard_size; i++) {
				for (int j = 0; j < chessboard_size; j++) {
					if (super.illegal(chessboard, new MyPoint(i, j), em)
							|| super.is22(new MyPoint(i, j))) // �˴���2,2λ���˵�
						continue;
					next_status = super.getNextStatus(chessboard, new MyPoint(
							i, j), em);
					if (super.is12(new MyPoint(i, j))
							&& !super.illegal(next_status,
									super.get11(new MyPoint(i, j)), self)) // �����λ����1,2λ����һ��������������ٽ���λ�õĶ���Ҳ������˵�
						continue;
					n0 = -flag;
					for (int m = 0; m < chessboard_size; m++) {
						for (int n = 0; n < chessboard_size; n++) {
							if (super.illegal(next_status, new MyPoint(m, n),
									self)
									|| super.is12(new MyPoint(m, n))
									|| super.is22(new MyPoint(m, n))) // ֱ�ӽ�1,2λ��2,2λ���˵�
								continue;
							if (judgeForTurn13(super.getNextStatus(next_status,
									new MyPoint(m, n), self), flag - 1) > n0)
								n0 = judgeForTurn13(super.getNextStatus(
										next_status, new MyPoint(m, n), self),
										flag - 1);
						}
					}
					if (n0 < n1)
						n1 = n0;
				}
			}
			return n1;
		}
		// ��������һ���޴�����ʱ��ֱ�Ӹ��ݵ�ǰ���̵���������ж�
		else {
			if (super.skip(chessboard, self))
				return 0;
			// ֱ���жϼ�����ں���2�����ܷ񵽴�1,3λ��������򷵻�flag+1
			for (int m = 0; m < chessboard_size; m++) {
				for (int n = 0; n < chessboard_size; n++) {
					if (super.illegal(chessboard, new MyPoint(m, n), self))
						continue;
					if (super.is13(new MyPoint(m, n)))
						return flag + 1;
				}
			}
			// �����2����˫�������ܵ���1,3λ�����flag��ֵ�����жϣ�
			// ���flagΪ0�򷵻�0��
			// ����ݹ���ø÷���������
			if (flag == 0)
				return 0;
			n0 = -flag;
			for (int m = 0; m < chessboard_size; m++) {
				for (int n = 0; n < chessboard_size; n++) {
					if (super.illegal(chessboard, new MyPoint(m, n), self)
							|| super.is12(new MyPoint(m, n))
							|| super.is22(new MyPoint(m, n))) // ֱ�ӽ�1,2λ��2,2λ���˵�
						continue;
					if (judgeForTurn13(super.getNextStatus(chessboard,
							new MyPoint(m, n), self), flag - 1) > n0)
						n0 = judgeForTurn13(super.getNextStatus(chessboard,
								new MyPoint(m, n), self), flag - 1);
				}
			}
			return n0;
		}
	}

	/**
	 * �ж϶�����һ���Ƿ��޷��ڳ���1,2λ��2,2λ��λ��������
	 * 
	 * @param chessboard
	 * @return
	 */
	public boolean cannotTurnExcept12And22(int[][] chessboard) {
		// int self = id;
		int em = id % 2 + 1;
		for (int i = 0; i < chessboard_size; i++) {
			for (int j = 0; j < chessboard_size; j++) {
				MyPoint point = new MyPoint(i, j);
				if (super.illegal(chessboard, point, em) || super.is12(point)
						|| super.is22(point))
					continue;
				return false;
			}
		}
		return true;
	}

	/**
	 * �赱ǰ�����ϵ�һ��1,3λ������ռ�ã��ٽ���1,3λ��1,4λ���÷�ռ�ã� �÷��������ж��Ƿ���������������
	 * �÷���һ������һ���߷���ʹ�������һ��������ڸ�1
	 * ,3λ�����1,4λ�÷�����Ϳ������ڸ�1,3λ�����1,3λ��������һ��������ڸ�1,3λ�����1
	 * ,3λ�÷�����Ϳ������ڸ�1,3λ�����1,4λ
	 * 
	 * @param chessboard
	 * @param point
	 * @param now_id
	 * @param turn
	 * @return
	 */
	public boolean turn3Or4(int[][] chessboard, MyPoint point, int now_id,
			boolean turn) {
		int self = now_id;
		int em = now_id % 2 + 1;
		int[][] next_status;
		int[][] status2;
		if (!(chessboard[point.getX()][point.getY()] == em
				&& chessboard[super.get14(point).getX()][super.get14(point)
						.getY()] == self
				&& chessboard[super.getOppo14(point).getX()][super.getOppo14(
						point).getY()] == 0 && chessboard[super
				.getOppo13(point).getX()][super.getOppo13(point).getY()] == 0))
			return false;
		if (chessboard[super.getOppo23(point).getX()][super.getOppo23(point)
				.getY()] == self
				|| chessboard[super.getOppo24(point).getX()][super.getOppo24(
						point).getY()] == self) {
			status2 = super.getNextStatus(chessboard, super.getOppo14(point),
					em);
			if (!super.illegal(status2, super.getOppo13(point), self)) {
				status2 = super.getNextStatus(chessboard,
						super.getOppo13(point), em);
				if (!super.illegal(status2, super.getOppo14(point), self))
					return true;
			}
		}
		if (turn && !super.illegal(chessboard, super.getOppo23(point), self)) {
			next_status = super.getNextStatus(chessboard,
					super.getOppo23(point), self);
			status2 = super.getNextStatus(next_status, super.getOppo14(point),
					em);
			if (!super.illegal(status2, super.getOppo13(point), self)) {
				status2 = super.getNextStatus(next_status,
						super.getOppo13(point), em);
				if (!super.illegal(status2, super.getOppo14(point), self))
					return true;
			}
		}
		if (turn && !super.illegal(chessboard, super.getOppo24(point), self)) {
			next_status = super.getNextStatus(chessboard,
					super.getOppo24(point), self);
			status2 = super.getNextStatus(next_status, super.getOppo14(point),
					em);
			if (!super.illegal(status2, super.getOppo13(point), self)) {
				status2 = super.getNextStatus(next_status,
						super.getOppo13(point), em);
				if (!super.illegal(status2, super.getOppo14(point), self))
					return true;
			}
		}
		return false;
	}

	/**
	 * �õ�������һ����֮�������ܵ���1,3λ�����ٸ���
	 * 
	 * @param chessboard
	 * @return
	 */
	public int minTurn13(int[][] chessboard) {
		int self = id;
		int em = id % 2 + 1;
		int result;
		int[][] next_status;
		if (!super.skip(chessboard, em)) {
			result = 8;
			for (int i = 0; i < chessboard_size; i++) {
				for (int j = 0; j < chessboard_size; j++) {
					if (super.illegal(chessboard, new MyPoint(i, j), em)
							|| super.is22(new MyPoint(i, j))) // �˴���2,2λ���˵�
						continue;
					next_status = super.getNextStatus(chessboard, new MyPoint(
							i, j), em);
					if (super.is12(new MyPoint(i, j))
							&& !super.illegal(next_status,
									super.get11(new MyPoint(i, j)), self)) // �����λ����1,2λ����һ��������������ٽ���λ�õĶ���Ҳ������˵�
						continue;
					if (super.canTurn13(next_status, self) < result)
						result = super.canTurn13(next_status, self);
				}
			}
		} else
			result = super.canTurn13(chessboard, self);
		return result;
	}

	/**
	 * �ж϶�����һ����֮�������Ƿ��ܵ���ĳ��λ��
	 * 
	 * @param chessboard
	 * @param point
	 * @return
	 */
	public boolean nextStepCanTurnPosition(int[][] chessboard, MyPoint point) {
		int self = id;
		int em = id % 2 + 1;
		boolean t;
		int[][] next_status;
		if (!super.skip(chessboard, em)) {
			for (int i = 0; i < chessboard_size; i++) {
				for (int j = 0; j < chessboard_size; j++) {
					if (super.illegal(chessboard, new MyPoint(i, j), em)
							|| super.is22(new MyPoint(i, j))) // �˴���2,2λ���˵�
						continue;
					next_status = super.getNextStatus(chessboard, new MyPoint(
							i, j), em);
					if (super.is12(new MyPoint(i, j))
							&& !super.illegal(next_status,
									super.get11(new MyPoint(i, j)), self)) // �����λ����1,2λ����һ��������������ٽ���λ�õĶ���Ҳ������˵�
						continue;
					t = false;
					if (!super.illegal(next_status, point, self))
						t = true;
					if (!t)
						return false;
				}
			}
		} else
			return !super.illegal(chessboard, point, self);
		return true;
	}

	public int emToTurn13(int[][] chessboard) {
		int self = id;
		int em = id % 2 + 1;
		int result = super.toTurn13(chessboard, em);
		int[][] next_status;
		if (!super.skip(chessboard, em)) {
			for (int i = 0; i < chessboard_size; i++) {
				for (int j = 0; j < chessboard_size; j++) {
					if (super.illegal(chessboard, new MyPoint(i, j), em)
							|| super.is22(new MyPoint(i, j))) // �˴���2,2λ���˵�
						continue;
					next_status = super.getNextStatus(chessboard, new MyPoint(
							i, j), em);
					if (super.is12(new MyPoint(i, j))
							&& !super.illegal(next_status,
									super.get11(new MyPoint(i, j)), self)) // �����λ����1,2λ����һ��������������ٽ���λ�õĶ���Ҳ������˵�
						continue;
					if (super.canTurn13(next_status, self) > super.canTurn13(
							chessboard, self))
						continue;
					if (super.is24(new MyPoint(i, j))) {
						if (!super.illegal(next_status,
								super.get14(new MyPoint(i, j)), self)) {
							int[][] status3 = super.getNextStatus(next_status,
									super.get14(new MyPoint(i, j)), self);
							if (super.toTurn13(status3, em) <= super.toTurn13(
									chessboard, em)) {
								if (next_status[super.get13(new MyPoint(i, j))
										.getX()][super.get13(new MyPoint(i, j))
										.getY()] == self
										|| next_status[super.get13(
												new MyPoint(i, j)).getX()][super
												.get13(new MyPoint(i, j))
												.getY()] == 0
										&& super.illegal(status3,
												super.get13(new MyPoint(i, j)),
												em)
										&& super.illegal(status3, super
												.getOppo13(new MyPoint(i, j)),
												em))
									continue;
							}
						}
					}
					if (super.toTurn13(next_status, em) > result)
						result = super.toTurn13(next_status, em);
				}
			}
		}
		return result;
	}

	/**
	 * ��ȡ��ǰ�����ϳ��˶��Ǻ��ٽ����ǵ�λ�ÿ�λ������
	 * 
	 * @param chessboard
	 * @return
	 */
	public int getCountOfLastPositionExceptNear11(int[][] chessboard) {
		int result = 0;
		for (int i = 0; i < chessboard_size; i++) {
			for (int j = 0; j < chessboard_size; j++) {
				MyPoint point = new MyPoint(i, j);
				if (super.is11(point) || super.is12(point) || super.is22(point))
					continue;
				if (chessboard[i][j] == 0)
					result++;
			}
		}
		return result;
	}

}
