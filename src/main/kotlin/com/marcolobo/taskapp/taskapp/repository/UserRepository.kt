package com.marcolobo.taskapp.taskapp.repository

import com.marcolobo.taskapp.taskapp.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean
}

