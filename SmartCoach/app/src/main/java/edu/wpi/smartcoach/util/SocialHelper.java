package edu.wpi.smartcoach.util;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.wpi.smartcoach.model.SocialNetworkSubmission;

/**
 * Functions to send and recieve suggestions from the "social network" server
 */
public class SocialHelper {

	private static final String TAG = SocialHelper.class.getSimpleName();

	private static final String SERVER_ADDRESS = "http://104.236.100.71";
	
	public static void submitSolution(final SocialNetworkSubmission submission){
		new Thread(){
			
			@Override
			public void run(){
				
				Gson gson = new Gson();
				String json = gson.toJson(submission);
				
				HttpClient client = new DefaultHttpClient();
			    HttpPost request = new HttpPost(SERVER_ADDRESS+"/submit");

			    try {
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			        nameValuePairs.add(new BasicNameValuePair("submission", json));
			        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			        // Execute HTTP Post Request
			        HttpResponse response = client.execute(request);
			        
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
			}
		}.start();
	}
	
	public static void getSuggestions(final String category, final List<String> conditions, final Callback<String[]> listener){
		new Thread(){
			
			@Override
			public void run(){
				
				Gson gson = new Gson();
				String json = gson.toJson(conditions);
				
				HttpClient client = new DefaultHttpClient();
			    HttpPost request = new HttpPost(SERVER_ADDRESS+"/suggest");

			    try {
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			        nameValuePairs.add(new BasicNameValuePair("category", category));
			        nameValuePairs.add(new BasicNameValuePair("conditions", json));
			        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			        // Execute HTTP Post Request
			        HttpResponse response = client.execute(request);
			        
			        Scanner sc = new Scanner(response.getEntity().getContent());
			        String res = "";
			        while(sc.hasNextLine()){
			        	res += sc.nextLine();
			        }
			        Log.d(TAG, "response="+res);
			        String[] solutions = new Gson().fromJson(res, new String[]{}.getClass());
			        			        
			        listener.callback(solutions);
			        
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
			}
			
		}.start();
	}

}

