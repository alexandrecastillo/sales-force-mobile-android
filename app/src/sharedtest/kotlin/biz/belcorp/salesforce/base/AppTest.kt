package biz.belcorp.salesforce.base

import biz.belcorp.salesforce.base.di.injectIntegrationTestModules


class AppTest : App() {

    override fun injectModules() {
        injectIntegrationTestModules(this)
    }

    override fun isTesting(): Boolean {
        return true
    }

}
