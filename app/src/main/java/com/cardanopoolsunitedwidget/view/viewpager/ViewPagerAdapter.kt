package com.cardanopoolsunitedwidget.view.viewpager

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.cardanopoolsunitedwidget.R
import com.cardanopoolsunitedwidget.model.Pool
import com.cardanopoolsunitedwidget.service.SharedPref
import com.cardanopoolsunitedwidget.view.MainActivity
import com.cardanopoolsunitedwidget.view.WebViewActivity
import com.cardanopoolsunitedwidget.widgets.CPUWidget
import kotlinx.android.synthetic.main.view_pager_element.view.*


class ViewPagerAdapter (private val mContext: Context, private  val itemList: ArrayList<Pool>) : PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater!!.inflate(R.layout.view_pager_element, container, false)
        setupLayout(view, position)
        container.addView(view, position)
        return view
    }

    private fun setupLayout(view: View, position: Int) {
        val pool: Pool? = SharedPref.getPoolFromStorage((mContext as MainActivity));
        if (pool?.poolID == itemList[position].poolID) {
            view.poolApplyBtn.setChecked(true);
        } else {
            view.poolApplyBtn.setChecked(false);
        }

        view.poolName.text = itemList[position].poolName;
        view.poolWebBtn.setOnClickListener(View.OnClickListener {
            startWebView(itemList[position].poolURL);
        })

        view.poolApplyBtn.setOnCheckedChangeListener { checked ->
            if (checked) {
                clearOtherChecks()
                itemList[position].isActive = true;
                savePoolToStorage((mContext as MainActivity), itemList[position]);
            } else {
                clearStorage()
            }
            notifyDataSetChanged();
        }
    }

    private fun clearOtherChecks() {
        itemList.forEach { pool ->
            pool.isActive = false;
        }
    }

    private fun clearStorage() {
        SharedPref.clearPoolFromStorage((mContext as MainActivity));
        updateWidget();
    }

    private fun savePoolToStorage(
        mainActivity: MainActivity,
        pool: Pool
    ) {
        SharedPref.savePoolIdToStorage(mainActivity, pool);
        updateWidget();
    }

    private fun updateWidget() {
        val context: Context = (mContext as MainActivity)
        val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(
            ComponentName(
                context,
                CPUWidget::class.java
            )
        )
        val myWidget = CPUWidget()
        myWidget.onUpdate(context, AppWidgetManager.getInstance(context), ids)
    }

    private fun startWebView(poolUrl: String) {
        val intent = Intent(mContext.applicationContext, WebViewActivity::class.java)
        intent.putExtra("message", poolUrl);
        mContext.startActivity(intent);
        (mContext as MainActivity).finish()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }

    override fun getPageWidth(position: Int): Float {
        return 0.85f
    }
}

