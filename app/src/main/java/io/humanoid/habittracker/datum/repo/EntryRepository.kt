package io.humanoid.habittracker.datum.repo

import androidx.lifecycle.LiveData
import io.humanoid.habittracker.datum.model.Entry

interface EntryRepository: Repository<Entry> {

    fun subscribeForTask(taskId: Long): LiveData<List<Entry>>
}