package holder;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ui.ServerFrame;
/**
 * 游戏组件容器(集成板)
 * @author Hqa
 * @date 2017-7-4 
 * @email 18806278373@sina.cn
 * @to TODO
 */
public final class FrameHolder {
	public static volatile ServerFrame sf;
	public static void setSf(ServerFrame sf) {
		FrameHolder.sf = sf;
	}
	public static ServerFrame getSf() {
		return sf;
	}
	
	public static void clearConsole(){
		if(sf ==null) return;
		JTextArea console = sf.getConsole();
		console.setText("");
	}
	public static void consoleAppend(String str,int code){
		if(sf ==null) return;
		JTextArea console = sf.getConsole();
		if(code == 0){
			console.setForeground(Color.black);
		}else if(code ==1){
			console.setForeground(Color.red);
		}else{
			console.setForeground(Color.green);
		}
		if(console.getText().length() > 100000)console.setText("");
		console.append("\n"+str);
		moveViewDown(console);
	}
	
	public static void moveViewDown(	JTextArea console){
		if(sf ==null) return;
		JScrollPane jsp = sf.getJsp();
		int height = 10;
		Point p = new Point();
		p.setLocation(0, console.getLineCount() * height);
		jsp.getViewport().setViewPosition(p);
	}
}
