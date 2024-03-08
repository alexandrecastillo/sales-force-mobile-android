package biz.belcorp.salesforce.core.domain.entities.zonificacion

interface ResponsableUA {
    val primerNombre: String?
    val primerApellido: String?
    val iniciales: String?
    val unidadAdministrativa: UnidadAdministrativa
}
