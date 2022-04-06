package io.humanoid.habittracker.datum.repo.impl

import androidx.lifecycle.LiveData
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.datum.repo.TaskRepository
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData

class TaskRepositoryImpl(
    private val box: Box<Task>
): TaskRepository {
    override fun get(id: Long): Task? {
        return box[id]
    }

    override fun insert(data: Task): Long {
        return box.put(data)
    }

    override fun remove(id: Long): Boolean {
        return box.remove(id)
    }

    override fun subscribeToAll(): LiveData<List<Task>> {
        return ObjectBoxLiveData(box.query().build())
    }
}