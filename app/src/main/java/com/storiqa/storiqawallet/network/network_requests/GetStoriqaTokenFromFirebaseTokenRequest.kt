package com.storiqa.storiqawallet.network.network_requests

class GetStoriqaTokenFromFirebaseTokenRequest(token: String, provider: String) {
    val operationName = "getJWTByProvider"
    val query = "mutation getJWTByProvider(\$input: CreateJWTProviderInput!) { getJWTByProvider(input: \$input) { token } }"
    val variables = HashMap<String, HashMap<String, String>>()

    init {
        val parameters = HashMap<String, String>()
        parameters["clientMutationId"] = ""
        parameters["provider"] = provider
        parameters["token"] = token

        variables["input"] = parameters
    }
}