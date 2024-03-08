package biz.belcorp.salesforce.core.sync.groups

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.features.sync.CampaignsSyncManager
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.core.sync.exceptions.LoginSyncException
import biz.belcorp.salesforce.core.utils.Metrics.CATEGORY_LOGIN
import biz.belcorp.salesforce.core.utils.Metrics.LOGIN_TIME_SECONDS
import biz.belcorp.salesforce.core.utils.Metrics.measureSeconds
import biz.belcorp.salesforce.core.utils.getAll
import biz.belcorp.salesforce.core.utils.mapParallel
import org.koin.core.KoinComponent

class LoginSyncGroupManager : KoinComponent {

    private val managers
        get() = getAll<SyncManager>()
            .filter { it.isAllowed(SyncGroup.LOGIN) }

    private var lastSyncManager = Constant.EMPTY_STRING
    var isLoginSupport = false

    suspend fun start() {
        try {
            measureSeconds(LOGIN_TIME_SECONDS, CATEGORY_LOGIN)
            {
                startSync()
            }
        } catch (e: Exception) {
            throw LoginSyncException(lastSyncManager, e)
        }
    }

    private suspend fun startSync() {
        val groups = managers.partition { it is CampaignsSyncManager }
        val firstSyncManager = groups.first.firstOrNull()
        firstSyncManager?.isLoginSupport(isLoginSupport)
        firstSyncManager?.start()
        groups.second.mapParallel {
            lastSyncManager = it.javaClass.name
            it.isLoginSupport(isLoginSupport)
            it.start()
        }
    }

}
