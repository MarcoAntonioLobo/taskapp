package com.marcolobo.taskapp.taskapp.dto

import java.time.LocalDate

data class TaskUpdateRequest(
    val title: String? = null,
    val description: String? = null,
    val dueDate: LocalDate? = null,
    val done: Boolean? = null
)
