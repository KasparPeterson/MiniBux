package com.kasparpeterson.minibux

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import com.kasparpeterson.minibux.api.TradingQuote
import com.kasparpeterson.minibux.api.TradingQuoteListener

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class MainActivity : AppCompatActivity(), TradingQuoteListener {

    override fun onUpdate(tradingQuote: TradingQuote) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
