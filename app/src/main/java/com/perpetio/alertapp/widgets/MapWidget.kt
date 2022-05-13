package com.perpetio.alertapp.widgets

import android.R.attr.resource
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.util.Log
import android.util.Size
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import com.perpetio.alertapp.R
import com.perpetio.alertapp.activities.MainActivity
import com.perpetio.alertapp.utils.draw
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
        val greenPaint = getPaint(R.color.green, context)
        val redPaint = getPaint(R.color.red, context)
        val canvas = Bitmap.createBitmap(
            mapWidth, mapHeight, Bitmap.Config.ARGB_8888
        )
        val uzhhorod = getBitmap(R.drawable.uzhhorod, context)
        val lviv = getBitmap(R.drawable.lviv, context)
        val lutsk = getBitmap(R.drawable.lutsk, context)
        val frank = getBitmap(R.drawable.frank, context)
        val ternopil = getBitmap(R.drawable.ternopil, context)
        val rivne = getBitmap(R.drawable.rivne, context)
        val chernivtsi = getBitmap(R.drawable.chernivtsi, context)
        val khmel = getBitmap(R.drawable.khmel, context)
        val zhytomyr = getBitmap(R.drawable.zhytomyr, context)
        val vinnytsia = getBitmap(R.drawable.vinnytsia, context)
        val kyiv = getBitmap(R.drawable.kyiv, context)
        val chernihiv = getBitmap(R.drawable.chernihiv, context)
        val cherkasy = getBitmap(R.drawable.cherkasy, context)
        val poltava = getBitmap(R.drawable.poltava, context)
        val sumy = getBitmap(R.drawable.sumy, context)
        val odessa = getBitmap(R.drawable.odessa, context)
        val mikolayiv = getBitmap(R.drawable.mikolayiv, context)
        val kherson = getBitmap(R.drawable.kherson, context)
        val dnipro = getBitmap(R.drawable.dnipro, context)
        val kharkiv = getBitmap(R.drawable.kharkiv, context)
        val crimea = getBitmap(R.drawable.crimea, context)
        val zaporizhzhia = getBitmap(R.drawable.zaporizhzhia, context)
        val donetsk = getBitmap(R.drawable.donetsk, context)
        val luhansk = getBitmap(R.drawable.luhansk, context)

        Log.d("123", "lviv image size: ${lviv.width}x${lviv.height}")
        Log.d("123", "uzhhorod image size: ${uzhhorod.width}x${uzhhorod.height}")
        Log.d("123", "groundImage size: ${canvas.width}x${canvas.height}")

        Canvas(canvas).apply {
            drawColor(Color.LTGRAY)
            draw(lviv, 174.1f, 286.5f, greenPaint)
            draw(uzhhorod, 146f, 420.5f, redPaint)
        }
        return canvas
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
        val mapWidth = 1144
        val mapHeight = 668
        val bitmapOptions = BitmapFactory.Options().apply {
            inScaled = false;
        }
    }
}