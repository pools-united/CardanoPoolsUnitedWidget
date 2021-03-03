package com.cardanopoolsunitedwidget.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.cardanopoolsunitedwidget.R
import com.cardanopoolsunitedwidget.data.network.RetrofitResponse
import com.cardanopoolsunitedwidget.model.Pool
import com.cardanopoolsunitedwidget.service.SharedPref
import com.cardanopoolsunitedwidget.util.Constants
import com.cardanopoolsunitedwidget.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

/**
 * Implementation of App Widget functionality.
 */
class CPUWidget : AppWidgetProvider() {

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
        if (Constants.WIDGET_UPDATE_KEY == intent.action) {

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
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(
        context.packageName,
        R.layout.c_p_u_widget
    )
    fetchPoolData(views, context, appWidgetManager, appWidgetId);
}

fun fetchPoolData(
    views: RemoteViews,
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
    ) {
    try {

        val pool: Pool? = getPoolDataFromStorage(context);
        var poolName = "";
        var poolApiURL = "";

        if (pool != null) {
            poolApiURL = "/pools/" + pool.poolID + "/summary.json";
            poolName = pool.poolName;
        }

        views.setOnClickPendingIntent(
            R.id.root_view,
            PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0)
        )
        retrofitFetchData(views, poolApiURL, poolName, appWidgetManager, appWidgetId);


    } catch (err: Error) {
        fetchPoolData(views, context, appWidgetManager, appWidgetId);
    }
}

fun retrofitFetchData(
    views: RemoteViews,
    poolApiURL: String,
    poolName: String,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val api = RetrofitResponse();
    GlobalScope.launch(Dispatchers.IO) {
        val response =
            api.retrofitApiService().getSpecificPoolDetails(poolApiURL).awaitResponse();
        if (response.isSuccessful) {
            withContext(Dispatchers.Main) {
                views.setTextViewText(R.id.widgetHeader, poolName);
                views.setTextViewText(
                    R.id.component_value_txt,
                    response.body()?.data?.blocksEpoch
                )
                views.setTextViewText(
                    R.id.component_value_txt2,
                    response.body()?.data?.blocksEstimated.toString()
                )
                views.setTextViewText(
                    R.id.component_value_txt3,
                    response.body()?.data?.roa + "%"
                )
                var totalStake = (response.body()?.data?.totalStake?.toLong());
                var adjusted: String = "";
                if (totalStake != null) {
                    adjusted = withSuffix(totalStake).toString()
                }
                views.setTextViewText(R.id.component_value_txt4, adjusted)

                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        } else {
            Log.d("Error", "Retrofit response failed" + response.errorBody().toString())
        }
    }
}

fun withSuffix(count: Long): String? {
    if (count < 1000) return "" + count
    val exp = (Math.log(count.toDouble()) / Math.log(1000.0)).toInt()
    return String.format(
        "%.1f %c",
        count / Math.pow(1000.0, exp.toDouble()),
        "kkkMGTPE"[exp - 1]
    )
}

fun getPoolDataFromStorage(context: Context): Pool? {
    return SharedPref.getPoolFromStorage(context);
}


