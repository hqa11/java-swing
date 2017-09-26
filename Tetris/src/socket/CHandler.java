package socket;

import javax.swing.JOptionPane;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import config.GameCpHolder;
import dto.ResponseMessage;
import holder.ServiceHolder;
import ui.FrameGame;

public class CHandler extends SimpleChannelHandler {

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
		System.out.println("channelConnected...");
	}

	/**
	 * 必须是连接已经建立，关闭通道的时候才会触发
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelDisconnected...");
		FrameGame fg = (FrameGame)GameCpHolder.get("frameGame");
		if(fg != null)JOptionPane.showMessageDialog(fg,"糟糕，你似乎已经掉线了!","警告", JOptionPane.DEFAULT_OPTION);
	}

	/**
	 * 异常状态触发
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		e.getCause().printStackTrace();
		System.out.println("exceptionCaught333..."+e.getCause().getStackTrace());
	}

	/**
	 * 接受消息时候触发
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		System.out.println("client messageReceived...");
		try {
			//获取客户端的请求体
			String message = e.getMessage().toString();
			Channel channel = ctx.getChannel();
			//将其转化为实体类
			ResponseMessage msg = null;
			try {
			//	msg = JSONUtils.fromJSON(message,ResponseMessage.class);
				msg = (ResponseMessage) e.getMessage();
			} catch (Exception e1) {
				channel.write("****ERROR***");
				e1.printStackTrace();
				return;
			}
			//目标方法
			ServiceHolder.executeRsp(msg,channel);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
