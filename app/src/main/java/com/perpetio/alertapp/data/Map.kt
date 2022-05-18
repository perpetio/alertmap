package com.perpetio.alertapp.data

import android.graphics.PointF
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data_models.MapAreaModel

object Map {
    const val width = 1090
    const val height = 760

    val areas = listOf(
        MapAreaModel(6, R.drawable.uzhhorod, PointF(46f, 333.5f)),
        MapAreaModel(12, R.drawable.lviv, PointF(74.1f, 199.5f)),
        MapAreaModel(2, R.drawable.lutsk, PointF(126.9f, 83.9f)),
        MapAreaModel(8, R.drawable.frank, PointF(123.5f, 296.2f)),
        MapAreaModel(18, R.drawable.ternopil, PointF(188.5f, 232.5f)),
        MapAreaModel(16, R.drawable.rivne, PointF(208.2f, 85.8f)),
        MapAreaModel(23, R.drawable.chernivtsi, PointF(200.7f, 367.8f)),
        MapAreaModel(21, R.drawable.khmel, PointF(268.3f, 204.5f)),
        MapAreaModel(5, R.drawable.zhytomyr, PointF(326.1f, 110.5f)),
        MapAreaModel(1, R.drawable.vinnytsia, PointF(336f, 265.6f)),
        MapAreaModel(9, R.drawable.kyiv, PointF(441.2f, 124.7f)),
        MapAreaModel(25, R.drawable.kyiv_city, PointF(491.8f, 197.4f)),
        MapAreaModel(24, R.drawable.chernihiv, PointF(509.7f, 46f)),
        MapAreaModel(22, R.drawable.cherkasy, PointF(459f, 233.7f)),
        MapAreaModel(15, R.drawable.poltava, PointF(596.1f, 208f)),
        MapAreaModel(17, R.drawable.sumy, PointF(644.3f, 46.7f)),
        MapAreaModel(14, R.drawable.odessa, PointF(381.9f, 405.8f)),
        MapAreaModel(13, R.drawable.mikolayiv, PointF(493.1f, 406f)),
        MapAreaModel(10, R.drawable.kirovohrad, PointF(467f, 318.5f)),
        MapAreaModel(20, R.drawable.kherson, PointF(564.8f, 458.7f)),
        MapAreaModel(3, R.drawable.dnipro, PointF(646.7f, 325.2f)),
        MapAreaModel(19, R.drawable.kharkiv, PointF(749.6f, 218f)),
        MapAreaModel(-1, R.drawable.crimea, PointF(618.9f, 569.4f)),
        MapAreaModel(-1, R.drawable.sevastopol, PointF(668.2f, 677.3f)),
        MapAreaModel(7, R.drawable.zaporizhzhia, PointF(714.3f, 413.5f)),
        MapAreaModel(4, R.drawable.donetsk, PointF(843.1f, 321.1f)),
        MapAreaModel(11, R.drawable.luhansk, PointF(916f, 250f)),
    )
}