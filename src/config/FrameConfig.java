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
	
	//ͼ������
	private final List<LayerConfig> layerConfig;
	
	//��������
	private final ButtonConfig buttonConfig;
	
	public FrameConfig(Element frame) {
		//��ȡ���ڿ��
		this.width = Integer.parseInt(frame.attributeValue("width"));
		//��ȡ���ڸ߶�
		this.height = Integer.parseInt(frame.attributeValue("height"));
		//��ȡ�߿��ϸ
		this.border = Integer.parseInt(frame.attributeValue("border"));
		//��ȡ�߿��ڱ߾�
		this.padding = Integer.parseInt(frame.attributeValue("padding"));
		//��ȡ����
		this.title = frame.attributeValue("title");
		//��ȡ���ڰθ�
		this.windowUp = Integer.parseInt(frame.attributeValue("windowUp"));
		//��ȡ�����С
		this.sizeRol = Integer.parseInt(frame.attributeValue("sizeRol"));
		//��Ϸʧ�ܵķ�����
		this.loseIdx = Integer.parseInt(frame.attributeValue("loseIdx"));
		//��ȡ��������
		@SuppressWarnings("unchecked")
		List<Element> layers = frame.elements("layer");
		layerConfig = new ArrayList<LayerConfig>();
		//��ȡ���д�������
		for(Element layer : layers) {
			//���õ�����������
			LayerConfig lc = new LayerConfig(
					layer.attributeValue("className"),
					Integer.parseInt(layer.attributeValue("x")),
					Integer.parseInt(layer.attributeValue("y")),
					Integer.parseInt(layer.attributeValue("w")),
					Integer.parseInt(layer.attributeValue("h")));
			layerConfig.add(lc);
		}
		//��ʼ����������
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
