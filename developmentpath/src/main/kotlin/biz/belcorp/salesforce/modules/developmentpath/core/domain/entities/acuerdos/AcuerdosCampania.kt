package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import java.util.*

class AcuerdosCampania(
    val id: Long,
    val zona: String,
    val campania: String,
    val nombreCorto: String,
    val contenido: String,
    val fecha: Date
) {
    lateinit var campaniaActual: Campania
}
