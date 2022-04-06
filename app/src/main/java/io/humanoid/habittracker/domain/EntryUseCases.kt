package io.humanoid.habittracker.domain

import io.humanoid.habittracker.datum.model.Entry
import io.humanoid.habittracker.datum.repo.EntryRepository

class EntryUseCases(
    private val repository: EntryRepository
) {
    fun insertEntry(entry: Entry) = repository.insert(entry)

    fun subscribeToAll() = repository.subscribeToAll()
}