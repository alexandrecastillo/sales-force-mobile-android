package biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository

import biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository.mappers.GroupSegDataMapper
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.entities.GroupsSegmentation
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.repository.GroupSegRepository

class GroupSegDataRepository(
    private val groupSegDataStore: GroupSegDataStore,
    private val segmentationDataStore: SegmentationDataStore,
    private val groupSegCloudStore: GroupSegCloudStore,
    private val groupSegDataMapper: GroupSegDataMapper
) : GroupSegRepository {

    override suspend fun getGroups(): List<GroupsSegmentation> {
        val listGroup = groupSegDataStore.getGroupSeg()
        val listSegmentation = segmentationDataStore.getSegmentation()
        return groupSegDataMapper.mapGroupsSeg(listGroup, listSegmentation)
    }

    override suspend fun syncGroups(country: String, ua: String) {
        val list = groupSegCloudStore.syncGroupSegmentacion(country, ua)
        groupSegDataStore.save(list.first)
        segmentationDataStore.save(list.second)
    }

}
