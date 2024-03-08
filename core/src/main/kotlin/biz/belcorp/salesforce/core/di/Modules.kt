package biz.belcorp.salesforce.core.di

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.provider.Settings
import biz.belcorp.salesforce.core.connectivity.ConnectivityLiveData
import biz.belcorp.salesforce.core.data.di.dataModules
import biz.belcorp.salesforce.core.data.executor.JobExecutor
import biz.belcorp.salesforce.core.data.preferences.di.preferencesModule
import biz.belcorp.salesforce.core.di.features.DependenciesManager
import biz.belcorp.salesforce.core.domain.di.domainModules
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.dynamic.SplitInstallLiveData
import biz.belcorp.salesforce.core.features.di.featureModules
import biz.belcorp.salesforce.core.features.executor.UIThread
import biz.belcorp.salesforce.core.features.permissions.PermissionsUtil
import biz.belcorp.salesforce.core.gps.RequestGPS
import biz.belcorp.salesforce.core.include.di.includeModule
import biz.belcorp.salesforce.core.sync.groups.LoginSyncGroupManager
import biz.belcorp.salesforce.core.sync.helpers.SyncNotificationHelper
import biz.belcorp.salesforce.core.update.UpdateManagerLiveData
import biz.belcorp.salesforce.core.utils.listByElementsOf
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coreModules by lazy {
    listByElementsOf<Module>(
        preferencesModule,
        domainModules,
        dataModules,
        featureModules,
        coreModule,
        fullSyncModule,
        threadsModule,
        firebaseModule,
        includeModule,
        hardwareModule
    )
}

internal val coreModule = module {

    single { get<Context>().resources }
    single { get<Context>().resources.displayMetrics }

    single { ConnectivityLiveData.init(get()) }
    single { DependenciesManager() }

    factory { RequestGPS() }
    factory { PermissionsUtil(get()) }

    factory { SyncNotificationHelper(get()) }

    factory { SplitInstallLiveData(SplitInstallManagerFactory.create(get())) }
    factory { UpdateManagerLiveData(AppUpdateManagerFactory.create(get())) }

    single { get<Context>().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

}

const val DEVICE_UUID = "DEVICE_UUID"

@SuppressLint("HardwareIds")
internal val hardwareModule = module {
    single(named(DEVICE_UUID)) {
        Settings.Secure.getString(get<Context>().contentResolver, Settings.Secure.ANDROID_ID)
    }
}

internal val fullSyncModule = module(override = true) {

    factory { LoginSyncGroupManager() }

}

internal val threadsModule = module {
    single<PostExecutionThread> { UIThread() }
    single<ThreadExecutor> { JobExecutor() }
}

internal val firebaseModule = module {
    single { FirebaseMessaging.getInstance() }
    single {
        FirebaseRemoteConfig.getInstance().apply {
            setConfigSettingsAsync(
                FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(0)
                    .build()
            )
        }
    }
}
