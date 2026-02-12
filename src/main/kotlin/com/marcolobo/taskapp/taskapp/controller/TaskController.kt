package com.marcolobo.taskapp.taskapp.controller

import com.marcolobo.taskapp.taskapp.dto.TaskCreateRequest
import com.marcolobo.taskapp.taskapp.dto.TaskDTO
import com.marcolobo.taskapp.taskapp.dto.TaskUpdateRequest
import com.marcolobo.taskapp.taskapp.mapper.TaskMapper
import com.marcolobo.taskapp.taskapp.model.User
import com.marcolobo.taskapp.taskapp.service.TaskService
import com.marcolobo.taskapp.taskapp.service.UserService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = ["http://localhost:4200"])
class TaskController(
    private val taskService: TaskService,
    private val userService: UserService
) {

    @GetMapping
    fun getTasks(authentication: Authentication): ResponseEntity<List<TaskDTO>> {
        val user: User = userService.findByEmail(authentication.name)
        val tasks: List<TaskDTO> = taskService.getTasksByUser(user).map(TaskMapper::toDTO)
        return ResponseEntity.ok(tasks)
    }

    @PostMapping
    fun createTask(
        authentication: Authentication,
        @RequestBody request: TaskCreateRequest
    ): ResponseEntity<TaskDTO> {
        val user: User = userService.findByEmail(authentication.name)
        val taskDTO: TaskDTO = TaskMapper.toDTO(taskService.createTask(user, request))
        return ResponseEntity.status(201).body(taskDTO)
    }

    @PutMapping("/{id}")
    fun updateTask(
        authentication: Authentication,
        @PathVariable id: Long,
        @RequestBody request: TaskUpdateRequest
    ): ResponseEntity<TaskDTO> {
        val user: User = userService.findByEmail(authentication.name)
        val taskDTO: TaskDTO = TaskMapper.toDTO(taskService.updateTask(user, id, request))
        return ResponseEntity.ok(taskDTO)
    }

    @DeleteMapping("/{id}")
    fun deleteTask(
        authentication: Authentication,
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        val user: User = userService.findByEmail(authentication.name)
        taskService.deleteTask(user, id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/by-date")
    fun getTasksByDate(
        authentication: Authentication,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): ResponseEntity<List<TaskDTO>> {
        val user: User = userService.findByEmail(authentication.name)
        val tasks: List<TaskDTO> = taskService.getTasksByDate(user, date).map(TaskMapper::toDTO)
        return ResponseEntity.ok(tasks)
    }
}
