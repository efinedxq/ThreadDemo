package com.example.treaddemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

public class MainActivity extends Activity {
   
	private WatchService watchService;
	
	private static TextView stopWatch;
    private static Handler handler = new Handler();
    private static String watch;
    private boolean isBound = false;
    
    
    public static void UpdateGUI(String str){
    	watch = str;
    	handler.post(RefreshLable);
    	
    	Log.d("debug MainActivity", "UpdateGUI()执行");
    }
       
    private static Runnable RefreshLable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d("debug MainActivity", "RefreshLable  run()");
			
			stopWatch.setText(watch);
		}
	};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stopWatch = (TextView)findViewById(R.id.textView1);
        Button start = (Button)findViewById(R.id.start);
        Button stop = (Button)findViewById(R.id.stop);
        Button speed = (Button)findViewById(R.id.speed);
        Button  reStart = (Button)findViewById(R.id.reStart);
        
        final Intent serviceIntent = new Intent(MainActivity.this,WatchService.class);
        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
        
        
        start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if(!isBound){
//					final Intent serviceIntent = new Intent(MainActivity.this,WatchService.class);
//			        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
//					isBound = true;
//				}
				if(watchService==null){
		    		Log.d("debug MainActivity", "watchService状态：未绑定");
		    		return;
		    	}
				watchService.start();
			}
		});
        
        
        speed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(watchService==null){
		    		Log.d("debug MainActivity", "watchService状态：未绑定");
		    		return;
		    	}
				watchService.speed();
			}
		});
        
        stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(watchService==null){
		    		Log.d("debug MainActivity", "watchService状态：未绑定");
		    		return;
		    	}
				watchService.stop();
			}
		});
        
        reStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(watchService==null){
		    		Log.d("debug MainActivity", "watchService状态：未绑定");
		    		return;
		    	}
				watchService.reStart();
			}
		});
    }
    
    private ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			watchService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			Log.d("debug MainActivity", "onServiceConnected() 回调函数 开始");
			watchService = ((WatchService.LocalBBinder)service).getService();
			Log.d("debug MainActivity", "onServiceConnected() 回调函数 结束");
		}
	};
}
