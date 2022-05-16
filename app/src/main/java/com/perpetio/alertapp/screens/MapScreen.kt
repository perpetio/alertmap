package com.perpetio.alertapp.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.perpetio.alertapp.R
import com.perpetio.alertapp.utils.MapDrawer

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MapScreen(
    // todo
) {
    val bitmap = MapDrawer.drawMap(LocalContext.current).asImageBitmap()
    Canvas(
        modifier = Modifier.fillMaxWidth(),
        contentDescription = stringResource(R.string.alert_map)
    ) {
        drawImage(bitmap)
    }
}