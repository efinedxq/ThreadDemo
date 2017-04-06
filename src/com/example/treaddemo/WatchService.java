package com.example.treaddemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class WatchService extends Service {

	private int watch = 0;

	private char THREAD_STATUS = 0; // 0初始，1运行，2暂停

	private final IBinder mBinder = new LocalBBinder();

	private Thread workThread;

	// 只要创建服务，就开启进程
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("debug WatchService", "WatchService onCreate()");
		workThread = new Thread(null, backgroundwork, "WorkThread");
		if (!workThread.isAlive()) {
			workThread.start();
		}
	}

	public class LocalBBinder extends Binder {
		WatchService getService() {
			Log.d("debug WatchService", "WatchService getService()创建对象");
			return WatchService.this;
		}
	}

	// 绑定服务
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("debug WatchService", "onBind()");
		return mBinder;
	}

	// 取消绑定
	@Override
	public boolean onUnbind(Intent intent) {
		Log.d("debug WatchService", "onUnbind()");
		return false;
	}

	public void start() {
		Log.d("debug watchService", "start()");
		THREAD_STATUS = 1;
	}

	// 秒表加速，一次加 10
	public void speed() {
		Log.d("debug watchService", "speed()");
		THREAD_STATUS = 2;
		watch += 10;
		THREAD_STATUS = 1;
	}

	// 秒表停止
	public void stop() {
		Log.d("debug watchService", "stop()");
		THREAD_STATUS = 2;
	}

	public void reStart() {
		Log.d("debug watchService", "reStart()");
		THREAD_STATUS = 0;
		watch = 0;
		String str = String.valueOf(watch);
		MainActivity.UpdateGUI(str);
	}

	// 后台服务线程
	private Runnable backgroundwork = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while (true) {
					while(THREAD_STATUS==1){
					Log.d("debug watchService", "watch:"+watch);
					watch++;
					String str = String.valueOf(watch);
					MainActivity.UpdateGUI(str);
					Thread.sleep(1000);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
}
