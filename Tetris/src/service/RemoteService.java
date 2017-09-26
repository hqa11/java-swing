package service;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jboss.netty.channel.Channel;

import annotions.SocketService;
import config.Constant;
import config.GameCpHolder;
import dto.Game;
import dto.GameDto;
import dto.Player;
import dto.RequestMessage;
import dto.ResponseMessage;
import entity.GameAct;
import ui.OFrameGame;
import ui.OPanelGame;
import ui.ReadyButton;
import ui.RefreshButton;
import ui.RoomDetPanel;
import ui.RoomEntryButton;
import ui.RoomFrame;
import ui.RoomLabel;
import ui.RoomListFrame;
import ui.RoomPanel;
import ui.ShowMessageFrame;
import ui.StartButton;

/**
 * 远程操作service
 * @author Administrator
 *
 */
public  class  RemoteService {
	//整一个线程池
	ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 8, 0L,
			TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100000),
			new ThreadPoolExecutor.CallerRunsPolicy());
	public RemoteService(){

	}

	/**
	 * 进入房间
	 * @param message
	 */
	@SocketService(module = 1, cmd = 1)
	public void entryRoom(RequestMessage message){

		Channel channel = (Channel) GameCpHolder.get("channel");

		//	channel.write(JSONUtils.toJSON(message));
		channel.write(message);
	}

	/**
	 * 处理请求进入房间的服务端响应
	 * @param message
	 */
	@SocketService(module = 1, cmd = 2)
	public void entryRoomRsp(ResponseMessage message,Channel channel){
		//解析message
		RoomListFrame roomListFrame = (RoomListFrame)GameCpHolder.get("roomListFrame");
		Map<String, Object> requestData = (Map<String, Object>) message.getRequestData();
		String isOk = requestData.get("entryOk") + "";
		String info = requestData.get("info") + "";
		if("1".equals(isOk)){
			Player player =(Player)GameCpHolder.get("player");
			//设置为未准备状态
			player.setUserStatus(0);
			//将房间列表隐藏
			roomListFrame.setVisible(false);
			//看看当时房间里面有没有其他老铁(这是当前用户进入之前的房间状况)
			Map<Integer, Player> roomUsers = (Map<Integer, Player>) requestData.get("roomUsers");
			Player player2 = null;
			if(roomUsers !=null && !roomUsers.isEmpty()){
				//渲染另外一个人
				for (Integer key : roomUsers.keySet()) {
					player2 = roomUsers.get(key);
					break;
				}
			}

			//显示房间
			RoomFrame rf = new RoomFrame(Integer.parseInt(requestData.get("roomNo") + ""));
			GameCpHolder.put("currentRoom", rf);
			System.out.println(GameCpHolder.gameComponets);
			//渲染房间
			RoomDetPanel rdp = (RoomDetPanel)(rf.getContentPane());
			rdp.setPlayer1(player);
			if(player2 != null)rdp.setPlayer2(player2);
			rdp.revalidate();
			//	rdp.repaint();
			//	rdp.repaint(0, 0, rdp.getWidth(), rdp.getHeight()/2);
			//将用户当前所在的房间标记下来
			Constant.BG_MUSIC_PLAYER.loopMusic(Constant.UI_CONSTANT.IN_ROOMBG_MUSIC); 
			System.out.println(info);
		}else{
			JOptionPane.showMessageDialog(roomListFrame, info,"警告", JOptionPane.DEFAULT_OPTION);
			System.out.println(info);
		}
		//将按钮变回"进入"
		RoomEntryButton rb = roomListFrame.getRps()[Integer.parseInt(requestData.get("roomNo") + "") - 1].getReb();
		rb.setFont(new Font("微软雅黑",Font.ITALIC,15));
		rb.setText("进入");
		rb.setForeground(null);
		rb.revalidate();
	}

	/**
	 * 拉取房间列表信息
	 * @param message
	 * @param channel
	 */
	@SocketService(module = 1, cmd = 3)
	public void getRoomInfo(RequestMessage message,Channel channel){
		System.out.println("开始拉取房间列表信息！" + message.getRequestData());
		//  channel.write(JSONUtils.toJSON(message));
		channel.write(message);
	}
	
	
	/**
	 * 刷新房间列表
	 * @param message
	 * @param channel
	 */
	@SocketService(module = 1, cmd = 17)
	public void refreshRoomList(RequestMessage message){
		Channel channel = (Channel) GameCpHolder.get("channel");
		channel.write(message);
	}

	/**
	 * 拉取房间列表响应处理
	 * @param message
	 * @param channel
	 */
	@SocketService(module = 1, cmd = 4)
	public void getRoomInfoRsp(ResponseMessage message,Channel channel){
		@SuppressWarnings("unchecked")
		Map<Integer, Map<Integer, Player>> roomInfo = (Map<Integer, Map<Integer, Player>>) ((Map<String, Object>)message.getRequestData()).get("roomInfo");
		System.out.println("接受到了房间列表信息＝＝＝＝＝＝>" + roomInfo);
		//开始渲染房间列表
		RoomListFrame roomListFrame = (RoomListFrame) GameCpHolder.get("roomListFrame");
		//房间数组
		RoomPanel[] rps = roomListFrame.getRps();
		for(int i = 1;i < 11 ;i ++){
			Map<Integer, Player> room = roomInfo.get(i);
			RoomLabel label = new RoomLabel(i);
			RoomEntryButton rb = new RoomEntryButton("进入",i);
			RoomPanel rp = null;
			if(room == null || room.isEmpty()){
				//这个房间毛人都没有
				label.setText("Room0"+i+":暂无玩家..");
				label.setToolTipText("Room0"+i+":暂无玩家..");
				label.setForeground(Color.green);
				rp = new RoomPanel(i,label, rb);
				//	rp.setBackground(Color.green);
				rp.setRoomState(0);
			}else{
				int size = room.size();
				String desc ="Room0"+i+ ":当前在线" + size + "人";
				String toolTip = "玩家 ";
				rp = new RoomPanel(i,label, rb);
				if(size == 2){
					desc +="(已满)";
					rb.setEnabled(false);  
					//		rp.setBackground(Color.red);
					rp.setRoomState(2);
				}else{
					//		rp.setBackground(Color.yellow);
					rp.setRoomState(1);
				}
				label.setText(desc);
				int index = 0;
				for (Integer key : room.keySet()) {
					toolTip += "," + room.get(key).getUserName();
					index ++;
				}
				if(index == 1){
					label.setToolTipText(toolTip + "正在等待游戏...");
					label.setForeground(Color.yellow);
				}else{
					label.setToolTipText(toolTip + "正在游戏中...");
					label.setForeground(Color.red);
				}

			}
			rps[i-1] = rp;
		}

		//拉取成功，将start按钮禁用
		StartButton sb = (StartButton) GameCpHolder.get("sb");
		sb.setEnabled(false);

		//创建一个头儿
		JPanel head = new JPanel();
		RefreshButton rb = new RefreshButton("刷新列表") ;
		head.setLayout(null);
		head.add(rb);
		roomListFrame.setRb(rb);
		roomListFrame.setHead(head);
		roomListFrame.add(head);
		roomListFrame.getContentPane().add(head);
		roomListFrame.appendRooms();
		//游戏进入到对战模式
		Game  game = (Game) GameCpHolder.get("game");
		game.setGameModel(2);
	}

	
	/**
	 * 刷新房间列表响应处理
	 * @param message
	 * @param channel
	 */
	@SocketService(module = 1, cmd = 18)
	public void refreshRoomInfoRsp(final ResponseMessage message,Channel channel){
		threadPoolExecutor.execute(new Runnable() {
			public void run() {
		
				Map<Integer, Map<Integer, Player>> roomInfo = (Map<Integer, Map<Integer, Player>>) ((Map<String, Object>)message.getRequestData()).get("roomInfo");
				System.out.println("接受到了房间列表信息＝＝＝＝＝＝>" + roomInfo);
				//开始渲染房间列表
				RoomListFrame roomListFrame = (RoomListFrame) GameCpHolder.get("roomListFrame");
				//房间数组
				RoomPanel[] rps = roomListFrame.getRps();
				//重新渲染房间
				int i = 1;
				for (RoomPanel rp : rps) {
					Map<Integer, Player> room = roomInfo.get(i);
					RoomLabel label = rp.getRb();
					RoomEntryButton rb = rp.getReb();
					if(room == null || room.isEmpty()){
						//这个房间毛人都没有
						label.setText("Room0"+i+":暂无玩家..");
						label.setToolTipText("Room0"+i+":暂无玩家..");
						label.setForeground(Color.green);
						rb.setEnabled(true);  
						rp.setRoomState(0);
					}else{
						int size = room.size();
						String desc ="Room0"+i+ ":当前在线" + size + "人";
						String toolTip = "玩家 ";
						if(size == 2){
							desc +="(已满)";
							rb.setEnabled(false);  
							rp.setRoomState(2);
						}else{
							rp.setRoomState(1);
							rb.setEnabled(true);  
						}
						label.setText(desc);
						int index = 0;
						for (Integer key : room.keySet()) {
							toolTip += "," + room.get(key).getUserName();
							index ++;
						}
						if(index == 1){
							label.setToolTipText(toolTip + "正在等待游戏...");
							label.setForeground(Color.yellow);
						}else{
							label.setToolTipText(toolTip + "正在游戏中...");
							label.setForeground(Color.red);
						}

					}
					i ++;
				}
				roomListFrame.getRb().setEnabled(true);
				roomListFrame.getRb().setText("刷新列表");
				roomListFrame.getRb().setForeground(Color.black);
				roomListFrame.getContentPane().revalidate();
			}
		});
		
	}

	/**
	 * 玩家2进入/离开了房间
	 * @param message
	 * @param channel
	 */
	@SocketService(module = 1, cmd = 9)
	public void theOtherLeave(ResponseMessage message,Channel channel){
		Map<String,Object> ret =(Map<String, Object>) message.getRequestData();
		//获取用户当前所在房间
		RoomFrame currentRoom = (RoomFrame)GameCpHolder.get("currentRoom");
		if(currentRoom == null)	JOptionPane.showMessageDialog(null,"Room is missing!","警告", JOptionPane.DEFAULT_OPTION);
		int come = (int) ret.get("come");
		if(come == 0){
			//对手离开			
			((RoomDetPanel)currentRoom.getContentPane()).setPlayer2(null);
		}else if(come == 1){
			//对手进来
			Player player2 = message.getPlayer();
			((RoomDetPanel)currentRoom.getContentPane()).setPlayer2(player2);
		}
		currentRoom.getContentPane().revalidate();
		currentRoom.getContentPane().repaint();
	}

	/**
	 * 准备/取消游戏
	 * @param message
	 * @param channel
	 */
	@SocketService(module = 1, cmd = 5)
	public void readyOrCancelGame(RequestMessage message){
		System.out.println("开始准备或取消！" + message.getRequestData());
		Channel channel = (Channel) GameCpHolder.get("channel");
		//  channel.write(JSONUtils.toJSON(message));
		channel.write(message);
	}


	/**
	 * 获取我准备成功的消息
	 * @param message
	 * @param channel
	 */
	@SocketService(module = 1, cmd = 10)
	public void getMyReadyResponse(ResponseMessage message,Channel channel){
		System.out.println("get到了准备响应！" );
		Map<String,Object> ret = (Map<String, Object>) message.getRequestData();
		RoomFrame currentRoom = (RoomFrame)GameCpHolder.get("currentRoom");
		int flag = (int) ret.get("flag");
		int op = (int) ret.get("op");
		if(flag == 0){
			//失败了
			JOptionPane.showMessageDialog(currentRoom, ret.get("info"),"警告", JOptionPane.DEFAULT_OPTION);
		}else{
			//准备成功！！！
			//将按钮变为取消
			RoomDetPanel rdp = (RoomDetPanel) currentRoom.getContentPane();
			ReadyButton rb = rdp.getRb();
			rb.setStatus(op == 0 ? 1 : 2);
			rb.setText(op == 0 ? "准备" : "取消");
			rb.setEnabled(true);
			//当前用户状态设为1(一准备)
			Player player = (Player) GameCpHolder.get("player");
			player.setUserStatus(op == 0 ? 0 : 1);
			//头像贴上logo（ready）
			rdp.getPlayer1().setUserStatus(op == 0 ? 0 : 1);
			rdp.validate();
			rdp.repaint();
		}

	}


	/**
	 * 获取对方已经准备或者取消的通知
	 * @param message
	 * @param channel
	 */
	@SocketService(module = 1, cmd = 11)
	public void getOtherReadyResponse(ResponseMessage message,Channel channel){
		System.out.println("get到了准备响应！" );
		Map<String,Object> ret = (Map<String, Object>) message.getRequestData();
		RoomFrame currentRoom = (RoomFrame)GameCpHolder.get("currentRoom");
		int op = (int) ret.get("op");
		RoomDetPanel rdp = (RoomDetPanel) currentRoom.getContentPane();
		//头像贴上logo（ready）
		rdp.getPlayer2().setUserStatus(op == 0 ? 0 : 1);
		rdp.validate();
		rdp.repaint();
	}


	/**
	 * 获取两人都已经准备好的通知，可以开始倒计时
	 * @param message
	 * @param channel
	 */
	@SocketService(module = 1, cmd = 12)
	public void getIsAllReady(ResponseMessage message,final Channel channel){
		System.out.println("get到了游戏倒计时响应！" );
		//JOptionPane.showMessageDialog(null, "准备准备，老铁，游戏即将开始！","警告", JOptionPane.DEFAULT_OPTION);
		final RoomFrame currentRoom = (RoomFrame)GameCpHolder.get("currentRoom");
		ShowMessageFrame sf = new ShowMessageFrame(5, currentRoom);
		final Player player =(Player)GameCpHolder.get("player");
		new Thread(new  Runnable() {
			public void run() {
				currentRoom.setEnabled(false);
				try {
					Thread.sleep(6000);
					//向服务器确认游戏开始
					Map<String,Object> map = new HashMap<>();
					map.put("roomNo", currentRoom.getRoomNo());
					player.setUserStatus(4);
					channel.write(new RequestMessage(1,7, player, map));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				currentRoom.setEnabled(true);
			}
		}).start();
	}

	/**
	 * 一切准备就绪，终于可以开始游戏了
	 * @param message
	 * @param channel
	 */
	@SocketService(module = 1, cmd = 13)
	public void startGame(ResponseMessage message, Channel channel){
		//1.将当前的ROOM隐藏
		RoomFrame currentRoom = (RoomFrame)GameCpHolder.get("currentRoom");
		currentRoom.setVisible(false);
		//2.开启一个新的GAMEFRAME
		//先创建一个
		GameDto gameDto = new GameDto();
		gameDto.setGameAct(new GameAct());
		OFrameGame og = new OFrameGame(gameDto);
		GameCpHolder.put("currentLookGamePanel",og);
		//3.开始本地游戏
		GameDto gd = (GameDto) GameCpHolder.get("gameDto");
		//开始游戏
		StartButton sb =  (StartButton) GameCpHolder.get("sb");
		sb.setEnabled(true);
		sb.doClick();
		sb.setEnabled(false);
		Player player =(Player)GameCpHolder.get("player");
		player.setUserStatus(2);
		og.setVisible(true);
	}



	/**
	 * 退出房间请求
	 * @param reqMsg
	 * @param channel
	 */
	@SuppressWarnings("unchecked")
	@SocketService(module = 1,cmd = 6)
	public synchronized  void leaveRoom(RequestMessage reqMsg){
		Channel channel = (Channel) GameCpHolder.get("channel");
		RoomFrame currentRoom = (RoomFrame)GameCpHolder.get("currentRoom");
		Player player = reqMsg.getPlayer();
		if(player.getUserStatus() == 1){
			JOptionPane.showMessageDialog(currentRoom, "系统检测到你正在准备状态，请先取消！","警告", JOptionPane.DEFAULT_OPTION);
			RoomDetPanel rdp = (RoomDetPanel) currentRoom.getContentPane();
			rdp.getRtb().setEnabled(true);
			return;
		}
		channel.write(reqMsg);
	}


	/**
	 * 退出房间响应
	 * @param reqMsg
	 * @param channel
	 */
	@SuppressWarnings("unchecked")
	@SocketService(module = 1,cmd = 8)
	public synchronized  void leaveRoomResp(ResponseMessage message, Channel channel){
		Map<String,Object> ret = (Map<String, Object>) message.getRequestData();
		int leaveOk = (int) ret.get("isOk");
		RoomFrame currentRoom = (RoomFrame)GameCpHolder.get("currentRoom");
		Player player =	(Player) GameCpHolder.get("player");
		RoomListFrame roomListFrame = (RoomListFrame)GameCpHolder.get("roomListFrame");
		if(leaveOk == 0){
			JOptionPane.showMessageDialog(currentRoom, "服务器异常，操作失败！","警告", JOptionPane.DEFAULT_OPTION);
		}else{
			//退回列表，销毁房间，修改玩家状态，
			if(currentRoom != null)currentRoom.dispose();
			player.setUserStatus(3);
			roomListFrame.reSetLocation();
			roomListFrame.setVisible(true);
			Constant.BG_MUSIC_PLAYER.loopMusic(Constant.UI_CONSTANT.BG_MUSIC_01_PATH); 
		}
	}




	/**
	 * 向房间中的对战用户推送游戏实体
	 * @param reqMsg
	 * @param channel
	 */
	@SuppressWarnings("unchecked")
	@SocketService(module = 2,cmd = 1)
	public synchronized  void pushGameDto(RequestMessage reqMsg){
		GameDto dto = (GameDto) GameCpHolder.get("gameDto");
		if(dto.getResult() != 0)return;
		Channel channel = (Channel) GameCpHolder.get("channel");
		RoomFrame currentRoom = (RoomFrame)GameCpHolder.get("currentRoom");
		((Map<String,Object>)reqMsg.getRequestData()).put("roomNo",currentRoom.getRoomNo());
		channel.write(reqMsg);
	}


	/**
	 * 渲染玩家二的famre　
	 * @param reqMsg
	 * @param channel
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unchecked")
	@SocketService(module = 2,cmd = 2)
	public synchronized void renderLookGame(final ResponseMessage message,final Channel channel){
		System.out.println("获取到了玩家２的数据包....");
		Map<String,Object> ret = (Map<String, Object>) message.getRequestData();
		final GameDto dg = (GameDto) ret.get("gameDto");
		//获取当前的玩家２游戏窗口
		OFrameGame og = (OFrameGame) GameCpHolder.get("currentLookGamePanel");
		final OPanelGame opg = og.getPanelGame();
		Thread  t = new Thread(new Runnable() {
			@Override
			public void run() {
				//对面玩家传送过来的游戏实体
				opg.setGameDto(dg);
				opg.resetLayer();
				opg.repaint();
			}
		});
		t.start();
	}

	/**
	 * 跪了的通知　
	 * @param reqMsg
	 * @param channel
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unchecked")
	@SocketService(module = 2,cmd = 5)
	public synchronized void whoWinNotice(ResponseMessage message,Channel channel){
		Map<String,Object> ret = (Map<String, Object>) message.getRequestData();
		final int win = Integer.parseInt(ret.get("win") + "");

		//获取当前的玩家２游戏窗口
		OFrameGame og = (OFrameGame) GameCpHolder.get("currentLookGamePanel");
		final OPanelGame opg = og.getPanelGame();
		//当前的ｄｔｏ
		final GameDto dg = opg.getGameDto();
		final GameDto dto = (GameDto) GameCpHolder.get("gameDto");
		Thread  t = new Thread(new Runnable() {
			@Override
			public void run() {
				//对面玩家传送过来的游戏实体
				if(win == 1){
					//我赢了
					dg.setResult(1);
					dto.setResult(2);
				}else{
					//我输了
					dg.setResult(2);
					dto.setResult(1);
				}
				dg.setIsEnd(1);
				dto.setIsEnd(1);
				opg.setGameDto(dg);
				opg.resetLayer();
				opg.repaint();
			}
		});

		t.start();

	}




	/**
	 * 玩家退回房间里面　
	 * @param reqMsg
	 * @param channel
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unchecked")
	@SocketService(module = 2,cmd = 3)
	public synchronized void goBackRoom(RequestMessage message){
		Channel channel = (Channel) GameCpHolder.get("channel");
		//通知服务端修改玩家状态
		RoomFrame currentRoom = (RoomFrame)GameCpHolder.get("currentRoom");
		Map<String,Object> map = new HashMap<>();
		map.put("roomNo", currentRoom.getRoomNo());
		message.setRequestData(map);
		channel.write(message);
	}


	/**
	 * 玩家退回房间服务器响应结果
	 * @param reqMsg
	 * @param channel
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unchecked")
	@SocketService(module = 2,cmd = 4)
	public synchronized void goBackRoomResp(ResponseMessage message,Channel channel){
		Map<String,Object> ret = (Map<String, Object>) message.getRequestData();
		String isOk = ret.get("backOk") + "";
		RoomFrame currentRoom = (RoomFrame)GameCpHolder.get("currentRoom");
		OFrameGame og = (OFrameGame) GameCpHolder.get("currentLookGamePanel");
		GameDto dto = (GameDto) GameCpHolder.get("gameDto");
		if("1".equals(isOk)){
			//清除当前玩家2界面
			og.dispose();
			//将玩家状态置为未准备
			RoomDetPanel rdp = (RoomDetPanel) currentRoom.getContentPane();
			ReadyButton rb = rdp.getRb();
			currentRoom.setVisible(true);
			rb.doClick();
			dto.superReSet();
			Constant.BG_MUSIC_PLAYER.loopMusic(Constant.UI_CONSTANT.IN_ROOMBG_MUSIC); 
		}else{
			JOptionPane.showMessageDialog(currentRoom, "服务器异常，操作失败！","警告", JOptionPane.DEFAULT_OPTION);
		}
	}

}
