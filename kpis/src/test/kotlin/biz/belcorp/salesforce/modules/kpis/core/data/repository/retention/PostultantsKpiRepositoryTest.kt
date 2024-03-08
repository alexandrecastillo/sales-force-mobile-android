package biz.belcorp.salesforce.modules.kpis.core.data.repository.retention

import biz.belcorp.salesforce.core.base.SyncOnDemandRepository
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.entities.capitalization.PostulantKpiEntity
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.PostulantsKpiDataRepository
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.PostulantsKpiCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.data.PostulantsKpiDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.mapper.PostulantsKpiMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.PostulantsKpiRepository
import io.mockk.coEvery
import io.mockk.mockk
import org.amshove.kluent.any
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldHaveSize
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test

class PostultantsKpiRepositoryTest {

    private val postulantsKpiDataStore = mockk<PostulantsKpiDataStore>()
    private val postulantsKpiCloudStore = mockk<PostulantsKpiCloudStore>()
    private val syncPreferencesMock = mockk<SyncSharedPreferences>(relaxed = true)

    private lateinit var repository: PostulantsKpiRepository
    private val mapper =
        PostulantsKpiMapper()

    @Before
    fun setup() {
        repository =
            PostulantsKpiDataRepository(
                postulantsKpiCloudStore,
                postulantsKpiDataStore,
                mapper,
                syncPreferencesMock,
                SyncOnDemandRepository.SyncField.CapitalizationKpi
            )
    }

    @Test
    fun `test for getting postulants-kpi`() {
        coEvery {
            postulantsKpiDataStore.getPostulantsKpi(any())
        } returns postulantsKpiData()
        val entities = postulantsKpiDataStore.getPostulantsKpi(any())
        entities shouldHaveSize 1
        entities shouldNotBe emptyList()
    }

    @Test
    fun `test for getting an empty list postulants-kpi`() {
        coEvery {
            postulantsKpiDataStore.getPostulantsKpi(any())
        } returns emptyList()
        val entities = postulantsKpiDataStore.getPostulantsKpi(any())
        entities shouldHaveSize 0
        entities shouldBe emptyList()
    }

    private fun postulantsKpiData() = listOf(
        PostulantKpiEntity(
            currentCampaign = "202004",
            region = "06",
            zone = "0620",
            section = "A",
            name = "CAROLINA VARGAS",
            inEvaluation = 3,
            preApproved = 4,
            approved = 12,
            rejected = 50,
            conversion = 0,
            daysOnHold = 0,
            anticipatedEntries = 100,
            preRegistered = 200
        )
    )
}
