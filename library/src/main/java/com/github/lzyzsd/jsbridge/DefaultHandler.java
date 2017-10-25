package com.github.lzyzsd.jsbridge;

import android.util.Log;

public class DefaultHandler implements BridgeHandler{

	String TAG = "DefaultHandler LiuTag";
	
	@Override
	public void handler(String data, CallBackFunction function) {
        Log.d(TAG, "handler: data = " + data);
        if(function != null){
			function.onCallBack("DefaultHandler response data");
		}
	}

}
