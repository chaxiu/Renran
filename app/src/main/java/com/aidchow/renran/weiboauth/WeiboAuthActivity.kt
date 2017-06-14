package com.aidchow.renran.weiboauth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.aidchow.renran.R
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.auth.WbConnectErrorMessage
import com.sina.weibo.sdk.auth.sso.SsoHandler
import kotlinx.android.synthetic.main.about_dialog_layout.*

class WeiboAuthActivity : AppCompatActivity(), WbAuthListener {


    private var mSsoHandler: SsoHandler? = null
    private var mAccessToken: Oauth2AccessToken? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weibo_auth)
        mSsoHandler = SsoHandler(this)
        auth_image.setOnClickListener { mSsoHandler?.authorize(this) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mSsoHandler.let { it?.authorizeCallBack(requestCode, resultCode, data) }
    }


    override fun onSuccess(token: Oauth2AccessToken?) {
        runOnUiThread {
            mAccessToken = token
            if (mAccessToken?.isSessionValid!!) {
                AccessTokenKeeper.writeAccessToken(this, mAccessToken)
            }
        }
    }

    override fun onFailure(p0: WbConnectErrorMessage?) {
        Toast.makeText(this, "认证失败,请重新认证", Toast.LENGTH_SHORT).show()
    }

    override fun cancel() {
        Toast.makeText(this, "取消认证", Toast.LENGTH_SHORT).show()
    }
}
