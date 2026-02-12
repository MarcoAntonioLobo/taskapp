package com.marcolobo.taskapp.taskapp.service

import com.marcolobo.taskapp.taskapp.dto.TaskCreateRequest
import com.marcolobo.taskapp.taskapp.dto.TaskUpdateRequest
import com.marcolobo.taskapp.taskapp.mapper.TaskMapper
import com.marcolobo.taskapp.taskapp.model.Task
import com.marcolobo.taskapp.taskapp.model.User
import com.marcolobo.taskapp.taskapp.repository.TaskRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class TaskService(
    private val taskRepository: TaskRepository
) {

    @Transactional(readOnly = true)
    fun getTasksByUser(user: User): List<Task> = taskRepository.findByUser(user)

    @Transactional(readOnly = true)
    fun getTasksByDate(user: User, date: LocalDate): List<Task> = taskRepository.findByUserAndDueDate(user, date)

    @Transactional
    fun createTask(user: User, request: TaskCreateRequest): Task {
        val task = TaskMapper.toEntity(request, user)
        return taskRepository.save(task)
    }

    @Transactional
    fun updateTask(user: User, taskId: Long, request: TaskUpdateRequest): Task {
        val task = taskRepository.findByIdAndUser(taskId, user)
            ?: throw IllegalArgumentException("Tarefa não encontrada")
        TaskMapper.updateEntity(task, request.title, request.description, request.dueDate, request.done)
        return task
    }

    @Transactional
    fun deleteTask(user: User, taskId: Long) {
        val task = taskRepository.findByIdAndUser(taskId, user)
            ?: throw IllegalArgumentException("Tarefa não encontrada")
        taskRepository.delete(task)
    }
}
