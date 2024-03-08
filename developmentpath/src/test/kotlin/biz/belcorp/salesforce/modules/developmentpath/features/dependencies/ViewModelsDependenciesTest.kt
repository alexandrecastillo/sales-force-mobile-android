package biz.belcorp.salesforce.modules.developmentpath.features.dependencies

import biz.belcorp.salesforce.modules.developmentpath.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.header.DetalleTipsDesarrolloViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section.TipsDesarrolloViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view.RankingChartsViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzagananciaanterior.CollectionInfoViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.contacto.DatosContactoViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.information.DatosPersonaViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera.CabeceraPerfilViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias.BrandsAndCategoriesViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.view.CapitalizationSeViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.view.ProfitSeViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.pedidosu6c.ProfileSeOrdersU6CViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.penetracionmarca.marcas.BrandsViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.view.ActivesRetentionViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.view.PerformanceSeViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.viewmodel.ResultsLastCampaignViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel.CatalogSaleConsultantViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel.GainConsultantViewModel
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.persona.DatosPersonaViewModel as OldDatosPersonaViewModel

class ViewModelsDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `solving dependencies for DatosPersonaViewModel`() {
        get<DatosPersonaViewModel>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for PerfilCabeceraViewModel`() {
        get<PerfilCabeceraViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DatosContactoViewModel`() {
        get<DatosContactoViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CabeceraPerfilViewModel`() {
        get<CabeceraPerfilViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para OldDatosPersonaViewModel`() {
        get<OldDatosPersonaViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para VentasCatalogoPresenter`() {
        get<CatalogSaleConsultantViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GananciaVentaPresenter`() {
        get<GainConsultantViewModel>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for ResultsLastCampaignViewModel`() {
        get<ResultsLastCampaignViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para BrandsAndCategoriesViewModel`() {
        get<BrandsAndCategoriesViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para BrandsViewModel`() {
        get<BrandsViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ProfileSeOrdersU6CViewModel`() {
        get<ProfileSeOrdersU6CViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ActivesRetentionViewModel`() {
        get<ActivesRetentionViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CollectionInfoViewModel`() {
        get<CollectionInfoViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CapitalizationSeViewModel`() {
        get<CapitalizationSeViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ProfitSeViewModel`() {
        get<ProfitSeViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RankingChartsViewModel`() {
        get<RankingChartsViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PerformanceSeViewModel`() {
        get<PerformanceSeViewModel>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for DetalleTipsDesarrolloViewModel`() {
        get<DetalleTipsDesarrolloViewModel>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for TipsDesarrolloViewModel`() {
        get<TipsDesarrolloViewModel>().shouldNotBeNull()
    }

}
