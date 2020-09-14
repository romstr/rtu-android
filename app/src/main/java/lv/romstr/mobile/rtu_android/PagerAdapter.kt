package lv.romstr.mobile.rtu_android

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return NUMBERS_OF_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) ProductFragment() else SecondFragment.newInstance("This message came from adapter")
    }

    companion object {
        const val NUMBERS_OF_PAGES = 2
    }
}