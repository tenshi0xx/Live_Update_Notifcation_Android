package com.nicos.liveupdatenotification.local_notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.nicos.liveupdatenotification.R

class LocalNotification(private val context: Context) {

    private val channelId = "Channel Id"
    private val channelName = "Channel Name"
    private val channelDescription = "Channel Description"

    internal fun showNotification() {
        Notification.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_android_black_24dp)
            setContentTitle("Local Notification Title")
            setContentText("Local Notification Text")
            setAutoCancel(true)

            var progressStyle: Notification.ProgressStyle =
                getAndCreateProgressStyle()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
                setStyle(progressStyle)
            }

            createNotificationChannel()

            with(NotificationManagerCompat.from(context)) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                notify(1, this@apply.build())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.BAKLAVA)
    private fun handleProgressSegment(): List<Notification.ProgressStyle.Segment> {
        return listOf(
            Notification.ProgressStyle.Segment(33)
                .setColor(context.getColor(R.color.teal_200)),
            Notification.ProgressStyle.Segment(33)
                .setColor(context.getColor(R.color.purple_200)),
            Notification.ProgressStyle.Segment(33)
                .setColor(context.getColor(R.color.purple_700))

        )
    }

    private fun getAndCreateProgressStyle(): Notification.ProgressStyle {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
            val progressSegmentList: List<Notification.ProgressStyle.Segment> =
                handleProgressSegment()
            val progressPointList: List<Notification.ProgressStyle.Point> =
                handleProgressPoint()
            Notification.ProgressStyle().apply {
                setStyledByProgress(false)
                setProgress(33)
                setProgressTrackerIcon(
                    Icon.createWithResource(
                        context,
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
    private fun handleProgressPoint(): List<Notification.ProgressStyle.Point> {
        return listOf(
            Notification.ProgressStyle.Point(33)
                .setColor(context.getColor(android.R.color.holo_green_light)),
            Notification.ProgressStyle.Point(66)
                .setColor(context.getColor(android.R.color.holo_green_light))
        )
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
            with((context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager)) {
                createNotificationChannel(this@apply)
            }
        }
    }
}