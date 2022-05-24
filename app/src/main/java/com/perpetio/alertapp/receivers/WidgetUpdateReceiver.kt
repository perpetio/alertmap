package com.perpetio.alertapp.receivers

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data_models.StatesInfoModel
import com.perpetio.alertapp.services.WidgetRefreshService
import com.perpetio.alertapp.utils.MapDrawer


class WidgetUpdateReceiver : AppWidgetProvider() {

    private var statesInfo: StatesInfoModel? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.apply {
            statesInfo = getParcelableExtra(STATES_INFO)
        }
        super.onReceive(context, intent)
    }

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
                setOnClickPendingIntent(R.id.btn_refresh, getRefreshWidgetIntent(context))
                statesInfo?.apply {
                    setTextViewText(R.id.tv_refresh_date, refreshTime)
                    setImageViewBitmap(R.id.img_map_holder, MapDrawer.drawMap(states, context))
                }
            }
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }

    private fun getRefreshWidgetIntent(context: Context): PendingIntent {
        val intent = Intent(context, WidgetRefreshService::class.java)
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        return PendingIntent.getService(context, 0, intent, flags)
    }

    companion object {
        const val STATES_INFO = "states_info"

        fun checkUpdate(
            statesInfo: StatesInfoModel,
            context: Context
        ) {
            val refreshIntent = getRefreshIntent(context)
            refreshIntent.putExtra(STATES_INFO, statesInfo)
            context.sendBroadcast(refreshIntent)
        }

        private fun getRefreshIntent(
            context: Context
        ): Intent {
            val componentName = ComponentName(
                context.applicationContext, WidgetUpdateReceiver::class.java
            )
            val ids = AppWidgetManager
                .getInstance(context.applicationContext)
                .getAppWidgetIds(componentName)
            val intent = Intent(context, WidgetUpdateReceiver::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            return intent
        }
    }
}