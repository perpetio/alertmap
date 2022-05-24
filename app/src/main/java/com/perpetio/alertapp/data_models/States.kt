package com.perpetio.alertapp.data_models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StateModel(
    val id: Int,
    @SerializedName("alert")
    val isAlert: Boolean,
    @SerializedName("changed")
    val updateTime: String,
    val name: String,
    @SerializedName("name_en")
    val nameEn: String
): Parcelable

@Parcelize
data class StatesInfoModel(
    val states: List<StateModel>,
    @SerializedName("last_update")
    val refreshTime: String,
): Parcelable