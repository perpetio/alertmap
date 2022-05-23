package com.perpetio.alertapp.receivers

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.perpetio.alertapp.AlertApp
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data_models.StateModel
import com.perpetio.alertapp.services.MapRefreshService
import com.perpetio.alertapp.utils.AlarmTimeManager
import com.perpetio.alertapp.utils.Formatter
import com.perpetio.alertapp.utils.MapDrawer
import java.util.*


class MapWidgetReceiver : AppWidgetProvider() {

    private var states: List<StateModel>? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.apply {
            states = getParcelableArrayListExtra(STATES)
        }
        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d("123", "Widget onUpdate")
        appWidgetIds.forEach { widgetId ->
            val views = RemoteViews(
                context.packageName,
                R.layout.widget_map
            ).apply {
                setTextViewText(R.id.tv_refresh_date, Formatter.getShortFormat(Date()))
                setOnClickPendingIntent(R.id.btn_refresh, getRefreshWidgetIntent(context))
                states?.let {
                    setImageViewBitmap(R.id.img_map_holder, MapDrawer.drawMap(it, context))
                }
            }
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }

    private fun getRefreshWidgetIntent(context: Context): PendingIntent {
        val intent = Intent(context, MapRefreshService::class.java)
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        return PendingIntent.getService(context, 0, intent, flags)
    }

    companion object {
        const val STATES = "states"

        fun checkUpdate(
            states: List<StateModel>,
            context: Context
        ) {
            val refreshIntent = getRefreshIntent(context)
            refreshIntent.putParcelableArrayListExtra(STATES, ArrayList(states))
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