package io.humanoid.habittracker.di

import io.humanoid.habittracker.domain.EntryUseCases
import io.humanoid.habittracker.domain.RoutineUseCases
import io.humanoid.habittracker.domain.TaskLinkUseCases
import io.humanoid.habittracker.domain.TaskUseCases

val domainModule: DomainModule = DomainContainer

interface DomainModule {
    val taskUseCases: TaskUseCases
    val taskLinkUseCases: TaskLinkUseCases
    val routineUseCases: RoutineUseCases
    val entryUseCases: EntryUseCases
}

private object DomainContainer: DomainModule {

    private val dataModule = appModule.dataAccessModule
    override val taskUseCases = TaskUseCases(dataModule.taskRepository)
    override val taskLinkUseCases = TaskLinkUseCases(dataModule.taskLinkRepository)
    override val routineUseCases = RoutineUseCases(dataModule.routineRepository)
    override val entryUseCases = EntryUseCases(dataModule.entryRepository)
}