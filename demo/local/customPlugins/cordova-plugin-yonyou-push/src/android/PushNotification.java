package com.yyuap.upush.plugin.pushnotification;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myuapp.PushServiceListenService;
import com.example.myuapp.PushServiceManager;
import com.yonyou.protocol.Constants.Notificatin;


public class PushNotification extends CordovaPlugin 
{
	private BroadcastReceiver receiver = null;
    private CallbackContext pushCallbackContext = null;
    
	public static PushNotification instance;
		
	public PushNotification() {
		instance = this;
	}
	
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException
	{
		if(action.equals("init"))
		{
	        
	         PushServiceManager.setDebugMode(true); 

	         PushServiceManager.init(cordova.getActivity().getApplicationContext());
	         
	         PushServiceManager.SetNotification.setContentTitle(Notificatin.NOTIFICATION_TITLE);

            SharedPreferences userSettings = cordova.getActivity().getApplicationContext().getSharedPreferences("device", 0);
	        String dId = userSettings.getString("deviceId",PushServiceManager.getDeviceId());
	        PushServiceManager.setDeviceId(dId);
	 		// android5.0后不支持
	 		// Intent intent = new Intent(PushServiceListenService.getAction());
	 		Intent intent = new Intent(cordova.getActivity(),PushServiceListenService.class);
	 		cordova.getActivity().startService(intent);
	        System.out.println("Service---------------------------------------"); 
            return true;
		}
		if (action.equals("setDeviceId")){
			String deviceId = args.getString(0);
			Toast.makeText(cordova.getActivity().getApplicationContext(), deviceId, 1).show();    
            PushServiceManager.setDeviceId(deviceId);
            Intent intent = new Intent(PushServiceListenService.getAction());
            cordova.getActivity().stopService(intent);
            cordova.getActivity().startService(intent);
            SharedPreferences userSettings = cordova.getActivity().getApplicationContext().getSharedPreferences("device", 0);
	        SharedPreferences.Editor editor = userSettings.edit(); 
	        editor.putString("deviceId",deviceId);
	        editor.commit();
            return true;
        }
        if (action.equals("startService")){
            Intent intent = new Intent(PushServiceListenService.getAction());
            cordova.getActivity().startService(intent);
            return true;
        }
        if (action.equals("stopService")){
            Intent intent = new Intent(PushServiceListenService.getAction());
            cordova.getActivity().stopService(intent);
            return true;
        }
		return false;
	}
	
	
	
	@SuppressWarnings("deprecation")
	public static void transmitOpen(String title,
			String description,String value) {
	
		if (instance == null) {
			return;
		}
		
		if(value==null)
		{
			value = "";
		}
		
		JSONObject data = new JSONObject();
		try {
			
			data.put("title", title);
			data.put("description", description);
			data.put("value", value);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// String js = String
		// 		.format("fastgoPushNotification.openNotificationInAndroidCallback('%s');",
		// 				data.toString());
        String js = String.format(
                "cordova.fireDocumentEvent('upush.NotificationReceived',{'upushNotification':'%s'})",
                data.toString());
        
		try {
//			webview.sendJavascript(js);
			instance.webView.sendJavascript(js);
			

		} catch (NullPointerException e) {

		} catch (Exception e) {

		}
		
	}
	
	
}
