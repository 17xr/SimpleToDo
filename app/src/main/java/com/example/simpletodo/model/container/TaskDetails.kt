package com.example.simpletodo.model.container

import java.time.LocalDate

data class TaskDetails(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val date: LocalDate = LocalDate.now(),
    val type: TaskType = TaskType.Personal,
    val done: Boolean = false
)

fun TaskDetails.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        date = date,
        type = type,
        done = done
    )
}

fun TaskDetails.isValid(): Boolean {
    return title.isNotEmpty() && description.isNotEmpty()
}