package biz.belcorp.salesforce.modules.postulants.features.entities

class UneteConfiguracionModel {

    var uuid: String = java.util.UUID.randomUUID().toString()
    var pais: String? = null
    var rol: String? = null

    var edicionpaso1: Boolean = false
    var edicionpaso2: Boolean = false
    var edicionpaso3: Boolean = false
    var edicionpaso4: Boolean = false
    var edicionpaso5: Boolean = false

    var validarburo: Boolean = false
    var rechazarPreAprobada: Boolean = false
    var rechazarEnEvaluacion: Boolean = false

    var capturarxypaso2: Boolean = false
    var autocompletardireccion: Boolean = false

    var validarMail: Boolean = false
    var validarCelular: Boolean = false

}
