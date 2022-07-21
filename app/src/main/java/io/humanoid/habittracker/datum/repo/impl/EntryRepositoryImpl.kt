package io.humanoid.habittracker.datum.repo.impl

import androidx.lifecycle.LiveData
import io.humanoid.habittracker.datum.model.Entry
import io.humanoid.habittracker.datum.model.Entry_
import io.humanoid.habittracker.datum.repo.EntryRepository
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData

class EntryRepositoryImpl(
    private val box: Box<Entry>
): EntryRepository {
    override fun get(id: Long): Entry? {
        return box[id]
    }

    override fun insert(data: Entry): Long {
        return box.put(data)
    }

    override fun remove(id: Long): Boolean {
        return box.remove(id)
    }

    override fun subscribeForTask(taskId: Long): LiveData<List<Entry>> {
        return ObjectBoxLiveData<Entry>(
            box.query()
                .equal(Entry_.taskId, taskId)
                .build()
        )
    }

    override fun subscribeToAll(): LiveData<List<Entry>> {
        return ObjectBoxLiveData(box.query().build())
    }
}