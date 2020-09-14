package lv.romstr.mobile.rtu_android

import android.app.Application
import androidx.room.Room

class App : Application() {

    val db by lazy {
        Room.databaseBuilder(this, ShoppingDatabase::class.java, "shopping-db")
            .allowMainThreadQueries()
            .build()
    }
}