package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.sync

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.acompaniamiento.CumplimientoConsolidadoEntity
import biz.belcorp.salesforce.core.entities.sql.anotaciones.AnotacionEntity
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoDetalleEntity
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoDetallePorcentajeEntity
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoEntity
import biz.belcorp.salesforce.core.entities.sql.datos.*
import biz.belcorp.salesforce.core.entities.sql.directorio.UsuarioPadreSesionEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.CronogramaEventosEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddXUaEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.TipoEventoRddEntity
import biz.belcorp.salesforce.core.entities.sql.filtros.TipoMetaEntity
import biz.belcorp.salesforce.core.entities.sql.focos.*
import biz.belcorp.salesforce.core.entities.sql.graficos.ConfiguracionGraficosGrEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.*
import biz.belcorp.salesforce.core.entities.sql.horariovisita.HorarioVisitaConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.horariovisita.HorarioVisitaEntity
import biz.belcorp.salesforce.core.entities.sql.marcas.OtraMarcaDetalleEntity
import biz.belcorp.salesforce.core.entities.sql.marcas.OtraMarcaEntity
import biz.belcorp.salesforce.core.entities.sql.novedades.NovedadesEntity
import biz.belcorp.salesforce.core.entities.sql.path.*
import biz.belcorp.salesforce.core.entities.sql.plan.*
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.NivelActualCaminoBrillanteEntity
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.NivelesCaminoBrillanteEntity
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipsDesarrolloEntity
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipsNegocioEntity
import biz.belcorp.salesforce.core.entities.sql.tips.rdd.DetalleTipsVisitaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.tips.rdd.TipsVisitaRDDEntity
import com.google.gson.annotations.SerializedName

class SyncResponse : BaseResponse() {

    @SerializedName("resultado")
    var resultado: Resultado? = null

    class Resultado {

        companion object {

            const val NONE = "none"
            const val PARCIAL = "parcial"
            const val FULL = "full"

        }

        @SerializedName("tipo")
        var tipo: String? = null

        @SerializedName("motivo")
        var motivo: String? = null

        @SerializedName("excepcion")
        var excepcion: String? = null

        @SerializedName("data")
        var data: Data? = null

        @SerializedName("fechaServidor")
        var fechaServidor: String? = ""

        class Data {

            @SerializedName("configuracionRDDList")
            var configuracionRDD: List<ConfiguracionRDDEntity> = emptyList()

            @SerializedName("indicadorTotalRutaDesarrolloList")
            var planRutaRDD: List<PlanRutaRDDEntity> = emptyList()

            @SerializedName("indicadorTotalRutaDesarrolloDetallesList")
            var detallePlanRutaRDD: List<DetallePlanRutaRDDEntity> = emptyList()

            @SerializedName("indicadorObservacionesVisitaRDDList")
            var indicadorObservacionesVisitaRDDList: List<ObservacionVisitaRDDEntity> = emptyList()

            @SerializedName("indicadorTotalAcuerdos")
            val acuerdos: List<AcuerdoEntity> = emptyList()

            @SerializedName("listaTipsVisitaRDD")
            var listaTipsVisitaRDD: List<TipsVisitaRDDEntity> = emptyList()

            @SerializedName("listaDetalleTipsVisitaRDD")
            var listaDetalleTipsVisitaRDD: List<DetalleTipsVisitaRDDEntity> = emptyList()

            @SerializedName("listaVisitaXFechaRDD")
            var listaVisitaXFechaRDD: List<VisitaXFechaRDDEntity> = emptyList()

            @SerializedName("listadoNotasConsultora")
            var listadoNotasConsultora: List<ListadoNotasEntity> = emptyList()

            @SerializedName("listadoMetasConsultoras")
            var listadoMetasConsultoras: List<MetasEntity> = emptyList()

            @SerializedName("listadoTipoMeta")
            var listadoTipoMeta: List<TipoMetaEntity> = emptyList()

            @SerializedName("listTipoEventoRDD")
            var tiposEventoRdd: List<TipoEventoRddEntity> = emptyList()

            @SerializedName("listEventoRDD")
            var eventosRdd: List<EventoRddEntity> = emptyList()

            @SerializedName("listEventoRDDPorUA")
            var eventosXUaRdd: List<EventoRddXUaEntity> = emptyList()

            @SerializedName("configuracionGraficosGr")
            var configuracionGraficosGrEntity: List<ConfiguracionGraficosGrEntity> = emptyList()

            @SerializedName("listIntencionPedido")
            var intencionPedidos: List<IntencionPedidoDbModel> = emptyList()

            @SerializedName("listaMarcaConsultora")
            var otrasMarcas: List<OtraMarcaEntity> = emptyList()

            @SerializedName("listaMarcaVentaConsultora")
            var otrasMarcasDetalle: List<OtraMarcaDetalleEntity> = emptyList()

            @SerializedName("listaCumplimientoConsolidado")
            var cumplimientosAcuerdos: List<CumplimientoConsolidadoEntity> = emptyList()

            @SerializedName("listaTipsDesarrollo")
            var tipsDesarrollo: List<TipsDesarrolloEntity> = emptyList()

            @SerializedName("listaDetalleTipsDesarrollo")
            var tipsNegocio: List<TipsNegocioEntity> = emptyList()

            @SerializedName("listaNovedadesConsultora")
            var novedades: List<NovedadesEntity> = emptyList()

            @SerializedName("listaComportamientos")
            var comportamientos: List<ComportamientoEntity> = emptyList()

            @SerializedName("listaComportamientosDetalleRDD")
            var comportamientosDetalleRDD: List<ComportamientoDetalleEntity> = emptyList()

            @SerializedName("listaComportamientosDetallePorcentaje")
            var comportamientoDetallePorcentaje: List<ComportamientoDetallePorcentajeEntity> =
                emptyList()

            @SerializedName("datosParaPlanificacion")
            var datosParaPlanificacion: List<ReporteEntity> = emptyList()

            @SerializedName("cronogramaEventos")
            var cronogramaEventos: List<CronogramaEventosEntity> = emptyList()

            @SerializedName("fechaNoHabilFacturacion")
            var fechaNoHabilFacturacion: List<FechaNoHabilFacturacionEntity> = emptyList()

            @SerializedName("listaTituloFocoCampania")
            var listaTituloFocoCampania: List<TituloFocoCampaniaEntity> = emptyList()

            @SerializedName("listaDetalleFocoCampania")
            var listaDetalleFocoCampania: List<DetalleFocoCompaniaEntity> = emptyList()

            @SerializedName("listaFocosRDD")
            var listaFocosRDD: List<FocoRddEntity> = emptyList()

            @SerializedName("listaFocosDetalleSE_RDD")
            var listaFocosDetalleSE: List<FocoDetalleSERddEntity> = emptyList()

            @SerializedName("listaReconocimientos")
            var reconocimientos: List<ReconocimientoEntity> = emptyList()

            @SerializedName("reconocimientoHabilidadesRDD")
            var reconocimientoHabilidadesRDD: List<ReconocimientoHabilidadesRDDEntity> = emptyList()

            @SerializedName("habilidadesRDD")
            var habilidadesMaestras: List<HabilidadEntity> = emptyList()

            @SerializedName("habilidadesAsignadasRDD")
            var habilidadesAsignadasRDD: List<HabilidadesAsignadasRDDEntity> = emptyList()

            @SerializedName("focosDetalleRDD")
            var focosRDD: List<FocoDetalleRddEntity> = emptyList()

            @SerializedName("detalleHabilidadesLiderazgoRDD")
            var detalleHabilidadesLiderazgoRDD: List<DetalleHabilidadesLiderazgoRDDEntity> =
                emptyList()

            @SerializedName("desarrolloHabilidadesRDD")
            var desarrolloHabilidadesRDD: List<DesarrolloHabilidadEntity> = emptyList()

            @SerializedName("desempenio")
            val desempenio: List<DesempenioEntity> = emptyList()

            @SerializedName("anotaciones")
            val anotaciones: List<AnotacionEntity> = emptyList()

            @SerializedName("cobranzaYGanancia")
            val cobranzaYGanancia: List<CobranzaYGananciaEntity> = emptyList()

            @SerializedName("datoPadreUsuarioLogueado")
            var datoPadreUsuarioLogueado: UsuarioPadreSesionEntity? = null

            @SerializedName("puntajeReconocimientos")
            var puntajeReconocimientos: List<PuntajeReconocimientoEntity> = emptyList()

            @SerializedName("listaNivelConsultora")
            var nivelesCaminoBrillante: List<NivelesCaminoBrillanteEntity> = emptyList()

            @SerializedName("listaNivelActualConsultora")
            var nivelActualCaminoBrillante: List<NivelActualCaminoBrillanteEntity> = emptyList()

            @SerializedName("listaHorarioVisita")
            var listaHorarioVisita: List<HorarioVisitaEntity> = emptyList()

            @SerializedName("listaHorarioVisitaConsultora")
            var listaHorarioVisitaConsultora: List<HorarioVisitaConsultoraEntity> = emptyList()
        }

    }

}
