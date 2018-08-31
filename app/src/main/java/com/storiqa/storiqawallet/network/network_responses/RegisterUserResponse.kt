package com.storiqa.storiqawallet.network.network_responses

data class RegisterUserResponse(val data : RegisterData, val errors : List<ErrorInfo>?) //TODO check, rename

data class RegisterData(val createUser : CreateUser)

data class CreateUser(val rawId : Long)