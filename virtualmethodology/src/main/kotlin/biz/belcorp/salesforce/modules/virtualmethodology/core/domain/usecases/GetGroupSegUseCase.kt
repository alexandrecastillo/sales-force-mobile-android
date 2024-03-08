package biz.belcorp.salesforce.modules.virtualmethodology.core.domain.usecases

import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.entities.GroupsSegmentation
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.repository.GroupSegRepository

class   GetGroupSegUseCase(
    private val groupSegRepository: GroupSegRepository
) {
    suspend fun get(): List<GroupsSegmentation> {
        return groupSegRepository.getGroups()
    }
}
