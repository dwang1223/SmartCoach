package edu.wpi.smartcoach.reminders;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import org.json.JSONArray;

import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.util.Callback;
import edu.wpi.smartcoach.util.FitbitHelper;

/**
 * Handles Fitbit tracking
 */
public class FitbitReciever extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if(!FitbitHelper.isUserLoggedIn(context)){
            return;
        }

        FitbitHelper.getActivities(context, new Callback<JSONArray>() {
            @Override
            public void callback(JSONArray result) {
                if(result.length() == 0){
                    showNotification(context, "SmartCoach Fitbit Activity Reminder", "Remember to track today's activities in Fitbit!");
                }
            }
        });

        FitbitHelper.getFood(context, new Callback<JSONArray>() {
            @Override
            public void callback(JSONArray result) {
                if(result.length() == 0){
                    showNotification(context, "SmartCoach Fitbit Food Reminder", "Remember to log today's food in Fitbit!");
                }
            }
        });

    }

    private void showNotification(Context context, String title, String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification n = builder.setSmallIcon(R.drawable.coach_head)
                .setContentTitle(title)
                .setContentText(message).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);

    }
}
