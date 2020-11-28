package com.cardanopoolsunitedwidget.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.cardanopoolsunitedwidget.R
import com.cardanopoolsunitedwidget.data.network.RetrofitResponse
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

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    //create()

    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName,
        R.layout.c_p_u_widget
    )

    try {
        val api = RetrofitResponse();
        GlobalScope.launch (Dispatchers.IO) {
            val response = api.retrofitApiService().getSpecificPoolDetails().awaitResponse();
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    views.setTextViewText(R.id.component_value_txt, response.body()?.data?.blocksEpoch)
                    views.setTextViewText(R.id.component_value_txt2, response.body()?.data?.blocksEstimated.toString())
                    views.setTextViewText(R.id.component_value_txt3, response.body()?.data?.activeStake)
                    // Instruct the widget manager to update the widget
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            } else {
                Log.d("Error","Retrofit response failed" + response.errorBody().toString())
            }
        }

    } catch (err: Error) {
        Log.e("Error", err.toString());
    }

}


