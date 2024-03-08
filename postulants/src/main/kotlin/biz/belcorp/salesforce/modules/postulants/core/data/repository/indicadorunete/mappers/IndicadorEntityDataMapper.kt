package biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.mappers

import biz.belcorp.salesforce.core.entities.sql.unete.IndicadorUneteEntity
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.IndicadorUnete

class IndicadorEntityDataMapper {
    fun parseListaIndicadorUnete(list: List<IndicadorUneteEntity>?): List<IndicadorUnete>? {
        var indicadorList: MutableList<IndicadorUnete>? = null

        if (list == null) return indicadorList

        indicadorList = mutableListOf()

        list.forEach {
            indicadorList.add(parseIndicadorUnete(it))
        }

        return indicadorList
    }

    fun parseIndicadorUnete(entity: IndicadorUneteEntity?): IndicadorUnete {
        val indicador = IndicadorUnete()

        if (entity == null) return indicador

        indicador.campaniaActual = entity.campaniaActual
        indicador.seccion = entity.seccion
        indicador.enEvaluacion = entity.enEvaluacion
        indicador.preAprobadas = entity.preAprobadas
        indicador.aprobadas = entity.aprobadas
        indicador.conversion = entity.conversion
        indicador.diasEnEspera = entity.diasEnEspera
        indicador.ingresosAnticipados = entity.ingresosAnticipados
        indicador.preInscritas = entity.preInscritas
        indicador.regularizarDocumento = entity.regularizarDocumento

        return indicador
    }

    fun parseListaIndicadorUneteEntity(list: List<IndicadorUnete>?): List<IndicadorUneteEntity>? {
        var indicadorList: MutableList<IndicadorUneteEntity>? = null

        if (list == null) return indicadorList

        indicadorList = mutableListOf()

        list.forEach {
            indicadorList.add(parseIndicadorUneteEntity(it)!!)
        }

        return indicadorList
    }

    private fun parseIndicadorUneteEntity(model: IndicadorUnete?): IndicadorUneteEntity? {
        var indicador: IndicadorUneteEntity? = null

        if (model == null) return indicador

        indicador = IndicadorUneteEntity()

        indicador.campaniaActual = model.campaniaActual
        indicador.seccion = model.seccion
        indicador.enEvaluacion = model.enEvaluacion
        indicador.preAprobadas = model.preAprobadas
        indicador.aprobadas = model.aprobadas
        indicador.conversion = model.conversion
        indicador.diasEnEspera = model.diasEnEspera
        indicador.ingresosAnticipados = model.ingresosAnticipados
        indicador.preInscritas = model.preInscritas
        indicador.regularizarDocumento = model.regularizarDocumento

        return indicador
    }


}
