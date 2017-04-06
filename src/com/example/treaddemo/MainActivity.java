package com.example.treaddemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
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
    
    
    public static void UpdateGUI(String str){
    	watch = str;
    	handler.post(RefreshLable);
    }
       
    private static Runnable RefreshLable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
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
        
        final Intent serviceIntent = new Intent(this,WatchService.class);
        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
        
        
        
        start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				watchService.start();
			}
		});
        
        
        speed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				watchService.speed();
			}
		});
        
        stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				watchService.stop();
			}
		});
        
        reStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
			watchService = ((WatchService.LocalBBinder)service).getService();
		}
	};

}
