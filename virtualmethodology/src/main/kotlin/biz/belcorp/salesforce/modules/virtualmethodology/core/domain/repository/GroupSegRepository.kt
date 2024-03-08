package biz.belcorp.salesforce.modules.virtualmethodology.core.domain.repository

import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.entities.GroupsSegmentation

interface GroupSegRepository {
    suspend fun getGroups(): List<GroupsSegmentation>
    suspend fun syncGroups(country: String, ua: String)
}
