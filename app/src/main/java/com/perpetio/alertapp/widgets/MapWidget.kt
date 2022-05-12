package com.perpetio.alertapp.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.util.Log
import android.util.Size
import android.widget.RemoteViews
import com.perpetio.alertapp.R
import com.perpetio.alertapp.activities.MainActivity
import com.perpetio.alertapp.utils.drawByWidth


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
                //setTextViewText(R.id.txt_name, "Map Widget")
                setImageViewBitmap(R.id.canvas_holder, drawMap(context))
                //setOnClickPendingIntent(R.id.btn_open_app, pendingIntent)
            }
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }

    private fun drawMap(
        context: Context
    ): Bitmap {
        val canvasSize = getCanvasSize(context)
        Log.d("123", "widget size: ${canvasSize.width}x${canvasSize.height}")
        val canvas = Bitmap.createBitmap(
            canvasSize.width, canvasSize.height, Bitmap.Config.ARGB_8888
        )
        val map = getBitmap(R.drawable.ukraine, context)
        Log.d("123", "map image size: ${map.width}x${map.height}")
        Log.d("123", "groundImage size: ${canvas.width}x${canvas.height}")

        val paint = Paint().apply {
            color = Color.GREEN
        }
        Canvas(canvas).apply {
            Log.d("123", "canvas size: ${width}x${height}")
            //drawColor(Color.LTGRAY)
            drawByWidth(map, -70f)
            //drawLine(500f, 50f, 400f, 50f, paint)
            //drawLine(0f, 350f, 400f, 350f, paint)
        }
        return canvas
    }

    private fun getBitmap(imgResId: Int, context: Context): Bitmap {
        return BitmapFactory.decodeResource(
            context.resources, imgResId, bitmapOptions
        )
    }

    private fun getCanvasSize(context: Context): Size {
        context.resources.apply {
            return Size(
                getDimension(R.dimen.widget_width).toInt(),
                getDimension(R.dimen.widget_canvas_height).toInt()
            )
        }
    }

    companion object {
        val bitmapOptions = BitmapFactory.Options().apply {
            inScaled = false;
        }
    }
}