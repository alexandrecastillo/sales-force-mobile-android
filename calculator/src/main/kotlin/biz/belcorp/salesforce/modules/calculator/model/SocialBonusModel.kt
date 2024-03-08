package biz.belcorp.salesforce.modules.calculator.model

data class SocialBonusModel (val codigoTipoBono: String?,
                             val descripcionTipoBono: String?,
                             val indicadorActivo: String?,
                             val codigoTipoMedicion: String?,
                             val indicadorTipoBono: String?,
                             val montoUnitarioBono: Double?,
                             val ingresaCantidad: Boolean,
                             val codConsulta: String?) {
    var cantidad: Int? = null
    var mensaje: String? = null
    var cantidadAlt: Int? = null
    var mensajeAlt: String? = null

    var cantidadIngresada: Int = 0
    var cantidadIngresadaAlt: Int = 0
}
