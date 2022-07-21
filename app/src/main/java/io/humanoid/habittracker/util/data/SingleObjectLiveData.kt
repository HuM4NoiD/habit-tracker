package io.humanoid.habittracker.util.data

import androidx.lifecycle.LiveData

class SingleObjectLiveData<T>(
    val id: Long
): LiveData<T>() {



    override fun onActive() {
        super.onActive()
    }

    override fun onInactive() {
        super.onInactive()
    }
}

