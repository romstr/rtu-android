package lv.romstr.mobile.rtu_android

import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import lv.romstr.mobile.rtu_android.screens.main.MainActivity
import lv.romstr.mobile.rtu_android.screens.main.MainActivity.Companion.EXTRA_ITEM
import lv.romstr.mobile.rtu_android.screens.main.MainActivity.Companion.EXTRA_NOTIFICATION_ID
import java.util.*

/*
Example JSON to send via legacy HTTP API as a notification:
{
  "to": "/topics/offers",
  "data": {
    "item": "Chocolate bar",
    "title": "New offer!",
    "body": "Add Chocolate bar to shopping list now!"
  }
}
More info here: https://firebase.google.com/docs/cloud-messaging/http-server-ref
 */

class MessageService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationId = Random().nextInt()
        val requestId = Random().nextInt()

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(EXTRA_ITEM, ShoppingItem(name = message.data["item"].orEmpty()))
            putExtra(EXTRA_NOTIFICATION_ID, notificationId)
        }

        val pendingIntent = PendingIntent.getActivity(this, requestId, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.btn_star)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["body"])
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(
                android.R.drawable.ic_input_add,
                getString(R.string.button_add),
                pendingIntent
            )
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

    @Suppress("RedundantOverride")
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    companion object {
        const val CHANNEL_ID = "notifications_offers"
    }
}