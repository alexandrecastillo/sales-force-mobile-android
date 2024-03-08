package biz.belcorp.salesforce.modules.postulants.core.data.repository.uneteconfiguracion

import biz.belcorp.salesforce.core.entities.sql.unete.ConfiguracionEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Configuracion

class ConfiguracionEntityDataMapper : Mapper<Configuracion, ConfiguracionEntity>() {

    override fun map(value: Configuracion): ConfiguracionEntity {
        throw UnsupportedOperationException()
    }

    override fun reverseMap(value: ConfiguracionEntity): Configuracion {

        val entity = Configuracion()
        entity.uuid = value.uuid
        entity.pais = value.pais
        entity.rol = value.rol
        entity.edicionpaso1 = value.edicionPaso1
        entity.edicionpaso2 = value.edicionPaso2
        entity.edicionpaso3 = value.edicionPaso3
        entity.edicionpaso4 = value.edicionPaso4
        entity.edicionpaso5 = value.edicionPaso5
        entity.validarburo = value.validarBuro
        entity.rechazarEnEvaluacion = value.rechazarEnEvaluacion
        entity.rechazarPreAprobada = value.rechazarPreAprobada
        entity.capturarxypaso2 = value.capturarXYPaso2
        entity.autocompletardireccion = value.autoCompletarDireccion
        entity.validarMail = value.validarMail
        entity.validarCelular = value.validarCelular

        return entity
    }

}
