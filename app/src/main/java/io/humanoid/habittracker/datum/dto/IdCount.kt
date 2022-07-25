package io.humanoid.habittracker.datum.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IdCount(
    val id: Long,
    val count: Int
): Parcelable

