package com.marcolobo.taskapp.taskapp.repository

import com.marcolobo.taskapp.taskapp.model.Task
import com.marcolobo.taskapp.taskapp.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface TaskRepository : JpaRepository<Task, Long> {

    fun findByUser(user: User): List<Task>

    fun findByUserAndDueDate(user: User, dueDate: LocalDate): List<Task>

    fun findByIdAndUser(id: Long, user: User): Task?
}
