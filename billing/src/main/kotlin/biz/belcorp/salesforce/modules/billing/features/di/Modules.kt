package biz.belcorp.salesforce.modules.billing.features.di

import biz.belcorp.salesforce.modules.billing.features.billing.di.billingModule

val featureModules by lazy {
    listOf(billingModule)
}
