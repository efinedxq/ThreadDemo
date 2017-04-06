package com.example.treaddemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class WatchService extends Service{

	private int watch = 0;
	
	private final IBinder mBinder = new LocalBBinder();
	
	private Thread workThread;
	
	//只要创建服务，就开启进程
	@Override
	public void onCreate() {
		super.onCreate();
		workThread = new Thread(null, backgroundwork, "WorkThread");
	}
	
	public class LocalBBinder extends Binder{
		WatchService getService(){
			return WatchService.this;
		}
	}
	
	//绑定服务
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}
    //取消绑定
	@Override
	public boolean onUnbind(Intent intent){
		return false;
	}
	
	public void start(){
	   if(!workThread.isAlive()){
	      workThread.start();
	   }
	}
	//秒表加速，一次加 10
	public void speed(){
		workThread.interrupt();
		watch+=10;
		workThread.start();
	}
	//秒表停止
	public void stop(){
		workThread.interrupt();
	}
	
	public void reStart(){
		workThread.interrupt();
		watch = 0;
		workThread.start();
	}
	//后台服务线程
	private Runnable backgroundwork = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while (!Thread.interrupted()) {
					watch ++;
					String str = String.valueOf(watch);
					MainActivity.UpdateGUI(str);
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
}
