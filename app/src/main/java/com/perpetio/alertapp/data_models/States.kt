package com.perpetio.alertapp.data_models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class StateModel(
    val id: Int,
    @SerializedName("alert")
    val isAlert: Boolean,
    @SerializedName("changed")
    val updateTime: String?,
    val name: String,
    @SerializedName("name_en")
    val nameEn: String,
    var isChecked: Boolean
): Parcelable

@Parcelize
data class StatesInfoModel(
    val states: List<StateModel>,
    val refreshTime: Date?,
): Parcelable