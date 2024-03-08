package biz.belcorp.salesforce.modules.postulants.features.mapper

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Configuracion
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteConfiguracionModel

class UneteConfiguracionModelDataMapper : Mapper<Configuracion, UneteConfiguracionModel>() {
    override fun map(value: Configuracion): UneteConfiguracionModel {
        val model = UneteConfiguracionModel()

        model.uuid = value.uuid
        model.pais = value.pais
        model.rol = value.rol

        model.edicionpaso1 = value.edicionpaso1
        model.edicionpaso2 = value.edicionpaso2
        model.edicionpaso3 = value.edicionpaso3
        model.edicionpaso4 = value.edicionpaso4
        model.edicionpaso5 = value.edicionpaso5

        model.validarburo = value.validarburo
        model.rechazarEnEvaluacion = value.rechazarEnEvaluacion
        model.rechazarPreAprobada = value.rechazarPreAprobada

        model.autocompletardireccion = value.autocompletardireccion
        model.capturarxypaso2 = value.capturarxypaso2

        model.validarMail = value.validarMail
        model.validarCelular = value.validarCelular

        return model
    }

    override fun reverseMap(value: UneteConfiguracionModel): Configuracion {
        val model = Configuracion()

        model.uuid = value.uuid
        model.pais = value.pais
        model.rol = value.rol

        model.edicionpaso1 = value.edicionpaso1
        model.edicionpaso2 = value.edicionpaso2
        model.edicionpaso3 = value.edicionpaso3
        model.edicionpaso4 = value.edicionpaso4
        model.edicionpaso5 = value.edicionpaso5

        model.validarburo = value.validarburo
        model.rechazarEnEvaluacion = value.rechazarEnEvaluacion
        model.rechazarPreAprobada = value.rechazarPreAprobada

        model.autocompletardireccion = value.autocompletardireccion
        model.capturarxypaso2 = value.capturarxypaso2

        model.validarMail = value.validarMail
        model.validarCelular = value.validarCelular

        return model
    }

}
