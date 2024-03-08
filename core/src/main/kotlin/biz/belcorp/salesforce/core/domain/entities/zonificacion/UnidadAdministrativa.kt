package biz.belcorp.salesforce.core.domain.entities.zonificacion


import biz.belcorp.salesforce.core.domain.entities.campania.Campania

abstract class UnidadAdministrativa(
    val codigo: String,
    val campania: Campania
) {

    abstract val responsableUA: ResponsableUA?

    abstract val padre: UnidadAdministrativa?

    abstract val llave: LlaveUA

    val coberturada get() = responsableUA != null
}
