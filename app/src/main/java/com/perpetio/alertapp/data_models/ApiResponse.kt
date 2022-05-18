package com.perpetio.alertapp.data_models

data class ApiResponse<T>(
    val data: T? = null,
    val error: ErrorResponse? = null
)

data class ErrorResponse(
    val message: String
)