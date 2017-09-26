package holder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dto.Player;

/**
 * 房间信息存储
 * @author Administrator
 *
 */
public final class RoomHolder {
	public static volatile Map<Integer,Map<Integer,Player>> roomHolder = new ConcurrentHashMap<>();
	public static volatile Map<Integer,Integer> roomChannelHolder = new ConcurrentHashMap<>();
	public static Map<Integer, Integer> getRoomChannelHolder() {
		return roomChannelHolder;
	}
	public static void setRoomChannelHolder(Map<Integer, Integer> roomChannelHolder) {
		RoomHolder.roomChannelHolder = roomChannelHolder;
	}
	public static Map<Integer,Map<Integer,Player>> getRoomHolder() {
		return roomHolder;
	}
	//有人来了
	public static void put(Integer roomId ,Player player,Integer channelId){
		Map<Integer,Player> userMap = roomHolder.get(roomId);
		if(userMap == null)userMap = new ConcurrentHashMap<>();
		userMap.put(channelId,player);
		roomHolder.put(roomId, userMap);
	}
	public static Map<Integer,Player> get(Integer roomId){
		return roomHolder.get(roomId);
	}
	//有人走了
	public static void remove(Integer roomId,Integer channelId) {
		Map<Integer, Player> userMap = roomHolder.get(roomId);
		if(userMap == null)return;
		userMap.remove(channelId);
	}
}
