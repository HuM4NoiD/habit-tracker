package io.humanoid.habittracker.datum.dto

import android.os.Parcelable
import io.humanoid.habittracker.datum.model.TaskType
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskDoneSummaryItem(
    val taskName: String,
    val taskType: TaskType,
    val count: Int
): Parcelable