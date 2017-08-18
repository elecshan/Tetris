package service;

import java.awt.Point;
import java.util.HashMap;
import java.util.Random;

import config.GameConfig;
import dto.GameDto;
import entity.GameAct;

public class GameTetris implements GameService{

	//��Ϸ���ݶ���
	private GameDto dto;
	
	//�����������
	private Random random = new Random();
	
	//������Ҫ����������
	private final int levelUp = GameConfig.getSYSTEM_CONFIG().getLevelUp();
	
	//���з���
	private static final HashMap<Integer, Integer> PLUS_POINT = GameConfig.getSYSTEM_CONFIG().getPointsMap();
	
	//�����������
	private static final int MAX_TYPE = GameConfig.getSYSTEM_CONFIG().getTypeConfig().size();
	
	public GameTetris(GameDto dto) {
		this.dto = dto;
	}
	
	/**
	 * ����������ϣ�
	 */
	public boolean keyUp() {
		if(this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
		return true;
	}
	
	/**
	 * ����������£�
	 */
	public boolean keyDown() {
		if(this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			//�жϷ����Ƿ��ƶ����ײ�
			if(this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
				return false;
			}
			//�����Ϸ��ͼ����
			boolean[][] map = this.dto.getGameMap();
			//��÷������
			Point[] act = this.dto.getGameAct().getActPoints();
			//������ѻ�����ͼ������
			for (int i = 0; i < act.length; i++) {
				map[act[i].x][act[i].y] = true;
			}
			//�ж����У��������������
			int exp = this.plusExp();
			//�ӷֲ���
			this.plusPoint(exp);
			//������һ������
			this.dto.getGameAct().init(this.dto.getNext());
			//�����������һ������
			this.dto.setNext(random.nextInt(MAX_TYPE));
			//�����Ϸ�Ƿ����
			if(this.isLose()) {
				//������Ϸ
				this.dto.setStart(false);
			}
		}
		return true;
	}

	/**
	 * �����������
	 */
	public boolean keyLeft() {
		if(this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
		}
		return true;
	}

	/**
	 * ����������ң�
	 */
	public boolean keyRight() {
		if(this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
		}
		return true;
	}

	/**
	 * ���װ���
	 */
	@Override
	public boolean keyFunUp() {
		this.dto.setCheat(true);
		this.plusPoint(4);
		return true;
	}

	/**
	 * ˲������
	 */
	@Override
	public boolean keyFunDown() {
		if(this.dto.isPause()) {
			return true;
		}
		while(!this.keyDown());
		return true;
	}

	/**
	 * ��Ӱ����
	 */
	@Override
	public boolean keyFunLeft() {
		this.dto.setShowShadow(!this.dto.isShowShadow());
		return true;
	}

	/**
	 * ��ͣ����
	 */
	@Override
	public boolean keyFunRight() {
		if(this.dto.isStart()) {
			this.dto.setPause(!this.dto.isPause());
		}
		return true;
	}
	
	/**
	 * ���в���
	 */
	private int plusExp() {
		int exp = 0;
		boolean[][] map = this.dto.getGameMap();
		//ɨ���ͼ���鿴�Ƿ��п�����
		for (int y = 0; y < GameDto.GAMEZONE_H; y++) {
			//�ж��Ƿ��������
			if(this.isCanRemoveLine(y, map)) {
				this.removeLine(y, map);
				exp++;
			}
		}
		return exp;
	}
	
	/**
	 * ���д���
	 * @param y
	 * @param map
	 */
	private void removeLine(int rawNumber, boolean[][] map) {
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			for (int y = rawNumber; y > 0; y--) {
				map[x][y] = map[x][y - 1];
			}
			map[x][0] = false;
		}
	}

	/**
	 * �ж�ĳһ���Ƿ��������
	 * @param y
	 * @param map
	 * @return
	 */
	private boolean isCanRemoveLine(int y, boolean[][] map) {
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			if(!map[x][y]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * �ӷֲ���
	 * @param exp
	 */
	@SuppressWarnings("static-access")
	private void plusPoint(int exp) {
		int level = this.dto.getLevel();
		int rmLine = this.dto.getNowRemoveLine();
		int point = this.dto.getNowPoint();
		
		if((rmLine % this.levelUp + exp) >= this.levelUp) {
			this.dto.setLevel(++level);
		}
		this.dto.setNowRemoveLine(rmLine + exp);
		this.dto.setNowPoint(point + this.PLUS_POINT.get(exp));
	}

	private boolean isLose() {
		Point[] actPoints = this.dto.getGameAct().getActPoints();
		boolean[][] map = this.dto.getGameMap();
		for (int i = 0; i < actPoints.length; i++) {
			if (map[actPoints[i].x][actPoints[i].y]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ��Ϸ���ݳ�ʼ��
	 */
	@Override
	public void startGame() {
		//���������һ������
		this.dto.setNext(random.nextInt(MAX_TYPE));
		//�������������ʾ�ķ���
		this.dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));
		//����Ϸ״̬��Ϊ��ʼ
		this.dto.setStart(true);
		//��ʼ��dto
		this.dto.dtoInit();
	}

	/**
	 * ��Ϸ����Ϊ
	 */
	@Override
	public void mainAction() {
		this.keyDown();
	}
}
