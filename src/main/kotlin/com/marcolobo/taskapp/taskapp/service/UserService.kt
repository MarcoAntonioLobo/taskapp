package com.marcolobo.taskapp.taskapp.service

import com.marcolobo.taskapp.taskapp.dto.UserCreateRequest
import com.marcolobo.taskapp.taskapp.dto.UserUpdateRequest
import com.marcolobo.taskapp.taskapp.model.User
import com.marcolobo.taskapp.taskapp.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository
) {

    @Transactional
    fun createUser(request: UserCreateRequest): User {

        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email já está em uso")
        }

        val user = User(
            email = request.email,
            password = request.password
        )

        return userRepository.save(user)
    }

    @Transactional(readOnly = true)
    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Usuário não encontrado") }
    }

    @Transactional(readOnly = true)
    fun findByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("Usuário com email $email não encontrado")
    }

    @Transactional
    fun updateUser(id: Long, request: UserUpdateRequest): User {

        val user = findById(id)

        request.email?.let {
            if (userRepository.existsByEmail(it) && it != user.email) {
                throw IllegalArgumentException("Email já está em uso")
            }
            user.email = it
        }

        request.password?.let {
            user.password = it
        }

        return user
    }

    @Transactional
    fun deleteUser(id: Long) {

        val user = findById(id)
        userRepository.delete(user)
    }
}
