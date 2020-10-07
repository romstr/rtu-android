package lv.romstr.mobile.rtu_android.clicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClickerViewModel : ViewModel() {

    private val _clicks = MutableLiveData<Int>()
    private val _dividableByTen = MutableLiveData<Boolean>()

    val clicks: LiveData<Int>
        get() = _clicks

    val dividableByTen: LiveData<Boolean>
        get() = _dividableByTen

    init {
        _clicks.value = 0
        _dividableByTen.value = false
    }

    fun incrementClicks() {
        _clicks.value = _clicks.value?.inc()
    }

    fun divideByTen() {
        _dividableByTen.value = _clicks.value?.rem(10) == 0
    }
}