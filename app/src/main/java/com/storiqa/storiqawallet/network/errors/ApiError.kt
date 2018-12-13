package com.storiqa.storiqawallet.network.errors

sealed class ApiError : Exception()

object BadRequest : ApiError()

object InternalServerError : ApiError()

object UnknownError : ApiError()

class UnprocessableEntity(val validationErrors: HashMap<String, Array<ValidationError>>) : ApiError()