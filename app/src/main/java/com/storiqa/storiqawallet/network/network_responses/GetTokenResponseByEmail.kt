package com.storiqa.storiqawallet.network.network_responses

data class GetTokenResponseByEmail(val data : TokenData?, val errors : List<ErrorInfo>?)

//data
data class TokenData(val getJWTByEmail : GetJWTByEmail)

data class GetJWTByEmail(val token : String)

//error
data class ErrorInfo(val data : ErrorInfoBody)

data class ErrorInfoBody(val code : Int, val details : ErrorInfoBodyDetails)

data class ErrorInfoBodyDetails(val code : String, val description : String, val message : String, val payload : String, val status : String)
