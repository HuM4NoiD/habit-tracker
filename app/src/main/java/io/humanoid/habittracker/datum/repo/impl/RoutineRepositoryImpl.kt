package io.humanoid.habittracker.datum.repo.impl

import androidx.lifecycle.LiveData
import io.humanoid.habittracker.datum.model.Routine
import io.humanoid.habittracker.datum.repo.RoutineRepository
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData

class RoutineRepositoryImpl(
    private val box: Box<Routine>
): RoutineRepository {
    override fun get(id: Long): Routine? {
        return box[id]
    }

    override fun insert(data: Routine): Long {
        return box.put(data)
    }

    override fun remove(id: Long): Boolean {
        return box.remove(id)
    }

    override fun subscribeToAll(): LiveData<List<Routine>> {
        return ObjectBoxLiveData(box.query().build())
    }
}