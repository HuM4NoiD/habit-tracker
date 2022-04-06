package io.humanoid.habittracker.datum.model

import android.os.Parcelable
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.relation.ToMany
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Routine(
    @Id
    var id: Long = 0,
    @Index
    val name: String,
    val interval: Int,
): Parcelable {
    lateinit var tasks: ToMany<Task>

    override fun toString(): String {
        return """
        Routine:
            - id: $id
            - name: $name
            - interval: $interval seconds
        """.trimIndent()
    }
}
