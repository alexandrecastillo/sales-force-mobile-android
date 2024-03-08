package biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.mappers

import biz.belcorp.salesforce.core.entities.sql.unete.detalle.DetalleIndicadorUneteEntity
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.DetalleIndicadorUnete

class DetalleIndicadorEntityDataMapper {

    fun parseDetalleIndicadorUneteEntityList(entityList: List<DetalleIndicadorUneteEntity>) =
        entityList.map { parseDetalleIndicadorEntityUnete(it) }

    private fun parseDetalleIndicadorEntityUnete(indicador: DetalleIndicadorUneteEntity) =
        DetalleIndicadorUnete().apply {
            campaniaActual = indicador.campaniaActual
            zona = indicador.zona
            seccion = indicador.seccion
            nombre = indicador.nombre
            enEvaluacion = indicador.enEvaluacion
            preAprobadas = indicador.preAprobadas
            aprobadas = indicador.aprobadas
            rechazadas = indicador.rechazadas
            conversion = indicador.conversion
            diasEnEspera = indicador.diasEnEspera
            ingresosAnticipados = indicador.ingresosAnticipados
            preInscritas = indicador.preInscritas
            regularizarDocumento = indicador.regularizarDocumento
            proactivoFinalizar = indicador.proactivoFinalizar
            proactivoFinalizados = indicador.proactivoFinalizados
            proactivoPreAprobados = indicador.proactivoPreAprobados
        }

    fun parseDetalleIndicadorUneteList(entityList: List<DetalleIndicadorUnete>) =
        entityList.map { parseDetalleIndicadorUnete(it) }

    private fun parseDetalleIndicadorUnete(indicador: DetalleIndicadorUnete) =
        DetalleIndicadorUneteEntity().apply {
            campaniaActual = indicador.campaniaActual
            zona = indicador.zona
            seccion = indicador.seccion
            nombre = indicador.nombre
            enEvaluacion = indicador.enEvaluacion
            preAprobadas = indicador.preAprobadas
            aprobadas = indicador.aprobadas
            rechazadas = indicador.rechazadas
            conversion = indicador.conversion
            diasEnEspera = indicador.diasEnEspera
            ingresosAnticipados = indicador.ingresosAnticipados
            preInscritas = indicador.preInscritas
            regularizarDocumento = indicador.regularizarDocumento
            proactivoFinalizar = indicador.proactivoFinalizar
            proactivoFinalizados = indicador.proactivoFinalizados
            proactivoPreAprobados = indicador.proactivoPreAprobados
        }

}
