package io.humanoid.habittracker.datum.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppSettings(
    val repsDuration: Int = 60,
): Parcelable