package biz.belcorp.salesforce.base.features.main

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import com.google.android.play.core.install.model.AppUpdateType

class MainMapper {

    fun map(config: Configuration): MainModel {
        return MainModel(
            updateType = getUpdateType(config)
        )
    }

    private fun getUpdateType(config: Configuration): Int? {
        return when (config.updateType) {
            Constant.FLEXIBLE_UPDATE_TYPE -> AppUpdateType.FLEXIBLE
            Constant.INMEDIATE_UPDATE_TYPE -> AppUpdateType.IMMEDIATE
            else -> null
        }
    }

}
