package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades

class DetalleHabilidadesAcompaniamiento(
    val habilidad: Habilidad,
    val detalleLista: List<String>,
    val comportamientoLista: List<String>
)
