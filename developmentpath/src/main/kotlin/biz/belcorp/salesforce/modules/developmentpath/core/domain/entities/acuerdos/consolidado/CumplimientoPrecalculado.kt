package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado

import biz.belcorp.salesforce.core.domain.entities.campania.Campania

class CumplimientoPrecalculado(
    val campania: Campania,
    val estado: CumplimientoCampania.Estado
)
