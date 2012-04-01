package com.foodtag.tasks;

import java.util.HashMap;

import org.json.JSONObject;

import com.foodtag.listeners.ResponseListener;
import com.foodtag.util.HTTPUtil;

import android.content.Context;

public class PostPicture extends ServerTask{

	

	public PostPicture(Context context,
			ResponseListener listener) {
		super(context, new HashMap<String,String>(), listener);

	}

	public void killAll(){
		try{
			
		}
		catch(Exception e){

		}
	}

	public void runTask() {

		HTTPUtil http = new HTTPUtil();

		try {
			String output = http.request(this.getReqParams(), "GET", "summary", "", "".toString());
			JSONObject object = new JSONObject(output);
	
			getResponseListener().onCompleteSummary(object);

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	@Override
	public String toString() {
		return "Summary Task";
	}
}