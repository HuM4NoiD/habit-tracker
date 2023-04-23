package io.humanoid.habittracker.datum.repo

import androidx.lifecycle.LiveData
import io.humanoid.habittracker.datum.model.TaskLink

interface TaskLinkRepository: Repository<TaskLink> {

    fun remove(ids: LongArray)

    fun subscribeForRoutine(routineId: Long): LiveData<List<TaskLink>>
}