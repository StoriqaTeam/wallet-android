package com.storiqa.storiqawallet.network.network_requests

import kotlin.collections.HashMap

class GetTokenByEmailRequest(email : String, password : String) {
    val operationName = "getJWTByEmail"
    val query = "mutation getJWTByEmail(\$input: CreateJWTEmailInput!) { getJWTByEmail(input: \$input) { token } }"
    val variables = HashMap<String, HashMap<String, String>>()

    init {
        val parameters = HashMap<String, String> ()
        parameters["clientMutationId"] = ""
        parameters["email"] = email
        parameters["password"] = password

        variables["input"] = parameters
    }

}