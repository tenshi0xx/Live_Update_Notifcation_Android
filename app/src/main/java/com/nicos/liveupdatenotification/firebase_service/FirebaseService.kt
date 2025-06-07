package com.nicos.liveupdatenotification.firebase_service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nicos.liveupdatenotification.R
import org.json.JSONObject

class FirebaseService : FirebaseMessagingService() {

    private val channelId = "Channel Id"
    private val channelName = "Channel Name"
    private val channelDescription = "Channel Description"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationModel = NotificationModel(JSONObject(message.data as Map<*, *>)))
        NotificationCompat.Builder(this, "Channel Id").apply {
            setSmallIcon(R.drawable.ic_android_black_24dp)
            setContentTitle("")
            setContentText("")
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_DEFAULT
            createNotificationChannel()
            /** notification receive */
            with(NotificationManagerCompat.from(this@FirebaseService)) {
                if (ActivityCompat.checkSelfPermission(
                        this@FirebaseService,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                notify(1, this@apply.build())
            }
        }
    }

    private fun createNotificationChannel(
    ) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
            with((this@FirebaseService.getSystemService(NOTIFICATION_SERVICE) as NotificationManager)) {
                createNotificationChannel(this@apply)
            }
        }
    }
}