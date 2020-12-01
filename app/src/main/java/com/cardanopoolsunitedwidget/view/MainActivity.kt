package com.cardanopoolsunitedwidget.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.cardanopoolsunitedwidget.R
import com.cardanopoolsunitedwidget.util.Constants
import com.royrodriguez.transitionbutton.TransitionButton
import com.royrodriguez.transitionbutton.TransitionButton.OnAnimationStopEndListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide();
        setupLayout();
    }

    override fun onResume() {
        super.onResume();
        showButtons();
    }

    private fun setupLayout() {
        pool_1.setOnClickListener(View.OnClickListener {
            pool_1.startAnimation()
            val handler = Handler()
            handler.postDelayed(Runnable {
                val isSuccessful = true
                if (isSuccessful) {
                    hideBtns()
                    pool_1.stopAnimation(
                        TransitionButton.StopAnimationStyle.EXPAND,
                        OnAnimationStopEndListener {
                            startWebView(Constants.FRESCO);
                        })
                } else {
                    pool_1.stopAnimation(
                        TransitionButton.StopAnimationStyle.SHAKE,
                        null
                    )
                }
            }, 2000)
        })

        pool_2.setOnClickListener(View.OnClickListener {
            pool_2.startAnimation()
            val handler = Handler()
            handler.postDelayed(Runnable {
                val isSuccessful = true
                if (isSuccessful) {
                    hideBtns()
                    pool_2.stopAnimation(
                        TransitionButton.StopAnimationStyle.EXPAND,
                        OnAnimationStopEndListener {
                            startWebView(Constants.ERA);
                        })
                } else {
                    pool_2.stopAnimation(
                        TransitionButton.StopAnimationStyle.SHAKE,
                        null
                    )
                }
            }, 2000)
        })


        pool_3.setOnClickListener(View.OnClickListener {
            pool_3.startAnimation()
            val handler = Handler()
            handler.postDelayed(Runnable {
                val isSuccessful = true
                if (isSuccessful) {
                    hideBtns()
                    pool_3.stopAnimation(
                        TransitionButton.StopAnimationStyle.EXPAND,
                        OnAnimationStopEndListener {
                            startWebView(Constants.UDP);
                        })
                } else {
                    pool_3.stopAnimation(
                        TransitionButton.StopAnimationStyle.SHAKE,
                        null
                    )
                }
            }, 2000)
        })

        pool_4.setOnClickListener(View.OnClickListener {
            pool_4.startAnimation()

            val handler = Handler()
            handler.postDelayed(Runnable {
                val isSuccessful = true
                if (isSuccessful) {
                    hideBtns()
                    pool_4.stopAnimation(
                        TransitionButton.StopAnimationStyle.EXPAND,
                        OnAnimationStopEndListener {
                            startWebView(Constants.UDP);
                        })
                } else {
                    pool_4.stopAnimation(
                        TransitionButton.StopAnimationStyle.SHAKE,
                        null
                    )
                }
            }, 2000)
        })

        pool_5.setOnClickListener(View.OnClickListener {
            pool_5.startAnimation()
            val handler = Handler()
            handler.postDelayed(Runnable {
                val isSuccessful = true
                if (isSuccessful) {
                    hideBtns()
                    pool_5.stopAnimation(
                        TransitionButton.StopAnimationStyle.EXPAND,
                        OnAnimationStopEndListener {
                            startWebView(Constants.CPU);
                        })
                } else {
                    pool_5.stopAnimation(
                        TransitionButton.StopAnimationStyle.SHAKE,
                        null
                    )
                }
            }, 2000)
        })
    }

    private fun startWebView(poolUrl: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("message", poolUrl);
        startActivity(intent)
        finish();
    }


    private fun hideBtns() {
        pool_1.text = "";
        pool_2.text = "";
        pool_3.text = "";
        pool_4.text = "";
        pool_4.text = "";
    }

    private fun showButtons() {
        pool_1.visibility = View.VISIBLE;
        pool_1.visibility = View.VISIBLE;
        pool_1.visibility = View.VISIBLE;
        pool_1.visibility = View.VISIBLE;
    }
}



