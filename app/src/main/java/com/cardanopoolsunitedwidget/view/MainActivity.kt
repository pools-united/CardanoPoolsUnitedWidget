package com.cardanopoolsunitedwidget.view

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cardanopoolsunitedwidget.R
import com.cardanopoolsunitedwidget.model.Pool
import com.cardanopoolsunitedwidget.service.SharedPref
import com.cardanopoolsunitedwidget.util.Constants
import com.cardanopoolsunitedwidget.view.viewpager.ViewPagerAdapter
import com.cardanopoolsunitedwidget.widgets.CPUWidget
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    //list of all pools
    var viewPagerPoolList = ArrayList<Pool>()
    val poolsMap = mutableMapOf<String, Pool>()
    var vpa: ViewPagerAdapter? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide();
        createPoolData();
        vpa = ViewPagerAdapter(this, viewPagerPoolList)

        smoolider.adapter = vpa;
        smoolider.offscreenPageLimit = viewPagerPoolList.size;

        //`val poolFromStorage: Pool? = SharedPref.getPoolFromStorage(this);
        //Handler().post(Runnable { smoolider.currentItem = viewPagerPoolList.indexOf(poolsMap.get(poolFromStorage?.poolID)) })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !SharedPref.getWidgetStatusFromStorage(
                this
            )
        ) {
            val mAppWidgetManager =
                getSystemService(AppWidgetManager::class.java)
            val myProvider = ComponentName(
                this@MainActivity,
                CPUWidget::class.java
            )
            val b = Bundle()
            b.putString("ggg", "ggg")
            if (mAppWidgetManager.isRequestPinAppWidgetSupported) {
                val pinnedWidgetCallbackIntent = Intent(
                    this@MainActivity,
                    CPUWidget::class.java
                )
                val successCallback = PendingIntent.getBroadcast(
                    this@MainActivity, 0,
                    pinnedWidgetCallbackIntent, 0
                )
                mAppWidgetManager.requestPinAppWidget(myProvider, b, successCallback)
                SharedPref.saveAddingWidget(this, true);
            }
        }

    }

    private fun createPoolData() {
        val cpu = Pool();
        cpu.poolID = "b45c1860e038baa0642b352ccf447ed5e14430342a11dd75bae52f39";
        cpu.poolName = "CPU Pool";
        cpu.poolURL = Constants.CPU;
        viewPagerPoolList.add(cpu)
        poolsMap.put(cpu.poolID, cpu)

        val fresco = Pool();
        fresco.poolID = "19cb138eab81d3559e70094df2b6cb1742bf275e920300d5c3972253";
        fresco.poolName = "Fresco Pool";
        fresco.poolURL = Constants.FRESCO;
        viewPagerPoolList.add(fresco)
        poolsMap.put(fresco.poolID, fresco)

        val udp = Pool();
        udp.poolID = "9f38b462566102fe9bc1061131f298164d51ea54464ad984d486ce87";
        udp.poolName = "Utterly Determined Pool";
        udp.poolURL = Constants.UDP;
        viewPagerPoolList.add(udp)
        poolsMap.put(udp.poolID, udp)

        val adastra = Pool();
        adastra.poolID = "3e5fcbaf750c0291cecb72384091724a1c2d35da10a71473e16c926f";
        adastra.poolName = "ADAstra Mines";
        adastra.poolURL = Constants.MINES;
        viewPagerPoolList.add(adastra)
        poolsMap.put(fresco.poolID, adastra)

        val era = Pool();
        era.poolID = "13375a4a5470b564246a3251ea0ccfef046ee5bcaf3ed6de6315abc7";
        era.poolName = "Nova Era Pool";
        era.poolURL = Constants.ERA;
        viewPagerPoolList.add(era)
        poolsMap.put(era.poolID, era)
        //adding default pool to storage
        val pool: Pool? = SharedPref.getPoolFromStorage(this);
        if (pool == null) {
            cpu.isActive = true;
            SharedPref.savePoolIdToStorage(this, cpu);
        }
    }

}



