package edu.wpi.smartcoach.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.util.Calendar;

import edu.wpi.smartcoach.R;

/**
 * Abstractions for Fitbit API
 */
public class FitbitHelper {

    private static final String TAG = FitbitHelper.class.getSimpleName();

    private static final String PREF_KEY_ACCESS_TOKEN = "fitbit_access_token";
    private static final String PREF_KEY_ACCESS_TOKEN_SECRET = "fitbit_access_token_secret";

    private static final String CLIENT_KEY = "10afc04216c74a8aa6f60352a715124b";
    private static final String CLIENT_SECRET = "da0ec9aee4f44d519937fe56fe6bbc71";

    private static final String JS_GET_ACCESS_TOKEN = "javascript:FitbitHelper.submitPin(document.getElementsByClassName('pin')[0].innerHTML)";
    private static final String JS_CHECK_DENIED = "javascript:if(document.getElementsByClassName('err')[0].innerHTML == 'Denied'){FitbitHelper.denied();}";
    private FitbitHelper(){};

    /**
     * Obtain an API access token by having the user login to Fitbit
     * @param context Android context
     * @param logInListener callback for when the login succeeds or fails
     */
    public static void doLogin(final Activity context, final Callback<Boolean> logInListener){

        final OAuthService service = getOAuthService();

        new Thread(){
            @Override
            public void run() {
                try {
                    final Token requestToken = service.getRequestToken();
                    final String authUrl = service.getAuthorizationUrl(requestToken);
                    Log.d(TAG, "Auth URL: "+authUrl);

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder fitbitDialogBuilder = new AlertDialog.Builder(context);
                            fitbitDialogBuilder.setTitle("Login to Fitbit");

                            View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_fitbit_dialog,  null);

                            final WebView webView = (WebView)dialogView.findViewById(R.id.webView);
                            final EditText dummyText = (EditText)dialogView.findViewById(R.id.editText); //we need this editText so that the keyboard shows up (bug with AlertDialog/Webview)

                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.loadUrl(authUrl);
                            webView.setWebViewClient(new WebViewClient(){
                                @Override
                                public void onPageFinished(WebView view, String url){
                                    view.loadUrl(JS_CHECK_DENIED); //check if the user denied the login (calls JavascriptInterface.denied();
                                    if(url.startsWith(FitbitApi.API_BASE_URL)) { //run the js to get the access token if the user has logged in
                                        view.loadUrl(JS_GET_ACCESS_TOKEN);//calls JavascriptInterface.submitPin() if one is found
                                    }
                                    webView.requestFocus(View.FOCUS_DOWN);
                                }
                            });
                            fitbitDialogBuilder.setView(dialogView);

                            final AlertDialog dialog = fitbitDialogBuilder.create();
                            dialog.show();
                            dummyText.setVisibility(View.GONE); //hide the dummy text view

                            //JS_GET_ACCESS_TOKEN calls submitPin() to pass the PIN to the app from the WebView
                            //JS_CHECK_DENIED calls denied() if the user clicked on "Deny" for fitbit access in the WebView
                            webView.addJavascriptInterface(new Object(){

                                @JavascriptInterface
                                public void submitPin(String pin){
                                    if(!pin.isEmpty()){
                                        retrieveAccessToken(service, requestToken,  pin, context, logInListener);
                                        dialog.dismiss();
                                    }
                                }

                                @JavascriptInterface
                                public void denied(){
                                    dialog.dismiss();
                                }
                            }, "FitbitHelper");
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                    logInListener.callback(false);
                }
            }
        }.start();
    }

    /**
     * Get an API access token using the PIN from the user login
     * The token is saved and can be retrieved using getAccessToken()
     * @param service
     * @param requestToken
     * @param pin
     * @param context Android context
     * @param logInListener callback for when the login succeeds or fails
     */
    private static void retrieveAccessToken(
            final OAuthService service,
            final Token requestToken,
            final String pin,
            final Context context,
            final Callback<Boolean> logInListener){
        new Thread(){
            @Override
            public void run(){
                try {
                    Verifier verifier = new Verifier(pin);
                    Token accessToken = service.getAccessToken(requestToken, verifier);
                    setAccessToken(accessToken, context);
                    logInListener.callback(true); //log in succeeded
                } catch(Exception e){
                    logInListener.callback(false);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * Gets the user's activity list for the current day and sends the list to the given Callback
     * @param context Android context
     * @param callback Callback object to receive activity list
     */
    public static void getActivities(final Context context, final Callback<JSONArray> callback){

        if(getAccessToken(context) == null){
            return;
        }

        new Thread() {
            @Override
            public void run() {

                    Calendar calendar = Calendar.getInstance();
                    //api url to get activity list for current date
                    String path = String.format("/1/user/-/activities/date/%d-%02d-%02d.json",
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH));

                    Response response = apiGet(path, context);

                    JSONArray activityList;

                    try {
                        JSONObject responseObject = new JSONObject(response.getBody());
                        activityList = responseObject.getJSONArray("activities");
                    } catch(Exception e){
                        e.printStackTrace();
                        activityList = new JSONArray();
                    }

                    if(callback != null){
                        callback.callback(activityList);
                    }
            }
        }.start();
    }

    /**
     * Gets the user's food list for the current day and send the list to the given callback
     * @param context Android context
     * @param callback Callback object to recieve food list
     */
    public static void getFood(final Context context, final Callback<JSONArray> callback){

        if(getAccessToken(context) == null){
            return;
        }

        new Thread(){
            @Override
            public void run(){
                Calendar calendar = Calendar.getInstance();
                //api url to get food list for current date
                String path = String.format("/1/user/-/foods/date/%d-%02d-%02d.json",
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH));

                Response response = apiGet(path, context);

                JSONArray foodList;

                try {
                    JSONObject responseObject = new JSONObject(response.getBody());
                    foodList = responseObject.getJSONArray("foods");
                } catch(Exception e){
                    e.printStackTrace();
                    foodList = new JSONArray();
                }

                if(callback != null){
                    callback.callback(foodList);
                }
            }
        }.start();
    }

    /**
     * Builds an OAuthService object for making API calls
     * @return OAuthService
     */
    private static OAuthService getOAuthService(){
        return  new ServiceBuilder()
                    .provider(new FitbitApi())
                    .apiKey(CLIENT_KEY)
                    .apiSecret(CLIENT_SECRET)
                    .build();
    }

    /**
     * Performs a fitbit api get request for the given url and returns the http Response
     * @param path api path (not including base url), ex "/1/user/-/activities..."
     * @param context Android context
     * @return Reponse object
     */
    private static Response apiGet(String path, Context context){
        Response response = null;

        OAuthService service = getOAuthService();
        Token accessToken = getAccessToken(context);

        OAuthRequest request = new OAuthRequest(Verb.GET, FitbitApi.API_BASE_URL+path);
        service.signRequest(accessToken, request);

        response = request.send();
        Log.d(TAG, response.getCode()+"::"+response.getBody());

        return response;
    }

    /**
     * Save the API access token
     * @param token Token to save
     * @param c Android context
     */
    private static void setAccessToken(Token token, Context c){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        if(token == null){
            prefs.edit()
                    .remove(PREF_KEY_ACCESS_TOKEN)
                    .remove(PREF_KEY_ACCESS_TOKEN_SECRET)
                    .apply();
        } else {
            prefs.edit()
                    .putString(PREF_KEY_ACCESS_TOKEN, token.getToken())
                    .putString(PREF_KEY_ACCESS_TOKEN_SECRET, token.getSecret())
                    .apply();
        }
    }

    /**
     * Retrieve the saved API access token,
     * @param c Android context
     * @return Saved Token, null if there isn't one saved
     */
    private static Token getAccessToken(Context c){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        String tokenString = prefs.getString(PREF_KEY_ACCESS_TOKEN, null);
        String secretString = prefs.getString(PREF_KEY_ACCESS_TOKEN_SECRET, null);
        Token token = null;
        if(tokenString != null && secretString != null){
            token = new Token(tokenString, secretString);
        }
        return token;
    }

    public static boolean isUserLoggedIn(Context c){
        return getAccessToken(c) != null;
    }

    public static void logOut(Context c){
        setAccessToken(null, c);
    }

}
