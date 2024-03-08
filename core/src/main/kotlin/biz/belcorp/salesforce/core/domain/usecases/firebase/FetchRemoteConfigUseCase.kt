package biz.belcorp.salesforce.core.domain.usecases.firebase

import biz.belcorp.salesforce.core.domain.repository.firebase.RemoteConfigRepository


class FetchRemoteConfigUseCase(
    private val remoteConfigRepository: RemoteConfigRepository
) {

    suspend fun fetchConfig() {
        remoteConfigRepository.fetchConfig()
    }

}
