package biz.belcorp.salesforce.analytics.core.domain.entities

import biz.belcorp.salesforce.core.BuildConfig
import biz.belcorp.salesforce.core.utils.toUpperCase

object Ambiente {

    private const val PRODUCCION = "PRODUCCION"
    private const val QAS = "QAS"

    private const val RELEASE = "RELEASE"
    private const val STAGE = "STAGE"
    private const val DEVELOP = "DEVELOP"
    private const val REVIEW = "REVIEW"

    fun getAmbiente(): String {
        return when (BuildConfig.BUILD_TYPE.toUpperCase()) {
            RELEASE, STAGE -> PRODUCCION
            DEVELOP, REVIEW -> QAS
            else -> QAS
        }
    }

}

