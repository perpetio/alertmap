package com.perpetio.alertapp.repository

import kotlinx.coroutines.delay

class Repository {
    suspend fun refreshMap(): List<DistrictState> {
        delay(1_000)
        return emptyList()
    }
}