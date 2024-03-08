package biz.belcorp.salesforce.core.domain.usecases.firebase

import biz.belcorp.salesforce.core.constants.Constant.UNDERSCORE
import biz.belcorp.salesforce.core.domain.repository.firebase.CloudMessagingRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol


class ManageTopicsUseCase(
    private val sessionRepository: SessionRepository,
    private val cloudMessagingRepository: CloudMessagingRepository
) {

    private val session get() = sessionRepository.getSession()

    fun subscribeTopics() {
        cloudMessagingRepository.subscribeTopics(getTopics())
        cloudMessagingRepository.subscribeTopicsWithEnv(getTopicsWithEnv())
    }

    fun unsubscribeTopics() {
        cloudMessagingRepository.unsubscribeTopics(getTopics())
        cloudMessagingRepository.unsubscribeTopicsWithEnv(getTopicsWithEnv())
    }

    private fun getTopics(): List<String> {
        return listOf(TOPIC_PUSH_RC)
    }

    private fun getTopicsWithEnv(): List<String> {
        return getTopicsByRol()
    }

    private fun getTopicsByRol(): List<String> = session?.run {
        return when (rol) {
            Rol.SOCIA_EMPRESARIA -> getTopicsForSE()
            Rol.GERENTE_ZONA -> getTopicsForGZ()
            Rol.GERENTE_REGION -> getTopicsForGR()
            Rol.DIRECTOR_VENTAS -> getTopicsForDV()
            else -> emptyList()
        }
    } ?: emptyList()

    private fun getTopicsForSE(): List<String> = session?.run {
        return listOf(
            buildTopic(ZONIFICATION, countryIso),
            buildTopic(ZONIFICATION, countryIso, region),
            buildTopic(ZONIFICATION, countryIso, region, zona),
            buildTopic(ZONIFICATION, countryIso, region, zona, seccion),
            buildTopic(LEADER, countryIso, region, zona, seccion)
        )
    } ?: emptyList()

    private fun getTopicsForGZ(): List<String> = session?.run {
        return listOf(
            buildTopic(ZONIFICATION, countryIso),
            buildTopic(ZONIFICATION, countryIso, region),
            buildTopic(ZONIFICATION, countryIso, region, zona),
            buildTopic(LEADER, countryIso, region, zona)
        )
    } ?: emptyList()

    private fun getTopicsForGR(): List<String> = session?.run {
        return listOf(
            buildTopic(ZONIFICATION, countryIso),
            buildTopic(ZONIFICATION, countryIso, region),
            buildTopic(LEADER, countryIso, region)
        )
    } ?: emptyList()

    private fun getTopicsForDV(): List<String> = session?.run {
        return listOf(
            buildTopic(ZONIFICATION, countryIso),
            buildTopic(LEADER, countryIso)
        )
    } ?: emptyList()

    private fun buildTopic(vararg values: String?): String {
        return values.mapNotNull { it }.joinToString(separator = UNDERSCORE)
    }

    companion object {

        const val LEADER = "LEADER"
        const val ZONIFICATION = "ZONIFICATION"

        const val TOPIC_PUSH_RC = "PUSH_RC"

    }

}
