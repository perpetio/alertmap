package com.perpetio.alertapp.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.util.Log
import android.widget.RemoteViews
import com.perpetio.alertapp.R
import com.perpetio.alertapp.activities.MainActivity


class MapWidgetReceiver : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
    }

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
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val views = RemoteViews(
                context.packageName,
                R.layout.widget_map
            ).apply {
                setTextViewText(R.id.txt_name, "Map Widget")
                setImageViewBitmap(R.id.canvas_holder, drawMap(context))
                setOnClickPendingIntent(R.id.btn_open_app, pendingIntent)
            }
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }

    private fun drawMap(
        context: Context
    ): Bitmap {
        val groundImage = Bitmap.createBitmap(
            1280, 720, Bitmap.Config.ARGB_8888
        )
        val map = getBitmap(R.drawable.ukraine, context)
        Log.d("123", "map image size: ${map.width}x${map.height}")
        Log.d("123", "canvas size: ${groundImage.width}x${groundImage.height}")
        val paint = Paint().apply {
            color = Color.GREEN
        }
        val mapMatrix = Matrix().apply {
            mapRect(RectF(0f, 0f, 1280f, 1280f))
        }
        Canvas(groundImage).apply {
            drawColor(Color.LTGRAY)
            drawBitmap(map,
                Rect(0, 0, map.width, map.height),
                RectF(50f, 100f, map.width/3f, map.height/3f),
                null)
            drawLine(0f, 50f, 100f, 50f, paint)
            drawLine(0f, 710f, 1280f, 710f, paint)
        }
        return groundImage
    }

    private fun getBitmap(imgResId: Int, context: Context): Bitmap {
        return BitmapFactory.decodeResource(context.resources, imgResId)
    }
}