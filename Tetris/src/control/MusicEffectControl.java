package control;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import Thread.MusicEffectThread;
import sun.audio.AudioPlayer;

/**
 *　游戏音乐控制器
 * @author Administrator
 *
 */
public class MusicEffectControl{

	private  InputStream currentMusic;

	private Thread currentThread;

	private AudioPlayer MEDIA_PLAYER = AudioPlayer.player;

	//整一个线程池
	ExecutorService cachedThreadPool = Executors.newCachedThreadPool(); 
	
	public void setCurrentMusic(InputStream currentMusic) {
		this.currentMusic = currentMusic;
	}
	public InputStream getCurrentMusic(){

		return this.currentMusic;
	}

	private MusicEffectControl(){}


	public static MusicEffectControl getInstance(){


		return new MusicEffectControl();
	}

	/**
	 * 播放音乐
	 * @param MusicName
	 */
	public  synchronized void playMusic(final String filePath){
		

			try {
				if(currentThread != null && currentThread.isAlive())((MusicEffectThread)currentThread).doEnd();
				if(this.currentMusic != null)currentMusic.close();
				Runnable musicThread = new MusicEffectThread(this,filePath);
				cachedThreadPool.execute(musicThread);
			} catch (IOException e) {
				e.printStackTrace();
			}


	}

	/**
	 * 停止音乐
	 * @param MusicName
	 */
	public  void stopMusic(){

		((MusicEffectThread)currentThread).doEnd();

	}



	public static void main(String[] args) throws InterruptedException, IOException {
		MusicEffectControl c = new MusicEffectControl();
		c.playMusic("music/5.wav");
		Thread.sleep(5000);
		MusicEffectControl c2 = new MusicEffectControl();
		c2.playMusic("music/5.wav");
	}



	public Thread getCurrentThread() {
		return currentThread;
	}



	public void setCurrentThread(Thread currentThread) {
		this.currentThread = currentThread;
	}



	public AudioPlayer getMEDIA_PLAYER() {
		return MEDIA_PLAYER;
	}



	public void setMEDIA_PLAYER(AudioPlayer mEDIA_PLAYER) {
		MEDIA_PLAYER = mEDIA_PLAYER;
	}



}
