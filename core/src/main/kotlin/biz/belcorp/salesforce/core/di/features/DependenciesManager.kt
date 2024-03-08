package biz.belcorp.salesforce.core.di.features

import biz.belcorp.salesforce.core.di.fullSyncModule
import biz.belcorp.salesforce.core.domain.usecases.features.DynamicFeature
import org.koin.core.context.loadKoinModules


class DependenciesManager {

    companion object {

        const val FEATURE_PACKAGE_NAME = "biz.belcorp.salesforce.modules"
        const val FEATURE_CLASS_PATH = "di.FeatureDependenciesProvider"

    }

    fun loadFeaturesModules() {
        DynamicFeature.values()
            .forEach {
                loadFeatureModules(it)
            }
        updateSyncManager()
    }

    private fun updateSyncManager() {
        loadKoinModules(fullSyncModule)
    }

    private fun loadFeatureModules(moduleName: String): Boolean {
        return try {
            val className = "$FEATURE_PACKAGE_NAME.$moduleName.$FEATURE_CLASS_PATH"
            val unsafeClass = Class.forName(className)
            val safeClass = unsafeClass.newInstance() as? DependenciesProvider
            safeClass?.init()
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

}
