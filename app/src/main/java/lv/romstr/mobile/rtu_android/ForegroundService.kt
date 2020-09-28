package lv.romstr.mobile.rtu_android

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat


class ForegroundService : Service() {

    private lateinit var player: MediaPlayer

    override fun onCreate() {
        super.onCreate()

        player = MediaPlayer.create(this, R.raw.bensound_creativeminds);
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        player.start()

        //Build a notification
        val notification = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setContentTitle("Media player")
            .setContentText("playing song right now")
            .setSmallIcon(R.drawable.ic_baseline_music_note_24)
            .build()

        //A notifcation HAS to be passed for the foreground service to be started.
        startForeground(1, notification)
        
        return START_NOT_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Media player has been stopped ", Toast.LENGTH_LONG).show()
        player.stop()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}