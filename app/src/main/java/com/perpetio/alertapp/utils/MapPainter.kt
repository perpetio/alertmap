package com.perpetio.alertapp.utils

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data.Map
import com.perpetio.alertapp.data_models.StateModel

object MapDrawer {
    private val bitmapOptions = BitmapFactory.Options().apply {
        inScaled = false;
    }

    fun drawMap(
        states: List<StateModel>,
        context: Context
    ): Bitmap {
        val calmPaint = getPaint(R.color.calm, context)
        val alertPaint = getPaint(R.color.alert, context)
        val noInfoPaint = getPaint(R.color.noInfo, context)
        val canvas = Bitmap.createBitmap(
            Map.width, Map.height, Bitmap.Config.ARGB_8888
        )
        Canvas(canvas).apply {
            Map.areas.forEach { area ->
                area.apply {
                    //val isAlert = Random.nextInt(0, 2) == 0
                    val image = getBitmap(imageResId, context)
                    val isAlert = states.find { state ->
                        state.id == id
                    }?.isAlert
                    val paint = when (isAlert){
                        null -> noInfoPaint
                        true -> alertPaint
                        else -> calmPaint
                    }
                    draw(image, pos.x, pos.y, paint)
                }
            }
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
}

fun Canvas.draw(bitmap: Bitmap, x: Float, y: Float, paint: Paint? = null) {
    val rect = RectF(x, y, x + bitmap.width.toFloat(), y + bitmap.height.toFloat())
    drawBitmap(bitmap, null, rect, paint)
}