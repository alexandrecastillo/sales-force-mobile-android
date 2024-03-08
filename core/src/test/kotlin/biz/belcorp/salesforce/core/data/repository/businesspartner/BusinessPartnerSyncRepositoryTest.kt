@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.data.repository.businesspartner

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.core.data.repository.businesspartners.BusinessPartnerSyncDataRepository
import biz.belcorp.salesforce.core.data.repository.businesspartners.cloud.BusinessPartnerCloudStore
import biz.belcorp.salesforce.core.data.repository.businesspartners.cloud.dto.BusinessPartnerDto
import biz.belcorp.salesforce.core.data.repository.businesspartners.data.BusinessPartnerDataStore
import biz.belcorp.salesforce.core.data.repository.businesspartners.data.BusinessPartnerTableDataStore
import biz.belcorp.salesforce.core.data.repository.businesspartners.mappers.BusinessPartnerMapper
import biz.belcorp.salesforce.core.data.repository.businesspartners.mappers.BusinessPartnerTableMapper
import biz.belcorp.salesforce.core.domain.entities.people.SyncParams
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerSyncRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class BusinessPartnerSyncRepositoryTest {

    private val cloudStore = mockk<BusinessPartnerCloudStore>(relaxed = true)
    private val dataStore = mockk<BusinessPartnerDataStore>(relaxed = true)
    private val tableDataStore = mockk<BusinessPartnerTableDataStore>(relaxed = true)
    private val mapper = BusinessPartnerMapper()
    private val tableMapper = BusinessPartnerTableMapper()

    private lateinit var repository: BusinessPartnerSyncRepository

    @Before
    fun before() {
        repository = BusinessPartnerSyncDataRepository(
            cloudStore,
            dataStore,
            tableDataStore,
            mapper,
            tableMapper
        )
    }

    @Test
    fun `test sync for Business Partners`() = runBlockingTest {
        coEvery { cloudStore.getBusinessPartners(any()) } returns createBusinessPartners()
        val syncParams = SyncParams(LlaveUA("", "", ""), "CO", EMPTY_STRING)
        repository.sync(syncParams)
        coVerify(exactly = 1) { dataStore.saveBusinessPartners(any()) }
    }

    private fun createBusinessPartners() =
        BusinessPartnerDto(
            listOf(
                BusinessPartnerDto.BusinessPartner(
                    consultantId = NUMBER_ZERO,
                    code = EMPTY_STRING,
                    anniversaryDate = EMPTY_STRING,
                    leaderClassification = EMPTY_STRING,
                    status = EMPTY_STRING,
                    region = EMPTY_STRING,
                    section = EMPTY_STRING,
                    zone = EMPTY_STRING,
                    campaignAdmission = EMPTY_STRING,
                    successful = false,
                    billingInfo = BusinessPartnerDto.BillingInfo(
                        billingFirstCampaign = EMPTY_STRING,
                        billingLastCampaign = EMPTY_STRING
                    ),
                    level = BusinessPartnerDto.Level(
                        levelCode = EMPTY_STRING,
                        levelName = EMPTY_STRING
                    ),
                    pendingDebt = ZERO_DECIMAL,
                    personalInfo = BusinessPartnerDto.PersonalInfo(
                        address = EMPTY_STRING,
                        document = EMPTY_STRING,
                        fullName = EMPTY_STRING,
                        firstName = EMPTY_STRING,
                        secondName = EMPTY_STRING,
                        firstSurname = EMPTY_STRING,
                        secondSurname = EMPTY_STRING,
                        email = EMPTY_STRING,
                        cellphone = EMPTY_STRING,
                        homephone = EMPTY_STRING,
                        birthDate = EMPTY_STRING
                    ),
                    productivity = EMPTY_STRING
                )
            )
        )
}
