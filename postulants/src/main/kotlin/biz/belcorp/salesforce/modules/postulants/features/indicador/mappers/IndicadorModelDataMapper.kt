package biz.belcorp.salesforce.modules.postulants.features.indicador.mappers

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.IndicadorUnete
import biz.belcorp.salesforce.modules.postulants.features.indicador.entities.IndicadorUneteModel

class IndicadorModelDataMapper {

    fun transformIndicadorUnete(entity: IndicadorUnete?): IndicadorUneteModel? {
        var result: IndicadorUneteModel? = null
        if (entity != null) {
            result = IndicadorUneteModel()
            result.campaniaActual = entity.campaniaActual
            result.enEvaluacion = entity.enEvaluacion
            result.preAprobadas = entity.preAprobadas
            result.aprobadas = entity.aprobadas
            result.rechazadas = entity.rechazadas
            result.conversion = entity.conversion
            result.diasEnEspera = entity.diasEnEspera
            result.ingresosAnticipados = entity.ingresosAnticipados
            result.preInscritas = entity.preInscritas
            result.regularizarDocumento = entity.regularizarDocumento
        }

        return result
    }

}
