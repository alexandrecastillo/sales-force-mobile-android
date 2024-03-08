package biz.belcorp.salesforce.base.features.build

import biz.belcorp.salesforce.base.BuildConfig
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.AppBuild

class AppBuildImpl: AppBuild {

    override val applicationId: String
        get() = BuildConfig.APPLICATION_ID

    override val versionName: String
        get() = BuildConfig.VERSION_NAME.split(Constant.HYPHEN).firstOrNull().toString()

    override val productType: Int
        get() = BuildConfig.productType

}
