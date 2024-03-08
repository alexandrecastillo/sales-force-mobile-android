package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.base.BaseRepositoryTest
import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.entities.collection.CollectionEntity
import biz.belcorp.salesforce.core.entities.collection.CollectionOrderEntity
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class ObjectBoxUtilsTest : BaseRepositoryTest() {

    @Test(timeout = 30000)
    fun `test deleteAndInsert is not freezing`() = runBlockingTest {
        repeat(200) {
            boxStore.boxFor<CollectionEntity>().deleteAndInsert(mockData())
        }
    }

    private fun mockData(): List<CollectionEntity> {
        val mockList = mutableListOf<CollectionEntity>()
        repeat(20) {
            val col = CollectionEntity()
            col.ordersRange.add(CollectionOrderEntity(total = 1))
            col.ordersRange.add(CollectionOrderEntity(total = 2))
            col.ordersRange.add(CollectionOrderEntity(total = 3))
            mockList.add(col)
        }
        return mockList
    }

}
