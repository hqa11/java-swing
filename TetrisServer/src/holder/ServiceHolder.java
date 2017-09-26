package holder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.channel.Channel;

import dto.RequestMessage;

import service.RemoteService;
import annotions.SocketService;

/**
 * 业务执行器
 * @author Administrator
 *
 */
public final class ServiceHolder {
	
	
	public static volatile Map<Integer,Map<Integer,Method>> serviceHolder = new ConcurrentHashMap<>();
    private static final RemoteService rs = new RemoteService();
    
    
	public static void put(Integer module ,Integer cmd,Method method){
		Map<Integer,Method> cmdServices = serviceHolder.get(module);
		if(cmdServices == null)cmdServices = new HashMap<Integer, Method>();
		cmdServices.put(cmd,method);
		serviceHolder.put(module, cmdServices);
	}
	
	public static Method get(Integer module,Integer cmd){
		Map<Integer,Method> cmdServices = serviceHolder.get(module);
		if(cmdServices == null)return null;
		return cmdServices.get(cmd);
	}

	/**
	 * 载入service方法
	 * @throws Exception 
	 */
	public static void loadServiceMethods() throws Exception{
		Class<?> clazz = Class.forName(RemoteService.class.getName());
		//获取此类所有方法
		Method[] methods = clazz.getDeclaredMethods();
		if(methods != null){
			for (Method method : methods) {
				SocketService socketService = method.getAnnotation(SocketService.class);
				if(socketService != null){
					if(ServiceHolder.serviceHolder.containsKey(socketService.module())){
							if(ServiceHolder.serviceHolder.get(socketService.module()).containsKey(socketService.cmd())){
								throw new Exception("--ERROR--业务方法模块号重复！装载初始化失败！");
							}
					}
					ServiceHolder.put(socketService.module(), socketService.cmd(), method);
				}
			}
		}
	}
     
	/**
	 * 执行目标方法
	 * @param msg
	 * @param channel 
	 * @throws Exception 
	 */
	public static void execute(Object... args) throws Exception {
		RequestMessage msg = (RequestMessage)args[0];
		if(msg == null)throw new Exception("无效参数！");
		Method method = ServiceHolder.get(msg.getModule(),msg.getCmd());
		if(method == null)throw new Exception("找不到目标业务方法！");
		method.invoke(rs,args);
	}
}
