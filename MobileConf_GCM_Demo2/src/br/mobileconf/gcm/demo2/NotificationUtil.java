package br.mobileconf.gcm.demo2;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * @author Ricardo Lecheta
 * 
 */
public class NotificationUtil {
	static int ID = R.drawable.ic_launcher;

	@SuppressLint("NewApi")
	public static void generateNotification(Context context, String message, Intent notificationIntent) {

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		String title = context.getString(R.string.app_name);
		Notification.Builder builder = new Notification.Builder(context).setContentTitle(message).setContentText(title)
				.setContentIntent(intent).setSmallIcon(R.drawable.ic_launcher);

		Notification notification = builder.build();

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);
	}
}
