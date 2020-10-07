package lv.romstr.mobile.rtu_android.chat

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_chat_send.*
import lv.romstr.mobile.rtu_android.R
import lv.romstr.mobile.rtu_android.chat.ChatReplyActivity.Companion.REPLY_MESSAGE_EXTRA

class ChatSendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_send)

        chatSendButton.setOnClickListener { sendMessage() }
    }

    private fun sendMessage() {
        val intent = Intent(this, ChatReplyActivity::class.java).apply {
            putExtra(SEND_MESSAGE_EXTRA, chatSendInput.text.toString())
            chatSendInput.text.clear()
        }
        startActivityForResult(intent, REPLY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REPLY_REQUEST_CODE) {
            data?.let {
                val reply = data.getStringExtra(REPLY_MESSAGE_EXTRA)
                chatSendTitle.visibility = View.VISIBLE
                chatSendText.visibility = View.VISIBLE
                chatSendText.text = reply
            }
        } else {
            chatSendTitle.visibility = View.GONE
            chatSendText.visibility = View.GONE
        }
    }

    companion object {
        const val REPLY_REQUEST_CODE = 1234
        const val SEND_MESSAGE_EXTRA = "lv.romstr.mobile.rtu_android.send_message"
    }
}