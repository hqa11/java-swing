package server;


import holder.FrameHolder;
import holder.RoomHolder;
import holder.ServiceHolder;
import holder.SocketHolder;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import dto.GameDto;
import dto.RequestMessage;
import service.RemoteService;
import util.JSONUtils;

public class Handler extends IdleStateAwareChannelHandler implements ChannelHandler {
	/**
	 * channel关闭的时候才会触发
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelClosed...");
	}

	/**
	 * 连接建立的时候触发
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		//将此connect装入Map
		Channel channel = ctx.getChannel();
		SocketHolder.put(channel.getId(), channel);
		System.out.println("channelConnected...");
		FrameHolder.consoleAppend("玩家连入了服务器.......",0);
	}

	/**
	 * 必须是连接已经建立，关闭通道的时候才会触发
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		Channel channel = ctx.getChannel();
		SocketHolder.remove(channel.getId());
		Integer roomNo = RoomHolder.getRoomChannelHolder().get(channel.getId());
		if(roomNo == null){
			FrameHolder.consoleAppend("玩家断开了连接.......",0);
			return;
		}
		RoomHolder.getRoomChannelHolder().remove(channel.getId());
		RoomHolder.remove(roomNo,channel.getId());
		System.out.println("channelDisconnected...");
		FrameHolder.consoleAppend("房间号为："+roomNo+"中的玩家断开了连接.......",0);
	}

	/**
	 * 异常状态触发
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		e.getCause().printStackTrace();
		System.out.println("exceptionCaught124..." + e.getCause().getStackTrace());
		FrameHolder.consoleAppend("exceptionCaught.......",1);
	}

	/**
	 * 接受消息时候触发
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		try {
			//获取客户端的请求体
			//			String message = e.getMessage().toString();
			Channel channel = ctx.getChannel();
			//将其转化为实体类
			RequestMessage msg = null;
			try {
				msg = (RequestMessage) e.getMessage();
				FrameHolder.consoleAppend("接收到请求module为"+msg.getModule()+",命令号为"+msg.getCmd()+"的数据包.......",0);
			} catch (Exception e1) {
				channel.write("****ERROR***");
				FrameHolder.consoleAppend("获取到错误格式的数据.,Exception:" + e1.getStackTrace(),1);
				e1.printStackTrace();
				return;
			}
			//目标方法
			ServiceHolder.execute(msg,channel);
			super.messageReceived(ctx, e);
		} catch (Exception e1) {
			e1.printStackTrace();
			FrameHolder.consoleAppend("业务方法执行错误,Exception:" + e1.getStackTrace(),1);
		}
	}

}
