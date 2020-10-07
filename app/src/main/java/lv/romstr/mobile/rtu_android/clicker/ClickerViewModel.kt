package lv.romstr.mobile.rtu_android.clicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClickerViewModel : ViewModel() {

    private val _clicks = MutableLiveData<Int>()
    private val _dividedByTen = MutableLiveData<Boolean>()

    val clicks: LiveData<Int>
        get() = _clicks

    val dividedByTen: LiveData<Boolean>
        get() = _dividedByTen

    init {
        _clicks.value = 0
        _dividedByTen.value = false
    }

    fun incrementClicks() {
        _clicks.value = _clicks.value?.inc()
    }

    fun divideByTen() {
        _dividedByTen.value = _clicks.value?.rem(10) == 0
    }

    fun setToastShown() {
        _dividedByTen.value = false
    }
}