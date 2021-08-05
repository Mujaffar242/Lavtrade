package com.mujaffar.lavtrade.fcm

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mujaffar.lavtrade.user_module.ui.activities.UserHomeActivity
import com.mujaffar.medremind.database.DatabaseBuySellModel
import com.mujaffar.medremind.database.getDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MyFirebaseMessagingService : FirebaseMessagingService() {

    val id = "id_product"


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        sendNotification(remoteMessage)
    }


    private fun sendNotification(remoteMessage: RemoteMessage) {

        var notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // The user-visible name of the channel.
            val name: CharSequence = "Product"
            // The user-visible description of the channel.
            val description = "Notifications regarding our products"
            val mChannel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
            // Configure the notification channel.
            mChannel.description = description
            mChannel.enableLights(true)
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.lightColor = Color.RED
            notificationManager.createNotificationChannel(mChannel)


        }

        NotificationCompat.Builder(this, id)

        val intent1 = Intent(getApplicationContext(), UserHomeActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            123,
            intent1,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext, "id_product"
        )
            .setSmallIcon(R.mipmap.sym_def_app_icon) //your app icon
            .setChannelId(id)
            .setContentTitle(remoteMessage.data.get("body"))
            .setAutoCancel(true).setContentIntent(pendingIntent)
            .setNumber(1)
            .setColor(255)
            .setContentText(remoteMessage.data.get("title"))
            .setWhen(System.currentTimeMillis())
        notificationManager.notify(1, notificationBuilder.build())



        insertSingleValue(
            DatabaseBuySellModel(
                remoteMessage.data.get("body").toString(),
                remoteMessage.data.get("title").toString(),
                false
            )
        )

    }


    fun insertSingleValue(databaseBuySellModel: DatabaseBuySellModel) {
        val database = getDatabase(application)

        database.buySellDao.insertSinglevalue(databaseBuySellModel)

    }

}
