package com.example.treaddemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class WatchService extends Service{

	private int watch = 0;
	
	private final IBinder mBinder = new LocalBBinder();
	
	private Thread workThread;
	
	//ֻҪ�������񣬾Ϳ�������
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
	
	//�󶨷���
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}
    //ȡ����
	@Override
	public boolean onUnbind(Intent intent){
		return false;
	}
	
	public void start(){
	   if(!workThread.isAlive()){
	      workThread.start();
	   }
	}
	//�����٣�һ�μ� 10
	public void speed(){
		workThread.interrupt();
		watch+=10;
		workThread.start();
	}
	//���ֹͣ
	public void stop(){
		workThread.interrupt();
	}
	
	public void reStart(){
		workThread.interrupt();
		watch = 0;
		workThread.start();
	}
	//��̨�����߳�
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
