package com.perpetio.alertapp.data_models

import com.google.gson.annotations.SerializedName

data class StateModel(
    val id: Int,
    @SerializedName("alert")
    val isAlert: Boolean,
    @SerializedName("changed")
    val updateTime: String,
    val name: String,
    @SerializedName("name_en")
    val nameEn: String
)

data class StatesInfoModel(
    val states: List<StateModel>,
    @SerializedName("last_update")
    val refreshTime: String,
)