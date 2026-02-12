package com.marcolobo.taskapp.taskapp.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class TaskCreateRequest(

    @field:NotBlank(message = "O título é obrigatório")
    val title: String,

    val description: String? = null,

    @field:NotNull(message = "A data de vencimento é obrigatória")
    var dueDate: LocalDate
)
