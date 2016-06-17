package com.app.im;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private WebView webview;
	private WebSettings settings; 
	
	private static Boolean IS_EXIT = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        webview = (WebView) findViewById(R.id.webView);
        initSettings();//初始化settings设置
        webview.loadUrl("http://192.168.1.111:8200/auth/home/index"); //加载需要显示的网页 
        webview.setWebViewClient(new IMWebViewClient()); //设置Web视图  
    }
    
    private void initSettings(){
    	settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);  //启用JS脚本
        settings.setBuiltInZoomControls(true);//启用内置缩放装置
        settings.setSupportZoom(true);//支持缩放
        //打开页面时， 自适应屏幕
        settings.setUseWideViewPort(true); 
        settings.setLoadWithOverviewMode(true);
    }
    
    /**
     * Web视图  
     * @author Administrator
     */
    private class IMWebViewClient extends WebViewClient {  
        @Override 
        public boolean shouldOverrideUrlLoading(WebView view, String url) {  
            view.loadUrl(url);  
            return true;  
        }  
    } 

    /**
     * 菜单项
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    /**
     * 触发菜单
     */
    @Override    
    public boolean onOptionsItemSelected(MenuItem item) {    
    	switch(item.getItemId()){    
    	case R.id.action_settings:
    		return false;
    	case R.id.action_exit: 
    		super.finish();  
    		System.exit(0);  
    		return true;  
    	default:    
    		return false;
    	}
    }
    
    /**
     *设置回退  
     *覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法 
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
        	if(webview.canGoBack()){ //如果可以返回上一页
        		webview.goBack(); //goBack()表示返回WebView的上一页面  
                return true;
        	} else { //否则表示已经在主页了，判断是否双击退出
        		exitBy2Click();	
        	}
        }
        return false;
    }
    
    /**
     * 双击退出函数
     */
    private void exitBy2Click() {
    	Timer tExit = null;
    	if (IS_EXIT == false) {
    		IS_EXIT = true; // 准备退出
    		Toast.makeText(this, "Click ones to exit", Toast.LENGTH_SHORT).show();
    		tExit = new Timer();
    		tExit.schedule(new TimerTask() {
    			@Override
    			public void run() {
    				IS_EXIT = false; // 取消退出
    			}
    		}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
    	} else {
    		finish();
    		System.exit(0);
    	}
    }
    
}
