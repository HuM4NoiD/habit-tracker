package io.humanoid.habittracker.ui.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IdCount(
    val id: Long,
    val count: Int
): Parcelable

