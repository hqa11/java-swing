package server;


import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;

/**
 * netty服务端
 * @author Administrator
 *
 */
public class ServerUtil {
	private ServerUtil(){}
	private static ServerBootstrap bootstrap;
	public static void stopServer(){
		bootstrap.releaseExternalResources();
		bootstrap.getFactory().shutdown();
	}
	
    /**
     * 启动服务
     */
	public static void startUpServer(String host,int port){
		//  服务类
		ServerBootstrap bootstrap = new ServerBootstrap();

		ExecutorService boss = Executors.newCachedThreadPool();
		ExecutorService worker = Executors.newCachedThreadPool();

		//设置nioSocket工厂
		bootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));

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
				pipeline.addLast("Handler",new Handler());
				return pipeline;
			}
		});

		//绑定端口
		bootstrap.bind(new InetSocketAddress(host,port));
		ServerUtil.bootstrap = bootstrap;
		System.out.println("Server has started!");
	}

}
