package io.humanoid.habittracker.datum.converter

import io.humanoid.habittracker.datum.model.TaskType
import io.objectbox.converter.PropertyConverter

class TaskTypeConverter : PropertyConverter<TaskType, String> {

    override fun convertToEntityProperty(databaseValue: String?) = TaskType.valueOf(databaseValue ?: "")

    override fun convertToDatabaseValue(entityProperty: TaskType) = entityProperty.name
}