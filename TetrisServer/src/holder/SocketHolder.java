package holder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.channel.Channel;
/**
 * 游戏组件容器(集成板)
 * @author Hqa
 * @date 2017-7-4 
 * @email 18806278373@sina.cn
 * @to TODO
 */
public final class SocketHolder {
	public static volatile Map<Integer,Channel> socketHolder = new ConcurrentHashMap<>();
	public static void put(Integer key ,Channel value){
		Channel channel = SocketHolder.get(key);
		if(channel != null)return;
		socketHolder.put(key, value);
	}
	public static Channel get(Integer key){
		return socketHolder.get(key);
	}
	public static void remove(Integer key) {
		socketHolder.remove(key);
	}
}
