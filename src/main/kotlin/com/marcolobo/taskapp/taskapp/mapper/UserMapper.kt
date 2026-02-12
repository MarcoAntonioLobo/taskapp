package com.marcolobo.taskapp.taskapp.mapper

import com.marcolobo.taskapp.taskapp.dto.UserDTO
import com.marcolobo.taskapp.taskapp.model.User

object UserMapper {

    fun toDTO(user: User): UserDTO {
        return UserDTO(
            id = user.id,
            email = user.email
        )
    }
}
