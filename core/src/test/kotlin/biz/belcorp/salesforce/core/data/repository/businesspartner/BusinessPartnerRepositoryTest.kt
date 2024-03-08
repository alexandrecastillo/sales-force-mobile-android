@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.data.repository.businesspartner

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMERO_CERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.core.data.repository.businesspartners.BusinessPartnerDataRepository
import biz.belcorp.salesforce.core.data.repository.businesspartners.data.BusinessPartnerDataStore
import biz.belcorp.salesforce.core.data.repository.businesspartners.mappers.BusinessPartnerMapper
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldHaveSize
import org.junit.Before
import org.junit.Test

class BusinessPartnerRepositoryTest {

    private val dataStore = mockk<BusinessPartnerDataStore>(relaxed = true)
    private val mapper = BusinessPartnerMapper()

    private lateinit var repository: BusinessPartnerRepository

    @Before
    fun before() {
        repository = BusinessPartnerDataRepository(dataStore, mapper)
    }

    @Test
    fun `test to get Business Partners`() = runBlockingTest {
        coEvery { dataStore.getBusinessPartners() } returns createBusinessPartnersData()
        val response = repository.getBusinessPartners()
        response shouldHaveSize 2
    }


    private fun createBusinessPartnersData() =
        listOf(
            BusinessPartnerEntity(
                partnerCode = EMPTY_STRING,
                level = EMPTY_STRING,
                status = EMPTY_STRING,
                leaderClassification = EMPTY_STRING,
                levelCode = EMPTY_STRING,
                document = EMPTY_STRING,
                firstName = EMPTY_STRING,
                secondName = EMPTY_STRING,
                firstSurname = EMPTY_STRING,
                secondSurname = EMPTY_STRING,
                birthDate = EMPTY_STRING,
                anniversaryDate = EMPTY_STRING,
                code = NUMERO_CERO.toLong(),
                zone = EMPTY_STRING,
                section = EMPTY_STRING,
                region = EMPTY_STRING,
                email = EMPTY_STRING,
                address = EMPTY_STRING,
                campaignAdmission = EMPTY_STRING,
                cellphone = EMPTY_STRING,
                homephone = EMPTY_STRING,
                successful = false,
                name = EMPTY_STRING,
                pendingDebt = ZERO_DECIMAL,
                productivity = EMPTY_STRING,
                billingFirstCampaign = EMPTY_STRING,
                billingLastCampaign = EMPTY_STRING
            ),
            BusinessPartnerEntity(
                partnerCode = EMPTY_STRING,
                level = EMPTY_STRING,
                status = EMPTY_STRING,
                leaderClassification = EMPTY_STRING,
                levelCode = EMPTY_STRING,
                document = EMPTY_STRING,
                firstName = EMPTY_STRING,
                secondName = EMPTY_STRING,
                firstSurname = EMPTY_STRING,
                secondSurname = EMPTY_STRING,
                birthDate = EMPTY_STRING,
                anniversaryDate = EMPTY_STRING,
                code = NUMERO_CERO.toLong(),
                zone = EMPTY_STRING,
                section = EMPTY_STRING,
                region = EMPTY_STRING,
                email = EMPTY_STRING,
                address = EMPTY_STRING,
                campaignAdmission = EMPTY_STRING,
                cellphone = EMPTY_STRING,
                homephone = EMPTY_STRING,
                successful = false,
                name = EMPTY_STRING,
                pendingDebt = ZERO_DECIMAL,
                productivity = EMPTY_STRING,
                billingFirstCampaign = EMPTY_STRING,
                billingLastCampaign = EMPTY_STRING
            )
        )
}
