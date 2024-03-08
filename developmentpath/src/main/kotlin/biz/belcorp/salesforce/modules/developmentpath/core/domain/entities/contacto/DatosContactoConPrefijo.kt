package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.contacto

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico

class DatosContactoConPrefijo(
    val datosContacto: DatosContacto,
    val directorioConPrefijo: DirectorioTelefonico
)
