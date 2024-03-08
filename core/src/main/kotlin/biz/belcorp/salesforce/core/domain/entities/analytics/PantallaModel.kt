package biz.belcorp.salesforce.core.domain.entities.analytics

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.hardware.BuildVariant
import biz.belcorp.salesforce.core.domain.entities.hardware.NetworkStatus
import biz.belcorp.salesforce.core.domain.entities.session.Sesion

class PantallaModel(
    val campania: Campania,
    val sesion: Sesion,
    val ambiente: BuildVariant,
    val estadoConexion: NetworkStatus,
    val codigoUsuario: String,
    val screenName: String,
    val codgioPersona: String
)
