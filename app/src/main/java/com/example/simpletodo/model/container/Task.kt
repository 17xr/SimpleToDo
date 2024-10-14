package com.example.simpletodo.model.container

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val date: LocalDate = LocalDate.now(),
    val type: TaskType = TaskType.Personal,
    val done: Boolean = false
)

fun Task.toTaskDetails(): TaskDetails {
    return TaskDetails(
        id = id,
        title = title,
        description = description,
        date = date,
        type = type,
        done = done
    )
}