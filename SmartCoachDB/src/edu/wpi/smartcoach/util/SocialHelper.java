package edu.wpi.smartcoach.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.google.gson.Gson;

import edu.wpi.smartcoach.model.QuestionResponseOutline;
import edu.wpi.smartcoach.model.SocialNetworkSubmission;

public class SocialHelper {
	
	public interface SuggestionListener{
		public void suggestionsRecieved(String[] solutions);
	}
	
	private static final String TAG = SocialHelper.class.getSimpleName();
	
	private static final int PORT = 1337;
	private static final String SERVER_ADDRESS = "http://130.215.79.136";//"http://192.168.1.152";//"chickenbellyfinn.com";
	
	public static void submitSolution(final SocialNetworkSubmission submission){
		new Thread(){
			
			@Override
			public void run(){
				
				Gson gson = new Gson();
				String json = gson.toJson(submission);
				Log.d(TAG, json);
				
				HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost(SERVER_ADDRESS+"/submit");

			    try {
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			        nameValuePairs.add(new BasicNameValuePair("submission", json));
			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			        // Execute HTTP Post Request
			        HttpResponse response = httpclient.execute(httppost);
			        
			    } catch (Exception e) {
			    	e.printStackTrace();
			    } 
				
//				try {				
//					Socket server = new Socket(SERVER_ADDRESS, PORT);
//					server.getOutputStream().write(json.getBytes());
//					server.close();
//				} catch(Exception e){
//					e.printStackTrace();
//				}
				
			}
			
		}.start();
	}
	
	public static void getSuggestions(final String category, final QuestionResponseOutline[] path, final SuggestionListener listener){
		new Thread(){
			
			@Override
			public void run(){
				
				Gson gson = new Gson();
				String json = gson.toJson(path);
				Log.d(TAG, json);
				
				HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost(SERVER_ADDRESS+"/suggest");

			    try {
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			        nameValuePairs.add(new BasicNameValuePair("category", category));
			        nameValuePairs.add(new BasicNameValuePair("path", json));
			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			        // Execute HTTP Post Request
			        HttpResponse response = httpclient.execute(httppost);
			        
			        Scanner sc = new Scanner(response.getEntity().getContent());
			        String res = "";
			        while(sc.hasNextLine()){
			        	res += sc.nextLine();
			        }
			        Log.d(TAG, "response="+res);
			        String[] solutions = new Gson().fromJson(res, new String[]{}.getClass());
			        			        
			        listener.suggestionsRecieved(solutions);
			        
			    } catch (Exception e) {
			    	e.printStackTrace();
			    } 
				
//				try {				
//					Socket server = new Socket(SERVER_ADDRESS, PORT);
//					server.getOutputStream().write(json.getBytes());
//					server.close();
//				} catch(Exception e){
//					e.printStackTrace();
//				}
				
			}
			
		}.start();
	}

}

