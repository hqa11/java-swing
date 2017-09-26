package util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import config.GameConfig;
import ui.Layer;

public class XmlReader {

	public static GameConfig analysisConfig(){
		GameConfig gc = new GameConfig();
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read("config/cfg.xml");
			Element game = doc.getRootElement();
			Element frame = game.element("frame");
			gc.setHeight(Integer.parseInt(frame.attributeValue("height")));
			gc.setWidth(Integer.parseInt(frame.attributeValue("width")));
			gc.setPadding(Integer.parseInt(frame.attributeValue("padding")));
			gc.setWindowSize(Integer.parseInt(frame.attributeValue("windowSize")));
			List<Element> layers = frame.elements("layer");
			for (Element layer : layers) {
				String w = layer.attributeValue("w");
				String h = layer.attributeValue("h");
				String x = layer.attributeValue("x");
				String y = layer.attributeValue("y");
				String className =  layer.attributeValue("class");
				//使用反射进行创建赋值
				Class<?> clazz = Class.forName(className);
				Constructor<?> constructor = clazz.getConstructor(int.class,int.class,int.class,int.class);
				Object newInstance = constructor.newInstance(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(w),Integer.parseInt(h));
				//调用addlayer方法进行复制
				gc.addLayer((Layer)newInstance);
				
			}
			Element system = game.element("system");
			gc.setRefreshOffsetY(Integer.parseInt(system.attributeValue("refreshOffsetY")));
			gc.setNeedActBackGround(Integer.parseInt(system.attributeValue("needActBackGround")));
			gc.setNeedShadow(Integer.parseInt(system.attributeValue("needShadow")));
			Element style = game.element("style");
			Element border = style.element("border");
			gc.setBorder(Integer.parseInt(border.attributeValue("value")));
			//玩家２的看板
			Element lookframe = game.element("lookframe");
			List<Element> olayers = lookframe.elements("layer");
			for (Element olayer : olayers) {
				String w = olayer.attributeValue("w");
				String h = olayer.attributeValue("h");
				String x = olayer.attributeValue("x");
				String y = olayer.attributeValue("y");
				String className =  olayer.attributeValue("class");
				//使用反射进行创建赋值
				Class<?> clazz = Class.forName(className);
				Constructor<?> constructor = clazz.getConstructor(int.class,int.class,int.class,int.class);
				Object newInstance = constructor.newInstance(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(w),Integer.parseInt(h));
				//调用addlayer方法进行复制
				gc.addOLayer((Layer)newInstance);
				
			}
			Element netty = game.element("netty");
			String host = netty.attributeValue("host");
			int port = Integer.parseInt(netty.attributeValue("port"));
			gc.setPort(port);
			gc.setHost(host);
			//----------------end-----------------------------------------
			System.out.println(gc.getBorder()+"----------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gc;
	}
	
	public static void main(String[] args) throws Exception {
		GameConfig gc = XmlReader.analysisConfig();
		System.out.println(gc);
	}
	
}
