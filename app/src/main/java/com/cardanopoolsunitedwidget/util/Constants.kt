package com.cardanopoolsunitedwidget.util

class Constants {
    companion object {
        const val BASE_URL = "https://js.adapools.org/";

        //pool urls
        const val CPU ="https://www.cpoolsunited.com/pool?id=CPU";
        const val FRESCO ="https://www.cpoolsunited.com/pool?id=VENUS";
        const val ERA = "https://www.cpoolsunited.com/pool?id=ERA";
        const val UDP = "https://www.cpoolsunited.com/pool?id=UDP";
        const val MINES = "https://www.cpoolsunited.com/pool?id=MINES"


        //shared pref
        const val  PREFERENCES_KEY = "MyPrefs"
        const val POOL_KEY = "poolIdKey"
        const val SAVED_WIDGET_KEY = "saved_widget_key"

        //widget update
        const val WIDGET_UPDATE_KEY = "widget_update_key"

        //
        const val DEFAULT_POOL_ENDPOINT= "/pools/b45c1860e038baa0642b352ccf447ed5e14430342a11dd75bae52f39/summary.json"
    }
}