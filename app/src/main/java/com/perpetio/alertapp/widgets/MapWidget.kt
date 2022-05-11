package com.perpetio.alertapp.widgets

import androidx.compose.runtime.Composable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class MapWidget : GlanceAppWidget() {
    @Composable
    override fun Content() {
    }
}

class MapWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MapWidget()
}