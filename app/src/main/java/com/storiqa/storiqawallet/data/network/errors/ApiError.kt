package com.storiqa.storiqawallet.data.network.errors

sealed class ApiError : Exception()

object BadRequest : ApiError()

object InternalServerError : ApiError()

object UnknownError : ApiError()

object TokenExpired : ApiError()

class UnprocessableEntity(val validationErrors: HashMap<String, Array<ValidationError>>) : ApiError()