package com.perpetio.alertapp.data

import android.graphics.PointF
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data_models.MapAreaModel

object Map {
    const val width = 1090
    const val height = 760

    val areas = listOf(
        MapAreaModel("uzhhorod", R.drawable.uzhhorod, PointF(46f, 333.5f)),
        MapAreaModel("lviv", R.drawable.lviv, PointF(74.1f, 199.5f)),
        MapAreaModel("lutsk", R.drawable.lutsk, PointF(126.9f, 83.9f)),
        MapAreaModel("frank", R.drawable.frank, PointF(123.5f, 296.2f)),
        MapAreaModel("ternopil", R.drawable.ternopil, PointF(188.5f, 232.5f)),
        MapAreaModel("rivne", R.drawable.rivne, PointF(208.2f, 85.8f)),
        MapAreaModel("chernivtsi", R.drawable.chernivtsi, PointF(200.7f, 367.8f)),
        MapAreaModel("khmel", R.drawable.khmel, PointF(268.3f, 204.5f)),
        MapAreaModel("zhytomyr", R.drawable.zhytomyr, PointF(326.1f, 110.5f)),
        MapAreaModel("vinnytsia", R.drawable.vinnytsia, PointF(336f, 265.6f)),
        MapAreaModel("kyiv", R.drawable.kyiv, PointF(441.2f, 124.7f)),
        MapAreaModel("kyiv_city", R.drawable.kyiv_city, PointF(491.8f, 197.4f)),
        MapAreaModel("chernihiv", R.drawable.chernihiv, PointF(509.7f, 46f)),
        MapAreaModel("cherkasy", R.drawable.cherkasy, PointF(459f, 233.7f)),
        MapAreaModel("poltava", R.drawable.poltava, PointF(596.1f, 208f)),
        MapAreaModel("sumy", R.drawable.sumy, PointF(644.3f, 46.7f)),
        MapAreaModel("odessa", R.drawable.odessa, PointF(381.9f, 405.8f)),
        MapAreaModel("mikolayiv", R.drawable.mikolayiv, PointF(493.1f, 406f)),
        MapAreaModel("kirovohrad", R.drawable.kirovohrad, PointF(467f, 318.5f)),
        MapAreaModel("kherson", R.drawable.kherson, PointF(564.8f, 458.7f)),
        MapAreaModel("dnipro", R.drawable.dnipro, PointF(646.7f, 325.2f)),
        MapAreaModel("kharkiv", R.drawable.kharkiv, PointF(749.6f, 218f)),
        MapAreaModel("crimea", R.drawable.crimea, PointF(618.9f, 569.4f)),
        MapAreaModel("sevastopol", R.drawable.sevastopol, PointF(668.2f, 677.3f)),
        MapAreaModel("zaporizhzhia", R.drawable.zaporizhzhia, PointF(714.3f, 413.5f)),
        MapAreaModel("donetsk", R.drawable.donetsk, PointF(843.1f, 321.1f)),
        MapAreaModel("luhansk", R.drawable.luhansk, PointF(916f, 250f)),
    )
}