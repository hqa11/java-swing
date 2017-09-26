package util;

import java.io.File;

public class ResourceUtil {

	/**
	 * 获取项目的根路径
	 * @return
	 */
	public static String getProPath(){
		String binPath = ResourceUtil.class.getClassLoader().getResource("").getPath();
		String parentPath = new File(binPath).getParent();
		return parentPath;
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println(ResourceUtil.getProPath() + "/config/teris.txt");
		
	}
}
