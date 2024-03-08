package biz.belcorp.salesforce.base.core.repository.options

import biz.belcorp.salesforce.base.base.BaseIntegrationTest
import biz.belcorp.salesforce.base.core.data.repository.options.cloud.OptionsCloudStore
import biz.belcorp.salesforce.base.core.data.repository.options.cloud.dto.OptionsQuery
import biz.belcorp.salesforce.core.utils.inject
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.Ignore
import org.junit.Test

@Ignore("Verificar test")
class OptionsCloudStoreTest : BaseIntegrationTest() {

    private val optionsCloudStore by inject<OptionsCloudStore>()

    @Test
    fun `get home options`() {
        runBlocking {
            val query = OptionsQuery("PE", "SE", "80", "8012", "A")
            val data = optionsCloudStore.getOptions(query)
            data.options.shouldNotBeEmpty()
        }
    }

}
