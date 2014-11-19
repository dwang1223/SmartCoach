package edu.wpi.smartcoach.reminders;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.service.ReminderService;

public class ReminderReciever extends BroadcastReceiver {
	public ReminderReciever() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		long id = intent.getLongExtra("id", -1);
		if(id != -1) {
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
