package com.cardanopoolsunitedwidget.widgets

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

    val pool: Pool? = getPoolDataFromStorage(context);
    val poolName: String;
    val poolApiURL: String;

    if (pool != null) {
        poolApiURL = "/pools/" + pool.poolID + "/summary.json";
        poolName = pool.poolName;
    } else {
        poolApiURL = Constants.DEFAULT_POOL_ENDPOINT;
        poolName = "Cardano pools united"
    }

    try {

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
                    views.setTextViewText(R.id.component_value_txt3, response.body()?.data?.roa)

                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            } else {
                Log.d("Error", "Retrofit response failed" + response.errorBody().toString())
            }
        }

    } catch (err: Error) {
        Log.e("Error", err.toString());
    }

}

fun getPoolDataFromStorage(context: Context): Pool? {
    return SharedPref.getPoolFromStorage(context);
}


