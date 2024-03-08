package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import java.util.*

class ModeloCreacionVisita(
    val planId: Long,
    val persona: PersonaRdd,
    val tipsId: Long,
    val esAdicional: Boolean,
    val fechaPlanificacion: Date,
    val fechaReprogramacion: Date = fechaPlanificacion
)
