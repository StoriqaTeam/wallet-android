package com.storiqa.storiqawallet.network.network_requests

class RegisterUserRequest(email: String, password: String, firstName: String, lastName: String) {
    val operationName = "createUser"
    val query = "mutation createUser(\$input: CreateUserInput!) { createUser(input: \$input) { rawId } }"
    val variables = HashMap<String, HashMap<String, String>>()

    init {
        val inputs = HashMap<String, String>()
        inputs["clientMutationId"] = ""
        inputs["email"] = email
        inputs["password"] = password
        inputs["firstName"] = firstName
        inputs["lastName"] = lastName

        variables["input"] = inputs
    }
}