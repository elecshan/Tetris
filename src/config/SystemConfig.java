package config;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

public class SystemConfig implements Serializable {

	private final int minX;
	
	private final int maxX;
	
	private final int minY;
	
	private final int maxY;
	
	private final int levelUp;
	
	private final List<Point[]> typeConfig;
	
	private final List<Boolean> typeRound;
	
	private final HashMap<Integer, Integer> pointsMap = new HashMap<Integer, Integer>();
	
	public SystemConfig(Element system) {
		this.minX = Integer.parseInt(system.attributeValue("minX"));
		
		this.maxX = Integer.parseInt(system.attributeValue("maxX"));
		
		this.minY = Integer.parseInt(system.attributeValue("minY"));
		
		this.maxY = Integer.parseInt(system.attributeValue("maxY"));
		
		this.levelUp = Integer.parseInt(system.attributeValue("levelUp"));
		
		@SuppressWarnings("unchecked")
		//获得方块元素列表
		List<Element> rects = system.elements("rect");
		//获得加分数组
		@SuppressWarnings("unchecked")
		List<Element> plusPoints = system.elements("plusPoint");
		typeConfig = new ArrayList<Point[]>(rects.size());
		typeRound = new ArrayList<Boolean>(rects.size());
		for (Element rect : rects) {
			//获取方块旋转标志
			this.typeRound.add(Boolean.parseBoolean(rect.attributeValue("round")));
			@SuppressWarnings("unchecked")
			//获得每一个方块元素的坐标列表
			List<Element> pointConfig = rect.elements("point");
			//创建一个二维坐标数组
			Point[] points = new Point[pointConfig.size()];
			for (int i = 0; i < points.length; i++) {
				//获得一个坐标
				int x = Integer.parseInt(pointConfig.get(i).attributeValue("x"));
				int y = Integer.parseInt(pointConfig.get(i).attributeValue("y"));
				points[i] = new Point(x, y);
			}
			//把数组增加到列表中
			this.typeConfig.add(points);
		}
		for (Element point : plusPoints) {
			this.pointsMap.put(Integer.parseInt(point.attributeValue("rm")), Integer.parseInt(point.attributeValue("point")));
		}
	}

	public int getMinX() {
		return minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getLevelUp() {
		return levelUp;
	}

	public List<Point[]> getTypeConfig() {
		return typeConfig;
	}

	public List<Boolean> getTypeRound() {
		return typeRound;
	}

	public HashMap<Integer, Integer> getPointsMap() {
		return pointsMap;
	}
	
}
