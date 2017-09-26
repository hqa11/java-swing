package socket;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import config.Constant;

/**
 * netty客户端
 * @author Administrator
 *
 */
public class ClientFactory {
public static Channel getChannel(){
//  客户端类
	ClientBootstrap bootstrap = new ClientBootstrap();
	
	//线程池
	ExecutorService boss = Executors.newCachedThreadPool();
	ExecutorService worker = Executors.newCachedThreadPool();
	
	//设置nioSocket工厂
	bootstrap.setFactory(new NioClientSocketChannelFactory(boss, worker));

    //设置管道工厂
	bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
		
		@Override
		public ChannelPipeline getPipeline() throws Exception {
			
			//获取一个管道
			ChannelPipeline pipeline = Channels.pipeline();
			
			//可以避免在接受消息的时候转为channelbuffer,直接使用string即可
			/*pipeline.addLast("decoder",new StringDecoder());
			//可以避免在发送消息的时候使用channelBuffer,直接使用string即可
			pipeline.addLast("encoder",new StringEncoder());*/
			pipeline.addLast("decoder",new ObjectDecoder(ClassResolvers.cacheDisabled(this
	                .getClass().getClassLoader())));
			pipeline.addLast("encoder",new ObjectEncoder());
			pipeline.addLast("HelloHandler",new CHandler());
			
			return pipeline;
		}
	});
	
	//绑定端口
	ChannelFuture connect = bootstrap.connect(new InetSocketAddress(Constant.NETTY_HOST,Constant.NETTY_PORT));
	
	//此处也可以从handler的ctx中获取
	Channel channel = connect.getChannel();
	
	return channel;
}
	
public static void main(String[] args) {
 
	Channel channel = ClientFactory.getChannel();
	
	Scanner sc = new Scanner(System.in); 
	
	
	System.out.println("Client has be ready to connect to Server!");

	while(true){
		
		System.out.println("请输入您要发送的消息:");
		
		String msg = sc.next();

		channel.write(msg);
		
	}
	
}
}
