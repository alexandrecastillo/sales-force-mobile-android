package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class CabeceraViewModel(
    val periodoCampania: Campania.Periodo?,
    val nombreCortoCampania: String,
    val iniciales: String,
    val planId: Long?,
    val rol: Rol?,
    val titulo: String,
    val esDuenia: Boolean,
    val sesion: Sesion
)
