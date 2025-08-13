package com.pluu.sample.saved.model

import java.io.Serializable

data class UserDto(
    val id: Int,
    val name: String
) : Serializable