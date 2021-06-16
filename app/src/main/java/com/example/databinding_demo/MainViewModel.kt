package com.example.databinding_demo

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private var mStatus = Status.STOP

    var activity: Activity? = null

    var text = MutableLiveData<Int>().apply { postValue(1) }

    val strategy = MutableLiveData<Strategy>().apply { postValue(StopStrategy()) }

    var textContent = ""

    val color: LiveData<Int> = Transformations.map(strategy) {
        when (it) {
            is StopStrategy -> Color.parseColor("#ff0000")
            is RunStrategy -> Color.parseColor("#00ff00")
            else -> Color.parseColor("#0000ff")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onTextClick(
        v: View,
        s: String
    ) {
        Log.d(TAG, "v:${v.accessibilityClassName} s:$s")
        text.value = text.value?.plus(1)
    }

    fun onButtonClick() {
        mStatus = when (mStatus) {
            Status.STOP -> {
                Status.RUN
            }
            Status.RUN -> {
                Status.WAIT
            }
            Status.WAIT -> {
                Status.STOP
            }
        }
        Log.d(TAG, "status:${mStatus}")
        strategy.value = StrategyFactory.createStrategy(mStatus)
    }

    fun onShowTextClick() {
        Toast.makeText(activity?.applicationContext, textContent, Toast.LENGTH_LONG).show()
    }
}

abstract class Strategy {
    abstract fun show(): Boolean
}

class StopStrategy : Strategy() {
    override fun show(): Boolean = true
}

class RunStrategy : Strategy() {
    override fun show(): Boolean = false
}

class WaitStrategy : Strategy() {
    override fun show(): Boolean = true
}

object StrategyFactory {
    fun createStrategy(s: Status): Strategy {
        return when (s) {
            Status.RUN -> RunStrategy()
            Status.STOP -> StopStrategy()
            Status.WAIT -> WaitStrategy()
        }
    }
}

enum class Status {
    STOP,
    RUN,
    WAIT
}