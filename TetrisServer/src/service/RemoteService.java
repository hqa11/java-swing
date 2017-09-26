package service;

import holder.RoomHolder;
import holder.SocketHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.util.internal.StringUtil;

import util.JSONUtils;

import annotions.SocketService;
import dto.GameDto;
import dto.Player;
import dto.RequestMessage;
import dto.ResponseMessage;

/**
 * 处理业务逻辑的service
 * @author Administrator
 *
 */
public class RemoteService {

	/**
	 * 处理客户端请求
	 * @param msg
	 * @param channel
	 */
	@SocketService(module = 1,cmd = 1)// 请求进入房间
	public void entryRoom(RequestMessage reqMsg, Channel channel) {
		if(reqMsg == null || channel == null) System.out.println("params is illegal!");
		@SuppressWarnings({ "unchecked" })
		Map<String,Object> map= (Map<String,Object>)reqMsg.getRequestData();
		//查看房间人数
		Integer roomNo = Integer.parseInt(map.get("roomNo") + "");
		Map<Integer, Player> roomPlayers = RoomHolder.get(roomNo);
		//返回体
		Map<String, Object> responseData = new HashMap<String, Object>();
		if(roomPlayers == null || roomPlayers.size() == 0 || roomPlayers.size() < 2){
			responseData.put("currentNum",roomPlayers == null ? "0" : roomPlayers.size());
			//查看此用户是不是正在里面
			if(roomPlayers != null){
				Player palyer  = roomPlayers.get(channel.getId());
				if(palyer != null){
					responseData.put("entryOk","0");
					responseData.put("info","你已经在里面了，老铁！");
					ResponseMessage respMsg = new ResponseMessage(1,2,null, responseData );
					//	channel.write(JSONUtils.toJSON(respMsg));
					channel.write(respMsg);
					return;
				}
			}
			//人数未满的业务逻辑
			responseData.put("entryOk","1");
			responseData.put("roomNo",roomNo);
			responseData.put("info","欢迎您进入此房间！");
			//将房里的人返回
			Map<Integer, Player> beforeRoomPlayers = new HashMap<>();
			if(roomPlayers != null)beforeRoomPlayers.putAll(roomPlayers);
			responseData.put("roomUsers",beforeRoomPlayers);

			//如果这个房间还有其他人，那么通知此人有人来了
			Map<String,Object> ret = new HashMap<>();
			if(roomPlayers != null && roomPlayers.size() > 0 ){
				for(Integer cId : roomPlayers.keySet()){
					ret.put("come", 1);
					//此为房间中的另一名玩家
					Channel vsChannel = SocketHolder.get(cId);
					//通知对方另一个人离开了
					vsChannel.write(new ResponseMessage(1,9, reqMsg.getPlayer(), ret));
				}
			}
			RoomHolder.put(roomNo, reqMsg.getPlayer(),channel.getId());
			//记录此人所在的位置
			RoomHolder.getRoomChannelHolder().put(channel.getId(), roomNo);

		}else if(roomPlayers.size() >= 2){
			//TODO 人数已满的业务逻辑
			responseData.put("currentNum",roomPlayers.size());
			responseData.put("info","这个房间人数已经满了！");
			responseData.put("roomNo",roomNo);
			responseData.put("entryOk","0");
		}
		ResponseMessage respMsg = new ResponseMessage(1, 2, null, responseData );
		//写出消息
		//	channel.write(JSONUtils.toJSON(respMsg));
		channel.write(respMsg);
	}

	/**
	 * 推送房间列表
	 * @param reqMsg
	 * @param channel
	 */
	@SocketService(module = 1,cmd = 3)
	public void pushRoomList(RequestMessage reqMsg, Channel channel){
		Map<Integer, Map<Integer, Player>> roomInfo = RoomHolder.getRoomHolder();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("roomInfo",roomInfo);
		data.put("flag",true);
		ResponseMessage respMsg = new ResponseMessage(1,4,null,data);
		//	channel.write(JSONUtils.toJSON(respMsg));
		channel.write(respMsg);
	}
	
	
	/**
	 * 刷新房间列表
	 * @param reqMsg
	 * @param channel
	 */
	@SocketService(module = 1,cmd = 17)
	public void refreshRoomList(RequestMessage reqMsg, Channel channel){
		Map<Integer, Map<Integer, Player>> roomInfo = RoomHolder.getRoomHolder();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("roomInfo",roomInfo);
		data.put("flag",true);
		ResponseMessage respMsg = new ResponseMessage(1,18,null,data);
		channel.write(respMsg);
	}
	

	/**
	 * 准备或者取消准备
	 * @param reqMsg
	 * @param channel
	 */
	@SocketService(module = 1,cmd = 5)
	public  void readyOrCancelGame(RequestMessage reqMsg, Channel channel){
		System.out.println("服务端收到了准备请求=========================>");
		Map<String,Object> map= (Map<String,Object>)reqMsg.getRequestData();
		//0.取消 //1. 开始
		Integer op = (Integer) map.get("op");
		Integer roomNo = (Integer) map.get("roomNo");
		Map<Integer, Map<Integer, Player>> roomInfo = RoomHolder.getRoomHolder();
		Map<Integer, Player> roomPlayers= roomInfo.get(roomNo);
		Player player = roomPlayers.get(channel.getId());
		//返回体
		Map<String,Object> ret = new HashMap<>();
		ret.put("op", op);
		int states =  player.getUserStatus();
		//校验当前用户状态
		if(states == op){
			ret.put("flag", 0);
			ret.put("info", "你已经在此状态了，请勿重复操作!");
			channel.write(new ResponseMessage(1,10, null, ret));
			return;
		}
		//isAllReady
		if(op == 0){
			//取消
			player.setUserStatus(0);
		}else{
			//准备
			player.setUserStatus(1);

		}
		//向房间中的其他人推送准备消息
		if(roomPlayers.isEmpty()){
			ret.put("flag", 0);
			ret.put("info", "当前时间无法准备!");
			channel.write(new ResponseMessage(1,10, null, ret));
			return;
		}
		int hasAllReady = 1;
		Channel vsChannel = null;
		for(Integer cId : roomPlayers.keySet()){
			Integer vsCid = channel.getId();
			if(cId != vsCid){
				//此为房间中的另一名玩家
				vsChannel = SocketHolder.get(cId);
				//通知对方已准备的消息通知
				ret.put("vsPlayer", player);
				vsChannel.write(new ResponseMessage(1,11, null, ret));
			}
			Player p = roomPlayers.get(cId);
			if(p.getUserStatus() != 1)hasAllReady = 0;
		}
		//通知己方准备ok
		ret.put("flag", 1);
		channel.write(new ResponseMessage(1,10, null, ret));

		//最后查看一下两个人是不是都已经准备了，如果都准备了那么，发送开始游戏的通知
		if(roomPlayers!= null && hasAllReady == 1 && roomPlayers.size() > 1){
			ret.put("isAllReady", 1);
			channel.write(new ResponseMessage(1,12, null, ret));
			vsChannel.write(new ResponseMessage(1,12, null, ret));
		}

	}

	/**
	 * 离开房间请求
	 * @param reqMsg
	 * @param channel
	 */
	@SocketService(module = 1,cmd = 6)
	public void leaveRoom(RequestMessage reqMsg, Channel channel){
		@SuppressWarnings("unchecked")
		Map<String,Object> map= (Map<String,Object>)reqMsg.getRequestData();
		Integer roomNo = Integer.parseInt(map.get("roomNo") + "");
		Map<String,Object> ret = new HashMap<>();
		Map<Integer, Player> roomPlayers = RoomHolder.get(roomNo);
		Integer id = channel.getId();
		if(roomPlayers == null || roomPlayers.get(channel.getId()) == null){
			ret.put("isOk", 0);
		}else{
			//移除此人
			roomPlayers.remove(channel.getId());
			ret.put("isOk", 1);
			//如果这个房间还有其他人，那么通知此人已经离开的消息
			if(roomPlayers.size() > 0 ){
				for(Integer cId : roomPlayers.keySet()){
					if(cId != id){
						ret.put("come",0);
						//此为房间中的另一名玩家
						Channel vsChannel = SocketHolder.get(cId);
						//通知对方另一个人离开了
						vsChannel.write(new ResponseMessage(1,9, null, ret));
					}
				}
			}
		}
		//告诉离开的人，离开成功/失败了
		channel.write(new ResponseMessage(1,8, null, ret));
	}


	/**
	 * 用户倒计时完成确认，如果都ok，那么通知客户端启动Game
	 * @param reqMsg
	 * @param channel
	 */
	@SocketService(module = 1,cmd = 7)
	public void confirmReadyOk(RequestMessage reqMsg, Channel channel){
		@SuppressWarnings("unchecked")
		Map<String,Object> map= (Map<String,Object>)reqMsg.getRequestData();
		Integer roomNo = Integer.parseInt(map.get("roomNo") + "");
		Map<Integer, Player> roomPlayers = RoomHolder.get(roomNo);
		Integer cId = channel.getId();
		Player player1 = roomPlayers.get(cId);
		player1.setUserStatus(4);
		//查看另一个人的状态是否为4
		Map<String,Object> ret = new HashMap<>();
		if(roomPlayers.size() > 0 ){
			for(Integer id : roomPlayers.keySet()){
				if(cId != id){
					//此为房间中的另一名玩家
					Player player2 = roomPlayers.get(id);
					if(player2.getUserStatus() == 4){
						ret.put("isAllReady", 1);
						channel.write(new ResponseMessage(1,13, null, ret));
						SocketHolder.get(id).write(new ResponseMessage(1,13, null, ret));
					}
				}
			}
		}
	}
	

	/**
	 * 向房间中的对战用户推送游戏实体
	 * @param reqMsg
	 * @param channel
	 */
	@SocketService(module = 2,cmd = 1)
	public void pushGameDto(RequestMessage reqMsg, Channel channel){
		Map<String,Object> map = (Map<String,Object>) reqMsg.getRequestData();
		GameDto dto = (GameDto) map.get("gameDto");
		if(dto.getResult() !=0 )return;
		//将此数据推送给对面儿
		Integer roomNo = Integer.parseInt(map.get("roomNo") + "");
		Map<Integer, Player> roomPlayers = RoomHolder.get(roomNo);
		Integer cId = channel.getId();
		Map<String,Object> ret = new HashMap<>();
		Channel channel2 = null;
		if(roomPlayers.size() > 0 ){
			for(Integer id : roomPlayers.keySet()){
				if(cId != id){
					//此为房间中的另一名玩家
					ret.put("gameDto", dto);
					channel2 = SocketHolder.get(id);
					channel2.write(new ResponseMessage(2, 2,null, ret));
				}

			}
		}

		//判断谁挂了
		Map<String,Object> ret2 = new HashMap<>();
		//必然有一名玩家先跪
		int isEnd = dto.getIsEnd();
		if(isEnd == 1){
			//向两边推送消息
			ret2.put("win", 1);
			channel2.write(new ResponseMessage(2,5,null, ret2));
			ret2.put("win", 0);
			ret2.remove("gameDto");
			channel.write(new ResponseMessage(2,5,null, ret2));

		}

	}

	/**
	 * 玩家返回房间
	 * @param reqMsg
	 * @param channel
	 */
	@SocketService(module = 2,cmd = 3)
	public void goBackRoom(RequestMessage reqMsg, Channel channel){
		//获取房间中的玩家
		Map<String,Object> map= (Map<String,Object>)reqMsg.getRequestData();
		Integer roomNo = Integer.parseInt(map.get("roomNo") + "");
		Map<Integer, Player> roomUsers = RoomHolder.get(roomNo);
		Map<String,Object> ret = new HashMap<>();
		if(roomUsers != null){
			ret.put("backOk", 1);
		}else{
			ret.put("backOk", 0);
		}
		channel.write(new ResponseMessage(2, 4,null, ret));

	}

}