package biz.belcorp.salesforce.core.utils

import android.os.Build
import biz.belcorp.salesforce.core.BuildConfig


object AppBuildConfig {

    private const val DEVELOP = "develop"
    private const val STAGE = "stage"
    private const val RELEASE = "release"

    fun getBuildVersion(): Int {
        return Build.VERSION.SDK_INT
    }

    fun getBuildType(): String {
        return BuildConfig.BUILD_TYPE
    }

    fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    fun getEnviroment(): String {
        return when (getBuildType()) {
            RELEASE, STAGE -> RELEASE
            else -> DEVELOP
        }
    }

}
