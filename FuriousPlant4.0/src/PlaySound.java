
import java.io.FileInputStream;
import java.io.InputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.*;

public class PlaySound{
	private String filePath;
	private AdvancedPlayer ap = null;
	private boolean loop = false;
	private InputStream is = null;
	private MusicThread mt = new MusicThread();
	    
	public PlaySound(String filePath,boolean loop) {
	    this.filePath = filePath;
	    this.loop = loop;
	    try {
	    	is=new FileInputStream(filePath);
	    	ap = new AdvancedPlayer(is);
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	}

	//音乐线程类
	private class MusicThread extends Thread {
		public void run() {
			do {
				try {
					is = new FileInputStream(filePath);
					ap = new AdvancedPlayer(is);
					ap.play();
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}catch (Exception e) {
				    e.printStackTrace();
				}
			} while (loop);
		}
	
	}

	//播放
	public void play() {
	    mt.start();
	}
	
	//停止
	public void stop() {
		mt.stop();
		loop = false;
	}

}
