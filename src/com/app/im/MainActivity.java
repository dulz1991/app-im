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
        initSettings();//��ʼ��settings����
        webview.loadUrl("http://192.168.1.111:8200/auth/home/index"); //������Ҫ��ʾ����ҳ 
        webview.setWebViewClient(new IMWebViewClient()); //����Web��ͼ  
    }
    
    private void initSettings(){
    	settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);  //����JS�ű�
        settings.setBuiltInZoomControls(true);//������������װ��
        settings.setSupportZoom(true);//֧������
        //��ҳ��ʱ�� ����Ӧ��Ļ
        settings.setUseWideViewPort(true); 
        settings.setLoadWithOverviewMode(true);
    }
    
    /**
     * Web��ͼ  
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
     * �˵���
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    /**
     * �����˵�
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
     *���û���  
     *����Activity���onKeyDown(int keyCoder,KeyEvent event)���� 
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
        	if(webview.canGoBack()){ //������Է�����һҳ
        		webview.goBack(); //goBack()��ʾ����WebView����һҳ��  
                return true;
        	} else { //�����ʾ�Ѿ�����ҳ�ˣ��ж��Ƿ�˫���˳�
        		exitBy2Click();	
        	}
        }
        return false;
    }
    
    /**
     * ˫���˳�����
     */
    private void exitBy2Click() {
    	Timer tExit = null;
    	if (IS_EXIT == false) {
    		IS_EXIT = true; // ׼���˳�
    		Toast.makeText(this, "Click ones to exit", Toast.LENGTH_SHORT).show();
    		tExit = new Timer();
    		tExit.schedule(new TimerTask() {
    			@Override
    			public void run() {
    				IS_EXIT = false; // ȡ���˳�
    			}
    		}, 2000); // ���2������û�а��·��ؼ�����������ʱ��ȡ�����ղ�ִ�е�����
    	} else {
    		finish();
    		System.exit(0);
    	}
    }
    
}
