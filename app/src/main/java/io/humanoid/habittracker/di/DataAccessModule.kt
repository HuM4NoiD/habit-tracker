package io.humanoid.habittracker.di

import io.humanoid.habittracker.datum.database.Database
import io.humanoid.habittracker.datum.repo.EntryRepository
import io.humanoid.habittracker.datum.repo.RoutineRepository
import io.humanoid.habittracker.datum.repo.TaskLinkRepository
import io.humanoid.habittracker.datum.repo.TaskRepository
import io.humanoid.habittracker.datum.repo.impl.EntryRepositoryImpl
import io.humanoid.habittracker.datum.repo.impl.RoutineRepositoryImpl
import io.humanoid.habittracker.datum.repo.impl.TaskLinkRepositoryImpl
import io.humanoid.habittracker.datum.repo.impl.TaskRepositoryImpl

val dataAccessModule: DataAccessModule = DataContainer

interface DataAccessModule {
    val taskRepository: TaskRepository
    val taskLinkRepository: TaskLinkRepository
    val routineRepository: RoutineRepository
    val entryRepository: EntryRepository
}

private object DataContainer: DataAccessModule{
    override val taskRepository by lazy {
        TaskRepositoryImpl(Database.taskBox, Database.taskLinkBox)
    }
    override val taskLinkRepository by lazy {
        TaskLinkRepositoryImpl(Database.taskLinkBox)
    }
    override val routineRepository by lazy {
        RoutineRepositoryImpl(Database.routineBox)
    }
    override val entryRepository by lazy {
        EntryRepositoryImpl(Database.entryBox)
    }
}