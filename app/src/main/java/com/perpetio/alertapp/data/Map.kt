package com.perpetio.alertapp.data

import android.graphics.PointF
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data_models.Area

object Map {
    val areas = listOf(
        Area("uzhhorod", R.drawable.uzhhorod, PointF(46f, 333.5f)),
        Area("lviv", R.drawable.lviv, PointF(74.1f, 199.5f)),
        Area("lutsk", R.drawable.lutsk, PointF(126.9f, 83.9f)),
        Area("frank", R.drawable.frank, PointF(123.5f, 296.2f)),
        Area("ternopil", R.drawable.ternopil, PointF(188.5f, 232.5f)),
        Area("rivne", R.drawable.rivne, PointF(208.2f, 85.8f)),
        Area("chernivtsi", R.drawable.chernivtsi, PointF(200.7f, 367.8f)),
        Area("khmel", R.drawable.khmel, PointF(268.3f, 204.5f)),
        Area("zhytomyr", R.drawable.zhytomyr, PointF(326.1f, 110.5f)),
        Area("vinnytsia", R.drawable.vinnytsia, PointF(336f, 265.6f)),
        Area("kyiv", R.drawable.kyiv, PointF(441.2f, 124.7f)),
        Area("kyiv_city", R.drawable.kyiv_city, PointF(491.8f, 197.4f)),
        Area("chernihiv", R.drawable.chernihiv, PointF(509.7f, 46f)),
        Area("cherkasy", R.drawable.cherkasy, PointF(459f, 233.7f)),
        Area("poltava", R.drawable.poltava, PointF(596.1f, 208f)),
        Area("sumy", R.drawable.sumy, PointF(644.3f, 46.7f)),
        Area("odessa", R.drawable.odessa, PointF(381.9f, 405.8f)),
        Area("mikolayiv", R.drawable.mikolayiv, PointF(493.1f, 406f)),
        Area("kirovohrad", R.drawable.kirovohrad, PointF(467f, 318.5f)),
        Area("kherson", R.drawable.kherson, PointF(564.8f, 458.7f)),
        Area("dnipro", R.drawable.dnipro, PointF(646.7f, 325.2f)),
        Area("kharkiv", R.drawable.kharkiv, PointF(749.6f, 218f)),
        Area("crimea", R.drawable.crimea, PointF(618.9f, 569.4f)),
        Area("zaporizhzhia", R.drawable.zaporizhzhia, PointF(714.3f, 413.5f)),
        Area("donetsk", R.drawable.donetsk, PointF(843.1f, 321.1f)),
        Area("luhansk", R.drawable.luhansk, PointF(916f, 250f)),
    )
}