package com.cardanopoolsunitedwidget.view

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
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
    private var viewPagerPoolList = ArrayList<Pool>()
    private val poolsMap = mutableMapOf<String, Pool>()
    private var vpa: ViewPagerAdapter? = null;
    private var isWidgetStored = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide();
        createPoolData();
        vpa = ViewPagerAdapter(this, viewPagerPoolList)

        smoolider.adapter = vpa;
        smoolider.offscreenPageLimit = viewPagerPoolList.size;

        fetchWidgetFromStorage();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !isWidgetStored
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
        } else{
            val widgetNotify = Intent(this, CPUWidget::class.java)
            widgetNotify.action = Constants.WIDGET_UPDATE_KEY;
            this.sendBroadcast(widgetNotify);
        }

    }

    private fun fetchWidgetFromStorage() {
        try {
            isWidgetStored = SharedPref.getWidgetStatusFromStorage(this);
        } catch (err: Error) {
            fetchWidgetFromStorage();
        }
    }

    private fun createPoolData() {
        val cpu = Pool();
        cpu.poolID = "b45c1860e038baa0642b352ccf447ed5e14430342a11dd75bae52f39";
        cpu.poolName = "CPU Pool";
        cpu.poolURL = Constants.CPU;
        viewPagerPoolList.add(cpu)
        poolsMap.put(cpu.poolID, cpu)

        val adastra = Pool();
        adastra.poolID = "3e5fcbaf750c0291cecb72384091724a1c2d35da10a71473e16c926f";
        adastra.poolName = "ADAstra Mines";
        adastra.poolURL = Constants.MINES;
        viewPagerPoolList.add(adastra)
        poolsMap[adastra.poolID] = adastra

        val fresco = Pool();
        fresco.poolID = "19cb138eab81d3559e70094df2b6cb1742bf275e920300d5c3972253";
        fresco.poolName = "Fresco Pool";
        fresco.poolURL = Constants.FRESCO;
        viewPagerPoolList.add(fresco)
        poolsMap[fresco.poolID] = fresco

        val era = Pool();
        era.poolID = "13375a4a5470b564246a3251ea0ccfef046ee5bcaf3ed6de6315abc7";
        era.poolName = "Nova Era Pool";
        era.poolURL = Constants.ERA;
        viewPagerPoolList.add(era)
        poolsMap[era.poolID] = era


        val curie = Pool();
        curie.poolID = "6c81475fe8b32b5dfde307325a2cb115de26a466037d0ec76bb499b3";
        curie.poolName = "Marie Curie Pool";
        curie.poolURL = Constants.CURIE;
        viewPagerPoolList.add(curie)
        poolsMap[curie.poolID] = curie

        val proto = Pool();
        proto.poolID = "b00b421fbc620f0a2fdcf3243265d253b2e30c40da2c172dc5ab4640";
        proto.poolName = "Proto Pool";
        proto.poolURL = Constants.PROTO;
        viewPagerPoolList.add(proto)
        poolsMap[proto.poolID] = proto


        //adding default pool to storage
        val pool: Pool? = SharedPref.getPoolFromStorage(this);
        if (pool == null) {
            cpu.isActive = true;
            SharedPref.savePoolIdToStorage(this, cpu);
        }
    }

}



