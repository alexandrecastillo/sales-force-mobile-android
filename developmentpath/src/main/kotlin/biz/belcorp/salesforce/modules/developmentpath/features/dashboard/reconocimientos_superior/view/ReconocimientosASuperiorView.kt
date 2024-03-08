package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.view

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.se.model.ReconocimientoModel

interface ReconocimientosASuperiorView {

    fun pintarNombre(nombre: String)

    fun pintarUnidadAdministrativa(ua: String)

    fun pintarReconocimientos(reconocimientos: List<ReconocimientoModel>)

    fun pintarPlaceholder()
}
