package com.aidchow.renran

import android.app.Application
import com.aidchow.renran.weiboauth.Constans
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo

import io.realm.Realm

/**
 * Created by aidchow on 17-6-7.
 */

class APP : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        WbSdk.install(this, AuthInfo(this, Constans.APP_KEY, Constans.REDIRECT_URL, Constans.SCOPE))
    }
}
