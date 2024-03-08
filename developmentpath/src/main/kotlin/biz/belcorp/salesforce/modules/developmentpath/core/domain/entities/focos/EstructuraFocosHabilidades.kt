package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos

class EstructuraFocosHabilidades(
    val verMisFocos: Boolean,
    val verMisHabilidades: Boolean,
    val focosHabilidadesUa: FocosHabilidadesUa
)

sealed class FocosHabilidadesUa

class Secciones : FocosHabilidadesUa()

class Zonas : FocosHabilidadesUa()

class Regiones : FocosHabilidadesUa()
