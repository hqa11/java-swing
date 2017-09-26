package ui;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class ShowMessageFrame extends JDialog {
    private JLabel text;
    private String str = null;
    private int second = 0;
    private Component parent; ;
    public ShowMessageFrame(int second,Component parent) {
    	this.second = second;
        this.str = str;
        this.parent = parent;
        this.setAlwaysOnTop(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initGUI();
            }
        }).start();
    }

    private void initGUI() {
        try {
        //  setUndecorated(true);
            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            {
                text = new JLabel("<html>" + str + "</html>", JLabel.CENTER);
                getContentPane().add(text, BorderLayout.CENTER);
            }
            pack();
            setBounds(parent.getX() +(parent.WIDTH-this.WIDTH)/2+65,parent.getY() + (parent.HEIGHT - this.HEIGHT)/2+100,275, 125);
           while(second >= 0){
        	   try {
        		    text.setText("<html>您的游戏将在" + second + "秒之后自动开始.....</html>");
        		    second -- ;
                   Thread.sleep(1000);
               } catch (InterruptedException e1) {
                   e1.printStackTrace();
               }
           }
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
	}
    
}