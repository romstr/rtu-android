package lv.romstr.mobile.rtu_android.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.firebase.ui.auth.AuthUI
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import lv.romstr.mobile.rtu_android.R
import lv.romstr.mobile.rtu_android.ShoppingItem
import lv.romstr.mobile.rtu_android.ShoppingItemRecyclerAdapter
import lv.romstr.mobile.rtu_android.screens.detail.DetailActivity

class MainActivity : AppCompatActivity(), AdapterClickListener {

    private val items = mutableListOf<ShoppingItem>()

    private lateinit var adapter: ShoppingItemRecyclerAdapter

    private val viewModel: MainViewModel by viewModels()

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (auth.currentUser == null) signIn() else updateUi()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        if (item.itemId == R.id.sign_out) {
            signOut()
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    private fun signIn() {
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            REQUEST_CODE_SIGN_IN
        )
    }

    private fun updateUi() {
        title = getString(R.string.label_greeting, auth.currentUser?.displayName.orEmpty())

        adapter = ShoppingItemRecyclerAdapter(this, items)
        mainItems.adapter = adapter

        observeChanges()

        mainButtonAdd.setOnClickListener { appendItem() }

        intent.getParcelableExtra<ShoppingItem>(EXTRA_ITEM)?.let {
            appendItem(it)
        }
        cancelNotification()
    }

    private fun cancelNotification() {
        val notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, NO_ID)
        if (notificationId != NO_ID) {
            NotificationManagerCompat.from(this).cancel(notificationId)
        }
    }

    private fun observeChanges() {
        viewModel.getItems()
            .observe(this, {
                items.clear()
                items.addAll(it)
                adapter.notifyDataSetChanged()
            })
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                Toast.makeText(this, R.string.label_signed_out, Toast.LENGTH_SHORT).show()
                signIn()
            }
    }

    private fun appendItem(newItem: ShoppingItem? = null) {
        val item = newItem ?: ShoppingItem(mainEditName.text.toString(), 1, "")
        FirebaseAnalytics.getInstance(this).logEvent("user_clicked_add", null)
        viewModel.createItem(item)
        mainEditName.text.clear()
    }

    override fun itemClicked(item: ShoppingItem) {
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra(EXTRA_ID, item.id)
        startActivityForResult(intent, REQUEST_CODE_DETAILS)
    }

    override fun deleteClicked(item: ShoppingItem) {
        FirebaseAnalytics.getInstance(this).logEvent("user_clicked_delete", null)
        viewModel.removeItem(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN && resultCode == RESULT_OK) {
            updateUi()
        }
    }

    companion object {
        const val EXTRA_ID = "lv.romstr.mobile.extras.shopping_item_id"
        const val EXTRA_ITEM = "lv.romstr.mobile.extras.shopping_item"
        const val EXTRA_NOTIFICATION_ID = "lv.romstr.mobile.extras.notification_id"
        const val REQUEST_CODE_DETAILS = 1234
        const val REQUEST_CODE_SIGN_IN = 2345
        const val NO_ID = -1
    }
}

interface AdapterClickListener {

    fun itemClicked(item: ShoppingItem)

    fun deleteClicked(item: ShoppingItem)

}