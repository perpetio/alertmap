package com.perpetio.alertapp.receivers

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.perpetio.alertapp.R
import com.perpetio.alertapp.utils.AlarmTimeManager
import com.perpetio.alertapp.utils.Formatter
import com.perpetio.alertapp.utils.MapDrawer
import java.util.*


class MapWidgetReceiver : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { widgetId ->
            val views = RemoteViews(
                context.packageName,
                R.layout.widget_map
            ).apply {
                setTextViewText(R.id.tv_refresh_date, Formatter.getShortFormat(Date()))
                setImageViewBitmap(R.id.img_map_holder, MapDrawer.drawMap(emptyList(), context))
                setOnClickPendingIntent(R.id.btn_refresh, getRefreshWidgetIntent(context))
            }
            appWidgetManager.updateAppWidget(widgetId, views)
        }
        AlarmTimeManager.setReminder(context)
    }

    private fun getRefreshWidgetIntent(context: Context): PendingIntent {
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        return PendingIntent.getBroadcast(
            context, 0, getRefreshIntent(context), flags
        )
    }

    companion object {
        fun checkUpdate(context: Context) {
            val refreshIntent = getRefreshIntent(context)
            context.sendBroadcast(refreshIntent)
        }

        private fun getRefreshIntent(
            context: Context
        ): Intent {
            val componentName = ComponentName(
                context.applicationContext, MapWidgetReceiver::class.java
            )
            val ids = AppWidgetManager
                .getInstance(context.applicationContext)
                .getAppWidgetIds(componentName)
            val intent = Intent(context, MapWidgetReceiver::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            return intent
        }
    }
}