package lv.romstr.mobile.rtu_android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    private lateinit var receiver: MyBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        createNotificationChannel()

        receiver = MyBroadcastReceiver()

        sendBroadCast.setOnClickListener {
            Intent().apply {
                action = CUSTOM_BROADCAST_ACTION
                putExtra("message", "Started media player")
            }.also {
                LocalBroadcastManager.getInstance(this).sendBroadcast(it)
            }
        }

        stopServiceButton.setOnClickListener {
            val intent = Intent(this, ForegroundService::class.java)
            stopService(intent)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "CHANNEL_ID",
                "Example Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver, IntentFilter(CUSTOM_BROADCAST_ACTION))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
    }

    companion object {
        const val CUSTOM_BROADCAST_ACTION = "lv.romstr.mobile.rtu_android.ACTION_CUSTOM_BROADCAST"
    }
}