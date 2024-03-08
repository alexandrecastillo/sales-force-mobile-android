package biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.view

import biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.model.ReconocimientoModel

interface ReconocimientoASuperiorDetalleView {

    fun pintarReconocida(model: ReconocimientoModel)

    fun pintarNoReconocida(model: ReconocimientoModel)

    fun alGrabarseReconocimiento(nombre: String)
}
