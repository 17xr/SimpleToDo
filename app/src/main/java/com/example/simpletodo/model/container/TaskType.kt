package com.example.simpletodo.model.container

import androidx.compose.ui.graphics.Color

enum class TaskType(val color: Color) {
    Personal(color = Color(0xFFE01A4F)),
    Family(color = Color(0xFFF15946)),
    Birth(color = Color(0xFFF9C22E)),
    Work(color = Color(0xFF53B3CB))
}