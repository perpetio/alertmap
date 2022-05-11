package com.perpetio.alertapp.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.perpetio.alertapp.R

@Composable
fun MapScreen(
    // todo
) {
    val image = ImageBitmap.imageResource(R.drawable.ukraine)
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawImage(
            image = image,
            topLeft = Offset(x = 5f, y = 0f)
        )
    }
}