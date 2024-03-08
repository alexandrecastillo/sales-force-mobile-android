package biz.belcorp.salesforce.core.domain.dependencies

import biz.belcorp.salesforce.core.base.BaseDependenciesTest
import biz.belcorp.salesforce.core.domain.usecases.browser.GetWebUrlUseCase
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.domain.usecases.device.UpdateTokenUseCase
import biz.belcorp.salesforce.core.domain.usecases.features.GetFeaturesUseCase
import biz.belcorp.salesforce.core.domain.usecases.firebase.FetchRemoteConfigUseCase
import biz.belcorp.salesforce.core.domain.usecases.firebase.ManageTopicsUseCase
import biz.belcorp.salesforce.core.domain.usecases.searchfilters.SearchFiltersUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.SyncTermsConditionsUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get


class UseCasesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para ObtenerCampaniasUseCase`() {
        get<ObtenerCampaniasUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerSesionUseCase`() {
        get<ObtenerSesionUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SearchFiltersUseCase`() {
        get<SearchFiltersUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SubscribeTopicsUseCase`() {
        get<ManageTopicsUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FetchRemoteConfigUseCase`() {
        get<FetchRemoteConfigUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GetFeaturesUseCase`() {
        get<GetFeaturesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GetWebUrlUseCase`() {
        get<GetWebUrlUseCase>().shouldNotBeNull()
    }

    @Test
    fun `solving test for UpdateTokenUseCase`() {
        get<UpdateTokenUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GetPoliticsTermsConditionsUseCase`() {
        get<TermConditionsUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SyncPoliticsTermsConditionsUseCase`() {
        get<SyncTermsConditionsUseCase>().shouldNotBeNull()
    }

}
