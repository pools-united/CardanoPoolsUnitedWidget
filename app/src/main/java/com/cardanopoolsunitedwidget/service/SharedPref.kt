package com.cardanopoolsunitedwidget.service

import android.content.Context
import android.content.SharedPreferences
import com.cardanopoolsunitedwidget.model.Pool
import com.cardanopoolsunitedwidget.util.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object SharedPref {


    private fun getSharedPref(context: Context): SharedPreferences {
        return context.applicationContext
            .getSharedPreferences(
                Constants.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            );
    }

    fun clearPoolFromStorage(context: Context) {
        val editor: SharedPreferences.Editor = getSharedPref(context).edit();
        editor.clear().apply();
    }

    fun savePoolIdToStorage(context: Context, chosenPool: Pool) {
        val editor: SharedPreferences.Editor = getSharedPref(context).edit();
        val convertedJSONPool = Gson().toJson(chosenPool)
        editor.putString(Constants.POOL_KEY, convertedJSONPool);
        editor.apply()
    }

    fun getPoolFromStorage(context: Context): Pool? {
        val gson = Gson()
        val poolObj = getSharedPref(context).getString(Constants.POOL_KEY, "");
        return if (poolObj != "") {
            val type = object :
                TypeToken<Pool>() {}.type
            gson.fromJson(poolObj, type)
        } else {
            null;
        }

    }

    fun saveAddingWidget(context: Context, isSaved: Boolean) {
        val editor: SharedPreferences.Editor = getSharedPref(context).edit();
        editor.putBoolean(Constants.SAVED_WIDGET_KEY, isSaved);
        editor.apply()
    }

    fun getWidgetStatusFromStorage(context: Context): Boolean {
        val status = getSharedPref(context).getBoolean(Constants.SAVED_WIDGET_KEY, false);
        return status;
    }
}
