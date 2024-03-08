package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.view

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.model.SeccionAvanceModel

interface AvanceView {

    fun cargarSeccionesSE(modelos: List<SeccionAvanceModel>)

    fun mostrarAreaReconocimientos()

    fun ocultarAreaReconocimientos()
}
