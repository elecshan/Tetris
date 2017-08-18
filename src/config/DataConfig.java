package config;

import java.io.Serializable;

import org.dom4j.Element;

public class DataConfig implements Serializable {

	private final int maxRaw;
	private final DataInterfaceConfig dataA;;
	private final DataInterfaceConfig dataB;
	
	public DataConfig(Element data) {
		this.dataA = new DataInterfaceConfig(data.element("dataA"));
		this.dataB = new DataInterfaceConfig(data.element("dataB"));
		this.maxRaw = Integer.parseInt(data.attributeValue("maxRaw"));
	}

	public DataInterfaceConfig getDataA() {
		return dataA;
	}

	public DataInterfaceConfig getDataB() {
		return dataB;
	}

	public int getMaxRaw() {
		return maxRaw;
	}
	
}
