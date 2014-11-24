package edu.wpi.smartcoach.reminders;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import edu.wpi.smartcoach.CheckinActivity;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.service.ReminderService;

public class ReminderReciever extends BroadcastReceiver {
	public ReminderReciever() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		long id = intent.getLongExtra("id", -1);
		if(id == 9001){
			  Intent inte = new Intent(context, CheckinActivity.class);
		        intent.putExtra("id", id);
		        PendingIntent pending = PendingIntent.getBroadcast(context, (int) id, intent, 0);
			
			NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
			Notification n = builder.setSmallIcon(R.drawable.coach_head)
			.setContentTitle("SmartCoach Check-In")
			.setContentText("Let's review your week!").setContentIntent(pending).build();
			
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

			notificationManager.notify(0, n);
		}
		else if(id != -1) {
			Reminder r = ReminderService.getInstance().getReminder(id);
			if(r != null){
				NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
				Notification n = builder.setSmallIcon(R.drawable.coach_head)
				.setContentTitle("SmartCoach Reminder")
				.setContentText(r.getMessage()).build();
				
				NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

				notificationManager.notify(0, n);
			}
		}
	}
}
