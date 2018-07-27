package com.boscatov.richnotification

import android.app.Notification
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import android.support.v4.app.NotificationManagerCompat





class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val noty = Notifications(this)
        val big = BitmapFactory.decodeResource(resources, R.drawable.big)
        n1.setOnClickListener{
            noty.bigPictureNotification(big, R.drawable.small, "Уведомление с большой картинкой")
        }
        n2.setOnClickListener{
            noty.textPictureNotification(R.drawable.small, "Уведомление с текстом", "Текст уведомления")
        }
        n3.setOnClickListener{
            noty.actionNotification(R.drawable.small, "Уведомление с действием", "Тык", "Тыгыдык")
        }
        n4.setOnClickListener {
            noty.customNotification()
        }
    }
}
