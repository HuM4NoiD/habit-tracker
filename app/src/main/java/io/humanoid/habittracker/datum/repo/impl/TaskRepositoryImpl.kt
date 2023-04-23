package io.humanoid.habittracker.datum.repo.impl

import androidx.lifecycle.LiveData
import io.humanoid.habittracker.datum.model.Routine_
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.datum.model.TaskLink_
import io.humanoid.habittracker.datum.model.Task_
import io.humanoid.habittracker.datum.repo.TaskRepository
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.kotlin.query
import io.objectbox.reactive.DataObserver
import io.objectbox.reactive.DataSubscription

class TaskRepositoryImpl(
    private val box: Box<Task>
) : TaskRepository {

    override fun get(id: Long): Task? {
        return box[id]
    }

    override fun get(ids: LongArray): List<Task> {
        return box.get(ids)
    }

    override fun insert(data: Task): Long {
        return box.put(data)
    }

    override fun remove(id: Long): Boolean {
        return box.remove(id)
    }

    override fun observeTask(observer: DataObserver<Class<Task>>): DataSubscription {
        return box.store.subscribe(Task::class.java).observer(observer)
    }

    override fun subscribeForRoutine(routineId: Long) = ObjectBoxLiveData<Task>(
        box.query {
            link(Task_.links).link(TaskLink_.routine).equal(Routine_.id, routineId)
        }
    )

    override fun subscribeToAll(): LiveData<List<Task>> {
        return ObjectBoxLiveData(box.query().build())
    }
}