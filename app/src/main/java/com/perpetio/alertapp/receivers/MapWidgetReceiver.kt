package com.perpetio.alertapp.receivers

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.perpetio.alertapp.R
import com.perpetio.alertapp.activities.MainActivity
import com.perpetio.alertapp.utils.AlarmTimeManager
import com.perpetio.alertapp.utils.MapDrawer
import java.text.SimpleDateFormat
import java.util.*


class MapWidgetReceiver : AppWidgetProvider() {

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
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val views = RemoteViews(
                context.packageName,
                R.layout.widget_map
            ).apply {
                //setTextViewText(R.id.txt_name, "Map Widget")
                setTextViewText(R.id.refresh_date, getDateTime())
                setImageViewBitmap(R.id.canvas_holder, MapDrawer.drawMap(context))
                setOnClickPendingIntent(R.id.btn_refresh, getRefreshWidgetIntent(context))
            }
            appWidgetManager.updateAppWidget(widgetId, views)
        }
        AlarmTimeManager.setReminder(context)
    }

    private fun getDateTime(): String {
        return SimpleDateFormat(
            "dd MMMM, HH:mm", Locale.getDefault()
        ).format(Date())
    }

    private fun getRefreshWidgetIntent(context: Context): PendingIntent {
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        return PendingIntent.getBroadcast(
            context, 0, getRefreshIntent(context), flags
        )
    }

    companion object {
        fun getRefreshIntent(
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