package com.perpetio.alertapp.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF

fun Canvas.draw(bitmap: Bitmap, x: Float, y: Float) {
    val rect = RectF(x, y, bitmap.width.toFloat(), bitmap.height.toFloat())
    drawBitmap(bitmap, null, rect, null)
}

fun Canvas.drawByWidth(bitmap: Bitmap, y: Float) {
    val scaledWidth = width.toFloat()
    val scaleHeight = scaledWidth / bitmap.width.toFloat() * bitmap.height
    val rect = RectF(0f, y, scaledWidth, y+scaleHeight)
    drawBitmap(bitmap, null, rect, null)
}