package edu.towson.cosc435.bajgai.todos_madhav

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyIntentService : IntentService("MyIntentService") {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this, NOTIF_CHANNEL_ID)
    }

    override fun onHandleIntent(intent: Intent?) {

        showNotification()

        Thread.sleep(10000)

        maybeCancelNotification()
    }

    private fun showNotification() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)


        val builder = NotificationCompat.Builder(this, NOTIF_CHANNEL_ID)
            .setContentTitle("Todo's")
            .setContentText("Click to launch Todo's App")
            .setSmallIcon(android.R.drawable.btn_default)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        val notification = builder.build()

        NotificationManagerCompat.from(this)
            .notify(NOTIF_ID, notification)
    }

    private fun maybeCancelNotification() {
        if(application is MyApplication) {
            val app = application as MyApplication
            if (app.isMainActivityVisible) {
                NotificationManagerCompat.from(this)
                    .cancel(NOTIF_ID)
                val intent = Intent(BROADCAST_ACTION)
                sendBroadcast(intent)
            }
        }
    }

    companion object {
        val NOTIF_CHANNEL_ID = "edu.towson.cosc431"
        val NOTIF_ID = 1
        val BROADCAST_ACTION = "edu.towson.cosc431.SERVICE_ACTION"
    }
}
