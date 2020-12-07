package com.cardanopoolsunitedwidget.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cardanopoolsunitedwidget.R
import com.cardanopoolsunitedwidget.model.Pool
import com.cardanopoolsunitedwidget.util.Constants
import com.cardanopoolsunitedwidget.view.viewpager.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_pager_element.view.*


class MainActivity : AppCompatActivity() {
    // list that hold adapter views
    var viewList = ArrayList<View>();
    //list of all pools
    var viewPagerPoolList = ArrayList<Pool>()
    var vpa:ViewPagerAdapter? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide();
        createPoolData();
        vpa = ViewPagerAdapter(this, viewPagerPoolList)
        smoolider.adapter = vpa;

    }

    fun updateAllViews(pPosition: Int) {
        //val pool: Pool? = SharedPref.getPoolFromStorage(this);
        //clearOtherChecks();
        //viewPagerPoolList[pPosition].isActive = true;

        //viewList[pPosition].poolApplyBtn.setChecked(true);
    }

    private fun createPoolData() {
        val cpu = Pool();
        cpu.poolID = "b45c1860e038baa0642b352ccf447ed5e14430342a11dd75bae52f39";
        cpu.poolName = "CPU Pool";
        cpu.poolURL = Constants.CPU;
        viewPagerPoolList.add(cpu)

        val fresco = Pool();
        fresco.poolID = "19cb138eab81d3559e70094df2b6cb1742bf275e920300d5c3972253";
        fresco.poolName = "Fresco Pool";
        fresco.poolURL = Constants.FRESCO;
        viewPagerPoolList.add(fresco)

        val udp = Pool();
        udp.poolID = "9f38b462566102fe9bc1061131f298164d51ea54464ad984d486ce87";
        udp.poolName = "Utterly Determined Pool";
        udp.poolURL = Constants.UDP;
        viewPagerPoolList.add(udp)

        val adastra = Pool();
        adastra.poolID = "3e5fcbaf750c0291cecb72384091724a1c2d35da10a71473e16c926f";
        adastra.poolName = "ADAstra Mines";
        adastra.poolURL = Constants.MINES;
        viewPagerPoolList.add(adastra)

        val era = Pool();
        era.poolID = "13375a4a5470b564246a3251ea0ccfef046ee5bcaf3ed6de6315abc7";
        era.poolName = "Nova Era Pool";
        era.poolURL = Constants.ERA;
        viewPagerPoolList.add(era)

    }

    public fun clearOtherChecks(position: Int) {

        for (view in viewList) {
            if (viewList.indexOf(view) != position) {
                view.poolApplyBtn.setChecked(false);
            }
        }
    }

}



