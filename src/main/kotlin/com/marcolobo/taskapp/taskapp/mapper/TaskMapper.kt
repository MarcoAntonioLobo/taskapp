package com.marcolobo.taskapp.taskapp.mapper

import com.marcolobo.taskapp.taskapp.dto.TaskCreateRequest
import com.marcolobo.taskapp.taskapp.dto.TaskDTO
import com.marcolobo.taskapp.taskapp.model.Task
import com.marcolobo.taskapp.taskapp.model.User
import java.time.LocalDate

object TaskMapper {

    fun toDTO(task: Task): TaskDTO {
        return TaskDTO(
            id = task.id,
            title = task.title,
            description = task.description,
            dueDate = task.dueDate,
            done = task.done
        )
    }

    fun toEntity(request: TaskCreateRequest, user: User): Task {
        return Task(
            title = request.title,
            description = request.description,
            dueDate = request.dueDate,
            done = false,
            user = user
        )
    }

    fun updateEntity(task: Task, title: String?, description: String?, dueDate: LocalDate?, done: Boolean?): Task {
        title?.let { task.title = it }
        description?.let { task.description = it }
        dueDate?.let { task.dueDate = it }
        done?.let { task.done = it }
        return task
    }
}
