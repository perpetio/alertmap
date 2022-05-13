package com.perpetio.alertapp.data_models

import android.graphics.Bitmap
import android.graphics.PointF

data class Area(
    val name: String,
    val image: Bitmap,
    val pos: PointF
)