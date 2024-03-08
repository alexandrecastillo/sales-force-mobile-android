package biz.belcorp.salesforce.core.domain.dependencies

import biz.belcorp.salesforce.core.base.BaseDependenciesTest
import biz.belcorp.salesforce.core.domain.repository.browser.DataReportRepository
import biz.belcorp.salesforce.core.domain.repository.browser.MyAcademyRepository
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerSyncRepository
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.device.DeviceRepository
import biz.belcorp.salesforce.core.domain.repository.directory.ManagerDirectoryRepository
import biz.belcorp.salesforce.core.domain.repository.directory.ManagerDirectorySyncRepository
import biz.belcorp.salesforce.core.domain.repository.firebase.CloudMessagingRepository
import biz.belcorp.salesforce.core.domain.repository.firebase.RemoteConfigRepository
import biz.belcorp.salesforce.core.domain.repository.searchfilters.SearchFiltersRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.repository.terms.TermsConditionsRepository
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get


class RepositoriesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para CampaniasRepository`() {
        get<CampaniasRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SessionRepository`() {
        get<SessionRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SearchFiltersRepository`() {
        get<SearchFiltersRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CloudMessagingRepository`() {
        get<CloudMessagingRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RemoteConfigRepository`() {
        get<RemoteConfigRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para BusinessPartnerRepository`() {
        get<BusinessPartnerRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para BusinessPartnerSyncRepository`() {
        get<BusinessPartnerSyncRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ManagerDirectoryRepository`() {
        get<ManagerDirectoryRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ManagerDirectorySyncRepository`() {
        get<ManagerDirectorySyncRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DataReportRepository`() {
        get<DataReportRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MyAcademyRepository`() {
        get<MyAcademyRepository>().shouldNotBeNull()
    }

    @Test
    fun `solving test for DeviceRepository`() {
        get<DeviceRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PoliticsTermsConditionsRepository`() {
        get<TermsConditionsRepository>().shouldNotBeNull()
    }

}
