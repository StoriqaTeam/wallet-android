package com.storiqa.storiqawallet.network.network_responses

data class ResetPasswordResponse(val data: ResetPasswordData)

data class ResetPasswordData(val requestPasswordReset : ResetRequest)

data class ResetRequest(val success : Boolean)