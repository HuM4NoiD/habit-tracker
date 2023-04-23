package io.humanoid.habittracker.datum.model

import android.os.Parcelable
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.relation.ToOne
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class TaskLink(
    @Id
    var id: Long = 0,
    @Index
    val index: Int = 0
): Parcelable {
    lateinit var link: ToOne<Task>

    lateinit var routine: ToOne<Routine>
}
