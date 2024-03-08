package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.headercontainer

import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section.PrepararseEsClaveModel

interface DetallePrepararseEsClaveView {
    fun mostrarElementos(elementos: List<PrepararseEsClaveModel>)
    fun establecerOpcionSeleccionada()
    fun moverAOpcionSeleccionada()
}
