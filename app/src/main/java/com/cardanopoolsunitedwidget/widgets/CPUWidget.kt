package com.cardanopoolsunitedwidget.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.cardanopoolsunitedwidget.R
import com.cardanopoolsunitedwidget.data.network.RetrofitResponse
import com.cardanopoolsunitedwidget.model.Epoch
import com.cardanopoolsunitedwidget.model.Pool
import com.cardanopoolsunitedwidget.model.PoolDetails
import com.cardanopoolsunitedwidget.service.PoolDataService.getCurrentEpoch
import com.cardanopoolsunitedwidget.service.PoolDataService.getPoolBlocks
import com.cardanopoolsunitedwidget.service.PoolDataService.getSpecificPoolData
import com.cardanopoolsunitedwidget.service.PoolDataService.withSuffix
import com.cardanopoolsunitedwidget.service.SharedPref.getPoolFromStorage
import com.cardanopoolsunitedwidget.util.Constants
import com.cardanopoolsunitedwidget.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Implementation of App Widget functionality.
 */
class CPUWidget : AppWidgetProvider() {
    private var poolID = "";

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId
            )
        }


    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    override fun onReceive(context: Context?, intent: Intent) {
        if (Constants.WIDGET_UPDATE_KEY == intent.action || intent.action == "MyOnClick") {
            val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(
                ComponentName(
                    context!!,
                    CPUWidget::class.java
                )
            )
            onUpdate(context, AppWidgetManager.getInstance(context), ids)
        } else {
            // Not our action, so let AppWidgetProvider handle it
            super.onReceive(context, intent)
        }
    }


    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(
            context.packageName,
            R.layout.c_p_u_widget
        )
        fetchPoolData(views, context, appWidgetManager, appWidgetId);
        views.setOnClickPendingIntent(
            R.id.btn,
            getPendingSelfIntent(context, "MyOnClick")
        );
        views.setOnClickPendingIntent(
            R.id.root_view,
            PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0)
        )
    }

    protected fun getPendingSelfIntent(context: Context?, action: String?): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }


    private fun fetchPoolData(
        views: RemoteViews,
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        try {
            val pool: Pool? = getPoolFromStorage(context);
            var poolName = "";

            if (pool != null) {
                poolID = pool.poolID;
                poolName = pool.poolName;
                retrofitFetchData(views, poolName, appWidgetManager, appWidgetId);
            } else {
                throw Exception("Failed to fetch pool data");
            }
        } catch (err: Error) {
            fetchPoolData(views, context, appWidgetManager, appWidgetId);
        }
    }


    private fun retrofitFetchData(
        views: RemoteViews,
        poolName: String,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val api = RetrofitResponse();
        GlobalScope.launch(Dispatchers.IO) {
            val currentEpoch: Epoch = getCurrentEpoch(api);
            val blocksMade = getPoolBlocks(currentEpoch.epoch, poolID, api);
            val specificPoolData = getSpecificPoolData(poolID, api);
            setViewData(views, blocksMade, specificPoolData, poolName, currentEpoch)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }


    private fun setViewData(
        views: RemoteViews,
        blocksMade: String,
        specificPoolData: PoolDetails?,
        poolName: String,
        currentEpoch: Epoch
    ) {
        views.setTextViewText(R.id.widgetHeader, poolName);

        views.setTextViewText(
            R.id.component_value_txt,
            blocksMade
        )
        views.setTextViewText(
            R.id.component_value_txt2,
            currentEpoch.blockCount.toString()
        )
        val pledge = (specificPoolData?.declaredPledge?.toLong())?.div(1000000)
        views.setTextViewText(
            R.id.component_value_txt3,
            pledge.toString() + " ₳"
        )
        val totalStake = (specificPoolData?.activeStake?.toLong());
        var adjusted = "";
        if (totalStake != null) {
            adjusted = withSuffix(totalStake).toString() + " ₳"
        }
        views.setTextViewText(R.id.component_value_txt4, adjusted)

        val taxVal = specificPoolData?.marginCost
        val tax = (taxVal?.times(100));
        views.setTextViewText(R.id.component_value_txt5, "$tax%")
    }
}




