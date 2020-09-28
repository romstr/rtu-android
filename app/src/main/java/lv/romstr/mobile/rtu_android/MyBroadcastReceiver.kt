package lv.romstr.mobile.rtu_android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val message = intent.getStringExtra("message")
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

        val serviceIntent = Intent(context, ForegroundService::class.java)
        context.startService(serviceIntent)
    }
}