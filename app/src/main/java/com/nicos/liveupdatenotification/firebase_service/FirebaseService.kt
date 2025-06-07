package com.nicos.liveupdatenotification.firebase_service

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nicos.liveupdatenotification.BuildConfig
import com.nicos.liveupdatenotification.R
import org.json.JSONObject

class FirebaseService : FirebaseMessagingService() {

    companion object {
        private const val CHANNEL_ID = "Channel Id"
        private const val CHANNEL_NAME = "Channel Name"
        private const val CHANNEL_DESCRIPTION = "Channel Description"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (BuildConfig.DEBUG) {
            Log.d("payload", message.data.toString())
        }

        val notificationModel = NotificationModel(JSONObject(message.data as Map<*, *>))
        Notification.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_android_black_24dp)
            setContentTitle(notificationModel.title)
            setContentText(notificationModel.body)
            setAutoCancel(true)

            var progressStyle: Notification.ProgressStyle =
                getAndCreateProgressStyle(notificationModel)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
                setStyle(progressStyle)
            }

            createNotificationChannel()

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

    private fun getAndCreateProgressStyle(notificationModel: NotificationModel): Notification.ProgressStyle {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
            val progressSegmentList: List<Notification.ProgressStyle.Segment> =
                handleProgressSegment(notificationModel)
            val progressPointList: List<Notification.ProgressStyle.Point> =
                handleProgressPoint(notificationModel)
            Notification.ProgressStyle().apply {
                setStyledByProgress(false)
                setProgress(notificationModel.currentProgress ?: 0)
                setProgressTrackerIcon(
                    Icon.createWithResource(
                        this@FirebaseService,
                        R.drawable.ic_android_red_24dp
                    )
                )
                setProgressSegments(
                    progressSegmentList
                )
                setProgressPoints(
                    progressPointList
                )
            }
        } else {
            TODO("VERSION.SDK_INT < BAKLAVA")
        }
    }

    @RequiresApi(Build.VERSION_CODES.BAKLAVA)
    private fun handleProgressSegment(notificationModel: NotificationModel): List<Notification.ProgressStyle.Segment> {
        if (notificationModel.currentProgressSegmentOne != null && notificationModel.currentProgressSegmentTwo != null && notificationModel.currentProgressSegmentThree != null) {
            return listOf(
                Notification.ProgressStyle.Segment(notificationModel.currentProgressSegmentOne)
                    .setColor(this@FirebaseService.getColor(R.color.teal_200)),
                Notification.ProgressStyle.Segment(notificationModel.currentProgressSegmentTwo)
                    .setColor(this@FirebaseService.getColor(R.color.purple_200)),
                Notification.ProgressStyle.Segment(notificationModel.currentProgressSegmentThree)
                    .setColor(this@FirebaseService.getColor(R.color.purple_700))

            )
        }

        return emptyList()
    }

    @RequiresApi(Build.VERSION_CODES.BAKLAVA)
    private fun handleProgressPoint(notificationModel: NotificationModel): List<Notification.ProgressStyle.Point> {
        if (notificationModel.currentProgressPointOne != null && notificationModel.currentProgressPointTwo != null) {
            return listOf(
                Notification.ProgressStyle.Point(notificationModel.currentProgressPointOne)
                    .setColor(this@FirebaseService.getColor(android.R.color.holo_green_light)),
                Notification.ProgressStyle.Point(notificationModel.currentProgressPointTwo)
                    .setColor(this@FirebaseService.getColor(android.R.color.holo_green_light))
            )
        }
        return emptyList()
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
            description = CHANNEL_DESCRIPTION
            with((this@FirebaseService.getSystemService(NOTIFICATION_SERVICE) as NotificationManager)) {
                createNotificationChannel(this@apply)
            }
        }
    }
}
