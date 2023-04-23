package io.humanoid.habittracker.datum.repo.impl

import androidx.lifecycle.LiveData
import io.humanoid.habittracker.datum.model.Routine_
import io.humanoid.habittracker.datum.model.TaskLink
import io.humanoid.habittracker.datum.model.TaskLink_
import io.humanoid.habittracker.datum.repo.TaskLinkRepository
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.kotlin.query

class TaskLinkRepositoryImpl(
    private val box: Box<TaskLink>
): TaskLinkRepository {

    override fun get(id: Long): TaskLink? = box[id]

    override fun insert(data: TaskLink): Long = box.put(data)

    override fun remove(id: Long): Boolean = box.remove(id)

    override fun remove(ids: LongArray) = box.remove(*ids)

    override fun subscribeToAll(): LiveData<List<TaskLink>> = ObjectBoxLiveData(box.query().build())

    override fun subscribeForRoutine(routineId: Long) = ObjectBoxLiveData<TaskLink>(
        box.query {
            link(TaskLink_.routine).equal(Routine_.id, routineId)
            sort { link1, link2 -> link1.index.compareTo(link2.index) }
        }
    )
}