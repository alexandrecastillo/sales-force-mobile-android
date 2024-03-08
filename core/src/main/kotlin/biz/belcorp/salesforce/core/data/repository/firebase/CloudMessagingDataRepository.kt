package biz.belcorp.salesforce.core.data.repository.firebase

import biz.belcorp.salesforce.core.constants.Constant.UNDERSCORE
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.core.domain.repository.firebase.CloudMessagingRepository
import biz.belcorp.salesforce.core.utils.AppBuildConfig
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*


class CloudMessagingDataRepository(
    private val fcm: FirebaseMessaging,
    private val configPreferences: ConfigSharedPreferences
) : CloudMessagingRepository {

    override fun subscribeTopics(topics: List<String>) {
        topics.forEach {
            fcm.subscribeToTopic(it)
        }
    }

    override fun subscribeTopicsWithEnv(topics: List<String>) {
        topics.map { getTopicWithEnv(it) }.forEach {
            saveLeaderTopic(it)
            fcm.subscribeToTopic(it)
        }
    }

    override fun unsubscribeTopics(topics: List<String>) {
        topics.forEach {
            fcm.unsubscribeFromTopic(it)
        }
    }

    override fun unsubscribeTopicsWithEnv(topics: List<String>) {
        topics.map { getTopicWithEnv(it) }.forEach {
            removeLeaderTopic(it)
            fcm.unsubscribeFromTopic(it)
        }
    }

    private fun getTopicWithEnv(topic: String): String {
        val env = AppBuildConfig.getEnviroment()
        return "$env$UNDERSCORE$topic".toUpperCase(Locale.getDefault())
    }

    private fun saveLeaderTopic(topic: String) {
        if (topic.contains(LEADER)) {
            configPreferences.leaderFcmTopic = topic
        }
    }

    private fun removeLeaderTopic(topic: String) {
        if (topic.contains(LEADER)) {
            configPreferences.leaderFcmTopic = null
        }
    }

    companion object {

        private const val LEADER = "LEADER"

    }

}
