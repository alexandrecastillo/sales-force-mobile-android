package biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.mappers

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.postulants.core.data.entities.unete.EvaluacionBuroMXEntity
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.EvaluacionBuroMX

class EvaluacionBuroDataMapper(val mapper: PostulanteEntityDataMapper) :
    Mapper<EvaluacionBuroMX, EvaluacionBuroMXEntity>() {

    override fun map(value: EvaluacionBuroMX): EvaluacionBuroMXEntity {
        val evaluacionBuroMXEntity = EvaluacionBuroMXEntity()

        value.postulante?.let {
            evaluacionBuroMXEntity.postulante = mapper.map(it)
        }
        evaluacionBuroMXEntity.fechaNacimiento = value.fechaNacimiento
        evaluacionBuroMXEntity.estado = value.estado
        evaluacionBuroMXEntity.ciudad = value.ciudad
        evaluacionBuroMXEntity.direccion = value.direccion
        evaluacionBuroMXEntity.tarjetaDeCredito = value.tarjetaDeCredito
        evaluacionBuroMXEntity.creditoHipotecario = value.creditoHipotecario
        evaluacionBuroMXEntity.creditoAutomotriz = value.creditoAutomotriz

        return evaluacionBuroMXEntity
    }

    override fun reverseMap(value: EvaluacionBuroMXEntity): EvaluacionBuroMX {
        val evaluacionBuroMX = EvaluacionBuroMX()

        value.postulante?.let {
            evaluacionBuroMX.postulante = mapper.reverseMap(it)
        }
        evaluacionBuroMX.fechaNacimiento = value.fechaNacimiento
        evaluacionBuroMX.estado = value.estado
        evaluacionBuroMX.ciudad = value.ciudad
        evaluacionBuroMX.direccion = value.direccion
        evaluacionBuroMX.tarjetaDeCredito = value.tarjetaDeCredito
        evaluacionBuroMX.creditoHipotecario = value.creditoHipotecario
        evaluacionBuroMX.creditoAutomotriz = value.creditoAutomotriz

        return evaluacionBuroMX
    }
}
