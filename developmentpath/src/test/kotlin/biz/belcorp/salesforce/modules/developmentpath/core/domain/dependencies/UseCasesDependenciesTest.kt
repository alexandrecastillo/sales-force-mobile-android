package biz.belcorp.salesforce.modules.developmentpath.core.domain.dependencies

import biz.belcorp.salesforce.modules.developmentpath.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.analytics.FirebaseAnalyticsUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.buscador.BuscarPersonasCercaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cabecera.CabeceraUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.GetResultsLastCampaignUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.ObtenerCampaniaACampaniaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.ObtenerCampaniasRddUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.RecuperarFechasCampaniaActual
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.CobranzaYGananciaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.ObtenerDatosCobranzaCampaniaAnteriorUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.ObtenerDatosCobranzaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.comportamientos.ObtenerComportamientosDetallePorcentajeUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.concursos.ConcursosUseCaseImpl
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard.AvanceRegionesUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard.AvanceSeccionesUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard.AvanceZonasUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.datos.ObtenerNombrePersonaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desarrollo.RecuperarTituloDesarrolloUaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desempenio.ObtenerDesempenioGrGzUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.estadocuenta.ObtenerEstadoDeCuentaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.flujo.FlujoCascadaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.flujo.FlujoRddUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.geolocation.GeolocationUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.pedidosu6c.ProfileSeOrdersU6CUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.historicos.HistoricaRegionBaseUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.navegacion.BarraNavegacionUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.observaciones.ObservacionesVisitaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.pedidos.ObtenerCantidadIntencionPedidoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan.EliminarDePlanUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan.PersonasEnPlanUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan.PlanIdUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.productivas.ProductivasU3CUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.CabeceraPerfilUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.RecuperarPersonaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.SyncProfileUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.ingresosextra.IngresosExtraUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.PrepararseEsClaveUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.digital.GetDigitalSaleUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.masvendido.marcasycategorias.GetBrandsAndCategoriesUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.masvendido.productosmasvendidos.GetTopSoldProductsUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.ventas.GetCatalogSaleConsultantUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.ventas.GetGainConsultantUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.puntaje.PuntajePremioUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ranking.GetRankingUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.verdetalle.ReconocimientoCampaniaActualUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.DefinidorDeRutaPropiaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.IrAMiRutaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.RddUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.RecuperarRutaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.ObtenerFechaSyncUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.ProfileSyncUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.SyncUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tips.ObtenerTipsVisitaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.TipsDesarrolloUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.caminobrillante.ProgresoCaminoBrillanteUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.digital.HerramientaDigitalUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.novedades.DocumentoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.novedades.NovedadesUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.tips.CaminoBrillanteTipsUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.tips.TipsOfertaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.tips.VentaGananciaTipsUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.ventaganancia.VentaGananciaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.*
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get

class UseCasesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para AcuerdosCampaniaActualUseCase`() {
        get<AcuerdosCampaniaActualUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AcuerdosHistoricosUseCase`() {
        get<AcuerdosHistoricosUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GetMarksAndCategoriesUseCase`() {
        get<GetBrandsAndCategoriesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ConsolidadoAcuerdosUseCase`() {
        get<ConsolidadoAcuerdosUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CreacionAcuerdosUseCase`() {
        get<CreacionAcuerdosUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ModificarAcuerdosUseCase`() {
        get<ModificarAcuerdosUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para BuscarPersonasCercaUseCase`() {
        get<BuscarPersonasCercaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CabeceraUseCase`() {
        get<CabeceraUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerCampaniaACampaniaUseCase`() {
        get<ObtenerCampaniaACampaniaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for GetResultsLastCampaignUseCase`() {
        get<GetResultsLastCampaignUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RecuperarFechasCampaniaActual`() {
        get<RecuperarFechasCampaniaActual>().shouldNotBeNull()
    }


    @Test
    fun `resolviendo dependencias para ObtenerCampaniasRddUseCase`() {
        get<ObtenerCampaniasRddUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CobranzaYGananciaUseCase`() {
        get<CobranzaYGananciaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerDatosCobranzaCampaniaAnteriorUseCase`() {
        get<ObtenerDatosCobranzaCampaniaAnteriorUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerDatosCobranzaUseCase`() {
        get<ObtenerDatosCobranzaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerComportamientosDetallePorcentajeUseCase`() {
        get<ObtenerComportamientosDetallePorcentajeUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ConcursosUseCaseImpl`() {
        get<ConcursosUseCaseImpl>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AvanceRegionesUseCase`() {
        get<AvanceRegionesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AvanceSeccionesUseCase`() {
        get<AvanceSeccionesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AvanceZonasUseCase`() {
        get<AvanceZonasUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerNombrePersonaUseCase`() {
        get<ObtenerNombrePersonaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RecuperarTituloDesarrolloUaUseCase`() {
        get<RecuperarTituloDesarrolloUaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerDesempenioGrGzUseCase`() {
        get<ObtenerDesempenioGrGzUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerEstadoDeCuentaUseCase`() {
        get<ObtenerEstadoDeCuentaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CambioEventoUseCase`() {
        get<CambioEventoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ConfirmarEventoAtraccionUseCase`() {
        get<ConfirmarEventoAtraccionUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CrearEventoUseCase`() {
        get<CrearEventoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para EditarEventoUseCase`() {
        get<EditarEventoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para EliminarEventoUseCase`() {
        get<EliminarEventoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ProgramarAlarmasEventosUseCase`() {
        get<ProgramarAlarmasEventosUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RecuperarDatosAgregarEventoUseCase`() {
        get<RecuperarDatosAgregarEventoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RecuperarEventoUseCase`() {
        get<RecuperarEventoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RecuperarRelacionObservadorEventoUseCase`() {
        get<RecuperarRelacionObservadorEventoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SolicitarEventosCambiadosUseCase`() {
        get<SolicitarEventosCambiadosUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FlujoRddUseCase`() {
        get<FlujoRddUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AsignarFocosUseCase`() {
        get<AsignarFocosUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DeterminarFocosParaRol`() {
        get<DeterminarFocosParaRol>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FocosHabilidadesEquipoUseCase`() {
        get<FocosHabilidadesEquipoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FocosZonaUseCase`() {
        get<FocosZonaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FocoUseCase`() {
        get<FocoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerFocosUseCase`() {
        get<ObtenerFocosUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PermitirAutoasignacionFocosUseCase`() {
        get<PermitirAutoasignacionFocosUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RecuperarMisFocosUseCase`() {
        get<RecuperarMisFocosUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SincronizadorFocos`() {
        get<SincronizadorFocos>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CapitalizacionSeccionUseCase`() {
        get<GraphicCapitalizationUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DefinirGraficoInferior`() {
        get<DefinirGraficoInferior>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GraphicProfitSeUseCase`() {
        get<GraphicProfitSeUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerGraficosGrUseCase`() {
        get<ObtenerGraficosGrUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerGraficoTopBottomUseCase`() {
        get<ObtenerGraficoTopBottomUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerTitulosGraficosGrUseCase`() {
        get<ObtenerTitulosGraficosGrUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RetencionActivasUseCase`() {
        get<ActivesRetentionUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para VentaNetaSeccionUseCase`() {
        get<GraphicNetSaleSeUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AsignarHabilidadesUseCase`() {
        get<AsignarHabilidadesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GetAvanceHabilidadesUseCase`() {
        get<GetAvanceHabilidadesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GetDesarrolloHabilidadesUseCase`() {
        get<GetDesarrolloHabilidadesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerHabilidadesDetalleUseCase`() {
        get<ObtenerHabilidadesDetalleUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PermitirAutoasignacionHabilidadesUseCase`() {
        get<PermitirAutoasignacionHabilidadesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ReconocerHabilidadesUseCase`() {
        get<ReconocerHabilidadesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RecuperarMisHabilidadesUseCase`() {
        get<RecuperarMisHabilidadesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SincronizadorAsignarHabilidades`() {
        get<SincronizadorAsignarHabilidades>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SincronizadorReconocerHabilidades`() {
        get<SincronizadorReconocerHabilidades>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para HistoricaRegionBaseUseCase`() {
        get<HistoricaRegionBaseUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MetaPersonalUseCase`() {
        get<MetaPersonalUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para BarraNavegacionUseCase`() {
        get<BarraNavegacionUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObservacionesVisitaUseCase`() {
        get<ObservacionesVisitaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerCantidadIntencionPedidoUseCase`() {
        get<ObtenerCantidadIntencionPedidoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para EliminarDePlanUseCase`() {
        get<EliminarDePlanUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PersonasEnPlanUseCase`() {
        get<PersonasEnPlanUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PlanIdUseCase`() {
        get<PlanIdUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ProductivasU3CUseCase`() {
        get<ProductivasU3CUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CabeceraPerfilUseCase`() {
        get<CabeceraPerfilUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para IrAPerfilUseCase`() {
        get<SyncProfileUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RecuperarPersonaUseCase`() {
        get<RecuperarPersonaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para IngresosExtraUseCase`() {
        get<IngresosExtraUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PrepararseEsClaveUseCase`() {
        get<PrepararseEsClaveUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para VentaDigitalUseCase`() {
        get<GetDigitalSaleUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerGanaciaConsultoraUserCase`() {
        get<GetGainConsultantUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GetCatalogSaleConsultantUseCase`() {
        get<GetCatalogSaleConsultantUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerMarcasYCategoriasUseCase`() {
        get<GetBrandsAndCategoriesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerProductosMasVendidosCoUseCase`() {
        get<GetTopSoldProductsUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GetBrandsUseCase`() {
        get<GetBrandsUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PuntajePremioUseCase`() {
        get<PuntajePremioUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ReconocimientoCampaniaActualUseCase`() {
        get<ReconocimientoCampaniaActualUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AdministrarReconcocimientosUseCase`() {
        get<AdministrarReconocimientosUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ComportamientosCampaniaActualUseCase`() {
        get<ComportamientosCampaniaActualUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MostrarIrAReconocerUseCase`() {
        get<MostrarIrAReconocerUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MostrarReconocimientoUseCase`() {
        get<MostrarReconocimientoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ProgresoReconocimientoUseCase`() {
        get<ProgresoReconocimientoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ReconocimientoAvanceUseCase`() {
        get<ReconocimientoAvanceUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SaveRecognitionUseCase`() {
        get<SaveRecognitionUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DefinidorDeRutaPropiaUseCase`() {
        get<DefinidorDeRutaPropiaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RddUseCase`() {
        get<RddUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RecuperarRutaUseCase`() {
        get<RecuperarRutaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerFechaSyncUseCase`() {
        get<ObtenerFechaSyncUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SyncUseCase`() {
        get<SyncUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerTipsVisitaUseCase`() {
        get<ObtenerTipsVisitaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ProgresoCaminoBrillanteUseCase`() {
        get<ProgresoCaminoBrillanteUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para HerramientaDigitalUseCase`() {
        get<HerramientaDigitalUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DocumentoUseCase`() {
        get<DocumentoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para NovedadesUseCase`() {
        get<NovedadesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CaminoBrillanteTipsUseCase`() {
        get<CaminoBrillanteTipsUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipsOfertaUseCase`() {
        get<TipsOfertaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para VentaGananciaTipsUseCase`() {
        get<VentaGananciaTipsUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para VentaGananciaUseCase`() {
        get<VentaGananciaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipsDesarrolloUseCase`() {
        get<TipsDesarrolloUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AdicionarVisitaUseCase`() {
        get<AdicionarVisitaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CargaInicialRegistroVisitaUseCase`() {
        get<CargaInicialRegistroVisitaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PlanificarVisitaUseCase`() {
        get<PlanificarVisitaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RecuperarAvanceVisitasUseCase`() {
        get<RecuperarAvanceVisitasUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RecuperarResultadoVisitasUseCase`() {
        get<RecuperarResultadoVisitasUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RecuperarVisitaUseCase`() {
        get<RecuperarVisitaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RegistrarVisitaUseCase`() {
        get<RegistrarVisitaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ReprogramarVisitaUseCase`() {
        get<ReprogramarVisitaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para IrAMiRutaUseCase`() {
        get<IrAMiRutaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FlujoCascadaUseCase`() {
        get<FlujoCascadaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RecuperarResultadoVisitasUseCase2`() {
        get<RecuperarResultadoVisitasUseCase>().shouldNotBeNull()
    }


    @Test
    fun `resolviendo dependencias para FirebaseAnalyticsUseCase`() {
        get<FirebaseAnalyticsUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipoMetaUseCase`() {
        get<TipoMetaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerListadoCampanaFinUseCase`() {
        get<ObtenerListadoCampanaFinUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AdministrarMetaConsultoraUseCase`() {
        get<AdministrarMetaConsultoraUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MetaSociaUseCase`() {
        get<MetaSociaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GeolocationUseCase`() {
        get<GeolocationUseCase>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for ProfileSyncUseCase`() {
        get<ProfileSyncUseCase>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for ProfileSeOrdersU6CUseCase`() {
        get<ProfileSeOrdersU6CUseCase>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for GetRankingUseCase`() {
        get<GetRankingUseCase>().shouldNotBeNull()
    }

}
