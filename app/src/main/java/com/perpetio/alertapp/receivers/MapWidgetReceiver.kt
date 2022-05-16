package com.perpetio.alertapp.receivers

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import com.perpetio.alertapp.R
import com.perpetio.alertapp.activities.MainActivity
import com.perpetio.alertapp.data.Map
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
        Canvas(canvas).apply {
            Map.areas.forEach { area ->
                val colorFlag = Random.nextInt(0, 2) == 0
                area.apply {
                    val image = getBitmap(imageResId, context)
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