package com.cardanopoolsunitedwidget.view


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent.Builder
import com.cardanopoolsunitedwidget.R


class WebViewActivity : AppCompatActivity() {
    private var mCustomTabsOpened = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        supportActionBar?.hide();

        val bundle = intent.extras
        val message = bundle!!.getString("message")
        lunchCustomTabs(message);
    }

    private fun lunchCustomTabs(message: String?) {
        val intentBuilder = Builder()
        intentBuilder.setStartAnimations(this, R.anim.slide_in_left, R.anim.slide_out_left)
        intentBuilder.setExitAnimations(
            this, R.anim.slide_in_right,
            R.anim.slide_out_right
        )
        intentBuilder.build()
            .launchUrl(this, Uri.parse(message))

    }

    override fun onResume() {
        super.onResume();
        if (mCustomTabsOpened) {
            mCustomTabsOpened = false;
            startMainActivity()
        }
    }

    override fun onPause() {
        super.onPause()
        mCustomTabsOpened = true;
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startMainActivity()
    }

    
}