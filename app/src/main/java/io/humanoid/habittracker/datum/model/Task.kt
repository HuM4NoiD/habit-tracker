package io.humanoid.habittracker.datum.model

import android.os.Parcelable
import io.humanoid.habittracker.datum.converter.TaskTypeConverter
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.relation.ToMany
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Task(
    @Id
    var id: Long = 0,
    @Index
    val name: String = "",
    val desc: String = "",
    @Convert(converter = TaskTypeConverter::class, dbType = String::class)
    val type: TaskType = TaskType.REPS
): Parcelable {
    @Backlink(to = "task")
    lateinit var entries: ToMany<Entry>

    lateinit var routines: ToMany<Routine>

    override fun toString(): String {
        return """
            Task:
                - id: $id
                - name: $name
                - desc: $desc
                - type: $type
        """.trimIndent()
    }
}

@Parcelize
enum class TaskType: Parcelable {
    REPS, DURATION
}