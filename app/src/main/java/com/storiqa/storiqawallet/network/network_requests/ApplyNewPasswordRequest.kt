package com.storiqa.storiqawallet.network.network_requests

class ApplyNewPasswordRequest(email : String, token : String, password : String) {
    val operationName = "applyPasswordReset"
    val query = "mutation applyPasswordReset(\$input: ResetApply!) { applyPasswordReset(input: \$input) { token } }"
    val variables = HashMap<String, HashMap<String, String>>()

    init {
        val inputs = HashMap<String, String>()
        inputs["clientMutationId"] = ""
        inputs["email"] = email
        inputs["token"] = token
        inputs["password"] = password

        variables["input"] = inputs
    }
}