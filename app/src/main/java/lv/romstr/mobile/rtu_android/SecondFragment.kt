package lv.romstr.mobile.rtu_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView.text = arguments!!.getString(EXTRA_STRING_MESSAGE)
    }

    companion object {
        fun newInstance(message: String): SecondFragment {
            val fragment = SecondFragment()

            val args = Bundle()
            args.putString(EXTRA_STRING_MESSAGE, message)
            fragment.arguments = args

            return fragment
        }

        const val EXTRA_STRING_MESSAGE = "EXTRA_STRING_MESSAGE"
    }
}