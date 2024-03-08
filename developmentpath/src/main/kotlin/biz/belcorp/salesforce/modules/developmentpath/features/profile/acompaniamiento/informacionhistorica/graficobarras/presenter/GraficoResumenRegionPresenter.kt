package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGraficoInferior
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.HistoricoRegion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.DefinirGraficoInferior
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.historicos.HistoricaRegionBaseUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion.focotrabajo.ProveedorIconoFocosTrabajo
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.GraficoHistoricoBase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.GraficoHistoricoConCuadro
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.GraficoHistoricoSinCuadro
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.HistoricoMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view.GraficoResumenView
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class GraficoResumenRegionPresenter(
    private val view: GraficoResumenView,
    private val graficosGrUseCase: HistoricaRegionBaseUseCase,
    private val definirGraficoInferior: DefinirGraficoInferior,
    private val mapper: HistoricoMapper
) {
    
    val proveedorIcono by lazy { ProveedorIconoFocosTrabajo() }

    lateinit var tipoGrafico: TipoGrafico

    fun recuperar(personaId: Long, rol: Rol, tipoGrafico: TipoGrafico) {
        this.tipoGrafico = tipoGrafico
        elegirGraficoInferior(tipoGrafico)
        recuperarInformacion(personaId, rol, tipoGrafico)
    }

    private fun recuperarInformacion(personaId: Long, rol: Rol, tipoGrafico: TipoGrafico) {
        val params = HistoricaRegionBaseUseCase.Params(
            gerenteId = personaId,
            rol = rol,
            tipoGrafico = tipoGrafico,
            observer = HistoricoObserver()
        )
        graficosGrUseCase.ejecutar(params)
    }

    private fun elegirGraficoInferior(tipoGrafico: TipoGrafico) {
        when (definirGraficoInferior.definir(tipoGrafico)) {
            TipoGraficoInferior.TopBottom -> view.mostrarGraficoTopBottom()
            TipoGraficoInferior.CuadroProductivas -> view.mostrarGraficoCuadroProductivas()
        }
    }

    fun procesarDataParaGrafico(valores: HistoricoRegion) {
        doAsync {
            val datosGrafico = mapper.map(valores)
            uiThread {
                verificarDisponibilidadCuadro(datosGrafico)
            }
        }
    }

    private fun verificarDisponibilidadCuadro(datosGrafico: GraficoHistoricoBase) {
        when (datosGrafico) {
            is GraficoHistoricoSinCuadro -> pintarSinCuadro(datosGrafico)
            is GraficoHistoricoConCuadro -> pintarConCuadro(datosGrafico)
        }
    }

    private fun pintarSinCuadro(datosGrafico: GraficoHistoricoSinCuadro) {
        pintarBase(datosGrafico)
        view.pintarGraficoSinCuadro(datosGrafico)
    }

    private fun pintarConCuadro(datosGrafico: GraficoHistoricoConCuadro) {
        pintarBase(datosGrafico)
        view.pintarValoresEnCuadro(datosGrafico.valoresXCampania)
        view.pintarTituloEnCuadro(datosGrafico.tituloGrafico)
        view.pintarGraficoConCuadro(datosGrafico)
    }

    private fun pintarBase(datosGrafico: GraficoHistoricoBase) {
        view.pintarTitulo(datosGrafico.titulo)
        view.pintarTituloGrafico(datosGrafico.tituloGrafico)
        view.pintarSubtitulo(datosGrafico.subtitulo)
        view.dibujarIcono(proveedorIcono.obtenerIconoPorTipoGrafico(tipoGrafico.codigo))
        view.pintarBackgroundColor(datosGrafico.colorBarras)
    }

    inner class HistoricoObserver : BaseSingleObserver<HistoricoRegion>() {

        override fun onError(e: Throwable) = e.printStackTrace()

        override fun onSuccess(t: HistoricoRegion) {
            procesarDataParaGrafico(t)
        }
    }
}
