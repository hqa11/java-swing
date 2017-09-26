package config;

import java.util.List;
import java.util.Map;

import dao.UserDao;

public class GameCache {

	public static volatile List<Map<String, Object>> localUserList;
	
	static{
		
		localUserList = UserDao.getFirstFiveUser();
		
	}
	
	public static void updateLocalUserCache(){
		
		localUserList = UserDao.getFirstFiveUser();
		
	}
}
