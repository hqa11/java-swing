package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import config.GameCache;
import util.DataUtil;
import util.JSONUtils;
import util.ResourceUtil;

public class UserDao {
	
	public static void saveUser(Map<String,Object> map){
		try {
			File file = new File(ResourceUtil.getProPath() + "/config/teris.txt");
			if(!file.exists()){
				file.createNewFile();
			}
			InputStream is =new FileInputStream(file);
			//获取原有内容
			byte[] b = new byte[1024];
			String cont = "";
			while(is.read(b, 0, 1024)!=-1){
				cont+=new String(b);
			}
			is.close();
			if(cont.length() > 10000){
				file.delete();
				file.createNewFile();
			}
			List<Map<String,Object>> list = new ArrayList<>(); 
			if(!"".equals(cont.trim()) && !"null".equals(cont.trim()))list = JSONUtils.fromJSON(cont, List.class);
			//将文件写入记录
			list.add(map);
			//从大到小排列
					Collections.sort(list,new Comparator<Map<String,Object>>() {
						@Override
						public int compare(Map<String, Object> o1, Map<String, Object> o2) {
							if((int)o1.get("score") > (int)o2.get("score"))	return -1;
							return 1;
						};
					});
			//只留100个最高分的
			list = list.subList(0,list.size() > 100 ? 100 : list.size());
			String tar = JSONUtils.toJSON(list);
			OutputStream os = new FileOutputStream(file);
			os.write(tar.getBytes());
			os.close();
			System.out.println("存储成功！");
			//更新缓存
			GameCache.updateLocalUserCache();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public  static List<Map<String,Object>> getFirstFiveUser(){
		try {
			File file = new File(ResourceUtil.getProPath() + "/config/teris.txt");
			if(!file.exists()){
				return null;
			}
			InputStream is =new FileInputStream(file);
			//获取原有内容
			byte[] b = new byte[1024];
			String cont = "";
			while(is.read(b, 0, 1024)!=-1){
				cont+=new String(b);
			}
			is.close();
			if("".equals(cont.trim()) || "null".equals(cont.trim()))return null;
			List<Map<String,Object>> list = JSONUtils.fromJSON(cont, List.class);
			//从大到小排列
			Collections.sort(list,new Comparator<Map<String,Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					if((int)o1.get("score") > (int)o2.get("score"))	return -1;
					return 1;
				};
			});
			return list.subList(0,list.size() > 5? 5 : list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}  		
}
