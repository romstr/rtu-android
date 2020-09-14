package lv.romstr.mobile.rtu_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PagerAdapter(this)

        pagerView.adapter = adapter

        TabLayoutMediator(tabLayout, pagerView) { tab, position ->
            tab.text = "Product tab ${position + 1}"
        }.attach()
    }
}