package config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * ÓÎÏ·×é¼þÈÝÆ÷
 * @author Hqa
 * @date 2017-7-4 
 * @email 18806278373@sina.cn
 * @to TODO
 */
public class GameCpHolder {
public static volatile Map<String,Object> gameComponets = new ConcurrentHashMap<>();
public static void put(String key ,Object value){
	gameComponets.put(key, value);
}
public static Object get(String key){
	return gameComponets.get(key);
}
}
