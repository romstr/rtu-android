package lv.romstr.mobile.rtu_android

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ProductDialogFragment(private val position: Int) : DialogFragment() {

    lateinit var dialog: AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)

        builder.setView(R.layout.dialog_layout)
            .setPositiveButton("ok") { _, _ ->
                val listener = targetFragment as AlertDialogClickListener
                listener.onPositiveButtonClick(position)
            }.setNegativeButton("cancel") { _, _ -> }

        dialog = builder.create()

        return dialog
    }

    override fun onStop() {
        super.onStop()
        dialog.dismiss()
    }

    interface AlertDialogClickListener {
        fun onPositiveButtonClick(position: Int)
    }
}