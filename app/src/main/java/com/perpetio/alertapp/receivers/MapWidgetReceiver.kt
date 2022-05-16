package com.perpetio.alertapp.receivers

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.util.Log
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import com.perpetio.alertapp.R
import com.perpetio.alertapp.activities.MainActivity
import com.perpetio.alertapp.data_models.Area
import com.perpetio.alertapp.utils.AlarmTimeManager
import com.perpetio.alertapp.utils.draw
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class MapWidgetReceiver : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d("123", "Update widget")
        appWidgetIds.forEach { widgetId ->
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val views = RemoteViews(
                context.packageName,
                R.layout.widget_map
            ).apply {
                //setTextViewText(R.id.txt_name, "Map Widget")
                setTextViewText(R.id.refresh_date, getDateTime())
                setImageViewBitmap(R.id.canvas_holder, drawMap(context))
                setOnClickPendingIntent(R.id.btn_refresh, getRefreshWidgetIntent(context))
            }
            appWidgetManager.updateAppWidget(widgetId, views)
        }
        AlarmTimeManager.setReminder(context)
    }

    private fun drawMap(
        context: Context
    ): Bitmap {
        val greenPaint = getPaint(R.color.green, context)
        val redPaint = getPaint(R.color.red, context)
        val canvas = Bitmap.createBitmap(
            mapWidth, mapHeight, Bitmap.Config.ARGB_8888
        )
        val areas = listOf(
            Area("uzhhorod", getBitmap(R.drawable.uzhhorod, context), PointF(46f, 333.5f)),
            Area("lviv", getBitmap(R.drawable.lviv, context), PointF(74.1f, 199.5f)),
            Area("lutsk", getBitmap(R.drawable.lutsk, context), PointF(126.9f, 83.9f)),
            Area("frank", getBitmap(R.drawable.frank, context), PointF(123.5f, 296.2f)),
            Area("ternopil", getBitmap(R.drawable.ternopil, context), PointF(188.5f, 232.5f)),
            Area("rivne", getBitmap(R.drawable.rivne, context), PointF(208.2f, 85.8f)),
            Area("chernivtsi", getBitmap(R.drawable.chernivtsi, context), PointF(200.7f, 367.8f)),
            Area("khmel", getBitmap(R.drawable.khmel, context), PointF(268.3f, 204.5f)),
            Area("zhytomyr", getBitmap(R.drawable.zhytomyr, context), PointF(326.1f, 110.5f)),
            Area("vinnytsia", getBitmap(R.drawable.vinnytsia, context), PointF(336f, 265.6f)),
            Area("kyiv", getBitmap(R.drawable.kyiv, context), PointF(441.2f, 124.7f)),
            Area("kyiv_city", getBitmap(R.drawable.kyiv_city, context), PointF(491.8f, 197.4f)),
            Area("chernihiv", getBitmap(R.drawable.chernihiv, context), PointF(509.7f, 46f)),
            Area("cherkasy", getBitmap(R.drawable.cherkasy, context), PointF(459f, 233.7f)),
            Area("poltava", getBitmap(R.drawable.poltava, context), PointF(596.1f, 208f)),
            Area("sumy", getBitmap(R.drawable.sumy, context), PointF(644.3f, 46.7f)),
            Area("odessa", getBitmap(R.drawable.odessa, context), PointF(381.9f, 405.8f)),
            Area("mikolayiv", getBitmap(R.drawable.mikolayiv, context), PointF(493.1f, 406f)),
            Area("kirovohrad", getBitmap(R.drawable.kirovohrad, context), PointF(467f, 318.5f)),
            Area("kherson", getBitmap(R.drawable.kherson, context), PointF(564.8f, 458.7f)),
            Area("dnipro", getBitmap(R.drawable.dnipro, context), PointF(646.7f, 325.2f)),
            Area("kharkiv", getBitmap(R.drawable.kharkiv, context), PointF(749.6f, 218f)),
            Area("crimea", getBitmap(R.drawable.crimea, context), PointF(618.9f, 569.4f)),
            Area(
                "zaporizhzhia",
                getBitmap(R.drawable.zaporizhzhia, context),
                PointF(714.3f, 413.5f)
            ),
            Area("donetsk", getBitmap(R.drawable.donetsk, context), PointF(843.1f, 321.1f)),
            Area("luhansk", getBitmap(R.drawable.luhansk, context), PointF(916f, 250f)),
        )

        Canvas(canvas).apply {
            areas.forEach { area ->
                val colorFlag = Random.nextInt(0, 2) == 0
                area.apply {
                    draw(image, pos.x, pos.y, if (colorFlag) redPaint else greenPaint)
                }
            }
        }
        return canvas
    }

    private fun getDateTime(): String {
        return SimpleDateFormat(
            "dd MMMM, HH:mm", Locale.getDefault()
        ).format(Date())
    }

    private fun getRefreshWidgetIntent(context: Context): PendingIntent {
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        return PendingIntent.getBroadcast(
            context, 0, getRefreshIntent(context), flags
        )
    }

    private fun getBitmap(imgResId: Int, context: Context): Bitmap {
        return BitmapFactory.decodeResource(
            context.resources, imgResId, bitmapOptions
        )
    }

    private fun getPaint(colorResId: Int, context: Context): Paint {
        val color = ContextCompat.getColor(context, colorResId)
        return Paint().apply {
            colorFilter = LightingColorFilter(0, color)
        }
    }

    companion object {
        const val mapWidth = 1090
        const val mapHeight = 760
        val bitmapOptions = BitmapFactory.Options().apply {
            inScaled = false;
        }

        fun getRefreshIntent(
            context: Context
        ): Intent {
            val componentName = ComponentName(
                context.applicationContext, MapWidgetReceiver::class.java
            )
            val ids = AppWidgetManager
                .getInstance(context.applicationContext)
                .getAppWidgetIds(componentName)
            val intent = Intent(context, MapWidgetReceiver::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            return intent
        }
    }
}