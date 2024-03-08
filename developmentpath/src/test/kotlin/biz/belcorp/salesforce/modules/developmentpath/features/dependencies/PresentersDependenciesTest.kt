package biz.belcorp.salesforce.modules.developmentpath.features.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.developmentpath.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.presenter.DesarrolloUaComportamientosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.presenter.DesarrolloUaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.DesarrolloUaView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.comportamientos.DesarrolloUaComportamientosView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.habilidades.DesarrolloUaHabilidadesView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avancecampania.presenter.AvanceCampaniaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avancecampania.view.AvanceCampaniaView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.presenter.AvanceRegionesPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.view.AvanceRegionesView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.presenter.AvanceRegionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.presenter.ReconocimientoRegionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.view.AvanceRegionView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.presenter.AvanceZonaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.view.AvanceView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.presenter.AvanceVisitasPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.view.AvanceVisitasView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera.CabeceraPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera.CabeceraView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.FocosContenedorPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.FocosContenedorView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr.FocosHabilidadesPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr.FocosHabilidadesView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gz.presenter.FocoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gz.view.FocosView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.presenter.MisFocosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.view.MisFocosView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.presenter.FocosSEPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.view.FocosSEView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.presenter.MisHabilidadesPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.view.MisHabilidadesView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.intencionpedido.IntencionPedidoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.intencionpedido.IntencionPedidoView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.presenter.ReconocimientosASuperiorPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.view.ReconocimientosASuperiorView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.resultadosvisita.ResultadoVisitasPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.resultadosvisita.ResultadoVisitasView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.TabsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.TabsView
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.FlujoCascadaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.presenter.AvanceHabilidadPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.view.AvanceHabilidadView
import biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas.HorarioVisitaContract
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.more.IngresosExtraOtrosContract
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.IngresosExtraContract
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.adicionar.AdicionarVisitaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.adicionar.AdicionarVisitaView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.BarraNavegacionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.BarraNavegacionView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eliminar.EliminarPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eliminar.EliminarView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.presenter.DetalleEventoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.view.DetalleEventoView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.presenter.AgregarEventoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.maps.GeolocationPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.maps.GeolocationView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificar.PlanificarPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.listarpersonas.PersonasEnPlanPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.planificar.PlanificarRapidoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.MapaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.RddPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.registrar.RegistrarPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.replanificar.ReprogramarPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa.MapaBaseView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.GraficosGrPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.GraficosGrView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.topbottom.GraficoTopBottomPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.topbottom.GraficoTopBottomView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.contenedor.presenter.InformacionHistoricaRegionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.presenter.GraficoResumenRegionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view.GraficoResumenView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.presenter.ProductivasU3CPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.view.ProductivasU3CView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.planificacion.PlanificacionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.planificacion.PlanificacionView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.reconocimiento.MostrarReconocimientoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.reconocimiento.MostrarReconocimientoView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.ProgresoCaminoBrillantePresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.ProgresoCaminoBrillanteView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos.ConcursosFragmentContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.herramientasdigitales.HerramientaDigitalContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.TipsContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents.DocumentoView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents.DocumentosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades.NovedadesPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades.NovedadesView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.programanuevas.TipsProgramaNuevasPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.VentaGananciaTipsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.VentaGananciaTipsView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.ventaganancia.VentaGananciaContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsvideo.TipsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.AcuerdosReconocimientosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.AcuerdosReconocimientosView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.campaniaactual.AcuerdosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.campaniaactual.AcuerdosView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.gz.CampaniaACampaniaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.consolidado.ConsolidadoAcuerdosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.consolidado.ConsolidadoAcuerdosView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.AcuerdosHistoricosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.AcuerdosHistoricosView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual.ComportamientosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual.ComportamientosView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual.detalle.ReconocimientoCampaniaActualPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual.detalle.ReconocimientoCampaniaActualView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.ProgresoComportamientosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.ProgresoComportamientosView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.detalle.ProgresoReconocimientoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.detalle.ProgresoReconocimientoView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer.ReconocerComportamientosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer.ReconocerComportamientosView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.redireccion.IrAReconocerPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.redireccion.IrAReconocerView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.cobranza.DatosCobranzaContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.estadocuenta.DatosEstadoCuentaContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia.CobranzaGananciaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia.DatosCobranzaGananciaContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.last6campaigns.DesempenioGrGzPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.last6campaigns.DesempenioGrGzView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.presenter.MetaPersonalPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.view.MetaPersonalView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.crear.CrearMetaConsultoraPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.crear.CrearMetaConsultoraView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.listar.ListarMetasConsultoraPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.listar.ListarMetasConsultorasView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.crear.CrearMetaSociaView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.crear.MetaSociaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.listar.ListarMetaSociaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.listar.ListarMetaSociaView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.headercontainer.DetallePrepararseEsClavePresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.headercontainer.DetallePrepararseEsClaveView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section.PrepararseEsClaveContract
import io.mockk.mockk
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.foco.FocoPresenter as FocoPresenterProfile


class PresentersDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para DatosEstadoCuentaPresenter`() {
        val view = mockk<DatosEstadoCuentaContract.View>()
        get<DatosEstadoCuentaContract.Presenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CobranzaGananciaPresenter`() {
        val view = mockk<DatosCobranzaGananciaContract.View>()
        get<CobranzaGananciaPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DatosCobranzaPresenter`() {
        val view = mockk<DatosCobranzaContract.View>()
        get<DatosCobranzaContract.Presenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para IngresosExtraPresenter`() {
        val view = mockk<IngresosExtraContract.View>()
        get<IngresosExtraContract.Presenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para HorarioVisitaPresenter`() {
        val view = mockk<HorarioVisitaContract.View>()
        get<HorarioVisitaContract.Presenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para IngresosExtraOtrosPresenter`() {
        val view = mockk<IngresosExtraOtrosContract.View>()
        get<IngresosExtraOtrosContract.Presenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CrearMetaConsultoraPresenter`() {
        val view = mockk<CrearMetaConsultoraView>()
        get<CrearMetaConsultoraPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ListarMetasConsultoraPresenter`() {
        val view = mockk<ListarMetasConsultorasView>()
        get<ListarMetasConsultoraPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MetaSociaPresenter`() {
        val view = mockk<CrearMetaSociaView>()
        get<MetaSociaPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ListarMetaSociaPresenter`() {
        val view = mockk<ListarMetaSociaView>()
        get<ListarMetaSociaPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DatosCobranzaGananciaPresenter`() {
        val view = mockk<DatosCobranzaGananciaContract.View>()
        get<CobranzaGananciaPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PrepararseEsClavePresenter`() {
        val view = mockk<PrepararseEsClaveContract.View>()
        get<PrepararseEsClaveContract.Presenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DetallePrepararseEsClavePresenter`() {
        val view = mockk<DetallePrepararseEsClaveView>()
        get<DetallePrepararseEsClavePresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ConsolidadoAcuerdosPresenter`() {
        val view = mockk<ConsolidadoAcuerdosView>()
        get<ConsolidadoAcuerdosPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AcuerdosHistoricosPresenter`() {
        val view = mockk<AcuerdosHistoricosView>()
        get<AcuerdosHistoricosPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AcuerdosPresenter`() {
        val view = mockk<AcuerdosView>()
        get<AcuerdosPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ReconocimientoCampaniaActualPresenter`() {
        val view = mockk<ReconocimientoCampaniaActualView>()
        get<ReconocimientoCampaniaActualPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ComportamientosPresenter`() {
        val view = mockk<ComportamientosView>()
        get<ComportamientosPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ProgresoReconocimientoPresenter`() {
        val view = mockk<ProgresoReconocimientoView>()
        get<ProgresoReconocimientoPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ProgresoComportamientosPresenter`() {
        val view = mockk<ProgresoComportamientosView>()
        get<ProgresoComportamientosPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ReconocerComportamientosPresenter`() {
        val view = mockk<ReconocerComportamientosView>()
        get<ReconocerComportamientosPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para IrAReconocerPresenter`() {
        val view = mockk<IrAReconocerView>()
        get<IrAReconocerPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AcuerdosReconocimientosPresenter`() {
        val view = mockk<AcuerdosReconocimientosView>()
        get<AcuerdosReconocimientosPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AvanceZonaPresenter`() {
        val view = mockk<AvanceView>()
        get<AvanceZonaPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AvanceVisitasPresenter`() {
        val view = mockk<AvanceVisitasView>()
        get<AvanceVisitasPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AvanceRegionPresenter`() {
        val view = mockk<AvanceRegionView>()
        get<AvanceRegionPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DesarrolloUaComportamientosPresenter`() {
        val view = mockk<DesarrolloUaComportamientosView>()
        get<DesarrolloUaComportamientosPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AvanceCampaniaPresenter`() {
        val view = mockk<AvanceCampaniaView>()
        get<AvanceCampaniaPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AvanceRegionesPresenter`() {
        val view = mockk<AvanceRegionesView>()
        get<AvanceRegionesPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ReconocimientoRegionPresenter`() {
        val view = mockk<DesarrolloUaHabilidadesView>()
        get<ReconocimientoRegionPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DesarrolloUaPresenter`() {
        val view = mockk<DesarrolloUaView>()
        get<DesarrolloUaPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CabeceraPresenter`() {
        val view = mockk<CabeceraView>()
        get<CabeceraPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FocosHabilidadesPresenter`() {
        val view = mockk<FocosHabilidadesView>()
        get<FocosHabilidadesPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FocoPresenter`() {
        val view = mockk<FocosView>()
        get<FocoPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MisFocosPresenter`() {
        val view = mockk<MisFocosView>()
        get<MisFocosPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FocosSEPresenter`() {
        val view = mockk<FocosSEView>()
        get<FocosSEPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FocosContenedorPresenter`() {
        val view = mockk<FocosContenedorView>()
        get<FocosContenedorPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MisHabilidadesPresenter`() {
        val view = mockk<MisHabilidadesView>()
        get<MisHabilidadesPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para IntencionPedidoPresenter`() {
        val view = mockk<IntencionPedidoView>()
        get<IntencionPedidoPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ReconocimientosASuperiorPresenter`() {
        val view = mockk<ReconocimientosASuperiorView>()
        get<ReconocimientosASuperiorPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ResultadoVisitasPresenter`() {
        val view = mockk<ResultadoVisitasView>()
        get<ResultadoVisitasPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TabsPresenter`() {
        val view = mockk<TabsView>()
        get<TabsPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FlujoCascadaPresenter`() {
        get<FlujoCascadaPresenter>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ConcursosFragmentPresenter`() {
        val view = mockk<ConcursosFragmentContract.View>()
        get<ConcursosFragmentContract.Presenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para NovedadesPresenter`() {
        val view = mockk<NovedadesView>()
        get<NovedadesPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DocumentosPresenter`() {
        val view = mockk<DocumentoView>()
        get<DocumentosPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para HerramientaDigitalPresenter`() {
        val view = mockk<HerramientaDigitalContract.View>()
        get<HerramientaDigitalContract.Presenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipsPresenter`() {
        val view = mockk<TipsContract.View>()
        get<TipsContract.Presenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipsProgramaNuevasPresenter`() {
        val view = mockk<TipsContract.View>()
        get<TipsProgramaNuevasPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para VentaGananciaPresenter`() {
        val view = mockk<VentaGananciaContract.View>()
        get<VentaGananciaContract.Presenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para VentaGananciaTipsPresenter`() {
        val view = mockk<VentaGananciaTipsView>()
        get<VentaGananciaTipsPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ProgresoCaminoBrillantePresenter`() {
        val view = mockk<ProgresoCaminoBrillanteView>()
        get<ProgresoCaminoBrillantePresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PlanificacionPresenter`() {
        val view = mockk<PlanificacionView>()
        get<PlanificacionPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipsVideoPresenter`() {
        get<TipsPresenter>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MostrarReconocimientoPresenter`() {
        val view = mockk<MostrarReconocimientoView>()
        get<MostrarReconocimientoPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ReprogramarPresenter`() {
        get<ReprogramarPresenter>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RegistrarPresenter`() {
        get<RegistrarPresenter>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para RddPresenter`() {
        get<RddPresenter>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MapaPresenter`() {
        val view = mockk<MapaBaseView>()
        get<MapaPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PlanificarRapidoPresenter`() {
        get<PlanificarRapidoPresenter>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PersonasEnPlanPresenter`() {
        get<PersonasEnPlanPresenter>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PlanificarPresenter`() {
        get<PlanificarPresenter>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GeolocationPresenter`() {
        val view = mockk<GeolocationView>()
        get<GeolocationPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AgregarEventoPresenter`() {
        get<AgregarEventoPresenter>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DetalleEventoPresenter`() {
        val view = mockk<DetalleEventoView>()
        get<DetalleEventoPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para EliminarPresenter`() {
        val view = mockk<EliminarView>()
        get<EliminarPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para BarraNavegacionPresenter`() {
        val view = mockk<BarraNavegacionView>()
        get<BarraNavegacionPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AdicionarVisitaPresenter`() {
        val view = mockk<AdicionarVisitaView>()
        get<AdicionarVisitaPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DesempenioGrGzPresenter`() {
        val view = mockk<DesempenioGrGzView>()
        get<DesempenioGrGzPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MetaPersonalPresenter`() {
        val view = mockk<MetaPersonalView>()
        get<MetaPersonalPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CampaniaACampaniaPresenter`() {
        get<CampaniaACampaniaPresenter>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para AvanceHabilidadPresenter`() {
        val view = mockk<AvanceHabilidadView>()
        get<AvanceHabilidadPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FocoPresenter profile`() {
        get<FocoPresenterProfile>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GraficosGrPresenter`() {
        val view = mockk<GraficosGrView>()
        get<GraficosGrPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GraficoTopBottomPresenter`() {
        val view = mockk<GraficoTopBottomView>()
        get<GraficoTopBottomPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GraficoResumenRegionPresenter`() {
        val view = mockk<GraficoResumenView>()
        get<GraficoResumenRegionPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ProductivasU3CPresenter`() {
        val view = mockk<ProductivasU3CView>()
        get<ProductivasU3CPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para InformacionHistoricaRegionPresenter`() {
        get<InformacionHistoricaRegionPresenter>().shouldNotBeNull()
    }

}
