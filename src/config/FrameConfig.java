package config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class FrameConfig implements Serializable {

	private final int width;
	private final int height;
	private final int border;
	private final int padding;
	private final String title;
	private final int windowUp;
	private final int sizeRol;
	private final int loseIdx;
	
	//图层属性
	private final List<LayerConfig> layerConfig;
	
	//按键属性
	private final ButtonConfig buttonConfig;
	
	public FrameConfig(Element frame) {
		//获取窗口宽度
		this.width = Integer.parseInt(frame.attributeValue("width"));
		//获取窗口高度
		this.height = Integer.parseInt(frame.attributeValue("height"));
		//获取边框粗细
		this.border = Integer.parseInt(frame.attributeValue("border"));
		//获取边框内边距
		this.padding = Integer.parseInt(frame.attributeValue("padding"));
		//获取标题
		this.title = frame.attributeValue("title");
		//获取窗口拔高
		this.windowUp = Integer.parseInt(frame.attributeValue("windowUp"));
		//获取方块大小
		this.sizeRol = Integer.parseInt(frame.attributeValue("sizeRol"));
		//游戏失败的方块编号
		this.loseIdx = Integer.parseInt(frame.attributeValue("loseIdx"));
		//获取窗体属性
		@SuppressWarnings("unchecked")
		List<Element> layers = frame.elements("layer");
		layerConfig = new ArrayList<LayerConfig>();
		//获取所有窗体属性
		for(Element layer : layers) {
			//设置单个窗体属性
			LayerConfig lc = new LayerConfig(
					layer.attributeValue("className"),
					Integer.parseInt(layer.attributeValue("x")),
					Integer.parseInt(layer.attributeValue("y")),
					Integer.parseInt(layer.attributeValue("w")),
					Integer.parseInt(layer.attributeValue("h")));
			layerConfig.add(lc);
		}
		//初始化按键属性
		buttonConfig = new ButtonConfig(frame.element("button"));
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getBorder() {
		return border;
	}

	public int getPadding() {
		return padding;
	}

	public String getTitle() {
		return title;
	}

	public int getWindowUp() {
		return windowUp;
	}

	public int getLoseIdx() {
		return loseIdx;
	}

	public List<LayerConfig> getLayerConfig() {
		return layerConfig;
	}

	public int getSizeRol() {
		return sizeRol;
	}

	public ButtonConfig getButtonConfig() {
		return buttonConfig;
	}
	
}
