package com.storiqa.storiqawallet.network.network_responses

data class GetTokenResponseByEmail(val data : TokenData?, val errors : List<GetTokenError>?)

//data
data class TokenData(val getJWTByEmail : GetJWTByEmail)

data class GetJWTByEmail(val token : String)

//error
data class GetTokenError(val data : GetTokenErrorData)

data class GetTokenErrorData(val code : Int, val details : GetTokenErrorDataDetails)

data class GetTokenErrorDataDetails(val code : String, val description : String, val message : String, val payload : String, val status : String)
