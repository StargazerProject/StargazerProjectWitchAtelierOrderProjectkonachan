package com.pisual.www.cells.yande.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.pisual.witchatelier.model.YandeCG;


/**
 * Html获取数据的处理过程
 * 
 * @function XmlHandling：处理获取到的XML数据
 * @function JsonHandling：处理获取到的Json数据
 * @author felixerio
 * @parameters List<String> Parameters 参数标准 list[0-n]:所有数据子节点
 * @version 1.0
 * **/
public class HtmlDataHandling {
	static Logger logger = Logger.getLogger(HtmlDataHandling.class.getName());

	public <E> List<E> XmlHandling(List<String> Parameters, String XMLData) {
		List<E> xmlResult = new ArrayList<E>();
		try {
			Document document = DocumentHelper.parseText(XMLData);
			Element root = document.getRootElement();
			List nodes = root.elements("post");
			for (Iterator it = nodes.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				YandeCG yandeCG = new YandeCG();
				for (int i = 0; i < Parameters.size(); i++) {
					String mess = elm.attributeValue(Parameters.get(i));
					PropertyUtil.setProperty(yandeCG, Parameters.get(i),
							mess);
				}
				xmlResult.add((E) yandeCG);
				logger.info("Reflective objects into the ID: "+yandeCG.getId()+" Has Success");
			}
		} catch (DocumentException e) {
			logger.error("Reflective objects into the Fault: "+e.getMessage());
		}
		return xmlResult;
	}

}
