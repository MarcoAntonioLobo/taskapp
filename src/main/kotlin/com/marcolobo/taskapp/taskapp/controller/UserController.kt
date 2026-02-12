package com.marcolobo.taskapp.taskapp.controller

import com.marcolobo.taskapp.taskapp.dto.UserCreateRequest
import com.marcolobo.taskapp.taskapp.dto.UserDTO
import com.marcolobo.taskapp.taskapp.dto.UserUpdateRequest
import com.marcolobo.taskapp.taskapp.mapper.UserMapper
import com.marcolobo.taskapp.taskapp.model.User
import com.marcolobo.taskapp.taskapp.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = ["http://localhost:4200"])
class UserController(
    private val userService: UserService
) {

    // ✅ Criar usuário
    @PostMapping
    fun createUser(@RequestBody request: UserCreateRequest): ResponseEntity<UserDTO> {
        val user = userService.createUser(request)
        return ResponseEntity.status(201).body(UserMapper.toDTO(user))
    }

    // ✅ Listar todos
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDTO>> {
        val users = userService.findAll().map(UserMapper::toDTO)
        return ResponseEntity.ok(users)
    }

    // ✅ Buscar por ID
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDTO> {
        val user = userService.findById(id)
        return ResponseEntity.ok(UserMapper.toDTO(user))
    }

    // ✅ Atualizar
    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody request: UserUpdateRequest
    ): ResponseEntity<UserDTO> {
        val updatedUser = userService.updateUser(id, request)
        return ResponseEntity.ok(UserMapper.toDTO(updatedUser))
    }

    // ✅ Deletar
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }

    // ✅ Usuário load
    @GetMapping("/me")
    fun getLoggedUser(authentication: Authentication): ResponseEntity<UserDTO> {
        val user: User = userService.findByEmail(authentication.name)
        return ResponseEntity.ok(UserMapper.toDTO(user))
    }
}
