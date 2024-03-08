package biz.belcorp.salesforce.core.domain.entities.socialbonus

data class SocialBonus (val codigoTipoBono: String?,
                                 val descripcionTipoBono: String?,
                                 val indicadorActivo: String?,
                                 val codigoTipoMedicion: String?,
                                 val indicadorTipoBono: String?,
                                 val montoUnitarioBono: Double?,
                                 val ingresaCantidad: Boolean,
                                 val codConsulta: String?)
