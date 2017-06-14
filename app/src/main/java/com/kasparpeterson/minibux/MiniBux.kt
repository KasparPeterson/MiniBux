package com.kasparpeterson.minibux

import android.app.Application
import com.google.gson.Gson

import com.kasparpeterson.minibux.api.BuxWebClient

/**
 * Created by kaspar on 13/06/2017.
 */

class MiniBux : Application() {

    companion object {
        var staticInstance: MiniBux? = null
            private set
    }

    var client: BuxWebClient? = null
        private set

    override fun onCreate() {
        super.onCreate()
        client = BuxWebClient()
        staticInstance = this
    }
}
