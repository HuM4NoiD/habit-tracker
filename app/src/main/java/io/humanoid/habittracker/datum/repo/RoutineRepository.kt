package io.humanoid.habittracker.datum.repo

import io.humanoid.habittracker.datum.model.Routine
import io.objectbox.reactive.DataObserver
import io.objectbox.reactive.DataSubscription

interface RoutineRepository: Repository<Routine> {

    fun observeRoutine(observer: DataObserver<Class<Routine>>): DataSubscription
}