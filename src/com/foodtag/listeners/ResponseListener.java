package com.foodtag.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.net.wifi.ScanResult;


public interface ResponseListener {

    public void onComplete(String response);
    
    public void onIOException(IOException e);

    public void onFileNotFoundException(FileNotFoundException e);

    public void onException(Exception e);
    
    public void onUpdateProgress(int val);

	public void makeToast(String text);

	public void onCompleteSignal(String signalStrength);
	
	public void onFail(String response);
	
	public void onCompleteSummary(JSONObject Object);

	

}