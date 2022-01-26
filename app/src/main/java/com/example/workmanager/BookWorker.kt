package com.example.workmanager

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class BookWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        createNotification()
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification() {

        val builder: NotificationCompat.Builder

        //Bildirimi kullanıcıya gösterebilmek için NotificationManager nesnesi oluşturuyoruz.
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(applicationContext, MainActivity::class.java)

        //Bildirim tıklandıktan sonra nereye yönlendirileceğini belirtiyoruz.
        val contentToGo = PendingIntent.getActivity(
            applicationContext,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //Min API level 26 Android 8.0 ile yapılabilir

        //Bildirimin yanınlaması için bir kanal oluşturuyoruz.
        val channelId = "channelId"
        val channelName = "channelName"
        val channelIntroduction = "channelIntroduction"
        //Önem deresecesini belirtiyoruz.
        val channelPriority = NotificationManager.IMPORTANCE_HIGH

        var channel: NotificationChannel? = notificationManager
            .getNotificationChannel(channelId)

        if (channel == null) {
            channel = NotificationChannel(channelId, channelName, channelPriority)
            channel.description = channelIntroduction
            notificationManager.createNotificationChannel(channel)
        }

        //Notificaiton ın özelliklerini belirtiyoruz.
        builder = NotificationCompat.Builder(applicationContext, channelId)
        builder
            .setContentTitle("Bugün kitap okudun mu?")
            .setContentText("Aman kitap zincirin kırılmasın!")
            .setSmallIcon(R.drawable.star_on)
            .setAutoCancel(true)
            .setContentIntent(contentToGo)

        notificationManager.notify(1, builder.build())
    }
}