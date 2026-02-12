package com.marcolobo.taskapp.taskapp.dto

import java.time.LocalDate

data class TaskDTO(
    val id: Long?,
    val title: String,
    val description: String?,
    val dueDate: LocalDate,
    val done: Boolean
)
