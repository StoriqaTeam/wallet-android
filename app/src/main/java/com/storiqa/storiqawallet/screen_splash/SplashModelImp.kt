package com.storiqa.storiqawallet.screen_splash

import com.storiqa.storiqawallet.StoriqaApp
import com.storiqa.storiqawallet.db.PreferencesHelper

class SplashModelImp : SplashModel {

    override fun setUserWentSplash() = PreferencesHelper(StoriqaApp.context).setIsUserWentFromSplash()

    override fun isUserWentFromSplash(): Boolean = PreferencesHelper(StoriqaApp.context).isUserWentFromSplash()

}