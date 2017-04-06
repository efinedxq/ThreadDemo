package com.example.treaddemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StopWatchService extends Service {

	private Thread workThread;

	@Override
	public void onCreate() {
		super.onCreate();
		workThread = new Thread(null, backgroundwork, "WorkThread");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
      super.onStart(intent, startId);
      if(!workThread.isAlive()){
    	  workThread.start();
      }
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		workThread.interrupt();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	private Runnable backgroundwork = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while (!Thread.interrupted()) {
					double randomDouble = Math.random();
//					MainActivity.UpdateGUI(randomDouble);
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
}
