package biz.belcorp.salesforce.modules.developmentpath.core.domain.dependencies

import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.firebase.FirebaseAnalyticsRepository
import biz.belcorp.salesforce.modules.developmentpath.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.mapper.ProfileSeOrdersU6CSyncMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync.UploadVisitasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos.AcuerdosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos.CumplimientoAcuerdosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.campania.CampaniaACampaniaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza.CobranzaYGananciaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza.DatosCobranzaCampaniaAnteriorRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza.DatosCobranzaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.comportamientos.ComportamientoDetallePorcentajeRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.comportamientos.ComportamientosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.concursos.ConcursosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.desempenio.DesempenioRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.AlertasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoXUaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.TiposEventoRddRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.geolocation.GeolocationRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraficosGrRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraphicsSERepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.asignar.HabilidadesAsignaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.avance.AvanceHabilidadesRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.detalle.HabilidadesAsignadasDetalleRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.detalle.HabilidadesDetalleRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.reconocer.HabilidadesReconoceRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.hito.HitoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.metas.MetasSociaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.metas.TipoMetaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.observaciones.ObservacionVisitaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.online.DashboardRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.online.PlanDetalleRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.preparingiskey.TopSalesCoSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.preparingiskey.TopSalesSeSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.capitalization.ProfileSeCapitalizationSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.preparingiskey.co.TopSalesCoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.preparingiskey.se.TopSalesSeRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.profit.ProfileProfitSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.seordersu6c.ProfileSeOrdersU6CSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.puntaje.PuntajePremioRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.puntaje.PuntajeReconocimientoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ranking.ProfileRankingRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.MadreUsuarioRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosEnCampaniaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.resultados.ResultadoVisitasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.resultados.ResultsLastCampaignRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.ConfigRddRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.FechaNoHabilRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.IntencionPedidoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.RutaOptimaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tips.TipsVisitaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.RddRegionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.RddSeccionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.RddZonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.UnidadAdministrativaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.visitas.VisitasPorFechaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.estadocuenta.TransactionAccountRepository
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get

class RepositoriesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para AcuerdosRepository`() {
        get<AcuerdosRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CumplimientoAcuerdosRepository`() {
        get<CumplimientoAcuerdosRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CampaniaACampaniaRepository`() {
        get<CampaniaACampaniaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CobranzaYGananciaRepository`() {
        get<CobranzaYGananciaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DatosCobranzaCampaniaAnteriorRepository`() {
        get<DatosCobranzaCampaniaAnteriorRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DatosCobranzaRepository`() {
        get<DatosCobranzaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ComportamientoDetallePorcentajeRepository`() {
        get<ComportamientoDetallePorcentajeRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ComportamientosRepository`() {
        get<ComportamientosRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ConcursosRepository`() {
        get<ConcursosRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DesempenioRepository`() {
        get<DesempenioRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para EstadoCuentaRepository`() {
        get<TransactionAccountRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AlertasRepository`() {
        get<AlertasRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para EventoRepository`() {
        get<EventoRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para EventoXUaRepository`() {
        get<EventoXUaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TiposEventoRddRepository`() {
        get<TiposEventoRddRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FocoRepository`() {
        get<FocoRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FocosHabilidadesEnDashboardRepository`() {
        get<FocosHabilidadesEnDashboardRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GuardadoFocosAsyncRepository`() {
        get<GuardadoFocosAsyncRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GuardadoFocosRepository`() {
        get<GuardadoFocosRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ListadoFocosEnAsignacionRepository`() {
        get<ListadoFocosEnAsignacionRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MisFocosRepository`() {
        get<MisFocosRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GraficosGrRepository`() {
        get<GraficosGrRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GraficosSERepository`() {
        get<GraphicsSERepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para HabilidadesAsignaRepository`() {
        get<HabilidadesAsignaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AvanceHabilidadesRepository`() {
        get<AvanceHabilidadesRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para HabilidadesAsignadasDetalleRepository`() {
        get<HabilidadesAsignadasDetalleRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para HabilidadesDetalleRepository`() {
        get<HabilidadesDetalleRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para HabilidadesReconoceRepository`() {
        get<HabilidadesReconoceRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DatosPersonaHabilidadesRepository`() {
        get<DatosPersonaHabilidadesRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DesarrolloHabilidadesRepository`() {
        get<DesarrolloHabilidadesRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para HabilidadesAsyncRepository`() {
        get<HabilidadesAsyncRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para HabilidadesRepository`() {
        get<HabilidadesRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MisHabilidadesRepository`() {
        get<MisHabilidadesRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para HitoRepository`() {
        get<HitoRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObservacionVisitaRepository`() {
        get<ObservacionVisitaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DashboardRepository`() {
        get<DashboardRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PlanDetalleRepository`() {
        get<PlanDetalleRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DatosPerfilRepository`() {
        get<DatosPerfilRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GananciasRepository`() {
        get<GainConsultantRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para IngresosExtraRepository`() {
        get<IngresosExtraRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MetaConsultoraRepository`() {
        get<MetaConsultoraRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MetaPersonalRepository`() {
        get<MetaPersonalRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PrepararseEsClaveRepository`() {
        get<PrepararseEsClaveRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para VentaCatalogoRepository`() {
        get<CatalogSaleConsultantRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para VentaDigitalRepository`() {
        get<DigitalSaleRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ConsultoraRDDRepository`() {
        get<ConsultoraRDDRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GerenteRegionRepository`() {
        get<GerenteRegionRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GerenteZonaRepository`() {
        get<GerenteZonaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PosibleConsultoraRepository`() {
        get<PosibleConsultoraRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RddPersonaRepository`() {
        get<RddPersonaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SociaEmpresariaRepository`() {
        get<SociaEmpresariaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RddPlanRepository`() {
        get<RddPlanRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PuntajePremioRepository`() {
        get<PuntajePremioRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PuntajeReconocimientoRepository`() {
        get<PuntajeReconocimientoRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ProfileRankingRepository`() {
        get<ProfileRankingRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MadreUsuarioRepository`() {
        get<MadreUsuarioRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ReconocimientoRepository`() {
        get<ReconocimientoRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ReconocimientosEnCampaniaRepository`() {
        get<ReconocimientosEnCampaniaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ReconocimientosRepository`() {
        get<ReconocimientosRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ResultadosCampaniaAnteriorRepository`() {
        get<ResultsLastCampaignRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ResultadoVisitasRepository`() {
        get<ResultadoVisitasRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ConfigRddRepository`() {
        get<ConfigRddRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FechaNoHabilRepository`() {
        get<FechaNoHabilRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para IntencionPedidoRepository`() {
        get<IntencionPedidoRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RutaOptimaRepository`() {
        get<RutaOptimaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipsVisitaRepository`() {
        get<TipsVisitaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DocumentoRepository`() {
        get<DocumentoRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para HerramientaDigitalRepository`() {
        get<HerramientaDigitalRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para NovedadesRepository`() {
        get<NovedadesRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ProgresoCaminoBrillanteRepository`() {
        get<ProgresoCaminoBrillanteRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipsDesarrolloRepository`() {
        get<TipsDesarrolloRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipsOfertaRepository`() {
        get<TipsOfertaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para VentaGananciaRepository`() {
        get<VentaGananciaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para VentaGananciaTipsRepository`() {
        get<VentaGananciaTipsRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RddRegionRepository`() {
        get<RddRegionRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RddSeccionRepository`() {
        get<RddSeccionRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RddZonaRepository`() {
        get<RddZonaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para UnidadAdministrativaRepository`() {
        get<UnidadAdministrativaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para VisitasPorFechaRepository`() {
        get<VisitasPorFechaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FirebaseAnalyticsRepository`() {
        get<FirebaseAnalyticsRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipoMetaRepository`() {
        get<TipoMetaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MetasSociaRepository`() {
        get<MetasSociaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para UploadVisitasRepository`() {
        get<UploadVisitasRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SessionPersonRepository`() {
        get<SessionPersonRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CampaniasRepository`() {
        get<CampaniasRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GeolocationRepository`() {
        get<GeolocationRepository>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for SaleConsultantSyncRepository`() {
        get<SaleConsultantSyncRepository>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for ProfileSeOrdersU6CSyncRepository`() {
        get<ProfileSeOrdersU6CSyncRepository>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for ProfileSeOrdersU6CSyncMapper`() {
        get<ProfileSeOrdersU6CSyncMapper>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for TopSalesCoSyncRepository`() {
        get<TopSalesCoSyncRepository>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for TopSalesCoRepository`() {
        get<TopSalesCoRepository>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for TopSalesSeSyncRepository`() {
        get<TopSalesSeSyncRepository>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for TopSalesSeRepository`() {
        get<TopSalesSeRepository>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for ProfileSeCapitalizationSyncRepository`() {
        get<ProfileSeCapitalizationSyncRepository>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for ProfileProfitSyncRepository`() {
        get<ProfileProfitSyncRepository>().shouldNotBeNull()
    }

}
