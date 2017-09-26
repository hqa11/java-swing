package Thread;

import control.MusicEffectControl;

public class MusicEffectThread extends Thread{

	private MusicEffectControl musicControl;

	private String filePath;

	public MusicEffectThread(MusicEffectControl musicControl,String filePath){

		this.musicControl = musicControl;
		this.filePath = filePath;

	}

	private void doStart(){

		try {
			this.musicControl.setCurrentMusic(MusicEffectControl.class.getClassLoader().getResourceAsStream(filePath));
			this.musicControl.setCurrentThread(this);
			this.musicControl.getMEDIA_PLAYER().start(musicControl.getCurrentMusic());
		} catch (Exception e) {
			e.printStackTrace();
		} 


	}

	public void doEnd(){

		this.musicControl.getMEDIA_PLAYER().stop(musicControl.getCurrentMusic());
		
        
	}

	@Override
	public void run() {
	
			doStart();		


	}

}
