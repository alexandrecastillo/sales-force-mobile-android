package biz.belcorp.salesforce.modules.inspires.mapper

import biz.belcorp.salesforce.modules.inspires.core.domain.entities.*
import biz.belcorp.salesforce.modules.inspires.model.*

class InspireModelDataMapper {

    fun transformIndicatorParcel(entity: InspiraIndicador): IndicadorInspiraModel {
        val model = IndicadorInspiraModel()
        model.id = entity.id!!
        model.campania = entity.campania!!
        model.ranking = entity.ranking!!
        model.destino = entity.destino
        model.nivel = entity.nivel
        model.puntaje = entity.puntaje
        model.puntajeMax = entity.puntajeMax
        model.nombreSE = entity.nombreSE
        model.campaniaInicioPrograma = entity.campaniaInicioPrograma!!
        model.campaniaFinPrograma = entity.campaniaFinPrograma!!
        model.activa = entity.activa!!
        model.topeRanking = entity.topeRanking!!
        model.imagenDestino = entity.imagenDestino
        return model
    }

    fun transformIndicator(entity: InspiraIndicador): InspireIndicatorModel {
        return entity.let {
            InspireIndicatorModel(
                it.id!!,
                it.campania!!,
                it.ranking!!,
                it.destino,
                it.nivel,
                it.puntaje,
                it.puntajeMax,
                it.nombreSE,
                it.campaniaInicioPrograma!!,
                it.campaniaFinPrograma!!,
                it.activa!!,
                it.topeRanking!!,
                it.imagenDestino
            )
        }
    }

    fun transformRanking(domain: InspiraRanking): InspiraRankingModel {
        return InspiraRankingModel(
            domain.id!!,
            domain.puesto!!,
            domain.usuario,
            domain.puntaje!!,
            domain.flagStatus,
            domain.isFlagEsUsuario,
            domain.bloque!!
        )
    }

    fun transformRanking(list: List<InspiraRanking>): List<InspiraRankingModel> {
        val result = arrayListOf<InspiraRankingModel>()
        list.forEach { result.add(transformRanking(it)) }
        return result
    }

    fun transformAdvance(list: List<InspiraAvances>): List<InspiraAvancesModel> {
        val result = arrayListOf<InspiraAvancesModel>()

        list.forEach {
            result.add(InspiraAvancesModel(
                it.campania!!,
                it.puntosAcumulados!!,
                it.statusNivel,
                it.porcRetencionActivas,
                it.actividad,
                it.rangoActividad,
                it.porcMontoPedido,
                it.porcPAV,
                it.capitalizacion,
                it.retencion))
        }

        return result
    }

    fun transformConditions(list: List<InspiraCondiciones>): List<InspiraCondicionesModel> {
        val result = arrayListOf<InspiraCondicionesModel>()

        list.forEach {
            result.add(InspiraCondicionesModel(
                it.codigo,
                it.condicion,
                it.descripcion))
        }

        return result
    }

    fun transformConditionsLegend(list: List<InspiraCondicionesLeyenda>): List<InspiraCondicionesLeyendaModel> {
        val result = arrayListOf<InspiraCondicionesLeyendaModel>()

        list.forEach {
            result.add(InspiraCondicionesLeyendaModel(
                it.codigo,
                it.titulo,
                it.descripcion,
                it.nota))
        }

        return result
    }

    fun transformConditionsLegendDetail(list: List<InspiraCondicionesLeyendaDetalle>): List<InspiraCondicionesLeyendaDetalleModel> {
        val result = arrayListOf<InspiraCondicionesLeyendaDetalleModel>()

        list.forEach {
            result.add(InspiraCondicionesLeyendaDetalleModel(
                it.codigo,
                it.rango,
                it.puntos))
        }

        return result
    }

    fun transformAdvancePeriod(list: List<InspiraAvancesPeriodo>): List<InspiraAvancePeriodoModel> {
        val result = arrayListOf<InspiraAvancePeriodoModel>()

        list.forEach {
            result.add(InspiraAvancePeriodoModel(
                it.codigoPeriodo!!,
                it.periodo!!,
                it.campaniaFinPeriodo!!,
                it.puntaje!!
            ))
        }

        return result
    }
}
