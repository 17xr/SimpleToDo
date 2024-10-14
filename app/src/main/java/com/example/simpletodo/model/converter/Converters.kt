package com.example.simpletodo.model.converter

import androidx.room.TypeConverter
import com.example.simpletodo.model.container.TaskType
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromTaskType(taskType: TaskType): Int {
        return taskType.ordinal
    }

    @TypeConverter
    fun toTaskType(int: Int): TaskType {
        return when (int) {
            0 -> TaskType.Personal
            1 -> TaskType.Family
            2 -> TaskType.Birth
            else -> TaskType.Work
        }
    }

    @TypeConverter
    fun fromLocalDate(localDate: LocalDate): String {
        return localDate.toString()
    }

    @TypeConverter
    fun toLocalDate(string: String): LocalDate {
        return LocalDate.parse(string)
    }
}