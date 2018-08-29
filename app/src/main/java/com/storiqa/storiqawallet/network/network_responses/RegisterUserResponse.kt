package com.storiqa.storiqawallet.network.network_responses

data class RegisterUserResponse(val data : RegisterData)

data class RegisterData(val createUser : CreateUser)

data class CreateUser(val rawId : Long)