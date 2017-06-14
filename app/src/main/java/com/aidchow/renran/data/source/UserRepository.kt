package com.aidchow.renran.data.source

import com.aidchow.renran.data.User

/**
 * Created by aidchow on 17-6-14.
 */
class UserReponsitory private constructor(private val mUserLocalDataSource: UserDataSource,
                                          private val mUserRemoteDataSource: UserDataSource) : UserDataSource {


    internal var mCacheUser: MutableMap<String, User>? = null

    companion object {
        private var INSTANCE: UserReponsitory? = null

        fun getInstance(userLocalDataSource: UserDataSource, userRemoteDataSource: UserDataSource): UserReponsitory {
            if (INSTANCE == null) {
                INSTANCE = UserReponsitory(userLocalDataSource, userRemoteDataSource)
            }
            return INSTANCE as UserReponsitory
        }

        /**
         * Used to force [.getInstance] to create a new instance
         * next time it's called.
         */
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun getUserInfoFromRemote(options: MutableMap<String, String?>,
                                       callBack: UserDataSource.LoadUserInfoCallBack) {
        mUserRemoteDataSource.getUserInfoFromRemote(options, object : UserDataSource.LoadUserInfoCallBack {
            override fun loadedUserInfoSuccess(user: User?) {
                callBack.loadedUserInfoSuccess(user)
            }

            override fun loadedUserInfoFail(failMsg: String?) {
                callBack.loadedUserInfoFail(failMsg)
            }
        })

    }

    override fun getUserInfo(uid: String?, callBack: UserDataSource.LoadUserInfoCallBack) {
        mUserLocalDataSource.getUserInfo(uid, object : UserDataSource.LoadUserInfoCallBack {
            override fun loadedUserInfoSuccess(user: User?) {
                callBack.loadedUserInfoSuccess(user)
            }

            override fun loadedUserInfoFail(failMsg: String?) {
                callBack.loadedUserInfoFail(failMsg)
            }
        })
    }

    override fun saveUser(user: User?) {
        mUserLocalDataSource.saveUser(user)
    }
}