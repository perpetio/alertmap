package com.perpetio.alertapp.utils

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data.Map
import com.perpetio.alertapp.data_models.StateModel
import kotlin.random.Random

object MapDrawer {
    private val bitmapOptions = BitmapFactory.Options().apply {
        inScaled = false;
    }

    fun drawMap(
        states: List<StateModel>,
        context: Context
    ): Bitmap {
        val greenPaint = getPaint(R.color.green, context)
        val redPaint = getPaint(R.color.red, context)
        val canvas = Bitmap.createBitmap(
            Map.width, Map.height, Bitmap.Config.ARGB_8888
        )
        Canvas(canvas).apply {
            Map.areas.forEach { area ->
                val colorFlag = Random.nextInt(0, 2) == 0
                area.apply {
                    val image = getBitmap(imageResId, context)
                    val isAlert = states.find { state ->
                        state.id == id
                    }?.isAlert == true
                    draw(image, pos.x, pos.y, if (isAlert) redPaint else greenPaint)
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