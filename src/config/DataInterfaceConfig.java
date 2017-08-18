package config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

public class DataInterfaceConfig implements Serializable {

	private final String className;
	
	private final HashMap<String, String> paramMap;
	
	public DataInterfaceConfig(Element dataInterfaceConfig) {
		this.className = dataInterfaceConfig.attributeValue("className");
		this.paramMap = new HashMap<String, String>();
		
		@SuppressWarnings("unchecked")
		List<Element> params = dataInterfaceConfig.elements("param");
		for (Element e : params) {
			String key = e.attributeValue("key");
			String value = e.attributeValue("value");
			this.paramMap.put(key, value);
		}
	}

	public String getClassName() {
		return className;
	}

	public HashMap<String, String> getParamMap() {
		return paramMap;
	}
	
}
