package com.perpetio.alertapp.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.Size

fun Canvas.draw(bitmap: Bitmap, x: Float, y: Float) {
    val rect = RectF(x, y, bitmap.width.toFloat(), bitmap.height.toFloat())
    drawBitmap(bitmap, null, rect, null)
}

fun Canvas.drawByWidth(bitmap: Bitmap, y: Float, paint: Paint) {
    val scaledWidth = width.toFloat()
    val scaleHeight = scaledWidth / bitmap.width.toFloat() * bitmap.height
    val rect = RectF(0f, y, scaledWidth, y+scaleHeight)
    drawBitmap(bitmap, null, rect, paint)
}

fun Canvas.draw(bitmap: Bitmap, x: Float, y: Float, paint: Paint? = null) {
    val rect = RectF(x, y, x + bitmap.width.toFloat(), y + bitmap.height.toFloat())
    drawBitmap(bitmap, null, rect, paint)
}