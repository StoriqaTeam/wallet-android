package com.storiqa.storiqawallet.network.network_requests

class ResetPasswordRequest(email: String) {
    val operationName = "requestPasswordReset"
    val query = "mutation requestPasswordReset(\$input: ResetRequest!) { requestPasswordReset(input: \$input) { success } }"
    val variables = HashMap<String, HashMap<String, String>>()

    init {
        val inputs = HashMap<String, String>()
        inputs["clientMutationId"] = ""
        inputs["email"] = email

        variables["input"] = inputs
    }
}