package com.boscatov.richnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.widget.RemoteViews



class Notifications(private val context: Context) {
    init {
        createNotificationChannel()
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "C123"
            val description = "C4"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance)
            channel.description = description
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }
    fun textPictureNotification(smallIcon: Int, title: String, text: String) {
        val notification = NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(intent())
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(122, notification.build())
    }
    fun bigPictureNotification(bitmap: Bitmap, smallIcon: Int, title: String) {
        val notification = NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, notification.build())
    }
    fun actionNotification(smallIcon: Int, title: String, text1: String, text2: String) {
        val action = NotificationCompat.Action.Builder(smallIcon, text1, intent()).build()
        val action2 = NotificationCompat.Action.Builder(smallIcon, text2, intent()).build()
        val notification = NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .addAction(action)
                .addAction(action2)
                .build()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(124, notification)
    }
    fun customNotification() {
        val contentView = RemoteViews(context.packageName, R.layout.custom_push)
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher)
        contentView.setTextViewText(R.id.title, "Свой нотификейшн")
        contentView.setTextViewText(R.id.text, "Из layout")
        val contentView2 = RemoteViews(context.packageName, R.layout.expanded_push)
        val notification = NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(R.drawable.small)
                .setContent(contentView)
                .setCustomBigContentView(contentView2)
                .build()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(124, notification)
    }
    fun intent(): PendingIntent {
        val intent = Intent(this.context, MyBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(this.context, 25, intent, 0)
    }
}