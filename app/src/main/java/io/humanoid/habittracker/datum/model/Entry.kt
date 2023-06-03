package io.humanoid.habittracker.datum.model

import android.os.Parcelable
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Entry(
    @Id
    var id: Long = 0,
    val count: Int = 0
): Parcelable {
    lateinit var task: ToOne<Task>
}
