package lv.romstr.mobile.rtu_android.chat

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_chat_reply.*
import lv.romstr.mobile.rtu_android.R
import lv.romstr.mobile.rtu_android.chat.ChatSendActivity.Companion.SEND_MESSAGE_EXTRA

class ChatReplyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_reply)

        val message = intent.getStringExtra(SEND_MESSAGE_EXTRA)
        if (message.isNullOrEmpty()) {
            chatReplyTitle.visibility = View.GONE
            chatReplyText.visibility = View.GONE
        } else {
            chatReplyTitle.visibility = View.VISIBLE
            chatReplyText.visibility = View.VISIBLE
            chatReplyText.text = message
        }

        chatReplyButton.setOnClickListener { reply() }
    }

    private fun reply() {
        val intent = Intent().apply {
            putExtra(REPLY_MESSAGE_EXTRA, chatReplyInput.text.toString())
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    companion object {
        const val REPLY_MESSAGE_EXTRA = "lv.romstr.mobile.rtu_android.reply_message"
    }
}