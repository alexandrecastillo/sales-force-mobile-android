package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.consolidado

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado.CumplimientoCampania

data class CumplimientoModel(
    val campania: String,
    val estado: CumplimientoCampania.Estado
)
